package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.Progress;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class AppPeopleActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlCustomerGender,mllDecorateProgress,mllCustomerLaiyuan;
    private RelativeLayout mCardNum;
    private EditText mEtCustomerName;//客户姓名
    private EditText mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private EditText et_customer_cardNum;//卡号
    private ImageView scan;//扫描卡号
    private EditText mEtXiaoQu;//小区名称
    private EditText mEditTextCode;
    private EditText mEtDecorateStyle;//装修风格
    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity,rl_yzm;
    private TextView mEtDecorateAddress;//装修地址
    private EditText mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, yzmCode,phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood,activity;
    private RadioGroup mRadioButton;
    private long lastClick;
    private static final int REQUEST_CODE_CITY = 2010;
    private static final int REQUEST_CODE_ACTIVITY = 2011;
    private static final int REQUEST_PROGRESS_ACTIVITY = 2012;
    private static final int REQUEST_CARD_NUM = 2018;
    private Dialog mRequestDialog;
    private TimeCount time;
    private View divder,cardNumLine;
    private boolean isVisiable=false;
    private  List<Progress.DataListBean> mDataListBeen;
    private String progress=null;
    private String cardNum,isUnderLine,isCheck;
    @Override
    public int setLayout() {
        return R.layout.activity_app_people;
    }


    @Override
    public void init() {
        PreferencesUtils.putString(getApplicationContext(),"cardNum",null);
        PreferencesUtils.putString(AppPeopleActivity.this, "sheng_name", "");
        PreferencesUtils.putString(AppPeopleActivity.this, "shi_name", "");
        PreferencesUtils.putString(AppPeopleActivity.this, "qu_name", "");
        mEtCustomerName = (EditText) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (EditText) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle = (EditText) findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mEtXiaoQu= (EditText) findViewById(R.id.et_customer_xiaoqu);
        et_customer_cardNum=findViewById(R.id.et_customer_cardNum);
        scan=findViewById(R.id.get_cardNum);
        mllDecorateProgress= (LinearLayout) findViewById(R.id.ll_DecorateProgress);
        mCardNum=findViewById(R.id.cardNum);
        mEditTextCode= (EditText) findViewById(R.id.et_customer_mobile_code);
        divder=findViewById(R.id.divder);
        cardNumLine=findViewById(R.id.cardNumLine);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        time = new TimeCount(60000, 1000);
        mButton2= (Button) findViewById(R.id.get_mobile_code);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AppPeopleActivity.this, CaptureActivity.class);
                intent.putExtra("type", "4");
                startActivityForResult(intent,REQUEST_CARD_NUM);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phone=mEtCustomerMobile.getText().toString();
                if(Phone.length()>11||!isMobileNO(Phone)){
                    ToastUtils.showShort("手机号格式不对");
                }
                else if(!Phone.equals("")){
                    mRequestDialog.show();
                    String url= BaseUrl.BASE_URL+"customerLoginController.do?method=sendYzm&type=4&phone="+Phone+"&token_id="+token_id;
                    Log.d("yzm",url);
                    OkhttpUtil.okHttpPost(url, new CallBackUtil.CallBackString() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            ToastUtils.showLong(e.toString());
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.d("yzm",response);
                            if(response!=null){
                                try {
                                    Response response1 = JSONUtil.fromJson(response, Response.class);
                                    if(response1.getRet().equals("200")){
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }

                                    }
                                    else {
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }
                                        ToastUtils.showLong(""+response1.getMsg());
                                    }
                                }
                                catch (Exception e){
                                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                        mRequestDialog.dismiss();
                                    }
                                }
                            }
                        }
                    });
                    time.start();
                }
                else {
                    ToastUtils.showShort("手机号不能为空");
                }
            }
        });
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        rl_yzm= (RelativeLayout) findViewById(R.id.yzm);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mRequestDialog.setCancelable(false);
        Intent data=getIntent();
        NewPeople.DataListBean dataListBean= (NewPeople.DataListBean) data.getSerializableExtra("data");
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rl_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AppPeopleActivity.this, ProActivityActivity.class), REQUEST_CODE_ACTIVITY);
            }
        });
        mButton = (Button) findViewById(R.id.btn_submit);
        token_id = PreferencesUtils.getString(this, "token_id");
        mTvIntentionToPurchaseProduct = (EditText) findViewById(R.id.et_intention_to_purchase_product);
        mEtDecorateAddress.setOnClickListener(this);
        mGenderDialog = new GenderDialog(this);
        mLlCustomerGender.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mGenderDialog.setListener(new GenderDialog.OnGenderDialogListener() {
            @Override
            public void onGenderDialogClick(String itemName) {
                mTvCustomerGender.setText(itemName);
            }
        });
        mTvDecorateProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AppPeopleActivity.this,ProgressActivity.class),REQUEST_PROGRESS_ACTIVITY);
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_customer_gender://客户性别
                new ActionSheetDialog(AppPeopleActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        mTvCustomerGender.setText("男");
                                    }
                                })
                        .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        mTvCustomerGender.setText("女");
                                    }
                                })
                        .show();
                break;
            case R.id.tv_customer_address:
                startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_CITY);
                break;
            case R.id.btn_submit:
                username = mEtCustomerName.getText().toString().replace(" ", "");
                phone = mEtCustomerMobile.getText().toString().replace(" ", "");
                if(phone.length()>11||!isMobileNO(phone)){
                    ToastUtils.showShort("手机号格式不对");
                    mButton.setEnabled(true);
                    return;
                }
               if(mTvCustomerGender.getText().toString().replace(" ", "").equals("男")){
                   sex="1";
               }else {
                   sex="2";
               }
                address = mEtDecorateAddress.getText().toString().replace(" ", "");
                xiaoqu=mEtXiaoQu.getText().toString().replace(" ", "");
                yzmCode=mEditTextCode.getText().toString().replace(" ", "");
                cardNum=et_customer_cardNum.getText().toString().replace(" ", "");
                decorationStyle=mEtDecorateStyle.getText().toString().replace(" ", "");
                thinkBuyGood=mTvIntentionToPurchaseProduct.getText().toString().replace(" ", "");
                decorationProgress = mTvDecorateProgress.getText().toString().replace(" ", "");
                activity=mTvActivity.getText().toString();
                String regEx = "^[A-Za-z0-9]+$";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(cardNum);
                boolean b = matcher.matches();
                if((isUnderLine==null||isUnderLine.equals(""))&&(isCheck==null||isCheck.equals(""))){
                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")||yzmCode.equals("")){
                        Toast.makeText(this, "必填项不能为空1", Toast.LENGTH_SHORT).show();
                        mButton.setEnabled(true);
                        return;
                    }
                }
                else if((isUnderLine==null||isUnderLine.equals(""))&&isCheck.equals("0")){
                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")){
                        Toast.makeText(this, "必填项不能为空1", Toast.LENGTH_SHORT).show();
                        mButton.setEnabled(true);
                        return;
                    }
                }
                else if(isUnderLine.equals("1")&&(isCheck==null||isCheck.equals("")||!isCheck.equals("0"))){
                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")||yzmCode.equals("")||cardNum.equals("")){
                        Toast.makeText(this, "必填项不能为空1", Toast.LENGTH_SHORT).show();
                        mButton.setEnabled(true);
                        return;
                    }
                }
                else if(isUnderLine.equals("1")&&isCheck.equals("0")){
                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")||cardNum.equals("")){
                        Toast.makeText(this, "必填项不能为空1", Toast.LENGTH_SHORT).show();
                        mButton.setEnabled(true);
                        return;
                    }
                    else if(!b){
                        Toast.makeText(this, "卡号含有非法字符", Toast.LENGTH_SHORT).show();
                        mButton.setEnabled(true);
                        return;
                    }
                }
                mRequestDialog.show();
                submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode,cardNum);

//                if(isUnderLine==null||isUnderLine.equals("")&&isCheck==null||isCheck.equals("")){
//                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")||yzmCode.equals("")){
//                        Toast.makeText(this, "必填项不能为空1", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        mRequestDialog.show();
//                        submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode,cardNum);
//                    }
//                }
//                else if(isUnderLine==null||isUnderLine.equals("")&&isCheck.equals("0")){
//                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")){
//                        Toast.makeText(this, "必填项不能为空2", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        mRequestDialog.show();
//                        submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode,cardNum);
//                    }
//                }
//                else if(isUnderLine.equals("1")&&isCheck.equals("0")){
//                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||cardNum.equals("")||activity.equals("")){
//                        Toast.makeText(this, "必填项不能为空3", Toast.LENGTH_SHORT).show();
//                    }
//                    else if(!b){
//                        Toast.makeText(this, "卡号含有非法字符", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        mRequestDialog.show();
//                        submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode,cardNum);
//                    }
//                }
//                else {
//                    if(username.equals("")||phone.equals("")||sex.equals("")||xiaoqu.equals("")||yzmCode.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")||activity.equals("")){
//                        Toast.makeText(this, "必填项不能为空4", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        mRequestDialog.show();
//                        submit(username, phone, sex, address,xiaoqu,decorationStyle, decorationProgress, sheng_name, shi_name, qu_name, sheng_code, shi_code, qu_code, activityName, activityCode, activityId,thinkBuyGood,yzmCode,cardNum);
//                    }
//                }
                break;
        }
    }


    public void submit(String username, String phone, String sex, String address, String xiaoqu, String decorationStyle, String decorationProgress, String sheng_name, String shi_name, String qu_name, String sheng_code, String shi_code, String qu_code, String activityName, String activityCode, String activityId, String thinkBuyGood, String yzmCode, String cardNum) {
        String url =  BaseUrl.BASE_URL + "phoneApi/customerManager.do?method=saveCustomer";
        Log.d("创建活动", yzmCode);
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token_id", token_id);
        paramsMap.put("username", username);
        paramsMap.put("address", xiaoqu);
        paramsMap.put("decorationStyle", decorationStyle);
        paramsMap.put("thinkBuyGood", thinkBuyGood);
        if(isUnderLine.equals("1")){
            paramsMap.put("cardNum",cardNum);
        }
        else {
            paramsMap.put("cardNum","");
        }
        if(yzmCode.equals("")){
            paramsMap.put("yzmCode","1234");
        }
        else {
            paramsMap.put("yzmCode",yzmCode);
        }
        paramsMap.put("osFlag","1");
        if(sheng_name!=null){
            paramsMap.put("sheng_name", sheng_name);
            paramsMap.put("sheng_code", sheng_code);
            paramsMap.put("shi_code", shi_code);
            paramsMap.put("qu_code", qu_code);
            paramsMap.put("shi_name", shi_name);
            paramsMap.put("qu_name", qu_name);
        }
        paramsMap.put("phone", phone);
        paramsMap.put("sex", sex);
        paramsMap.put("decorationProgress", decorationProgress);
        if (activityName == null) {
            paramsMap.put("type", "0");
        } else {
            paramsMap.put("type", "1");
            paramsMap.put("activityCode", activityCode);
            paramsMap.put("activityId", activityId);
            paramsMap.put("activityName", activityName);
        }

        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                    mRequestDialog.dismiss();
                }
                ToastUtils.showLong(e.toString());
                mButton.setEnabled(true);
            }

            @Override
            public void onResponse(String response) {
                Log.d("创建活动",response);
                if(response!=null){
                    try {
                        Response response1 = JSONUtil.fromJson(response, Response.class);
                        if(response1.getRet().equals("200")){
                            mButton.setEnabled(true);
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            PreferencesUtils.putString(AppPeopleActivity.this, "sheng_code", null);
                            PreferencesUtils.putString(AppPeopleActivity.this, "shi_code", null);
                            PreferencesUtils.putString(AppPeopleActivity.this, "qu_code", null);
                            PreferencesUtils.putString(AppPeopleActivity.this, "sheng_name", null);
                            PreferencesUtils.putString(AppPeopleActivity.this, "shi_name", null);
                            PreferencesUtils.putString(AppPeopleActivity.this, "qu_name", null);
                            Toast.makeText(AppPeopleActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            mEtCustomerName.setText("");
                            mEtDecorateAddress.setText("");
                            mEtCustomerMobile.setText("");
                            mEtDecorateStyle.setText("");
                            mEtXiaoQu.setText("");
                            mTvCustomerGender.setText("");
                            mTvDecorateProgress.setText("");
                            mTvIntentionToPurchaseProduct.setText("");
                            et_customer_cardNum.setText("");
                            mEditTextCode.setText("");
                            if(isCheck.equals("0")){
                                mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector2));
                                mButton2.setClickable(false);
                                mButton2.setText("无需验证码");
                                mEditTextCode.setEnabled(false);
                            }
                            else {
                                mButton2.setText("获取验证码");
                                mButton2.setClickable(true);
                                mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector));
                                mEditTextCode.setEnabled(true);
                            }
                            if(time!=null){
                                time.cancel();
                            }
                        }
                        else if(response1.getRet().equals("201")){
                            mButton.setEnabled(true);
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(AppPeopleActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        else if (response1.getRet().equals("101")) {
                            mButton.setEnabled(true);
                            Toast.makeText(AppPeopleActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            PreferencesUtils.putString(AppPeopleActivity.this, "token_id", null);
                            startActivity(new Intent(AppPeopleActivity.this, LoginActivity2.class));
                            finish();
                        }
                        else {
                            mButton.setEnabled(true);
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(AppPeopleActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        ToastUtils.showLong(e.toString());
                        mButton.setEnabled(true);
                    }

                }

            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (time != null) {
            time.cancel();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ExtAlertDialog.showSureDlg(AppPeopleActivity.this, "提醒", "确定退出拓客界面吗?", "确定", new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if(result==1){
                            finish();
                        }
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ExtAlertDialog.showSureDlg(AppPeopleActivity.this, "提醒", "确定退出拓客界面吗?", "确定", new ExtAlertDialog.IExtDlgClick() {
            @Override
            public void Oclick(int result) {
                if(result==1){
                    finish();
                }
            }
        });
        return super.onKeyDown(keyCode, event);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CITY) {
            if(PreferencesUtils.getString(AppPeopleActivity.this, "sheng_code")==null){
                mEtDecorateAddress.setText("");
            }else {
                sheng_code = PreferencesUtils.getString(AppPeopleActivity.this, "sheng_code");
                shi_code = PreferencesUtils.getString(AppPeopleActivity.this, "shi_code");
                qu_code = PreferencesUtils.getString(AppPeopleActivity.this, "qu_code");
                sheng_name = PreferencesUtils.getString(AppPeopleActivity.this, "sheng_name");
                shi_name = PreferencesUtils.getString(AppPeopleActivity.this, "shi_name");
                qu_name = PreferencesUtils.getString(AppPeopleActivity.this, "qu_name");
                mEtDecorateAddress.setText(sheng_name + shi_name + qu_name);
            }
        } else if (requestCode == REQUEST_CARD_NUM) {
            String num=PreferencesUtils.getString(getApplicationContext(),"cardNum");
            if(num!=null){
                et_customer_cardNum.setText(num);
            }
            else {
                et_customer_cardNum.setText("");
            }


        }
        else if (requestCode == REQUEST_CODE_ACTIVITY) {
            if (data != null && data.getStringExtra("activityName") != null) {
                activityName = data.getStringExtra("activityName");
                activityId = data.getStringExtra("activityId");
                activityCode = data.getStringExtra("activityCode");
                isUnderLine=data.getStringExtra("isUnderLine");
                isCheck=data.getStringExtra("isCheck");
                mTvActivity.setText(activityName);
                PreferencesUtils.putString(this, "activity_id", activityId);
                if(isCheck.equals("0")){
                    mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector2));
                    mButton2.setClickable(false);
                    mButton2.setText("无需验证码");
                    mEditTextCode.setEnabled(false);
                }
                else {
                    mButton2.setText("获取验证码");
                    mButton2.setClickable(true);
                    mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector));
                    mEditTextCode.setEnabled(true);
                }
                if(isUnderLine==null||isUnderLine.equals("")){
                    mCardNum.setVisibility(View.GONE);
                    cardNumLine.setVisibility(View.GONE);
                }else {
                    if(isUnderLine.equals("1")){
                        mCardNum.setVisibility(View.VISIBLE);
                        cardNumLine.setVisibility(View.VISIBLE);
                    }
                    else {
                        mCardNum.setVisibility(View.GONE);
                        cardNumLine.setVisibility(View.GONE);
                    }
                }
                Log.d("活动id", activityId);
            }

        }
        else if(requestCode==REQUEST_PROGRESS_ACTIVITY){
            if(PreferencesUtils.getString(AppPeopleActivity.this, "DecorationProgressName")==null){
                mTvDecorateProgress.setText("");
            }else {
                progress = PreferencesUtils.getString(AppPeopleActivity.this, "DecorationProgressName");
                mTvDecorateProgress.setText(progress);
            }
        }
    }


    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }



    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            mButton2.setBackgroundColor(Color.parseColor("#AAA"));
            mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector2));
            mButton2.setClickable(false);
            mButton2.setText("("+millisUntilFinished / 1000 +")秒后获取");
        }

        @Override
        public void onFinish() {
            mButton2.setText("获取验证码");
            mButton2.setClickable(true);
            mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector));

        }
    }


    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][123456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }



}

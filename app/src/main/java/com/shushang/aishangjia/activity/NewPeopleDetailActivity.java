package com.shushang.aishangjia.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;

import okhttp3.Call;

public class NewPeopleDetailActivity extends AppCompatActivity {
    private LinearLayout mLlCustomerGender,mLLXiaoqu,mLLStyle,mLLProduct;
    private TextView mEtCustomerName;//客户姓名
    private TextView mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private TextView mEtXiaoQu;//小区名称
    private TextView mEdit;
    private RelativeLayout rl_cardNum;
    private TextView mKaHao;//小区名称
    private TextView mEtDecorateStyle;//装修风格
//    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private TextView mEtDecorateAddress;//装修地址
    private TextView mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private View line;
    private Button mButton1,mButton2;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood,card_num;
    private RadioGroup mRadioButton;
    private String customerActionId;
    private static final int REQUEST_CODE_CITY = 2010;
    private static final int REQUEST_PROGRESS_ACTIVITY = 2012;
    private String progress=null;
    private String type;
    private String lianmeng;
//    private TimeCount time;
    private Dialog mRequestDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_people_detail);
        mEtCustomerName = (TextView) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (TextView) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle =  findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mLLXiaoqu=findViewById(R.id.ll_xiaoqu);
        mLLStyle=findViewById(R.id.ll_style);
        mLLProduct=findViewById(R.id.ll_product);
        mEtXiaoQu=  findViewById(R.id.et_customer_xiaoqu);
        mEdit=findViewById(R.id.edit_user);
        mButton2=findViewById(R.id.btn_submit);
        mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector2));
        mButton2.setClickable(false);
        mButton2.setEnabled(false);
        mKaHao=findViewById(R.id.et_customer_kahao);
        mTvIntentionToPurchaseProduct= findViewById(R.id.et_intention_to_purchase_product);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        line=findViewById(R.id.cardNumLine);
        rl_cardNum=findViewById(R.id.cardNum);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mRequestDialog.setCancelable(false);
        token_id = PreferencesUtils.getString(this, "token_id");
        final Intent data=getIntent();
        final NewPeople.DataListBean dataListBean= (NewPeople.DataListBean) data.getSerializableExtra("data");
        type=dataListBean.getType();
        if(type.equals("4")){
            mEdit.setVisibility(View.GONE);
        }
        mTvDecorateProgress.setEnabled(false);
        mEtDecorateAddress.setEnabled(false);
        mLLProduct.setEnabled(false);
        mLLStyle.setEnabled(false);
        mLLXiaoqu.setEnabled(false);
        mEtDecorateStyle.setEnabled(false);
        mEtXiaoQu.setEnabled(false);
        mTvIntentionToPurchaseProduct.setEnabled(false);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(dataListBean);
        mTvDecorateProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewPeopleDetailActivity.this,ProgressActivity.class),REQUEST_PROGRESS_ACTIVITY);
            }
        });
        mEtDecorateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewPeopleDetailActivity.this, CityActivity.class), REQUEST_CODE_CITY);
            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlertDialog.showSureDlg(NewPeopleDetailActivity.this, "注意", getString(R.string.edit), getString(R.string.sure), new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if(result==1){
                            mTvDecorateProgress.setEnabled(true);
                            mEtDecorateAddress.setEnabled(true);
                            mEtDecorateStyle.setEnabled(true);
                            mEtXiaoQu.setEnabled(true);
                            mTvIntentionToPurchaseProduct.setEnabled(true);
                            mLLProduct.setEnabled(true);
                            mLLStyle.setEnabled(true);
                            mLLXiaoqu.setEnabled(true);
                            mButton2.setClickable(true);
                            mButton2.setEnabled(true);
                            mButton2.setBackground(getResources().getDrawable(R.drawable.register_btn_bg_selector));
                        }
                    }
                });
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mEtCustomerName.getText().toString().replace(" ", "");
                phone = mEtCustomerMobile.getText().toString().replace(" ", "");
                address = mEtDecorateAddress.getText().toString().replace(" ", "");
                xiaoqu=mEtXiaoQu.getText().toString().replace(" ", "");
                card_num=mKaHao.getText().toString().replace(" ", "");
                decorationStyle=mEtDecorateStyle.getText().toString().replace(" ", "");
                thinkBuyGood=mTvIntentionToPurchaseProduct.getText().toString().replace(" ", "");
                decorationProgress = mTvDecorateProgress.getText().toString().replace(" ", "");
                sex=mTvCustomerGender.getText().toString().replace(" ", "");
                if(dataListBean.getActivityName()!=null){
                    activityName = dataListBean.getActivityName().toString();
                    activityId = dataListBean.getActivityId().toString();
                    activityCode = dataListBean.getActivityCode().toString();
                }
                if(dataListBean.getSheng_name()!=null){
                    sheng_code = dataListBean.getSheng_code().toString();
                    shi_code = dataListBean.getShi_code().toString();
                    qu_code = dataListBean.getQu_code().toString();
                    sheng_name = dataListBean.getSheng_name().toString();
                    shi_name = dataListBean.getShi_name().toString();
                    qu_name = dataListBean.getQu_name().toString();
                }
                customerActionId=dataListBean.getCustomerActionId();
                if(xiaoqu.equals("")||decorationProgress.equals("")||thinkBuyGood.equals("")){
                    Toast.makeText(NewPeopleDetailActivity.this, "必填项不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    submit(username,phone,address,xiaoqu,card_num,decorationStyle,thinkBuyGood,decorationProgress,activityName,activityId,activityCode,sheng_code,shi_code,qu_code,sheng_name,shi_name,qu_name,customerActionId);
                }

            }
        });


        mLLStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("我被点击了","6666666");
               ExtAlertDialog.showEditDlg(NewPeopleDetailActivity.this, "修改装修风格", "修改", false, new ExtAlertDialog.IExtDlgClick2() {
                   @Override
                   public void Oclick(int result, Editable text) {
                       if(result==1){
                           if(text==null){
                               mEtDecorateStyle.setText("");
                           }
                           else {
                               mEtDecorateStyle.setText(text);
                           }
                       }
                   }
               });
            }
        });


        mLLXiaoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlertDialog.showEditDlg(NewPeopleDetailActivity.this, "修改小区名称", "修改", false, new ExtAlertDialog.IExtDlgClick2() {
                    @Override
                    public void Oclick(int result, Editable text) {
                        if(result==1){
                            if(text==null){
                                mEtXiaoQu.setText("");
                            }
                            else {
                                mEtXiaoQu.setText(text);
                            }
                        }
                    }
                });
            }
        });


        mLLProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlertDialog.showEditDlg(NewPeopleDetailActivity.this, "修改意向产品", "修改", false, new ExtAlertDialog.IExtDlgClick2() {
                    @Override
                    public void Oclick(int result, Editable text) {
                        if(result==1){
                            if(text==null){
                                mTvIntentionToPurchaseProduct.setText("");
                            }
                            else {
                                mTvIntentionToPurchaseProduct.setText(text);
                            }
                        }
                    }
                });
            }
        });


    }

    private void submit(String username, String phone, String address, String xiaoqu, String card_num, String decorationStyle, String thinkBuyGood, String decorationProgress, String activityName, String activityId, String activityCode, String sheng_code, String shi_code, String qu_code, String sheng_name, String shi_name, String qu_name, String customerActionId) {
        mRequestDialog.show();
        String url =  BaseUrl.BASE_URL + "phoneApi/customerManager.do?method=updateCustomer";
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token_id", token_id);
        paramsMap.put("username", username);
        paramsMap.put("address", xiaoqu);
        if(decorationStyle!=null){
            paramsMap.put("decorationStyle", decorationStyle);
        }
        else {
            paramsMap.put("decorationStyle", "");
        }
        paramsMap.put("thinkBuyGood", thinkBuyGood);
        paramsMap.put("customerActionId",customerActionId);
        if(card_num!=null){
            paramsMap.put("cardNum",card_num);
        }
        else {
            paramsMap.put("cardNum","");
        }
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
        if(decorationProgress!=null){
            paramsMap.put("decorationProgress", decorationProgress);
        }
        else {
            paramsMap.put("decorationProgress", "");
        }

        if (activityName == null) {
            paramsMap.put("type", "0");
        } else {
            paramsMap.put("type", "1");
            paramsMap.put("activityCode", activityCode);
            paramsMap.put("activityId", activityId);
            paramsMap.put("activityName", activityName);
        }

        try {
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    ToastUtils.showLong(e.toString());

                }

                @Override
                public void onResponse(String response) {
                    Log.d("修改信息",response);
                    if(response!=null){
                        try {
                            Response response1 = JSONUtil.fromJson(response, Response.class);
                            if(response1.getRet().equals("200")){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                ToastUtils.showLong("修改成功");
                                mTvDecorateProgress.setEnabled(false);
                                mEtDecorateStyle.setEnabled(false);
                                mEtDecorateStyle.clearFocus();
                                mEtDecorateAddress.setEnabled(false);
                                mEtXiaoQu.setEnabled(false);
                                mEtXiaoQu.clearFocus();
                                mTvIntentionToPurchaseProduct.setEnabled(false);
                                mTvIntentionToPurchaseProduct.clearFocus();
                            }
                            else if(response1.getRet().equals("201")){

                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NewPeopleDetailActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response1.getRet().equals("101")) {

                                Toast.makeText(NewPeopleDetailActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(NewPeopleDetailActivity.this, "token_id", null);
                                startActivity(new Intent(NewPeopleDetailActivity.this, LoginActivity2.class));
                                finish();
                            }
                            else {

                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NewPeopleDetailActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            ToastUtils.showLong(e.toString());
                        }

                    }

                }
            });
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong("服务器出错，请稍后再试");
        }



    }

    private void initData(NewPeople.DataListBean dataListBean) {
        if(dataListBean.getUsername()!=null){
            mEtCustomerName.setText(String.valueOf(dataListBean.getUsername()));
        }
        if(dataListBean.getPhone()!=null){
            mEtCustomerMobile.setText(String.valueOf(dataListBean.getPhone()));
        }
        if(dataListBean.getSex()!=null){
            mTvCustomerGender.setText(String.valueOf(dataListBean.getSex()));
        }
        if(dataListBean.getDecorationProgress()!=null){
            mTvDecorateProgress.setText(String.valueOf(dataListBean.getDecorationProgress()));
        }
        if(dataListBean.getAddress()!=null){
            mEtXiaoQu.setText(String.valueOf(dataListBean.getAddress()));
        }
        if(dataListBean.getDecorationStyle()!=null){
            mEtDecorateStyle.setText(dataListBean.getDecorationStyle().toString());
        }
        if(dataListBean.getSheng_name()!=null){
            mEtDecorateAddress.setText(String.valueOf(dataListBean.getSheng_name())+String.valueOf(dataListBean.getShi_name())+String.valueOf(dataListBean.getQu_name()));
        }
        if(dataListBean.getThinkBuyGood()!=null){
            mTvIntentionToPurchaseProduct.setText(dataListBean.getThinkBuyGood().toString());
        }
        if(dataListBean.getActivityName()!=null){
            rl_activity.setVisibility(View.VISIBLE);
            mTvActivity.setText(dataListBean.getActivityName().toString());
        }
        if(!dataListBean.getType().equals("1")){
            rl_cardNum.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        else {
            if(dataListBean.getCardNum()!=null){
                if(!dataListBean.getCardNum().equals("")){
                    mKaHao.setText(dataListBean.getCardNum().toString());
                    rl_cardNum.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CITY) {
            if(PreferencesUtils.getString(NewPeopleDetailActivity.this, "sheng_code")==null){
                mEtDecorateAddress.setText("");
            }else {
                sheng_code = PreferencesUtils.getString(NewPeopleDetailActivity.this, "sheng_code");
                shi_code = PreferencesUtils.getString(NewPeopleDetailActivity.this, "shi_code");
                qu_code = PreferencesUtils.getString(NewPeopleDetailActivity.this, "qu_code");
                sheng_name = PreferencesUtils.getString(NewPeopleDetailActivity.this, "sheng_name");
                shi_name = PreferencesUtils.getString(NewPeopleDetailActivity.this, "shi_name");
                qu_name = PreferencesUtils.getString(NewPeopleDetailActivity.this, "qu_name");
                mEtDecorateAddress.setText(sheng_name + shi_name + qu_name);
            }
        }
        else if(requestCode==REQUEST_PROGRESS_ACTIVITY){
            if(PreferencesUtils.getString(NewPeopleDetailActivity.this, "DecorationProgressName")==null){
                mTvDecorateProgress.setText("");
            }else {
                progress = PreferencesUtils.getString(NewPeopleDetailActivity.this, "DecorationProgressName");
                mTvDecorateProgress.setText(progress);
            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}

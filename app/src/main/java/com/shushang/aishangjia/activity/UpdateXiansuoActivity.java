package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.Progress;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.Bean.XiansuoInfo;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.DecorateProgressDialog;
import com.shushang.aishangjia.ui.DecorateProgressDialog2;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class UpdateXiansuoActivity extends BaseActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private LinearLayout mLlCustomerGender,mllSource,mllThink;
    private TextView mEtCustomerName;//客户姓名
    private TextView mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender,mTvSource,mTvThink;
    private TextView mEtXiaoQu;//小区名称
    private TextView mXiansuo;//销售线索信息
    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private EditText mTvIntentionToPurchaseProduct;//本次沟通时间
    private TextView mNextTime;//下次沟通时间
    private TextView mXiansuoThink;//本次次线索意向
    private TextView mBiezhu;//本次备注信息
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private DecorateProgressDialog mDecorateProgressDialog;
    private DecorateProgressDialog2 mDecorateProgressDialog2;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, phone, sex, xiaoqu,  xiansuo,info,thinkbuy,nowTime,nextTime,source;
    private RadioGroup mRadioButton;
    private long lastClick;
    private static final int REQUEST_CODE_CITY = 2010;
    private static final int REQUEST_CODE_ACTIVITY = 2011;
    private static final int REQUEST_PROGRESS_ACTIVITY = 2012;
    private static final int REQUEST_INFO_ACTIVITY = 2013;
    private static final int REQUEST_BEIZHU_ACTIVITY = 2014;
    private static final int REQUEST_NEXT_BEIZHU_ACTIVITY = 2015;
    private Dialog mRequestDialog;
    private boolean isVisiable=false;
    private  List<Progress.DataListBean> mDataListBeen;
    private String progress=null;
    private int select_year,select_mounth,select_day;
    private int now_year,now_mounth,now_day;
    private  String clueId;
    @Override
    public int setLayout() {
        return R.layout.activity_update_xiaosuo;
    }


    @Override
    public void init() {
        mEtCustomerName = (TextView) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (TextView) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mllThink= (LinearLayout) findViewById(R.id.ll_thinkbuy);
        mllSource= (LinearLayout) findViewById(R.id.ll_source);
        mTvSource= (TextView) findViewById(R.id.tv_source);
        mXiansuo = (TextView) findViewById(R.id.et_decorate_style);
        mNextTime= (TextView) findViewById(R.id.next_time);
        mXiansuoThink= (TextView) findViewById(R.id.et_xiansuo_think);
        mEtXiaoQu= (TextView) findViewById(R.id.et_customer_xiaoqu);
        mllSource= (LinearLayout) findViewById(R.id.ll_source);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        Intent data=getIntent();
        NewPeople.DataListBean dataListBean= (NewPeople.DataListBean) data.getSerializableExtra("data");
        String genjinData = PreferencesUtils.getString(UpdateXiansuoActivity.this, "genjinData");
        XiansuoInfo xiansuoInfo = JSONUtil.fromJson(genjinData, XiansuoInfo.class);
        showData(xiansuoInfo);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButton = (Button) findViewById(R.id.btn_submit);
        token_id = PreferencesUtils.getString(this, "token_id");
        clueId=xiansuoInfo.getData().getClueId();
        mTvIntentionToPurchaseProduct = (EditText) findViewById(R.id.et_intention_to_purchase_product);
        mGenderDialog = new GenderDialog(this);
        mDecorateProgressDialog=new DecorateProgressDialog(this);
        mDecorateProgressDialog2=new DecorateProgressDialog2(this);
        mLlCustomerGender.setOnClickListener(this);
        mllSource.setOnClickListener(this);
        mllThink.setOnClickListener(this);
        mButton.setOnClickListener(this);

        mXiansuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(UpdateXiansuoActivity.this,InfoActivity.class),REQUEST_INFO_ACTIVITY);
            }
        });

        mNextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                       UpdateXiansuoActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setAutoHighlight(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        Calendar c = Calendar.getInstance();//
        now_year = c.get(Calendar.YEAR); // 获取当前年份
        now_mounth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        now_day = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Log.d("mTvIntention",String.valueOf(System.currentTimeMillis()));
        mTvIntentionToPurchaseProduct.setText(simpleDateFormat.format(date));

    }

    private void showData(XiansuoInfo xiansuoInfo) {
        if(null!=xiansuoInfo.getData().getName()){
            mEtCustomerName.setText(xiansuoInfo.getData().getName());
        }
        if(null!=xiansuoInfo.getData().getTelephone()){
            mEtCustomerMobile.setText(xiansuoInfo.getData().getTelephone());
        }
        if(null!=xiansuoInfo.getData().getSex()){
            if(xiansuoInfo.getData().getSex().equals("1")){
                mTvCustomerGender.setText("男");
            }
            else if(xiansuoInfo.getData().getSex().equals("2")){
                mTvCustomerGender.setText("女");
            }

        }
        if(null!=xiansuoInfo.getData().getStatus()){
            mTvSource.setText(xiansuoInfo.getData().getStatus());
        }
        if(null!=xiansuoInfo.getData().getAddress()){
            mEtXiaoQu.setText(xiansuoInfo.getData().getAddress());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_thinkbuy://客户来源
                getCustomeIntent();
                break;
            case R.id.btn_submit:
                xiansuo=mXiansuo.getText().toString().replace(" ", "");
                nowTime=mTvIntentionToPurchaseProduct.getText().toString();
                nextTime=mNextTime.getText().toString();
                try {
                    if(nowTime.equals("")||nextTime.equals("")||xiansuo.equals("")||thinkbuy.equals("")){
                        Toast.makeText(this, "必填项不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mRequestDialog.show();
                        submit(xiansuo,thinkbuy,nowTime,nextTime);
                    }
                }
                catch (Exception e){
                    ToastUtils.showLong(e.toString());
                }

                break;
        }
    }

    private void getCustomeIntent() {
        new ActionSheetDialog(UpdateXiansuoActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("未知", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("未知");
                                thinkbuy="1";
                            }
                        })
                .addSheetItem("无意向", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("无意向");
                                thinkbuy="2";
                            }
                        })
                .addSheetItem("有需求暂无意向", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("有需求暂无意向");
                                thinkbuy="3";
                            }
                        })
                .addSheetItem("有意向，需考虑竞品", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("有意向，需考虑竞品");
                                thinkbuy="4";
                            }
                        })
                .addSheetItem("有意向，需考虑价格", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("有意向，需考虑价格");
                                thinkbuy="5";
                            }
                        })
                .addSheetItem("非常有意向，考虑成交", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mXiansuoThink.setText("");
                                mXiansuoThink.setText("非常有意向，考虑成交");
                                thinkbuy="6";
                            }
                        })
                .show();
    }


    public void submit(String xiansuo,String thinkbuy,String nowTime,String nextTime) {
        String url =  BaseUrl.BASE_URL + "phoneApi/clueController.do?method=followClue";
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token_id", token_id);
        paramsMap.put("clueId", clueId);
        paramsMap.put("nowIntention", thinkbuy);
        paramsMap.put("info", xiansuo);
        paramsMap.put("nowTime", nowTime);
        paramsMap.put("nextTime", nextTime);

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
                Log.d("创建活动",response);
                if(response!=null){
                    Response response1 = JSONUtil.fromJson(response, Response.class);
                    if(response1.getRet().equals("200")){
                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                            mRequestDialog.dismiss();
                        }
                        finish();
                    }
                    else if(response1.getRet().equals("201")){
                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                            mRequestDialog.dismiss();
                        }
                        Toast.makeText(UpdateXiansuoActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INFO_ACTIVITY) {
            if(PreferencesUtils.getString(UpdateXiansuoActivity.this, "info")==null){
                mXiansuo.setText("");
            }else {
                mXiansuo.setText("");
                info= PreferencesUtils.getString(getApplicationContext(),"info");
                mXiansuo.setText(info);
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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        select_year=year;
        select_mounth=monthOfYear;
        select_day=dayOfMonth;
        if(select_year<now_year){
            ToastUtils.showLong("请选择当前时间以后的年份");
        }
        else if(select_year==now_year&&select_mounth<now_mounth-1){
            ToastUtils.showLong("请选择当前时间以后的月份");
        }
        else if(select_mounth==now_mounth&&select_day<now_day){
            ToastUtils.showLong("请选择当前时间以后的日期");
        }
        else {
            Calendar now = Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    UpdateXiansuoActivity.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
            tpd.setAccentColor(Color.parseColor("#2196F3"));
            tpd.setTabIndicators("","");
            tpd.show(getFragmentManager(), "Timepickerdialog");
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time=select_year+"-"+select_mounth+"-"+select_day+" "+hourString+":"+minuteString+":00";
        Date date=new Date(select_year-1900,select_mounth,select_day,hourOfDay,minute,0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Log.d("time",time);
        mNextTime.setText(simpleDateFormat.format(date));
    }


    @Override
    protected void onPause() {
        super.onPause();
        mEtXiaoQu.clearFocus();
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

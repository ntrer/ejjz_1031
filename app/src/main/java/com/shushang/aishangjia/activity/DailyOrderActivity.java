package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import okhttp3.Call;

public class DailyOrderActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtCustomerName;//客户姓名
    private EditText mEtCustomerMobile;//客户电话
    private EditText mEtCustomerMoney;//客户电话
    private Button mButton;
    private String token_id;
    private String username, money,phone;
    private Dialog mRequestDialog;
    private Toolbar mToolbar;
    @Override
    public int setLayout() {
        return R.layout.activity_daily_order;
    }

    @Override
    public void init() {
        mToolbar=findViewById(R.id.toolbar);
        mEtCustomerName = (EditText) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (EditText) findViewById(R.id.et_customer_mobile);
        mEtCustomerMoney=findViewById(R.id.money);
        mButton = (Button) findViewById(R.id.btn_submit);
        token_id = PreferencesUtils.getString(this, "token_id");
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mButton.setOnClickListener(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                username = mEtCustomerName.getText().toString().replace(" ", "");
                phone = mEtCustomerMobile.getText().toString().replace(" ", "");
                money=mEtCustomerMoney.getText().toString().replace(" ", "");
                if(username.equals("")||phone.equals("")||money.equals("")){
                    Toast.makeText(this, "必填项不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    mRequestDialog.show();
                    submit(username, phone, money);
                }
                break;
        }
    }

    private void submit(String username, String phone, String money) {
        String url =  BaseUrl.BASE_URL + "phoneApi/activityController.do?method=saveDailyOrder";
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token_id", token_id);
        paramsMap.put("customerName", username);
        paramsMap.put("customerPhone", phone);
        paramsMap.put("money", money);
        Log.d("token_id",token_id+"customerName="+username+"customerPhone="+phone+"money="+money);
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
                    try {
                        Response response1 = JSONUtil.fromJson(response, Response.class);
                        if(response1.getRet().equals("200")){
                            if(response1.getMsg().equals("success")){
                                Toast.makeText(DailyOrderActivity.this, "成功", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(DailyOrderActivity.this, ""+response1.getMsg(), Toast.LENGTH_LONG).show();
                            }
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            mEtCustomerName.setText("");
                            mEtCustomerMobile.setText("");
                            mEtCustomerMoney.setText("");
                            EventBus.getDefault().post(new MessageEvent(""));
                        }
                        else if(response1.getRet().equals("201")){
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(DailyOrderActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(DailyOrderActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        ToastUtils.showLong(e.toString());
                    }

                }

            }
        });
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
    
}



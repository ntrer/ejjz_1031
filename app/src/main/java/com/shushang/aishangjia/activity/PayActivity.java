package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.Diko;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.Bean.goods;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.SLEditTextView;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PayActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_customer_name)
    SLEditTextView mEtCustomerName;
    @BindView(R.id.et_customer_phone)
    SLEditTextView mEtCustomerPhone;
    @BindView(R.id.et_customer_dikou)
    TextView mEtCustomerDikou;
    @BindView(R.id.et_customer_realget)
    TextView mEtCustomerRealget;
    @BindView(R.id.et_customer_activieget)
    SLEditTextView mEtCustomerActivieget;
    @BindView(R.id.et_customer_beizhu)
    SLEditTextView mEtCustomerBeizhu;
    @BindView(R.id.total_price)
    TextView mTotalPrice;
    @BindView(R.id.bottom_edit2)
    LinearLayout mBottomEdit2;
    @BindView(R.id.btn_dingjin)
    Button mBtnDingjin;
    @BindView(R.id.total_price_text)
    TextView mTotalPriceText;
    @BindView(R.id.ll_total)
    LinearLayout mLlTotal;
    @BindView(R.id.et_customer_dikou_text)
    TextView mEtCustomerDikouText;
    @BindView(R.id.et_customer_realget_text)
    TextView mEtCustomerRealgetText;
    private List<goods> mGoodsLists = new ArrayList<>();
    private String total_Price;
    private String name, phone, shishou, beizhu, dikou, yingshou,orderIntentIds,realYingshou;
    private String token_id = null;
    private Dialog mRequestDialog;
    private static final int REQUEST_CODE_DINGJIN = 5677;
    private String data;
    private List<Diko.DataListBean> selectDatas;
    private Double dingjinData=0.0;
    private List<String> dingjinId=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mRequestDialog.setCancelable(false);
        token_id = PreferencesUtils.getString(this, "token_id");
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mGoodsLists = (List<goods>) intent.getSerializableExtra("goodsData");
        total_Price = intent.getStringExtra("totalprice");
        initData(total_Price, mGoodsLists);
        EventBus.getDefault().register(this);
    }

    private void initData(String total_price, List<goods> total_price1) {
        mTotalPriceText.setText(total_price);
        mEtCustomerRealgetText.setText(total_price);
        data = JSONUtil.toJSON(total_price1);
    }


    @OnClick(R.id.btn_dingjin)
    void dingjin() {
        dingjinData=0.0;
        dingjinId.clear();
        name = mEtCustomerName.getText().toString().replace(" ", "");
        phone = mEtCustomerPhone.getText().toString().replace(" ", "");
        if (phone.equals("")) {
            ToastUtils.showLong("手机号不能为空");
        } else if (phone.length() > 11 || !isMobileNO(phone)) {
            ToastUtils.showShort("手机号格式不对");
        } else {
            String url = BaseUrl.BASE_URL + "phoneApi/goodsOrderController.do?method=getIntentByCustomerPhone&token_id=" + token_id + "&phone=" + phone;
            Log.d("url", url);
            try {
                RestClient.builder()
                        .url(url)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                Log.d("response", response);
                                if (response != null) {
                                    Diko sheng = JSONUtil.fromJson(response, Diko.class);
                                    if (sheng.getRet().equals("200")) {
                                        if(sheng.getDataList().size()==0){
                                            ToastUtils.showLong("暂无订金");
                                        }
                                        else {
                                            Intent intent=new Intent(PayActivity.this,DingJinActivity.class);
                                            intent.putExtra("phone",phone+"");
                                            startActivityForResult(intent,REQUEST_CODE_DINGJIN);
                                        }

                                    } else {
                                        ToastUtils.showLong("" + sheng.getMsg());
                                    }
                                } else {

                                }
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFailure() {

                                Toast.makeText(PayActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                            }
                        }).error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                        Toast.makeText(PayActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                })
                        .build()
                        .get();
            } catch (Exception e) {

            }
        }
    }


    @OnClick(R.id.bottom_edit2)
    void pay_money() {
        dikou = mEtCustomerDikouText.getText().toString().replace(" ", "");
        beizhu = mEtCustomerBeizhu.getText().toString().replace(" ", "");
        name = mEtCustomerName.getText().toString().replace(" ", "");
        phone = mEtCustomerPhone.getText().toString().replace(" ", "");
        shishou = mEtCustomerActivieget.getText().toString().replace(" ", "");
        yingshou = mEtCustomerRealgetText.getText().toString().replace(" ", "");
        if (phone.equals("")) {
            ToastUtils.showLong("手机号不能为空");
            return;
        } else if (phone.length() > 11 || !isMobileNO(phone)) {
            ToastUtils.showShort("手机号格式不对");
            return;
        }

        if (name.equals("") || shishou.equals("")) {
            ToastUtils.showLong("请填写完整");
            return;
        }

       try {

           String url = BaseUrl.BASE_URL + "phoneApi/goodsOrderController.do?method=saveGoodsOrder";
           HashMap<String, String> paramsMap = new HashMap<>();
           paramsMap.put("token_id", token_id);
           paramsMap.put("customerName", name);
           paramsMap.put("customerPhone", phone);
           if(null==orderIntentIds){
               paramsMap.put("orderIntentIds", "");
           }
           else {
               paramsMap.put("orderIntentIds", orderIntentIds);
           }

           paramsMap.put("totalPrice", total_Price);
           if (dikou == null) {
               paramsMap.put("dikoujia", "0");
           }
           else if(dikou.equals("0")){
               paramsMap.put("dikoujia", "0");
           }
           else if(dikou.equals("")){
               paramsMap.put("dikoujia", "0");
           }
           else {
               paramsMap.put("dikoujia", dikou);
           }

           paramsMap.put("shishou", shishou);
           if (beizhu == null) {
               paramsMap.put("beizhu", "");
           }
           else {
               paramsMap.put("beizhu", beizhu);
           }
           paramsMap.put("yingshou", yingshou);
           paramsMap.put("goods", data);
           mRequestDialog.show();

           OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
               @Override
               public void onFailure(Call call, Exception e) {
                   if (mRequestDialog != null && mRequestDialog.isShowing()) {
                       mRequestDialog.dismiss();
                   }
                   ToastUtils.showLong("错误了");
               }

               @Override
               public void onResponse(String response) {
                   Log.d("创建活动", response);
                   if (response != null) {
                       try {
                           Response response1 = com.shushang.aishangjia.utils.Json.JSONUtil.fromJson(response, Response.class);
                           if (response1.getRet().equals("200")) {
                               if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                   mRequestDialog.dismiss();
                               }
                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       EventBus.getDefault().post(new MessageEvent("提交订单"));
                                   }
                               },800);


                           } else if (response1.getRet().equals("201")) {
                               if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                   mRequestDialog.dismiss();
                               }
                               ToastUtils.showLong(response1.getMsg());

                           } else if (response1.getRet().equals("101")) {
                               Toast.makeText(PayActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                               PreferencesUtils.putString(PayActivity.this, "token_id", null);
                               startActivity(new Intent(PayActivity.this, LoginActivity2.class));
                               finish();
                           } else {

                           }
                       } catch (Exception e) {
                           if (mRequestDialog != null && mRequestDialog.isShowing()) {
                               mRequestDialog.dismiss();
                           }
                           ToastUtils.showLong(e.toString());

                       }

                   }

               }
           });
       }
       catch (Exception e){
           if (mRequestDialog != null && mRequestDialog.isShowing()) {
               mRequestDialog.dismiss();
           }
           ToastUtils.showLong(e.toString());
       }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode==REQUEST_CODE_DINGJIN&&resultCode==1){
                selectDatas= (List<Diko.DataListBean>) data.getSerializableExtra("dingjinData");
                if(selectDatas.size()==0){
                    mEtCustomerDikouText.setText("0");
                    orderIntentIds="";
                }
                else {
                    for (int i = 0; i < selectDatas.size(); i++) {
                        dingjinData=dingjinData+selectDatas.get(i).getTotalPrice();
                        dingjinId.add(selectDatas.get(i).getOrderId());
                    }
                    StringBuilder stringBuilder = dataToString(dingjinId);
                    orderIntentIds=stringBuilder.toString();
                    Log.d("orderIntentIds",orderIntentIds);
                    mEtCustomerDikouText.setText(String.valueOf(dingjinData));
                    mEtCustomerRealgetText.setText(String.valueOf(formatDouble4((Double.parseDouble(total_Price)-dingjinData))));
                }
            }
            else {
                mEtCustomerDikouText.setText("0");
                orderIntentIds="";
                mEtCustomerRealgetText.setText(String.valueOf(formatDouble4((Double.parseDouble(total_Price)))));
            }
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }


    }



    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("#.00");


        return df.format(d);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("结束")){
            EventBus.getDefault().post(new MessageEvent("订单"));
            finish();
        }
    }


    private static StringBuilder dataToString(List<String> imgIds) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < imgIds.size(); i++) {
            if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                sb.append(",");
            }
            sb.append(imgIds.get(i));
        }

        return sb;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

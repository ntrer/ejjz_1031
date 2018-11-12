package com.shushang.aishangjia.fragment.YiXiangJinFragment.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Quit;
import com.shushang.aishangjia.Bean.YiXiangJin;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.xys.libzxing.zxing.net.RestClient;
import com.xys.libzxing.zxing.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by YD on 2018/7/19.
 */

public class MoneyPeopleRecyclerViewAdapter2 extends BaseQuickAdapter<YiXiangJin.DataListBean,BaseViewHolder> {

    private Handler mHandler;

    public MoneyPeopleRecyclerViewAdapter2(@LayoutRes int layoutResId, @Nullable List<YiXiangJin.DataListBean> data, Handler handler) {
        super(layoutResId, data);
        this.mHandler=handler;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final YiXiangJin.DataListBean item) {
        helper.setText(R.id.people,item.getCustomerName());
        helper.setText(R.id.phone,item.getCustomerPhone());
        if(item.getSource().equals("0")){
            helper.setText(R.id.tag,"日常");
        }
        else {
            helper.setText(R.id.tag,"活动");
        }

        if(item.getStatus().equals("0")){
            helper.getView(R.id.getPaper).setVisibility(View.GONE);
            helper.getView(R.id.getPaper2).setVisibility(View.GONE);
        }
        else if(item.getStatus().equals("100")||item.getStatus().equals("-300")){
            helper.getView(R.id.getPaper2).setVisibility(View.VISIBLE);
            helper.getView(R.id.getPaper).setVisibility(View.GONE);
        }
        else if(item.getStatus().equals("-200")){
            helper.getView(R.id.getPaper2).setVisibility(View.GONE);
            helper.getView(R.id.getPaper).setVisibility(View.VISIBLE);
        }




        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(item.getCjsj());
        String time=simpleDateFormat.format(date);
        helper.setText(R.id.date,time);
        if(item.getOrderMerchantName()!=null){
            helper.setText(R.id.label,item.getOrderMerchantName().toString());
        }
        helper.setText(R.id.money,String.valueOf(item.getTotalPrice())+"元");
        final String tokenId= PreferencesUtils.getString(mContext,"token_id");
        final String activity_id=PreferencesUtils.getString(mContext,"activityId");
        final String order_id=item.getOrderId();
            helper.getView(R.id.quit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.getStatus().equals("0")){
                        final String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=refundOrder&token_id="+tokenId+"&activity_id="+activity_id+"&order_id="+order_id;
                        Log.d("quitUrl",url);
                        RestClient.builder()
                                .url(url)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        if(response!=null){
                                            Log.d("quitUrl",response);
                                            Quit quit = JSONUtil.fromJson(response, Quit.class);
                                            if(quit.getRet().equals("200")){
                                                Message message=Message.obtain();
                                                message.what=2;
                                                mHandler.sendMessage(message);
                                                ToastUtils.showLong("退单成功");
                                            }
                                            else {
                                                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "退单失败："+quit.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                })
                                .build()
                                .get();
                    }
                    else if(item.getStatus().equals("100")||item.getStatus().equals("-300")){
                        ToastUtils.showLong("您已使用此订金");
                    }
                    else {
                        ToastUtils.showLong("您已退过此单");
                    }

                }
            });

    }
}

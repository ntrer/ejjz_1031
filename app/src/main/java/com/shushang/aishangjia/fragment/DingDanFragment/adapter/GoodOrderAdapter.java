package com.shushang.aishangjia.fragment.DingDanFragment.adapter;

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
import com.shushang.aishangjia.Bean.GoodsOrder;
import com.shushang.aishangjia.Bean.Quit;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.ui.ExtAlertDialog;
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

public class GoodOrderAdapter extends BaseQuickAdapter<GoodsOrder.DataListBean,BaseViewHolder> {

    private Handler mHandler;

    public GoodOrderAdapter(@LayoutRes int layoutResId, @Nullable List<GoodsOrder.DataListBean> data, Handler handler) {
        super(layoutResId, data);
        this.mHandler=handler;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GoodsOrder.DataListBean item) {

        final String tokenId= PreferencesUtils.getString(mContext,"token_id");
        final String  goodsorderId=item.getGoodsorderId();

        if(String.valueOf(item.getCjsj())!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date(item.getCjsj());
            String time=simpleDateFormat.format(date);
            helper.setText(R.id.diingdan_time,time);
        }

        helper.setText(R.id.dingdan_num,"商品:"+item.getGoods().size()+"件");


     if(String.valueOf(item.getTotalPrice())!=null){
         helper.setText(R.id.dingdan_total,"总价:"+item.getTotalPrice()+"元");
     }

     if(String.valueOf(item.getYingshou())!=null){
         helper.setText(R.id.dingdan_get,"应收:"+item.getYingshou()+"元");
     }

     if(String.valueOf(item.getShishou())!=null){
         helper.setText(R.id.dingdan_shouldget,"实收:"+item.getShishou()+"元");
     }

     if(item.getOrderManagerName()!=null){
         helper.setText(R.id.dingdan_creater,"创建人:"+item.getOrderManagerName());
     }

     if(String.valueOf(item.getDikoujia())!=null){
         helper.setText(R.id.dingdan_money,"抵扣金额:"+item.getDikoujia()+"元");
     }

     if(item.getOrders().size()!=0){
         helper.setText(R.id.dingdan_dingjin,"订金:"+item.getOrders().size()+"单");
     }
     else {
         helper.setText(R.id.dingdan_dingjin,"订金:0单");
     }


    if(String.valueOf(item.getGoodsorderNum())!=null&&item.getCustomerName()!=null&&item.getCustomerPhone()!=null){
         helper.setText(R.id.dingdan_title,item.getGoodsorderNum()+"--"+item.getCustomerName()+"("+item.getCustomerPhone()+")");
    }

    if(item.getEnable()!=null){
         if (item.getEnable().endsWith("1")){
             helper.getView(R.id.dingdan_notquit).setVisibility(View.VISIBLE);
             helper.getView(R.id.dingdan_quit).setVisibility(View.GONE);
         }
         else {
             helper.getView(R.id.dingdan_notquit).setVisibility(View.GONE);
             helper.getView(R.id.dingdan_quit).setVisibility(View.VISIBLE);
         }
    }

    helper.getView(R.id.tuidan).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            ExtAlertDialog.showSureDlg2(mContext, "提醒", "确定要退单吗？", "确定", false,new ExtAlertDialog.IExtDlgClick() {
                @Override
                public void Oclick(int result) {
                    if(result==1){
                        if(item.getEnable().equals("0")){
                            ToastUtils.showLong("您已退过此单");
                        }
                        else if(item.getEnable().equals("1")){
                            String url=BaseUrl.BASE_URL+"goodsOrderController.do?method=refundGoodsOrder&token_id="+tokenId+"&goodsorderId="+goodsorderId;
                            Log.d("url",url);
                            try {
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
                            catch (Exception e){
                                ToastUtils.showLong(e.toString());
                            }
                        }
                    }
                }
            });

        }
    });

    }
}

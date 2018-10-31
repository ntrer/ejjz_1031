package com.shushang.aishangjia.activity.adapter;

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
import com.shushang.aishangjia.Bean.LeaguesList;
import com.shushang.aishangjia.Bean.Quit;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ZhangDanAdapter extends BaseQuickAdapter<LeaguesList.DataListBean,BaseViewHolder> {

    private Handler mHandler;
    String tokenId= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),"token_id");
    String leagueFinanceId=null;
    public ZhangDanAdapter(@LayoutRes int layoutResId, @Nullable List<LeaguesList.DataListBean> data, Handler handler) {
        super(layoutResId, data);
        this.mHandler=handler;
    }

    @Override
    protected void convert(BaseViewHolder helper, final LeaguesList.DataListBean item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(item.getCjsj());
        helper.setText(R.id.merchat,item.getLeagueName());
        helper.setText(R.id.prove,"证明人:"+item.getReterenceName());
        helper.setText(R.id.date,String.valueOf(simpleDateFormat.format(date)));
        helper.setText(R.id.money,"金额:"+String.valueOf(item.getLeagueInOut()));
        String type = item.getType();
        if(type.equals("0")){
            helper.getView(R.id.type2).setVisibility(View.VISIBLE);
            helper.getView(R.id.type).setVisibility(View.GONE);
        }
        else if(type.equals("1")){
            helper.getView(R.id.type2).setVisibility(View.GONE);
            helper.getView(R.id.type).setVisibility(View.VISIBLE);
        }

        if(item.getEnable().equals("0")){
            helper.getView(R.id.getPaper).setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.zhangdan_pic,R.mipmap.zfliebiao);
        }
        else {
            helper.setImageResource(R.id.zhangdan_pic,R.mipmap.zhangdandan);
            helper.getView(R.id.getPaper).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.ll_zhangdan);
        helper.getView(R.id.quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leagueFinanceId=item.getLeagueFinanceId();
                if(item.getEnable().equals("1")){
                    final String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=cancelFinance&token_id="+tokenId+"&leagueFinanceId="+leagueFinanceId;
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
                                            ToastUtils.showLong("作废成功");
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
                else {
                    ToastUtils.showLong("您已退过此单");
                }
            }
        });
    }
}

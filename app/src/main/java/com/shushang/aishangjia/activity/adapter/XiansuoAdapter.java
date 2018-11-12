package com.shushang.aishangjia.activity.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Xiansuo;
import com.shushang.aishangjia.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by YD on 2018/8/7.
 */

public class XiansuoAdapter extends BaseQuickAdapter<Xiansuo.DataListBean,BaseViewHolder> {

    public XiansuoAdapter(int item_sheng, List<Xiansuo.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, Xiansuo.DataListBean item) {
        if(item.getName()!=null){
            helper.setText(R.id.people,item.getName());
        }
        if(item.getStatus()!=null){
            switch (item.getStatus()){
                case "1":
                    helper.setText(R.id.intent,"本次:未知");
                 break;
                case "2":
                    helper.setText(R.id.intent,"本次:无意向");
                    break;
                case "3":
                    helper.setText(R.id.intent,"本次:有需求暂无意向");
                    break;
                case "4":
                    helper.setText(R.id.intent,"本次:有意向,需考虑竞品");
                    break;
                case "5":
                    helper.setText(R.id.intent,"本次:有意向,需考虑价格");
                    break;
                case "6":
                    helper.setText(R.id.intent,"本次:考虑成交");
                    break;
            }
        }

        if(item.getOutTime()!=null){
            if (item.getOutTime().equals("0")){
                helper.getView(R.id.outTimrPic).setVisibility(View.GONE);
            }
            else {
                helper.getView(R.id.outTimrPic).setVisibility(View.VISIBLE);
            }
        }

        if(item.getLastFollowupPersonName()!=null){
            helper.setText(R.id.xiugairen,"修改人:"+item.getLastFollowupPersonName());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(item.getNextTime());
        String time=simpleDateFormat.format(date);
        helper.setText(R.id.date,time);

        if(item.getTelephone()!=null){
            helper.setText(R.id.phone,item.getTelephone());
        }

        if(item.getSource()!=null){
            switch (item.getSource()){
                case "1":
                    helper.setText(R.id.source,"来源:微信引流");
                    break;
                case "2":
                    helper.setText(R.id.source,"来源:客户来源");
                    break;
                case "3":
                    helper.setText(R.id.source,"来源:广告");
                    break;
                case "4":
                    helper.setText(R.id.source,"来源:销售拜访");
                    break;
                case "5":
                    helper.setText(R.id.source,"来源:电话");
                    break;
                case "6":
                    helper.setText(R.id.source,"来源:自然进店");
                    break;
                case "7":
                    helper.setText(R.id.source,"来源:网上宣传");
                    break;
                case "8":
                    helper.setText(R.id.source,"来源:朋友宣传");
                    break;
                case "9":
                    helper.setText(R.id.source,"来源:其他");
                    break;
            }
        }

        if(item.getFuZeRen()!=null){
            helper.getView(R.id.share2).setVisibility(View.VISIBLE);
            helper.getView(R.id.share).setVisibility(View.GONE);
        }
        else {
            helper.getView(R.id.share2).setVisibility(View.GONE);
            helper.getView(R.id.share).setVisibility(View.VISIBLE);
        }

    }
}

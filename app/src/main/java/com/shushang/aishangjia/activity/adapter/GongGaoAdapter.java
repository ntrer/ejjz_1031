package com.shushang.aishangjia.activity.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.GongGao;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.GongGaoActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GongGaoAdapter extends BaseQuickAdapter<GongGao.DataListBean,BaseViewHolder> {

    private  GongGaoActivity gongGaoActivity;

    public GongGaoAdapter(@LayoutRes int layoutResId, @Nullable List<GongGao.DataListBean> data, GongGaoActivity gongGaoActivity) {
        super(layoutResId, data);
        this.gongGaoActivity=gongGaoActivity;
    }
    @Override
    protected void convert(BaseViewHolder helper, GongGao.DataListBean item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(item.getCjsj());
        if(item.getIsRead().equals("1")){
            helper.setTextColor(R.id.gonggao_time,gongGaoActivity.getResources().getColor(R.color.colorPrimaryDark));
            helper.setTextColor(R.id.gonggao_content,gongGaoActivity.getResources().getColor(R.color.colorPrimaryDark));
            helper.setTextColor(R.id.gonggao_title,gongGaoActivity.getResources().getColor(R.color.colorPrimaryDark));
            helper.setTextColor(R.id.gonggao_league,gongGaoActivity.getResources().getColor(R.color.color_bg_guide2));
            helper.setImageResource(R.id.notice_pic,R.mipmap.notice_read);
        }
        helper.setText(R.id.gonggao_time,simpleDateFormat.format(date));
        helper.setText(R.id.gonggao_title,item.getLeagueTitle());
        helper.setText(R.id.gonggao_content,item.getLeagueNotice());
        helper.setText(R.id.gonggao_league,item.getLeagueName());
    }
}

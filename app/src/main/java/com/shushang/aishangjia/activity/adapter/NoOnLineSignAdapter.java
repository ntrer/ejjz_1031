package com.shushang.aishangjia.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.ActionCustomersBean;
import com.shushang.aishangjia.R;

import java.text.SimpleDateFormat;
import java.util.List;


public class NoOnLineSignAdapter extends BaseQuickAdapter<ActionCustomersBean,BaseViewHolder> {


    public NoOnLineSignAdapter(int layoutResId, @Nullable List<ActionCustomersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ActionCustomersBean actionCustomersBean) {
        if(actionCustomersBean!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            baseViewHolder.setText(R.id.date,simpleDateFormat.format(actionCustomersBean.getQdsj()));
            baseViewHolder.setText(R.id.people,actionCustomersBean.getCustomerName());
            baseViewHolder.setText(R.id.phone,actionCustomersBean.getCustomerMobile());
            baseViewHolder.setText(R.id.label,actionCustomersBean.getMerchantName());
        }
    }
}

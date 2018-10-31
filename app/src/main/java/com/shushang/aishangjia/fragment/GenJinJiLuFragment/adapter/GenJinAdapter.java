package com.shushang.aishangjia.fragment.GenJinJiLuFragment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Sheng;

import java.util.List;

/**
 * Created by YD on 2018/9/20.
 */

public class GenJinAdapter extends BaseQuickAdapter<Sheng.DataListBean,BaseViewHolder> {

    public GenJinAdapter(int item_sheng, List<Sheng.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, Sheng.DataListBean item) {

    }
}

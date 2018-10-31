package com.shushang.aishangjia.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.ProvePeople;
import com.shushang.aishangjia.R;

import java.util.List;

public class ProvePeopleAdapter extends BaseQuickAdapter<ProvePeople.DataListBean,BaseViewHolder> {

    public ProvePeopleAdapter(int item_sheng, List<ProvePeople.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProvePeople.DataListBean item) {
        helper.setText(R.id.sheng_name,item.getUserName()+"("+item.getMerchantName()+")");
    }
}

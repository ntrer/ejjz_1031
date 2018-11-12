package com.shushang.aishangjia.activity.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.Diko;
import com.shushang.aishangjia.R;

import java.util.HashMap;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by YD on 2018/8/7.
 */

public class DingjinAdapter extends BaseQuickAdapter<Diko.DataListBean,BaseViewHolder> {

    public static HashMap<Integer, Boolean> isSelected;
    private List<Diko.DataListBean> datas;

    public DingjinAdapter(int item_sheng, List<Diko.DataListBean> dataListBeen) {
        super(item_sheng, dataListBeen);
        this.datas=dataListBeen;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }



    @Override
    protected void convert(BaseViewHolder helper, Diko.DataListBean item) {
        SmoothCheckBox view = helper.getView(R.id.checkbox);
        view.setChecked(isSelected.get(helper.getAdapterPosition()),true);
        helper.addOnClickListener(R.id.checkbox);
        if(item.getSource().equals("0")){
            helper.setText(R.id.dingjin,"(日常)"+item.getCustomerManagerName()+"("+String.valueOf(item.getTotalPrice())+")元");
        }
        else {
            helper.setText(R.id.dingjin,"(活动)"+item.getCustomerManagerName()+"("+String.valueOf(item.getTotalPrice())+")元");
        }

    }
}

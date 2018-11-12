package com.shushang.aishangjia.fragment.GenJinJiLuFragment.adapter;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.XiansuoInfo;
import com.shushang.aishangjia.R;

import java.util.List;

/**
 * Created by YD on 2018/9/20.
 */

public class GenJinAdapter extends BaseQuickAdapter<XiansuoInfo.DataBean.ClueActionsBean,BaseViewHolder> {

    public GenJinAdapter(int item_sheng, List<XiansuoInfo.DataBean.ClueActionsBean> dataListBeen) {
        super(item_sheng, dataListBeen);
    }

    @Override
    protected void convert(BaseViewHolder helper, XiansuoInfo.DataBean.ClueActionsBean item) {
        try {
            helper.setText(R.id.genjin_date,item.getTime());
            helper.setText(R.id.xiansuoren,item.getTitle());
            helper.setText(R.id.xiansuo_content,item.getDetalInfo());
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

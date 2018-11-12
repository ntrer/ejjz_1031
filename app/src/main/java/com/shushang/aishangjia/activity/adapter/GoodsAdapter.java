package com.shushang.aishangjia.activity.adapter;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.othershe.library.NiceImageView;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.R;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by YD on 2018/8/7.
 */

public class GoodsAdapter extends BaseQuickAdapter<Good.DataListBean,BaseViewHolder> {


    private List<Good.DataListBean> datas;
    public static HashMap<Integer, Boolean> isSelected;

    public GoodsAdapter(int item_sheng, List<Good.DataListBean> dataListBeen) {
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


//    public void remove(int position){
//        datas.remove(position);
//        notifyItemRemoved(position);
//    }


    @Override
    protected void convert(BaseViewHolder helper, Good.DataListBean item) {
        SmoothCheckBox view = helper.getView(R.id.checkbox);
        view.setChecked(isSelected.get(helper.getAdapterPosition()),true);
        NiceImageView niceImageView = helper.getView(R.id.good_pic);
        helper.addOnClickListener(R.id.checkbox);
        if(item.getGoodsImages()!=null){
            String[] split = item.getGoodsImages().split(",");
            String url="http://192.168.0.55/fileController.do?method=showimage&id="+split[0]+ "&token_id="+PreferencesUtils.getString(mContext,"token_id");
            Glide.with(mContext).load(url).override(ConvertUtils.dp2px(80),ConvertUtils.dp2px(80)).centerCrop().into(niceImageView);
        }
        if(item.getGoodsName()!=null){
            helper.setText(R.id.good_name,item.getGoodsName());
        }
        if(item.getGoodsCode()!=null&&item.getGoodsSpecs()!=null){
            helper.setText(R.id.good_info,"编号:"+item.getGoodsCode()+"/规格:"+item.getGoodsSpecs());
        }

        if(String.valueOf(item.getGoodsRetailPrice())!=null){
            helper.setText(R.id.good_price,"零售价:￥"+item.getGoodsRetailPrice());
        }




    }
}

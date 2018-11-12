package com.shushang.aishangjia.activity.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.othershe.library.NiceImageView;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.DingDanActivity2;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by YD on 2018/8/7.
 */

public class DingDanAdapter2 extends BaseQuickAdapter<Good.DataListBean,BaseViewHolder> {

   private LinearLayout mLinearLayout;
   private TextView mTextView,mTextView2;
   private Button mButton;
   private double price;
   private double old_price;
   private double youhui_price;
   private Context mContext;
   private Button btn_youhui;
   private double value=1.0;
   private List<Good.DataListBean> dataListBeen;
   private boolean isYouHui=false;
   private int count;
   private ImageView mImageView1,mImageView2;
    public static HashMap<Integer, Boolean> isSelected;

    public DingDanAdapter2(int item_sheng, List<Good.DataListBean> dataListBeen, LinearLayout llCheckAll, double price, DingDanActivity2 dingDanActivity) {
        super(item_sheng, dataListBeen);
        mLinearLayout=llCheckAll;
        this.price=price;
        this.old_price=price;
        mContext=dingDanActivity;
        this.dataListBeen=dataListBeen;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < dataListBeen.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Good.DataListBean item) {
        SmoothCheckBox view = helper.getView(R.id.checkbox);
        view.setChecked(isSelected.get(helper.getAdapterPosition()),true);
        helper.addOnClickListener(R.id.checkbox);
        helper.addOnClickListener(R.id.delete);
        helper.addOnClickListener(R.id.btn_reduce);
        helper.addOnClickListener(R.id.btn_add);
        helper.addOnClickListener(R.id.youhui_btn);
        if(item.getGoodsImages()!=null){
            String[] split = item.getGoodsImages().split(",");
            String url="http://192.168.0.55/fileController.do?method=showimage&id="+split[0]+ "&token_id="+PreferencesUtils.getString(mContext,"token_id");
            Glide.with(mContext).load(url).override(ConvertUtils.dp2px(80),ConvertUtils.dp2px(80)).centerCrop().placeholder(R.mipmap.img).into((NiceImageView) helper.getView(R.id.good_pic));
        }
        if(item.getGoodsName()!=null){
            helper.setText(R.id.product_name,item.getGoodsName());
        }

        if(item.getGoodsCode()!=null){
            helper.setText(R.id.product_info,"(编号:"+item.getGoodsCode()+")");
        }


        if(String.valueOf(item.getGoodsRetailPrice())!=null){
            helper.setText(R.id.youhui_danjia_price,item.getGoodsRetailPrice()+"");
            helper.setText(R.id.lingshou_price,item.getGoodsRetailPrice()+"");
        }


        if(item.getGoodsSpecs()!=null){
            helper.setText(R.id.guige,"规格:"+item.getGoodsSpecs());
        }

        if(String.valueOf(item.getGoodsRetailPrice())!=null){
            helper.setText(R.id.total_money_text,item.getGoodsRetailPrice()+"");
        }


    }
}

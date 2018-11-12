package com.shushang.aishangjia.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.othershe.library.NiceImageView;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;

public class MultiAdapter extends RecyclerView.Adapter {

    private List<Good.DataListBean> datas;
    public static HashMap<Integer, Boolean> isSelected;

    public MultiAdapter(List<Good.DataListBean> datas) {
        this.datas = datas;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MultiViewHolder){
            final MultiViewHolder viewHolder = (MultiViewHolder) holder;
            Good.DataListBean dataListBean = datas.get(position);
            viewHolder.mCheckBox.setChecked(isSelected.get(position),true);
            if(dataListBean.getGoodsImages()!=null){
                String[] split = dataListBean.getGoodsImages().split(",");
                String url="http://192.168.0.55/fileController.do?method=showimage&id="+split[0]+ "&token_id="+PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),"token_id");
                Glide.with(MyApplication.getInstance().getApplicationContext()).load(url).override(ConvertUtils.dp2px(80),ConvertUtils.dp2px(80)).centerCrop().into(((MultiViewHolder) holder).mNiceImageView);
            }
            if(dataListBean.getGoodsName()!=null){
                ((MultiViewHolder) holder).mTextView1.setText(dataListBean.getGoodsName());
            }
            if(dataListBean.getGoodsCode()!=null&&dataListBean.getGoodsSpecs()!=null){
                ((MultiViewHolder) holder).mTextView2.setText("编号:"+dataListBean.getGoodsCode()+"/规格:"+dataListBean.getGoodsSpecs());
            }

            if(String.valueOf(dataListBean.getGoodsRetailPrice())!=null){
                ((MultiViewHolder) holder).mTextView3.setText("零售价:￥"+dataListBean.getGoodsRetailPrice());
            }

            if (mOnItemClickLitener != null)
            {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mOnItemClickLitener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder{
        NiceImageView mNiceImageView;
        TextView mTextView1,mTextView2,mTextView3;
        SmoothCheckBox mCheckBox;
        public MultiViewHolder(View itemView) {
            super(itemView);
            mCheckBox =itemView.findViewById(R.id.checkbox);
            mNiceImageView = itemView.findViewById(R.id.good_pic);
            mTextView1=itemView.findViewById(R.id.good_name);
            mTextView2=itemView.findViewById(R.id.good_info);
            mTextView3=itemView.findViewById(R.id.good_price);
        }
    }

}

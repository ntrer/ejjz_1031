package com.shushang.aishangjia.fragment.AppFragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushang.aishangjia.Bean.MenuItem;
import com.shushang.aishangjia.R;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.MyViewHolder> {

    private List<MenuItem> list;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    public AppAdapter(List<MenuItem> list) {
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView iv_icon;
        private MyItemClickListener mItemClickListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.menu_text);
            iv_icon=itemView.findViewById(R.id.menu_img);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MenuItem menuItem = list.get(position);
        holder.name.setText(menuItem.getName());
        holder.iv_icon.setBackgroundResource(menuItem.getImg_id());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null)
                    itemClickListener.onItemClick(holder.name.getText().toString(),position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mOnItemLongClickListener!=null){
                    mOnItemLongClickListener.onItemLongClick();
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    //点击事件接口
    public interface OnItemClickListener{
        void onItemClick(String name,int position);
    }
    //设置点击事件的方法
    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    //点击事件接口
    public interface OnItemLongClickListener{
        void onItemLongClick();
    }
    //设置点击事件的方法
    public void setOnItemLongClickListener(OnItemLongClickListener LongitemClickListener){
        this.mOnItemLongClickListener = LongitemClickListener;
    }

}


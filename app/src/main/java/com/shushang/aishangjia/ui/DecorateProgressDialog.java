package com.shushang.aishangjia.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shushang.aishangjia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/9/5.
 */

public class DecorateProgressDialog extends Dialog  {

    private OnDecorateProgressDialogListener mOnDecorateProgressDialogListener;

    private ListView mLvContent;
    private String select_text;
    private ArrayList<DecorateBean> mSelectItemList = new ArrayList<>();
    private ArrayList<DecorateBean> mDecorateBeanList;
    private DecorateAdapter mDecorateAdapter;
    private Context mContext;

    public DecorateProgressDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initDialog();
    }

    /**
     * 清除选中记录
     */
    public void clearSelectRecord() {
        for (DecorateBean decorateBean : mSelectItemList) {
            decorateBean.isSelect = false;
        }
        mDecorateAdapter.notifyDataSetChanged();
    }

    /**
     * 显示原有记录
     *
     * @param oldRecords
     */
    public void displayOldRecords(String oldRecords) {
        String decorateProgressText = oldRecords.trim();
        if (TextUtils.isEmpty(decorateProgressText))
            return;

        String otherWithColon = mContext.getResources().getString(R.string.other_with_colon);
        String choiceContent = oldRecords;
//        if (oldRecords.contains(otherWithColon)) {
//            int otherIndex = oldRecords.indexOf(otherWithColon);
//            String otherContent = oldRecords.substring(otherIndex + 3, oldRecords.length());
//            mEtOther.requestFocus();
//            mEtOther.setText(otherContent);
//            mEtOther.setSelection(otherContent.length());
//            choiceContent.substring(0, otherIndex);
//        }
        for (DecorateBean decorateBean : mDecorateBeanList) {
            if (choiceContent.contains(decorateBean.name)) {
                decorateBean.isSelect = true;
            }
        }
        mDecorateAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化对话框
     */
    private void initDialog() {
        setContentView(R.layout.dialog_decorate_progress);

        mLvContent = (ListView) findViewById(R.id.lv_content);
//        findViewById(R.id.ll_other_container).setVisibility(View.GONE);

        mDecorateBeanList = new ArrayList<>();
//        mDecorateBeanList.add(new DecorateBean("木门"));
//        mDecorateBeanList.add(new DecorateBean("瓦工"));
//        mDecorateBeanList.add(new DecorateBean("木工"));
//        mDecorateBeanList.add(new DecorateBean("油漆"));

        mDecorateBeanList.add(new DecorateBean("客户介绍"));
        mDecorateBeanList.add(new DecorateBean("广告"));
        mDecorateBeanList.add(new DecorateBean("销售拜访"));

        mDecorateBeanList.add(new DecorateBean("电话"));
        mDecorateBeanList.add(new DecorateBean("自然进店"));
        mDecorateBeanList.add(new DecorateBean("网上宣传"));
        mDecorateBeanList.add(new DecorateBean("朋友圈宣传"));

//        mDecorateBeanList.add(new DecorateBean("未知 "));
//        mDecorateBeanList.add(new DecorateBean("无意向"));
//        mDecorateBeanList.add(new DecorateBean("有需求暂无意向"));
//        mDecorateBeanList.add(new DecorateBean("有意向，需考虑竞品"));
//
//        mDecorateBeanList.add(new DecorateBean("有意向，需考虑价格"));
//        mDecorateBeanList.add(new DecorateBean("非常有意向，考虑成交"));
        mDecorateAdapter = new DecorateAdapter(mDecorateBeanList);
        mLvContent.setAdapter(mDecorateAdapter);

        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadioButton rbSelectStatus = (RadioButton) view.findViewById(R.id.rb_select_status);
                DecorateBean decorateBean = mDecorateBeanList.get(position);

                if (decorateBean.isSelect) {
                    decorateBean.isSelect = false;
                    rbSelectStatus.setChecked(false);
                    mSelectItemList.remove(decorateBean);
                } else {
                    decorateBean.isSelect = true;
                    rbSelectStatus.setChecked(true);
                    mSelectItemList.add(decorateBean);
                }

                for(int i=0;i<mDecorateBeanList.size();i++){
                    mDecorateBeanList.get(i).isSelect=false;
                    rbSelectStatus.setSelected(false);
                }

                select_text=decorateBean.name;
                if (mOnDecorateProgressDialogListener != null)
                    mOnDecorateProgressDialogListener.onDecorateProgressDialogClick(select_text.toString());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 300);
            }
        });

//        mBtnConfirm.setOnClickListener(this);


        Window window = this.getWindow();
        //位于屏幕中间
        window.setGravity(Gravity.CENTER);
        //添加动画
        window.setWindowAnimations(R.style.dialog_style);
    }

    public void setListener(OnDecorateProgressDialogListener listener) {
        mOnDecorateProgressDialogListener = listener;
    }




    public interface OnDecorateProgressDialogListener {
        void onDecorateProgressDialogClick(String itemName);
    }

    public class DecorateAdapter extends BaseAdapter {

        private List<DecorateBean> mDataList;

        public DecorateAdapter(List<DecorateBean> dataList) {
            mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public DecorateBean getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_decorate_progress_listview_item, parent, false);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.rbSelectStatus = (RadioButton) convertView.findViewById(R.id.rb_select_status);
                convertView.setTag(holder);
            } else holder = (ViewHolder) convertView.getTag();

            DecorateBean decorateBean = getItem(position);
            if (decorateBean.isSelect) {
                holder.rbSelectStatus.setChecked(true);
                if (!mSelectItemList.contains(decorateBean))
                    mSelectItemList.add(decorateBean);
            } else
                holder.rbSelectStatus.setChecked(false);

            holder.tvName.setText(decorateBean.name);
            return convertView;
        }
    }

    public class DecorateBean {

        public DecorateBean(String name) {
            this.name = name;
        }

        public String name;
        public boolean isSelect;
    }

    public class ViewHolder {
        TextView tvName;
        RadioButton rbSelectStatus;

    }
}

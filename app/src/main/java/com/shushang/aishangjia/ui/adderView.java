package com.shushang.aishangjia.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;

import butterknife.OnClick;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/15 15:41
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：自定义删除增加按钮
 */
public class adderView extends LinearLayout implements View.OnClickListener {
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;
    private final TextView tvCount;

    private boolean isreduce=false;

    public adderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.add_sub_view, this);
        ImageView btn_reduce = (ImageView) view.findViewById(R.id.btn_reduce);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ImageView btn_add = (ImageView) view.findViewById(R.id.btn_add);
        btn_reduce.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        //设置默认值
        int value = getValue();
        setValue(value);
    }

    @OnClick({R.id.btn_reduce, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }

    /**
     * 如果当前值大于最小值   减
     */
    private void reduce() {
        if (value > minValue) {
            value--;
        }
        setValue(value);
        setIsreduce(true);
        if (onValueChangeListene != null) {
            onValueChangeListene.onValueChange(value);
        }
    }

    /**
     * 如果当前值小于最小值  加
     */
    private void add() {
        if (value < maxValue) {
            value++;
        }
        setValue(value);
        setIsreduce(false);
        if (onValueChangeListene != null) {
            onValueChangeListene.onValueChange(value);
        }
    }

    //获取具体值
    public int getValue() {
        String countStr = tvCount.getText().toString().trim();
        if (countStr != null) {
            value = Integer.valueOf(countStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvCount.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }


    public boolean isIsreduce() {
        return isreduce;
    }

    public void setIsreduce(boolean isreduce) {
        this.isreduce = isreduce;
    }



    //监听回调
    public interface OnValueChangeListener {
        public void onValueChange(int value);
    }

    private OnValueChangeListener onValueChangeListene;

    public void setOnValueChangeListene(OnValueChangeListener onValueChangeListene) {
        this.onValueChangeListene = onValueChangeListene;
    }
}


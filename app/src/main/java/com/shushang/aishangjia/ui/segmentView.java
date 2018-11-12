package com.shushang.aishangjia.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;

public class segmentView extends LinearLayout {
    private TextView leftTextView;
    private TextView rightTextView;
    private onSegmentViewClickListener segmentListener;

    public segmentView(Context context) {
        this(context, null);
    }

    public segmentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public segmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        leftTextView = new TextView(getContext());
        rightTextView = new TextView(getContext());
        //设置文字大小
        leftTextView.setText("订金");
        rightTextView.setText("订单");
        setSegmentTextSize(15);
        leftTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        rightTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        //设置textView的内容位置居中
        leftTextView.setGravity(Gravity.CENTER);
        rightTextView.setGravity(Gravity.CENTER);
        //设置textView的内边距
        leftTextView.setPadding(20, 8, 20, 8);
        rightTextView.setPadding(20, 8, 20, 8);

        //设置背景资源
        leftTextView.setBackgroundResource(R.drawable.segment_left_background);
        rightTextView.setBackgroundResource(R.drawable.segment_right_background);
        //默认左侧textView为选中状态
        leftTextView.setSelected(true);
        setSelectTextColor(0);
        this.removeAllViews();
        //加入textView
        this.addView(leftTextView);
        this.addView(rightTextView);
        this.invalidate();

        leftTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftTextView.isSelected()) {
                    return;
                }
                leftTextView.setSelected(true);
                rightTextView.setSelected(false);
                setSelectTextColor(0);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(leftTextView, 0);
                }
            }
        });

        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightTextView.isSelected()) {
                    return;
                }
                rightTextView.setSelected(true);
                leftTextView.setSelected(false);
                setSelectTextColor(1);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(rightTextView, 1);
                }
            }
        });
    }

    /**
     * 设置字体大小
     *
     * @param size
     */
    private void setSegmentTextSize(int size) {
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置选中的状态并改变字体的颜色
     *
     * @param position
     */
    public void setSelectTextColor(int position) {
        if (position == 0) {
            leftTextView.setSelected(true);
            rightTextView.setSelected(false);
            //设置字体的颜色
            leftTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            rightTextView.setTextColor(getResources().getColor(R.color.white));
        }
        if (position == 1) {
            leftTextView.setSelected(false);
            rightTextView.setSelected(true);
            rightTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            leftTextView.setTextColor(getResources().getColor(R.color.white));
        }
    }


    /**
     * 设置控件显示的文字
     *
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            leftTextView.setText(text);
        }
        if (position == 1) {
            rightTextView.setText(text);

        }
    }

    //定义一个接口接收点击事件
    public interface onSegmentViewClickListener {
        void onSegmentViewClick(View view, int position);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener segmentListener) {
        this.segmentListener = segmentListener;
    }
}


package com.shushang.aishangjia.fragment.ShopTopFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.XianSuoChiActivity;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by YD on 2018/9/12.
 */

public class ShopTopFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.loading)
    RelativeLayout mLoading;
    @BindView(R.id.ll_pk)
    LinearLayout mLlPk;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.ll_xiansuo)
    LinearLayout mLlXiansuo;
    @BindView(R.id.ll_customer)
    LinearLayout mLlCustomer;
    @BindView(R.id.ll_sign)
    LinearLayout mLlSign;
    @BindView(R.id.rv_sign)
    RecyclerView mRvSign;
    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSrlHome;
    private View mView;
    Unbinder unbinder;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mView = View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview7, null);

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shop_top, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }




    @OnClick(R.id.ll_xiansuo)
    void go() {
        startActivity(new Intent(getActivity(), XianSuoChiActivity.class));
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

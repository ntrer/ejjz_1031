package com.shushang.aishangjia.fragment.CustomerDetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseFragment;

/**
 * Created by YD on 2018/9/20.
 */

public class CustomerDetailFragment extends BaseFragment {

    public CustomerDetailFragment() {
        // Required empty public constructor
    }

    public static CustomerDetailFragment newInstance() {
        return  new CustomerDetailFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_ziliao, null);
        return view;
    }
}

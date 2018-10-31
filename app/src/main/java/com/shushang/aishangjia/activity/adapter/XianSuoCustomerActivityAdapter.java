package com.shushang.aishangjia.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shushang.aishangjia.base.BaseViewPagerAdapter;

/**
 * Created by YD on 2018/9/20.
 */

public class XianSuoCustomerActivityAdapter extends BaseViewPagerAdapter {

    public XianSuoCustomerActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
}

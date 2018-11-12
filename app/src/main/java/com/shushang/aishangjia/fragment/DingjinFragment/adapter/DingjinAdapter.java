package com.shushang.aishangjia.fragment.DingjinFragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.shushang.aishangjia.base.BaseViewPagerAdapter;

public class DingjinAdapter extends BaseViewPagerAdapter {

    public DingjinAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
}

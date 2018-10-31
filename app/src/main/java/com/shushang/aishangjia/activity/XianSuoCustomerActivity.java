package com.shushang.aishangjia.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.XianSuoCustomerActivityAdapter;
import com.shushang.aishangjia.fragment.CustomerDetailFragment.CustomerDetailFragment;
import com.shushang.aishangjia.fragment.GenJinJiLuFragment.GenJinJiLuFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shushang.aishangjia.R.id.viewpager;

public class XianSuoCustomerActivity extends AppCompatActivity {


    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.money_detail)
    TextView mMoneyDetail;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.time_detail)
    TextView mTimeDetail;
    @BindView(R.id.jieduan)
    TextView mJieduan;
    @BindView(R.id.jieduan_detail)
    TextView mJieduanDetail;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(viewpager)
    ViewPager mViewpager;
    @BindView(R.id.cl)
    CoordinatorLayout mCl;
    @BindView(R.id.bottom_edit)
    LinearLayout mBottomEdit;
    @BindView(R.id.bottom_edit2)
    LinearLayout mBottomEdit2;

    private  int selectedTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xian_suo_customer);
        ButterKnife.bind(this);
        initPageView();
        initToobar();
        SetUpViewPager(mViewpager);
        mSlidingTabs.setupWithViewPager(mViewpager);
        setIndicator(mSlidingTabs, 60, 60);
        mBottomEdit.setVisibility(View.VISIBLE);
        mBottomEdit2.setVisibility(View.GONE);
        mSlidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 selectedTabPosition = mSlidingTabs.getSelectedTabPosition();
                if (selectedTabPosition == 1) {
                    mBottomEdit2.setVisibility(View.VISIBLE);
                    mBottomEdit.setVisibility(View.GONE);
                }
                else {
                    mBottomEdit.setVisibility(View.VISIBLE);
                    mBottomEdit2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        EventBus.getDefault().register(this);
    }


    /**
     * 初始化toolbar
     */
    private void initToobar() {
//        设置支持toolbar
        setSupportActionBar(mToolbar);
//        设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化MD风格的界面
     *
     * @param
     */
    private void initPageView() {
        mMovieCollapsingToolbar.setTitle("候慧峰");
        mMovieCollapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    /**
     * 返回上级
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetUpViewPager(ViewPager bookViewpager) {
        XianSuoCustomerActivityAdapter adapter = new XianSuoCustomerActivityAdapter(getSupportFragmentManager());
        adapter.addFragment(GenJinJiLuFragment.newInstance(), "跟进记录");
        adapter.addFragment(CustomerDetailFragment.newInstance(), "详细资料");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);

            child.invalidate();
        }

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(MessageEvent messageEvent) {
//        mAppbar.setExpanded(false);
//    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }
}

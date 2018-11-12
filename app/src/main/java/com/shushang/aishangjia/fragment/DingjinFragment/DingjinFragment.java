package com.shushang.aishangjia.fragment.DingjinFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.GoodsActivity;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.fragment.DingDanFragment.DingDanFragment;
import com.shushang.aishangjia.fragment.DingjinFragment.adapter.DingjinAdapter;
import com.shushang.aishangjia.fragment.YiXiangJinFragment.YiXiangJinFragment;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.ui.segmentView;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

public class DingjinFragment extends BaseFragment implements OnFabClickListener {

    segmentView mSegmentView;
    Toolbar mToolbar;
    ViewPager mViewpager;
    SuspensionFab mFabTop;
    private String resourceName=null;
    private Handler fabHandler=new Handler();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_GONGGAO= 2013;
    private static final int REQUEST_CODE_MYXIANSUO = 9090;
    private static final int REQUEST_CODE_NEW_PEOPLE =2662;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        SetUpViewPager(mViewpager);
        mSegmentView.setOnSegmentViewClickListener(new segmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View view, int position) {
                if(position==0){
                    mViewpager.setCurrentItem(0,true);
                }
                else if(position==1){
                    mViewpager.setCurrentItem(1,true);
                }
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_dingjin, null);
        mSegmentView=view.findViewById(R.id.segmentView);
        mToolbar=view.findViewById(R.id.toolbar);
        mViewpager=view.findViewById(R.id.viewpager);
        mFabTop=view.findViewById(R.id.fab_top);
        resourceName= PreferencesUtils.getString(mContext,"ResourceName");
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.note))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes xiansuo = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.xiansuo))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(3)
                .build();

        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.people_add))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

        FabAttributes email2 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_activity))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(4)
                .build();

        FabAttributes email3 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_4_coloring))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(5)
                .build();

        FabAttributes email4 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.dingdan))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(6)
                .build();

        if(resourceName!=null&&resourceName.equals("1116")){
            mFabTop.addFab(collection,xiansuo,email,email2,email3,email4);
        }
        else {
            mFabTop.addFab(collection,email,email2,email3,email4);
        }
        mFabTop.setAnimationManager(new FabAlphaAnimate(mFabTop));
        mFabTop.setFabClickListener(this);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              if(position==0){
                 mSegmentView.setSelectTextColor(0);
              }
              else {
                  mSegmentView.setSelectTextColor(1);
              }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }



    private void SetUpViewPager(ViewPager bookViewpager) {
        DingjinAdapter adapter = new DingjinAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(YiXiangJinFragment.newInstance(), "");
        adapter.addFragment(DingDanFragment.newInstance(), "");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }


    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), SignActivity.class),REQUEST_CODE_SIGN);
        }else if (tag.equals(2)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFabTop.closeAnimate();
                }
            },1000);
//            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(3)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), XiansuoActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(4)){
            mFabTop.closeAnimate();
            //表示所有权限都授权了
            Intent openCameraIntent = new Intent(getActivity(), ProActivityActivity2.class);
            openCameraIntent.putExtra("type", "3");
            openCameraIntent.putExtra("event","6");
            startActivityForResult(openCameraIntent, REQUEST_CODE_ACTIVITY );
        }
        else if (tag.equals(5)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), DailyOrderActivity.class),REQUEST_CODE_DAILY);
        }
        else if (tag.equals(6)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), GoodsActivity.class),REQUEST_CODE_DAILY);
        }
    }
}

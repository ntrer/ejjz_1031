package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseUrl;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.ArrayList;

public class PhotoViewActivity extends FragmentActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ArrayList<ImageView> mList;
    private TextView textView;
    private String showphoto[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initShowphoto();
        initView();
    }


    private void initShowphoto() {
        //Bundle bundle = getIntent().getExtras();
        Intent intent=getIntent();
        showphoto =  intent.getStringArrayExtra("showphoto");
        viewPager=(ViewPager)findViewById(R.id.ViewPager);
        viewPager.setCurrentItem(0);
    }


    private void initView() {
        textView=(TextView)findViewById(R.id.text);
        textView.setText("1/"+showphoto.length);
        mList = new ArrayList<ImageView>();
        pagerAdapter = new PagerAdapter() {

            // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
            @Override
            public int getCount() {
                return showphoto.length;

                //return Integer.MAX_VALUE;    返回一个比较大的值，目的是为了实现无限轮播
            }

            // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，
            // 我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(PhotoViewActivity.this);
                String url= BaseUrl.BASE_URL +"fileController.do?method=showimage&id="+showphoto[position]+ "&token_id="+PreferencesUtils.getString(PhotoViewActivity.this,"token_id");
                Glide.with(PhotoViewActivity.this).load(url).into(imageView);

                //使图片具有放缩功能
//                PhotoViewAttacher mAttacher=new PhotoViewAttacher(imageView);
//                mAttacher.update();

                container.addView(imageView);
                mList.add(imageView);
                return imageView;
            }

            //PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mList.get(position));
            }


        };

        viewPager.setAdapter(pagerAdapter);

        //设置滑动监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //滑动到第几张图片的调用的方法，position当前显示图片位置
            @Override
            public void onPageSelected(int position) {
                textView.setText((position+1)+"/"+showphoto.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


}

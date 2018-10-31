package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shushang.aishangjia.Bean.LeaguesList;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoneyInfoActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView,mImageView2;
    private List<String> imgId=new ArrayList<>();
    @Override
    public int setLayout() {
        return R.layout.activity_money_info;
    }

    @Override
    public void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mTextView1=findViewById(R.id.et_lianmeng_name);
        mTextView2=findViewById(R.id.et_lianmeng_money);
        mTextView3=findViewById(R.id.et_reason);
        mImageView2=findViewById(R.id.zuofei);
        mTextView4=findViewById(R.id.tv_beizhu);
        mTextView5=findViewById(R.id.tv_prove);
        mTextView6=findViewById(R.id.et_date);
        mImageView=findViewById(R.id.pic_money);
        mRelativeLayout=findViewById(R.id.pic_detail);
        final Intent data=getIntent();
        final LeaguesList.DataListBean dataListBean= (LeaguesList.DataListBean) data.getSerializableExtra("data");
        initData(dataListBean);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initData(LeaguesList.DataListBean dataListBean) {
        if(dataListBean.getEnable().equals("1")){
            mImageView2.setVisibility(View.GONE);
        }
        else {
            mImageView2.setVisibility(View.VISIBLE);
        }
        if(dataListBean.getLeagueName()!=null){
            mTextView1.setText(String.valueOf(dataListBean.getLeagueName()));
        }
        if(String.valueOf(dataListBean.getLeagueInOut())!=null){
            if (dataListBean.getType().equals("0")){
                mTextView2.setText(String.valueOf("-"+dataListBean.getLeagueInOut()));
                mTextView2.setTextColor(getResources().getColor(R.color.md_green_500));
            }
            else if(dataListBean.getType().equals("1")){
                mTextView2.setText(String.valueOf("+"+dataListBean.getLeagueInOut()));
                mTextView2.setTextColor(getResources().getColor(R.color.md_deep_orange_A700));
            }
        }
        if(dataListBean.getUseCase()!=null){
            mTextView3.setText(String.valueOf(dataListBean.getUseCase()));
        }
        if(dataListBean.getBeizhu()!=null){
            mTextView4.setText(String.valueOf(dataListBean.getBeizhu()));
        }
        if(dataListBean.getReterenceName()!=null){
            mTextView5.setText(String.valueOf(dataListBean.getReterenceName()));
        }
        if(String.valueOf(dataListBean.getCjsj())!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(dataListBean.getCjsj());
            mTextView6.setText(String.valueOf(simpleDateFormat.format(date)));
        }

        if(dataListBean.getImageIds()==null||dataListBean.getImageIds().equals("")){
            Log.d("getImageIds","无图言屌");
        }
        else {
            final String[] split = dataListBean.getImageIds().split(",");
            String url="http://192.168.0.55:8999/fileController.do?method=showimage&id="+split[0]+ "&token_id="+PreferencesUtils.getString(MoneyInfoActivity.this,"token_id");
            Glide.with(MoneyInfoActivity.this).load(url).into(mImageView);
            mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MoneyInfoActivity.this,PhotoViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("showphoto", split);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

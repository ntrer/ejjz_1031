package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.XiansuoInfo;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.XianSuoCustomerActivityAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.CustomerDetailFragment.CustomerDetailFragment;
import com.shushang.aishangjia.fragment.GenJinJiLuFragment.GenJinJiLuFragment;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

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
//    @BindView(R.id.bottom_edit)
//    LinearLayout mBottomEdit;
    @BindView(R.id.bottom_edit2)
    LinearLayout mBottomEdit2;
    private ImageView mImageView;
    private String token_id=null;
    private  int selectedTabPosition;
    private TextView mTextView;
    private String phone;
    private Dialog mRequestDialog;
    private String clueId;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5;
    private static final int REQUEST_UPDATE_XIANSUO = 8012;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xian_suo_customer);
        ButterKnife.bind(this);
        initToobar();
        mSlidingTabs.setupWithViewPager(mViewpager);
        setIndicator(mSlidingTabs, 60, 60);
        Intent intent=getIntent();
        clueId = intent.getStringExtra("clueId");
        PreferencesUtils.putString(XianSuoCustomerActivity.this,"clueId",clueId);
        phone = intent.getStringExtra("phone");
        PreferencesUtils.putString(XianSuoCustomerActivity.this,"phoneNum",phone);
        token_id= PreferencesUtils.getString(this,"token_id");
        SetUpViewPager(mViewpager);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mRequestDialog.setCancelable(false);
        getData(clueId,token_id);
        mBottomEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(XianSuoCustomerActivity.this,UpdateXiansuoActivity.class);
                startActivityForResult(intent1,REQUEST_UPDATE_XIANSUO);
            }
        });
    }

    private void getData( String clueIds, String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/clueController.do?method=getClueById&token_id="+token_id+"&clueId="+clueIds;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                if(response!=null){
                                    XiansuoInfo xiansuo = JSONUtil.fromJson(response, XiansuoInfo.class);
                                    if(xiansuo.getRet().equals("200")){
                                        if(xiansuo.getData()!=null){
                                            PreferencesUtils.putString(XianSuoCustomerActivity.this, "genjinData",response);
                                            XiansuoInfo.DataBean data = xiansuo.getData();
                                            showData(data);
                                        }
                                        else {

                                        }
                                    }
                                    else if(xiansuo.getRet().equals("101")){
                                        Toast.makeText(XianSuoCustomerActivity.this, ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(XianSuoCustomerActivity.this,"token_id",null);
                                        startActivity(new Intent(XianSuoCustomerActivity.this, LoginActivity2.class));
                                        finish();
                                    }
                                    else if(xiansuo.getRet().equals("201")){
                                        Toast.makeText(XianSuoCustomerActivity.this, ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){
                                ToastUtils.showLong(e.toString());
                            }

                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {

                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {

                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();

                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){

            ToastUtils.showLong(e.toString());
        }
    }

    private void showData(XiansuoInfo.DataBean data) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            if(data.getIsSelf()!=null&&data.getIsSelf().equals("0")){
                mBottomEdit2.setVisibility(View.GONE);
            }
            else if(data.getIsSelf()!=null&&data.getIsSelf().equals("1")){
                mBottomEdit2.setVisibility(View.VISIBLE);
            }
            if(data.getOutTime()!=null&&data.getOutTime().equals("0")){
                mImageView.setVisibility(View.GONE);
            }
            else if(data.getOutTime()!=null&&data.getOutTime().equals("1")){
                mImageView.setVisibility(View.VISIBLE);
            }
            if(phone!=null){
                mTextView1.setText(phone);
            }

            if(data.getName()!=null){
                mTextView5.setText(data.getName());
            }

            if(data.getStatus()!=null){
                switch (data.getStatus()){
                    case "1":
                        mTextView2.setText("未知");
                        break;
                    case "2":
                        mTextView2.setText("无意向");
                        break;
                    case "3":
                        mTextView2.setText("有需求暂无意向");
                        break;
                    case "4":
                        mTextView2.setText("有意向，需考虑竞品");
                        break;
                    case "5":
                        mTextView2.setText("有意向，需考虑价格");
                        break;
                    case "6":
                        mTextView2.setText("非常有意向，考虑成交");
                        break;

                }
            }


            mTextView3.setText(simpleDateFormat.format(new Date(data.getNowTime())));
            mTextView4.setText(simpleDateFormat.format(new Date(data.getNextTime())));
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }


    /**
     * 初始化toolbar
     */
    private void initToobar() {
        mTextView1=findViewById(R.id.phone_detail);
        mTextView2=findViewById(R.id.intent_detail);
        mTextView3=findViewById(R.id.money_detail);
        mTextView4=findViewById(R.id.time_detail);
        mImageView=findViewById(R.id.outtime);
        mTextView5=findViewById(R.id.name_detail);
        mToolbar.setTitle("");
//        设置支持toolbar
        setSupportActionBar(mToolbar);
//        设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化MD风格的界面
     *
     * @param
     * @param name
     */
    private void initPageView(String name) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_UPDATE_XIANSUO){
            getData(clueId,token_id);
            EventBus.getDefault().post(new MessageEvent("跟进记录"));
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
    protected void onDestroy() {
        super.onDestroy();
    }
}

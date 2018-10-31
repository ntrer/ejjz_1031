package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.MoneyFragment.adapter.MoneyPeopleRecyclerViewAdapter;
import com.shushang.aishangjia.fragment.MoneyFragment.refreshHandler.MoneyDataRefreshHandler;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class YiXiangJInActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView,mSignPeopleRecyclerView,mRecyclerView2;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private TextView mTextView;
    private ImageView mSearch,tabTextView;
    private ImageView mImageView;
    private MoneyDataRefreshHandler mMoneyDataRefreshHandler;
    private RelativeLayout mLoading;
    private boolean isFirst=true;
    private LinearLayout llnodata;
    List<MoneyPeople.DataListBean> SignPeopleData = new ArrayList<>();
    List<MoneyPeople.DataListBean> refreshSignPeopleData = new ArrayList<>();
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private  String activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
    String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getSilvers&token_id="+token_id+"&page=1"+"&rows=10"+"&activity_id="+activity_id;
    private MoneyPeopleRecyclerViewAdapter mMoneyPeopleRecyclerViewAdapter;
    public Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    getData();
                    break;
                case 2:
                    getData();
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_yi_xiang_jin;
    }

    @Override
    public void init() {
        mRecyclerView=findViewById(R.id.rl_tab);
        mSignPeopleRecyclerView=findViewById(R.id.rv_sign);
        mSwipeRefreshLayout=findViewById(R.id.srl_home);
        mToolbar=findViewById(R.id.toolbar);
        llnodata=findViewById(R.id.ll_no_data);
        mLoading=findViewById(R.id.loading);
        mImageView=findViewById(R.id.more);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("");
        getData();
    }

    private void getData() {
        mSwipeRefreshLayout.setRefreshing(true);
        RestClient.builder()
                .url(signPelpleUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("SignPeopleData",response);
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (response != null) {
                            try {
                                MoneyPeople moneyPeople = JSONUtil.fromJson(response, MoneyPeople.class);
                                if(moneyPeople.getRet().equals("200")){
                                    SignPeopleData = moneyPeople.getDataList();
                                    if(SignPeopleData.size()!=0){
                                        showTabData(SignPeopleData);
                                        llnodata.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(SignPeopleData);
                                        llnodata.setVisibility(View.VISIBLE);
                                    }
                                }
                                else if(moneyPeople.getRet().equals("101")){
                                    Toast.makeText(YiXiangJInActivity.this, "token失效了", Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(YiXiangJInActivity.this,"token_id",null);
                                    startActivity(new Intent(YiXiangJInActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(moneyPeople.getRet().equals("201")){
                                    Toast.makeText(YiXiangJInActivity.this, ""+moneyPeople.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                              Log.d("出错了",e.toString());
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    private void showTabData(List<MoneyPeople.DataListBean> signPeopleData) {
        mMoneyPeopleRecyclerViewAdapter = new MoneyPeopleRecyclerViewAdapter(R.layout.item_money2, signPeopleData,mHandler);
        mMoneyPeopleRecyclerViewAdapter.setOnLoadMoreListener(this, mSignPeopleRecyclerView);
        //重复执行动画
        mMoneyPeopleRecyclerViewAdapter.isFirstOnly(false);
        mSignPeopleRecyclerView.setAdapter(mMoneyPeopleRecyclerViewAdapter);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMoreRequested() {

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

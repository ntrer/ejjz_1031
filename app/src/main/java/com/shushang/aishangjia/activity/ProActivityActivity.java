package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Activity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ActivityAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

public class ProActivityActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private String token_id=null;
    private String activityId=null;
    private String activityCode=null;
    private String activityName=null;
    List<Activity.DataListBean> dataList;
    private ActivityAdapter mActivityAdapter;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private LinearLayout ll_nodata;
    private String isUnderLine;
    private String isCheck;
    @Override
    public int setLayout() {
        return R.layout.activity_pro_activity;
    }

    @Override
    public void init() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_activity);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mProgressBar=findViewById(R.id.loading);
        ll_nodata= (LinearLayout) findViewById(R.id.ll_no_data);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        token_id= PreferencesUtils.getString(this,"token_id");
        initData(token_id);
        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData(String token_id) {
        mProgressBar.setVisibility(View.VISIBLE);
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getActivitys&token_id="+token_id;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("活动列表",response);
                        if(response!=null){
                            try {
                                Activity activity = JSONUtil.fromJson(response, Activity.class);
                                Log.d("进来了","进来了");
                                if(activity.getRet().equals("101")){
                                    Log.d("进来了","进来了2");
                                    Toast.makeText(ProActivityActivity.this, ""+activity.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ProActivityActivity.this,"token_id",null);
                                    startActivity(new Intent(ProActivityActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(activity.getRet().equals("200")){
                                    if( activity.getDataList()!=null){
                                        mProgressBar.setVisibility(View.GONE);
                                        dataList = activity.getDataList();
                                        if(dataList.size()==0){
                                            ll_nodata.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            showData(dataList);
                                            ll_nodata.setVisibility(View.GONE);
                                        }
                                    }
                                    else {
                                        ll_nodata.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                        ToastUtils.showLong(""+activity.getMsg());
                                    }
                                }
                                else {
                                    ll_nodata.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            }
                            catch (Exception e){
                                mProgressBar.setVisibility(View.GONE);
                                ll_nodata.setVisibility(View.VISIBLE);
                                Log.d("出错了",e.toString());
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        ll_nodata.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(ProActivityActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                ll_nodata.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(ProActivityActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<Activity.DataListBean> dataList) {
        mActivityAdapter=new ActivityAdapter(R.layout.item_activity,dataList);
        mRecyclerView.setAdapter(mActivityAdapter);
        mRecyclerView.scrollToPosition(0);
        if(dataList.size()>0){
            mActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    activityId=dataList.get(position).getActivityId();
                    activityName=dataList.get(position).getActivityName();
                    isUnderLine=String.valueOf(dataList.get(position).getIsUnderLine());
                    isCheck=String.valueOf(dataList.get(position).getisCheck());
                    activityCode=String.valueOf(dataList.get(position).getActivityCode());
                    Intent data=new Intent();
                    data.putExtra("activityId",activityId);
                    data.putExtra("activityName",activityName);
                    data.putExtra("activityCode",activityCode);
                    if(isUnderLine!=null){
                        data.putExtra("isUnderLine",isUnderLine);
                    }
                    else {
                        data.putExtra("isUnderLine","");
                    }

                    if(isCheck!=null){
                        data.putExtra("isCheck",isCheck);
                    }
                    else {
                        data.putExtra("isCheck","");
                    }


//                    startActivity(data);
                    setResult(100,data);
                    ActivityCompat.finishAfterTransition(ProActivityActivity.this);
                }
            });
        }
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

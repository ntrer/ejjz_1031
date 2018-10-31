package com.shushang.aishangjia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.LianMeng;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.LianMengFragment.adapter.LianMengAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class LianMengActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private RelativeLayout mLoading;
    private boolean isFirst=true;
    private LinearLayout llnodata;
    private SuspensionFab fabTop;
    private int page=1;
    private String resourceName=null;
    private Handler fabHandler=new Handler();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_XIANSUO = 2002;
    private static final int REQUEST_CODE_NEW_PEOPLE =2662;
    private List<LianMeng.DataListBean> dataList=new ArrayList<>();
    private  List<LianMeng.DataListBean> data=new ArrayList<>();
    private LianMengAdapter mLianMengAdapter;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private Context myContext;
    private MainActivity2 mMainActivity2;

    @Override
    public int setLayout() {
        return R.layout.fragment_lianmeng;
    }

    @Override
    public void init() {
        mRecyclerView=findViewById(R.id.rv_lianmeng);
        mToolbar=findViewById(R.id.toolbar);
        fabTop= findViewById(R.id.fab_top);
        llnodata=findViewById(R.id.ll_no_data);
        mLoading=findViewById(R.id.loading);
        myContext=this;
        mToolbar.setTitle("");
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resourceName=PreferencesUtils.getString(this,"ResourceName");
        mSwipeRefreshLayout=findViewById(R.id.srl_lianmeng);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getData();
    }


    public void getData() {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getShareCustomers&token_id="+token_id+"&page=1&rows=10";
        Log.d("BaseUrl",url);
        mSwipeRefreshLayout.setRefreshing(true);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.d("AppPeopleActivity",response);
                            try {
                                LianMeng test = JSONUtil.fromJson(response, LianMeng.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(LianMengActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(LianMengActivity.this,"token_id",null);
                                    startActivity(new Intent(LianMengActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    dataList = test.getDataList();
                                    if(dataList.size()!=0){
                                        showTabData(dataList);
                                        llnodata.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(dataList);
                                        llnodata.setVisibility(View.VISIBLE);
                                    }
                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(LianMengActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                                Toast.makeText(LianMengActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(LianMengActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(LianMengActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showTabData(final List<LianMeng.DataListBean> dataList) {
        mLianMengAdapter = new LianMengAdapter(R.layout.item_lianmeng, dataList,this);
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
        mLianMengAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mLianMengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(LianMengActivity.this,NewPeopleDetailActivity2.class);
                LianMeng.DataListBean dataListBean = dataList.get(position);
                intent.putExtra("data",dataListBean);
                startActivityForResult(intent,REQUEST_CODE_NEW_PEOPLE);
            }
        });
        //重复执行动画
        mLianMengAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mLianMengAdapter);

    }






    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getShareCustomers&token_id="+token_id+"&page="+page+"&rows=10";
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null) {
                                Log.d("nnnnnnn",response);
                                LianMeng test = JSONUtil.fromJson(response, LianMeng.class);
                                if(test.getRet().equals("200")){
                                    if(page>test.getIntmaxPage()){
                                        page=1;
                                        mLianMengAdapter.loadMoreComplete();
                                        mLianMengAdapter.loadMoreEnd();
                                    }
                                    else if(test.getDataList().size()!=0){
                                        List<LianMeng.DataListBean> dataList = test.getDataList();
                                        LoadMoreData(dataList);
                                        Log.d("33333333333",response);
                                        mLianMengAdapter.loadMoreComplete();
                                    }
                                    else if(test.getDataList().size()==0){
                                        mLianMengAdapter.loadMoreComplete();
                                        mLianMengAdapter.loadMoreEnd();
                                    }
                                }
                                else {
                                    mLianMengAdapter.loadMoreComplete();
                                    mLianMengAdapter.loadMoreEnd();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(LianMengActivity.this, "错误了", Toast.LENGTH_SHORT).show();
                            mLianMengAdapter.loadMoreComplete();
                            mLianMengAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(LianMengActivity.this, "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mLianMengAdapter.loadMoreComplete();
                            mLianMengAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){

        }

    }

    private void LoadMoreData(List<LianMeng.DataListBean> dataList) {
        if(dataList.size()!=0){
            mLianMengAdapter.addData(dataList);
            mLianMengAdapter.loadMoreComplete();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_NEW_PEOPLE){
            getData();
        }
    }
}

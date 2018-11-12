package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.GongGao;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.GongGaoAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GongGaoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    private static final int REQUEST_CODE_GONG_GAO =2662;
    private int page=1;
    Toolbar mToolbar;
    RecyclerView mRvGonggao;
    SwipeRefreshLayout mSrlGonggao;
    ProgressBar mLoading;
    LinearLayout mLlNoData;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private List<GongGao.DataListBean> dataList=new ArrayList<>();
    private  List<GongGao.DataListBean> data=new ArrayList<>();
    private GongGaoAdapter mGongGaoAdapter;
    private ImageView mImageView;
    private String isRead="3";
    @Override
    public int setLayout() {
        return R.layout.activity_gong_gao;
    }

    @Override
    public void init() {
        mToolbar=findViewById(R.id.toolbar);
        mRvGonggao=findViewById(R.id.rv_gonggao);
        mSrlGonggao=findViewById(R.id.srl_gonggao);
        mLoading=findViewById(R.id.loading);
        mLlNoData=findViewById(R.id.ll_no_data);
        mImageView=findViewById(R.id.shaixuan);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlGonggao.setOnRefreshListener(this);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ActionSheetDialog(GongGaoActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("全部", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                       getData("");
                                    }
                                })
                        .addSheetItem("已读", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        isRead="1";
                                        getData("1");
                                    }
                                })
                        .addSheetItem("未读", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                      isRead="0";
                                      getData("0");
                                    }
                                })
                        .show();
            }
        });
        getData("");
    }

    public void getData(String isRead) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getNoticesByMerchantId&token_id="+token_id+"&page=1&rows=10&isRead="+isRead;
        Log.d("BaseUrl",url);
        mSrlGonggao.setRefreshing(true);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            mSrlGonggao.setRefreshing(false);
                            Log.d("AppPeopleActivity",response);
                            try {
                                GongGao test = JSONUtil.fromJson(response, GongGao.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(GongGaoActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(GongGaoActivity.this,"token_id",null);
                                    startActivity(new Intent(GongGaoActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    dataList = test.getDataList();
                                    if(dataList.size()!=0){
                                        showTabData(dataList);
                                        mLlNoData.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(dataList);
                                        mLlNoData.setVisibility(View.VISIBLE);
                                    }
                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(GongGaoActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(GongGaoActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSrlGonggao.setRefreshing(false);
                        Toast.makeText(GongGaoActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                mSrlGonggao.setRefreshing(false);
                Toast.makeText(GongGaoActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showTabData(final List<GongGao.DataListBean> dataList) {
        mGongGaoAdapter = new GongGaoAdapter(R.layout.item_gonggao, dataList,this);
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRvGonggao.setLayoutManager(linermanager);
        mGongGaoAdapter.setOnLoadMoreListener(this, mRvGonggao);
        mGongGaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(GongGaoActivity.this,GongGaoDetailActivity.class);
                GongGao.DataListBean dataListBean = dataList.get(position);
                intent.putExtra("data",dataListBean);
                startActivityForResult(intent,REQUEST_CODE_GONG_GAO);
            }
        });
        //重复执行动画
        mGongGaoAdapter.isFirstOnly(false);
        mRvGonggao.setAdapter(mGongGaoAdapter);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_GONG_GAO){
            if(isRead.equals("0")){
                getData("0");
            }
            else if(isRead.equals("1")){
                getData("1");
            }
            else {
                getData("");
            }
        }
    }

    @Override
    public void onRefresh() {
        if(isRead.equals("0")){
            getData("0");
        }
        else if(isRead.equals("1")){
            getData("1");
        }
        else {
            getData("");
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if(isRead.equals("0")){
            loadMore("0");
        }
        else if(isRead.equals("1")){
            loadMore("1");
        }
        else {
            loadMore("");
        }
    }

    private void loadMore(String isRead) {
        Log.d("BaseUrl","加载更多");
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getNoticesByMerchantId&token_id="+token_id+"&page="+page+"&rows=10&isRead="+isRead;
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null) {
                                Log.d("nnnnnnn",response);
                                GongGao test = JSONUtil.fromJson(response, GongGao.class);
                                if(test.getRet().equals("200")){
                                    if(page>test.getIntmaxPage()){
                                        page=1;
                                        mGongGaoAdapter.loadMoreComplete();
                                        mGongGaoAdapter.loadMoreEnd();
                                    }
                                    else if(test.getDataList().size()!=0){
                                        List<GongGao.DataListBean> dataList = test.getDataList();
                                        LoadMoreData(dataList);
                                        Log.d("33333333333",response);
                                        mGongGaoAdapter.loadMoreComplete();
                                    }
                                    else if(test.getDataList().size()==0){
                                        mGongGaoAdapter.loadMoreComplete();
                                        mGongGaoAdapter.loadMoreEnd();
                                    }
                                }
                                else {
                                    mGongGaoAdapter.loadMoreComplete();
                                    mGongGaoAdapter.loadMoreEnd();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(GongGaoActivity.this, "错误了", Toast.LENGTH_SHORT).show();
                            mGongGaoAdapter.loadMoreComplete();
                            mGongGaoAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(GongGaoActivity.this, "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mGongGaoAdapter.loadMoreComplete();
                            mGongGaoAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){

        }

    }

    private void LoadMoreData(List<GongGao.DataListBean> dataList) {
        if(dataList.size()!=0){
            mGongGaoAdapter.addData(dataList);
            mGongGaoAdapter.loadMoreComplete();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}

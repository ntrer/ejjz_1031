package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.shushang.aishangjia.Bean.Leagues;
import com.shushang.aishangjia.Bean.LeaguesList;
import com.shushang.aishangjia.Bean.LeaguesTongji;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.TabRecyclerViewAdapter3;
import com.shushang.aishangjia.activity.adapter.ZhangDanAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.SwipeItemLayout;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class ZhangDanActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mToolbar;
    private RelativeLayout mLoading;
    private TextView mMounth;
    private RecyclerView mRlTab;
    private ImageView mMore;
    private LinearLayout mTabLayout,mLinearLayout;
    private View mLine;
    private View mView;
    private int page=1;
    private RecyclerView mRvZhangdan;
    private SwipeRefreshLayout mSrlZhangdan;
    private LinearLayout mLlNoData;
    private TabRecyclerViewAdapter3 tabRecyclerViewAdapter;
    private List<Leagues.DataListBean> data;
    private boolean isFirst=true;
    private String allData="100";
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6,mTextView7,mTextView8,mTextView9,mTextView10,mTextView11,mTextView12;
    private List<LeaguesList.DataListBean> dataList=new ArrayList<>();
    private  List<NewPeople.DataListBean> data2=new ArrayList<>();
    private ZhangDanAdapter mZhangDanAdapter;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private String shangjia_id= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "shangjia_id");
    private String type= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "type");
    private String ResourceName3= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),"ResourceName3");
    private String ResourceName0= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),"ResourceName0");
    private String  leagueId=null;
    private boolean noTab=false;
    public Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 2:
                    if(shangjia_id==null||shangjia_id.equals("")){
                        Log.d("leagueId",leagueId);
                        getData(leagueId);
                    }
                    else {
                        getData(shangjia_id);
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    public int setLayout() {
        return R.layout.activity_zhang_dan;
    }

    @Override
    public void init() {
        mLoading=findViewById(R.id.loading);
        mRlTab=findViewById(R.id.rl_tab);
        mTabLayout=findViewById(R.id.TabLayout);
        mRvZhangdan=findViewById(R.id.rv_zhangdan);
        mSrlZhangdan=findViewById(R.id.srl_zhangdan);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mLlNoData=findViewById(R.id.ll_no_data);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTabData("");
        mSrlZhangdan.setOnRefreshListener(this);
    }

    private void getTongjiData(String leagueId) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getFinancesCount&token_id="+token_id+"&leagueId="+leagueId;
        Log.d("TabList",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("TabList",response);
                        if(response!=null){
                            try {
                                LeaguesTongji tabList = JSONUtil.fromJson(response, LeaguesTongji.class);
                                if(tabList.getRet().equals("101")){
                                    Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ZhangDanActivity.this,"token_id",null);
                                    startActivity(new Intent(ZhangDanActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else {
                                    if(tabList.getRet().equals("200")){
                                       if(String.valueOf(tabList.getData().getWeekIn())==null||String.valueOf(tabList.getData().getWeekIn()).equals("0.0")){
                                          mTextView1.setText("0.0");
                                       }
                                       else {
                                           mTextView1.setText(String.valueOf(tabList.getData().getWeekIn()));
                                       }

                                        if(String.valueOf(tabList.getData().getMonthIn())==null||String.valueOf(tabList.getData().getMonthIn()).equals("0.0")){
                                            mTextView2.setText("0.0");
                                        }
                                        else {
                                            mTextView2.setText(String.valueOf(tabList.getData().getMonthIn()));
                                        }

                                        if(String.valueOf(tabList.getData().getYearIn())==null||String.valueOf(tabList.getData().getYearIn()).equals("0.0")){
                                            mTextView3.setText("0.0");
                                        }
                                        else {
                                            mTextView3.setText(String.valueOf(tabList.getData().getYearIn()));
                                        }

                                        if(String.valueOf(tabList.getData().getTotalIn())==null||String.valueOf(tabList.getData().getTotalIn()).equals("0.0")){
                                            mTextView4.setText("0.0");
                                        }
                                        else {
                                            mTextView4.setText(String.valueOf(tabList.getData().getTotalIn()));
                                        }

                                        if(String.valueOf(tabList.getData().getWeekOut())==null||String.valueOf(tabList.getData().getWeekOut()).equals("0.0")){
                                            mTextView5.setText("0.0");
                                        }
                                        else {
                                            mTextView5.setText(String.valueOf(tabList.getData().getWeekOut()));
                                        }

                                        if(String.valueOf(tabList.getData().getMonthOut())==null||String.valueOf(tabList.getData().getMonthOut()).equals("0.0")){
                                            mTextView6.setText("0.0");
                                        }
                                        else {
                                            mTextView6.setText(String.valueOf(tabList.getData().getMonthOut()));
                                        }

                                        if(String.valueOf(tabList.getData().getYearOut())==null||String.valueOf(tabList.getData().getYearOut()).equals("0.0")){
                                            mTextView7.setText("0.0");
                                        }
                                        else {
                                            mTextView7.setText(String.valueOf(tabList.getData().getYearOut()));
                                        }

                                        if(String.valueOf(tabList.getData().getTotalOut())==null||String.valueOf(tabList.getData().getTotalOut()).equals("0.0")){
                                            mTextView8.setText("0.0");
                                        }
                                        else {
                                            mTextView8.setText(String.valueOf(tabList.getData().getTotalOut()));
                                        }
                                        if(String.valueOf(tabList.getData().getWeekBalance())==null||String.valueOf(tabList.getData().getWeekBalance()).equals("0.0")){
                                            mTextView9.setText("0.0");
                                        }
                                        else {
                                            mTextView9.setText(String.valueOf(tabList.getData().getWeekBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getMonthBalance())==null||String.valueOf(tabList.getData().getMonthBalance()).equals("0.0")){
                                            mTextView10.setText("0.0");
                                        }
                                        else {
                                            mTextView10.setText(String.valueOf(tabList.getData().getMonthBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getYearBalance())==null||String.valueOf(tabList.getData().getYearBalance()).equals("0.0")){
                                            mTextView11.setText("0.0");
                                        }
                                        else {
                                            mTextView11.setText(String.valueOf(tabList.getData().getYearBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getTotalBalance())==null||String.valueOf(tabList.getData().getTotalBalance()).equals("0.0")){
                                            mTextView12.setText("0.0");
                                        }
                                        else {
                                            mTextView12.setText(String.valueOf(tabList.getData().getTotalBalance()));
                                        }
                                    }
                                    else if(tabList.getRet().equals("201")){
                                        mSrlZhangdan.setRefreshing(false);
                                        Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){
                                mSrlZhangdan.setRefreshing(false);
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSrlZhangdan.setRefreshing(false);
                        Toast.makeText(ZhangDanActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }

    //获取tab栏数据
    private void getTabData(final String allData) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getLeaguesByMerchant&token_id="+token_id;
        mSrlZhangdan.setRefreshing(true);
        Log.d("TabList",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("TabList",response);
                        if(response!=null){
                            try {
                                Leagues tabList = JSONUtil.fromJson(response, Leagues.class);
                                if(tabList.getRet().equals("101")){
                                    Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ZhangDanActivity.this,"token_id",null);
                                    startActivity(new Intent(ZhangDanActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else {
                                    if(tabList.getRet().equals("200")){
                                        data = tabList.getDataList();
                                        if(data.size()!=0){
                                            showTabData(data);
                                            getData(data.get(0).getMerchantId());
//                                            getTongjiData(data.get(0).getMerchantId());
                                        }
                                        else {
                                            noTab=true;
                                            getData(shangjia_id);
                                            mTabLayout.setVisibility(View.GONE);
                                            mLinearLayout.setVisibility(View.GONE);
                                        }

                                    }
                                    else if(tabList.getRet().equals("201")){
                                        mSrlZhangdan.setRefreshing(false);
                                        Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){
                                mSrlZhangdan.setRefreshing(false);
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSrlZhangdan.setRefreshing(false);
                        Toast.makeText(ZhangDanActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }



    public void getData(final String merchantId) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getFinances&token_id="+token_id+"&page=1&rows=10&leagueId="+merchantId;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            mSrlZhangdan.setRefreshing(false);
                            Log.d("AppPeopleActivity",response);
                            try {
                                LeaguesList test = JSONUtil.fromJson(response, LeaguesList.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(ZhangDanActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ZhangDanActivity.this,"token_id",null);
                                    startActivity(new Intent(ZhangDanActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    dataList = test.getDataList();
                                    if(dataList.size()!=0){
                                        showData(dataList);
                                        getTongjiData(merchantId);
                                        mLlNoData.setVisibility(View.GONE);
                                    }
                                    else {
                                        showData(dataList);
                                        getTongjiData(merchantId);
                                        mLlNoData.setVisibility(View.VISIBLE);
                                    }
                                    isFirst=false;
                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(ZhangDanActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                                Toast.makeText(ZhangDanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                        mSrlZhangdan.setRefreshing(false);
                        Toast.makeText(ZhangDanActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

                mSrlZhangdan.setRefreshing(false);
                Toast.makeText(ZhangDanActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<LeaguesList.DataListBean> dataList) {
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview8,null);
        mLinearLayout=mView.findViewById(R.id.shouzhi);
        mTextView1=mView.findViewById(R.id.week_get);
        mTextView2=mView.findViewById(R.id.mounth_get);
        mTextView3=mView.findViewById(R.id.year_get);
        mTextView4=mView.findViewById(R.id.total_get);
        mTextView5=mView.findViewById(R.id.week_pay);
        mTextView6=mView.findViewById(R.id.mounth_pay);
        mTextView7=mView.findViewById(R.id.year_pay);
        mTextView8=mView.findViewById(R.id.total_pay);
        mTextView9=mView.findViewById(R.id.weekBalance);
        mTextView10=mView.findViewById(R.id.monthBalance);
        mTextView11=mView.findViewById(R.id.yearBalance);
        mTextView12=mView.findViewById(R.id.totalBalance);
        if(noTab){
            mLinearLayout.setVisibility(View.GONE);
        }
        mZhangDanAdapter = new ZhangDanAdapter(R.layout.item_zhangdan, dataList,mHandler);
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRvZhangdan.setLayoutManager(linermanager);
        mZhangDanAdapter.setOnLoadMoreListener(this, mRvZhangdan);
        mZhangDanAdapter.addHeaderView(mView);
        if(isFirst){
            if(type.equals("7")){
                if(ResourceName3!=null){
                    mRvZhangdan.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(MyApplication.getInstance().getApplicationContext()));
                }
            }
        }
        mZhangDanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ll_zhangdan:
                        Intent intent=new Intent(ZhangDanActivity.this,MoneyInfoActivity.class);
                        LeaguesList.DataListBean dataListBean = dataList.get(position);
                        intent.putExtra("data",dataListBean);
                        startActivity(intent);
                     break;
                }
            }
        });
        //重复执行动画
        mZhangDanAdapter.isFirstOnly(false);
        mRvZhangdan.setAdapter(mZhangDanAdapter);

    }


    private void showTabData(List<Leagues.DataListBean> data) {
        if(isFirst){
            tabRecyclerViewAdapter=new TabRecyclerViewAdapter3(R.layout.tab_items,data);
            tabRecyclerViewAdapter.setThisPosition(0);
            leagueId=data.get(0).getMerchantId();
        }
        initRecyclerView(tabRecyclerViewAdapter);
    }


    private void initRecyclerView(final TabRecyclerViewAdapter3 tabRecyclerViewAdapter) {
        final LinearLayoutManager manager=new LinearLayoutManager(ZhangDanActivity.this,LinearLayoutManager.HORIZONTAL,false);
        mRlTab.setLayoutManager(manager);
        //解决嵌套滑动冲突
        mRlTab.setNestedScrollingEnabled(false);
        mRlTab.setAdapter(tabRecyclerViewAdapter);
        tabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                leagueId=data.get(position).getMerchantId();
                getData(data.get(position).getMerchantId());
            }
        });
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



    @Override
    public void onRefresh() {
        if(shangjia_id==null||shangjia_id.equals("")){
            Log.d("leagueId",leagueId);
            getData(leagueId);
        }
        else {
            getData(shangjia_id);
        }
    }


    @Override
    public void onLoadMoreRequested() {
        if(shangjia_id==null||shangjia_id.equals("")){
            loadMore(leagueId);
        }
        else {
            loadMore(shangjia_id);
        }
    }

    private void loadMore(String leagueId) {
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getFinances&token_id="+token_id+"&page="+page+"&rows=10&leagueId="+leagueId;
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null) {
                                Log.d("nnnnnnn",response);
                                LeaguesList test = JSONUtil.fromJson(response, LeaguesList.class);
                                if(test.getRet().equals("200")){
                                    if(page>test.getIntmaxPage()){
                                        page=1;
                                        mZhangDanAdapter.loadMoreComplete();
                                        mZhangDanAdapter.loadMoreEnd();
                                    }
                                    else if(test.getDataList().size()!=0){
                                        List<LeaguesList.DataListBean> dataList = test.getDataList();
                                        LoadMoreData(dataList);
                                        Log.d("33333333333",response);
                                        mZhangDanAdapter.loadMoreComplete();
                                    }
                                    else if(test.getDataList().size()==0){
                                        mZhangDanAdapter.loadMoreComplete();
                                        mZhangDanAdapter.loadMoreEnd();
                                    }
                                }
                                else {
                                    mZhangDanAdapter.loadMoreComplete();
                                    mZhangDanAdapter.loadMoreEnd();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(ZhangDanActivity.this, "错误了", Toast.LENGTH_SHORT).show();
                            mZhangDanAdapter.loadMoreComplete();
                            mZhangDanAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(ZhangDanActivity.this, "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mZhangDanAdapter.loadMoreComplete();
                            mZhangDanAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){

        }

    }

    private void LoadMoreData(List<LeaguesList.DataListBean> dataList) {
        if(dataList.size()!=0){
            mZhangDanAdapter.addData(dataList);
            mZhangDanAdapter.loadMoreComplete();
        }

    }
}

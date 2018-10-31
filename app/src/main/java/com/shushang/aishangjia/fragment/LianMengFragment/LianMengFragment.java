package com.shushang.aishangjia.fragment.LianMengFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.MainActivity2;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.fragment.LianMengFragment.adapter.LianMengAdapter;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class LianMengFragment extends BaseFragment  implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    private  List<NewPeople.DataListBean> dataList=new ArrayList<>();
    private  List<NewPeople.DataListBean> data=new ArrayList<>();
    private LianMengAdapter mLianMengAdapter;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private Context myContext;
    private MainActivity2 mMainActivity2;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        mRecyclerView=rootView.findViewById(R.id.rv_lianmeng);
//        mToolbar=rootView.findViewById(R.id.toolbar);
//        fabTop= rootView.findViewById(R.id.fab_top);
//        llnodata=rootView.findViewById(R.id.ll_no_data);
//        mLoading=rootView.findViewById(R.id.loading);
//        myContext=getActivity();
//        mToolbar.setTitle("");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_lianmeng, null);
        mSwipeRefreshLayout=view.findViewById(R.id.srl_lianmeng);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity2 = (MainActivity2) getActivity();
    }

    @Override
    public void initData() {
        super.initData();
        resourceName=PreferencesUtils.getString(mContext,"ResourceName");
//        getData();
    }

//    public void getData() {
//        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page=1&rows=10&date=2018-10-17"+"&type=0";
//        Log.d("BaseUrl",url);
//        mSwipeRefreshLayout.setRefreshing(true);
//        RestClient.builder()
//                .url(url)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        if(response!=null){
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            Log.d("AppPeopleActivity",response);
//                            try {
//                                NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
//                                if(test.getRet().equals("101")){
//                                    Toast.makeText(mContext, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
//                                    PreferencesUtils.putString(mContext,"token_id",null);
//                                    startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                    getActivity().finish();
//                                }
//                                else if(test.getRet().equals("200")){
//                                    dataList = test.getDataList();
//                                    if(dataList.size()!=0){
//                                        showTabData(dataList);
//                                        llnodata.setVisibility(View.GONE);
//                                    }
//                                    else {
//                                        showTabData(dataList);
//                                        llnodata.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                                else if(test.getRet().equals("201")){
//                                    Toast.makeText(mContext, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            catch (Exception e){
//
//                                Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                    }
//                }).error(new IError() {
//            @Override
//            public void onError(int code, String msg) {
//
//                mSwipeRefreshLayout.setRefreshing(false);
//                Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
//            }
//        })
//                .build()
//                .get();
//    }

    private void showTabData(final List<NewPeople.DataListBean> dataList) {
//        mLianMengAdapter = new LianMengAdapter(R.layout.item_sign, dataList,mMainActivity2);
//        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(linermanager);
//        mLianMengAdapter.setOnLoadMoreListener(this, mRecyclerView);
//        mLianMengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent=new Intent(mContext,NewPeopleDetailActivity.class);
//                NewPeople.DataListBean dataListBean = dataList.get(position);
//                intent.putExtra("data",dataListBean);
//                startActivityForResult(intent,REQUEST_CODE_NEW_PEOPLE);
//            }
//        });
//        //重复执行动画
//        mLianMengAdapter.isFirstOnly(false);
//        mRecyclerView.setAdapter(mLianMengAdapter);

    }






    @Override
    public void onRefresh() {
//        getData();
    }


    @Override
    public void onLoadMoreRequested() {
//        loadMore();
    }

//    private void loadMore() {
//        page=page+1;
//        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page="+page+"&rows=10"+"&date=2018-10-17"+"&type=0";
//        try {
//            RestClient.builder()
//                    .url(url)
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            if(response!=null) {
//                                Log.d("nnnnnnn",response);
//                                NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
//                                if(test.getRet().equals("200")){
//                                    if(page>test.getIntmaxPage()){
//                                        page=1;
//                                        mLianMengAdapter.loadMoreComplete();
//                                        mLianMengAdapter.loadMoreEnd();
//                                    }
//                                    else if(test.getDataList().size()!=0){
//                                        List<NewPeople.DataListBean> dataList = test.getDataList();
//                                        LoadMoreData(dataList);
//                                        Log.d("33333333333",response);
//                                        mLianMengAdapter.loadMoreComplete();
//                                    }
//                                    else if(test.getDataList().size()==0){
//                                        mLianMengAdapter.loadMoreComplete();
//                                        mLianMengAdapter.loadMoreEnd();
//                                    }
//                                }
//                                else {
//                                    mLianMengAdapter.loadMoreComplete();
//                                    mLianMengAdapter.loadMoreEnd();
//                                }
//                            }
//                        }
//                    })
//                    .failure(new IFailure() {
//                        @Override
//                        public void onFailure() {
//                            Toast.makeText(getActivity(), "错误了", Toast.LENGTH_SHORT).show();
//                            mLianMengAdapter.loadMoreComplete();
//                            mLianMengAdapter.loadMoreEnd();
//                        }
//                    })
//                    .error(new IError() {
//                        @Override
//                        public void onError(int code, String msg) {
//                            Toast.makeText(getActivity(), "错误了"+code+msg, Toast.LENGTH_SHORT).show();
//                            mLianMengAdapter.loadMoreComplete();
//                            mLianMengAdapter.loadMoreEnd();
//                        }
//                    })
//                    .build()
//                    .get();
//        }
//        catch (Exception e){
//
//        }
//
//    }
//
//    private void LoadMoreData(List<NewPeople.DataListBean> dataList) {
//            if(dataList.size()!=0){
//                mLianMengAdapter.addData(dataList);
//                mLianMengAdapter.loadMoreComplete();
//            }
//
//    }


}

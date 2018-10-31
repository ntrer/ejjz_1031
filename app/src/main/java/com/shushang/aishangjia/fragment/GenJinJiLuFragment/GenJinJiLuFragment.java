package com.shushang.aishangjia.fragment.GenJinJiLuFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.Sheng;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.GenJinJiLuFragment.adapter.GenJinAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.util.List;

/**
 * Created by YD on 2018/9/20.
 */

public class GenJinJiLuFragment extends BaseFragment {


    private String token_id=null;
    private List<Sheng.DataListBean> mDataListBeen;
    private GenJinAdapter mShengAdapter;
    private String sheng_code=null;
    private String sheng_name=null;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int REQUEST_CODE_CHILDCITY = 2011;

    public GenJinJiLuFragment() {
        // Required empty public constructor
    }

    public static GenJinJiLuFragment newInstance() {
        return new GenJinJiLuFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_sign);
        mSwipeRefreshLayout=rootView.findViewById(R.id.srl_home);
        token_id= PreferencesUtils.getString(mContext,"token_id");
        getData(token_id);
        initRecyclerView();
    }

    private void getData(String token_id) {
        String url= BaseUrl.BASE_URL+"addressController.do?method=getSheng&token_id="+ token_id;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null){
                                Sheng sheng = JSONUtil.fromJson(response, Sheng.class);
                                if(sheng.getRet().equals("200")){
                                    mDataListBeen = sheng.getDataList();
//                                    mDataListBeen=new ArrayList<Sheng.DataListBean>();
                                    showData(mDataListBeen);
                                }
                                else {
                                    ToastUtils.showLong(""+sheng.getMsg());
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {

                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {

                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){

        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_genjin, null);
        return view;
    }



    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linermanager);
        mRecyclerView.setNestedScrollingEnabled(true);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(dy>10){
//                    EventBus.getDefault().post(new MessageEvent(""));
//                }
//            }
//        });
    }

    private void showData(List<Sheng.DataListBean> dataListBeen) {
        mShengAdapter=new GenJinAdapter(R.layout.item_genjin,dataListBeen);
        mRecyclerView.setAdapter(mShengAdapter);
        mRecyclerView.scrollToPosition(0);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

package com.shushang.aishangjia.fragment.GenJinJiLuFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.XiansuoInfo;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.GenJinJiLuFragment.adapter.GenJinAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YD on 2018/9/20.
 */

public class GenJinJiLuFragment extends BaseFragment {


    private String token_id=null;
    private List<XiansuoInfo.DataBean.ClueActionsBean> clueActions=new ArrayList<>();
    private GenJinAdapter mShengAdapter;
    private String sheng_code=null;
    private String sheng_name=null;
    private RecyclerView mRecyclerView;
    private static final int REQUEST_CODE_CHILDCITY = 2011;
    private LinearLayout ll_nodata;
    private Dialog mRequestDialog;
    private String clueId;
    private static final int REQUEST_UPDATE_XIANSUO = 8012;
    public GenJinJiLuFragment() {
        // Required empty public constructor
    }

    public static GenJinJiLuFragment newInstance() {
        return new GenJinJiLuFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_sign);
        ll_nodata=rootView.findViewById(R.id.ll_no_data);
        token_id= PreferencesUtils.getString(mContext,"token_id");
        clueId= PreferencesUtils.getString(mContext,"clueId");
        mRequestDialog = ExtAlertDialog.creatRequestDialog(getActivity(), getString(R.string.request));
        mRequestDialog.setCancelable(false);
        initRecyclerView();
        getData(token_id,clueId);
        EventBus.getDefault().register(this);
    }

    private void getData(String token_id, String clueId) {
        mRequestDialog.show();
        String url= BaseUrl.BASE_URL+"phoneApi/clueController.do?method=getClueById&token_id="+token_id+"&clueId="+clueId;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                if(response!=null){
                                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                        mRequestDialog.dismiss();
                                    }
                                    XiansuoInfo xiansuo = JSONUtil.fromJson(response, XiansuoInfo.class);
                                    if(xiansuo.getRet().equals("200")){
                                        if(xiansuo.getData()!=null){
                                            XiansuoInfo xiansuoInfo = JSONUtil.fromJson(response, XiansuoInfo.class);
                                            if(xiansuoInfo.getData().getClueActions().size()!=0){
                                                clueActions = xiansuo.getData().getClueActions();
                                                showData(clueActions);
                                                ll_nodata.setVisibility(View.GONE);
                                            }
                                            else {
                                                ll_nodata.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                    else if(xiansuo.getRet().equals("101")){
                                        Toast.makeText(getActivity(), ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(getActivity(),"token_id",null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    }
                                    else if(xiansuo.getRet().equals("201")){
                                        Toast.makeText(getActivity(), ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
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
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();

                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
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
        mRecyclerView.setNestedScrollingEnabled(false);

    }

    private void showData(List<XiansuoInfo.DataBean.ClueActionsBean> clueActions) {
        mShengAdapter=new GenJinAdapter(R.layout.item_genjin,clueActions);
        mRecyclerView.setAdapter(mShengAdapter);
        mRecyclerView.scrollToPosition(0);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("跟进记录")){
            getData(token_id,clueId);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

package com.shushang.aishangjia.fragment.CustomerDetailFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.XiansuoInfo;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * Created by YD on 2018/9/20.
 */

public class CustomerDetailFragment extends BaseFragment {



    TextView mEtSex;
    TextView mEtAddress;
    TextView mEtCustomerMobile;
    TextView mSource;
    TextView mXiansuoDetail;
    TextView mEditFuzeren;
    TextView mBeizhu;
    LinearLayout mMessageLl;
    Unbinder unbinder;
    private String phoneNum;
    private Dialog mRequestDialog;
    private String clueId;
    private static final int REQUEST_UPDATE_XIANSUO = 8012;
    private List<XiansuoInfo.DataBean.ClueActionsBean> clueActions=new ArrayList<>();
    private String token_id=null;
    public CustomerDetailFragment() {
        // Required empty public constructor
    }

    public static CustomerDetailFragment newInstance() {
        return new CustomerDetailFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mEtSex=rootView.findViewById(R.id.et_sex);
        mEtAddress=rootView.findViewById(R.id.et_address);
        mEtCustomerMobile=rootView.findViewById(R.id.et_customer_mobile);
        mSource=rootView.findViewById(R.id.source);
        mXiansuoDetail=rootView.findViewById(R.id.xiansuo_detail);
        mEditFuzeren=rootView.findViewById(R.id.edit_fuzeren);
//        mBeizhu=rootView.findViewById(R.id.beizhu);
        token_id= PreferencesUtils.getString(mContext,"token_id");
        clueId= PreferencesUtils.getString(mContext,"clueId");
        phoneNum = PreferencesUtils.getString(mContext, "phoneNum");
        getData(token_id,clueId);
        EventBus.getDefault().register(this);
//        XiansuoInfo xiansuoInfo = JSONUtil.fromJson(genjinData, XiansuoInfo.class);
//        showData(xiansuoInfo.getData());

    }

    private void getData(String token_id, String clueId) {
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
                                    XiansuoInfo xiansuo = JSONUtil.fromJson(response, XiansuoInfo.class);
                                    if(xiansuo.getRet().equals("200")){
                                        if(xiansuo.getData()!=null){
                                            XiansuoInfo xiansuoInfo = JSONUtil.fromJson(response, XiansuoInfo.class);
                                            if(xiansuoInfo.getData().getClueActions().size()!=0){
                                                XiansuoInfo.DataBean data = xiansuo.getData();
                                                showData(data);
                                            }
                                            else {

                                            }
                                        }
                                        else {

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
            if(data.getSex().equals("1")){
                mEtSex.setText("男");
            }
            else if(data.getSex().equals("2")){
                mEtSex.setText("女");
            }
            mEtCustomerMobile.setText(phoneNum);
            mEtAddress.setText(data.getAddress());
            if (data.getSource()!=null){
                switch (data.getSource()){
                    case "1":
                        mSource.setText("微信引流");
                        break;
                    case "2":
                        mSource.setText("客户介绍");
                        break;
                    case "3":
                        mSource.setText("广告");
                        break;
                    case "4":
                        mSource.setText("销售拜访");
                        break;
                    case "5":
                        mSource.setText("电话");
                        break;
                    case "6":
                        mSource.setText("自然进店");
                        break;
                    case "7":
                        mSource.setText("网上宣传");
                        break;
                    case "8":
                        mSource.setText("朋友宣传");
                        break;
                    case "9":
                        mSource.setText("其他");
                        break;
                }
            }
            mXiansuoDetail.setText(data.getClueActions().get(0).getDetalInfo());
            if(data.getFuZeRenName()!=null){
                mEditFuzeren.setText(String.valueOf(data.getFuZeRenName()));
            }
//            mBeizhu.setText(data.getBeizhu());

        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("跟进记录")){
            getData(token_id,clueId);
        }
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_ziliao, null);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

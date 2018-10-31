package com.shushang.aishangjia.fragment.SignFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.necer.ncalendar.calendar.WeekCalendar;
import com.necer.ncalendar.listener.OnWeekCalendarChangedListener;
import com.necer.ncalendar.utils.MyLog;
import com.shushang.aishangjia.Bean.Activity;
import com.shushang.aishangjia.Bean.SignList;
import com.shushang.aishangjia.Bean.SignTabData;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.activity.adapter.ActivityAdapter;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.ScanFragment.adapter.ScanAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class SignFragment extends BaseFragment implements View.OnClickListener, OnWeekCalendarChangedListener, OnFabClickListener,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WeekCalendar ncalendar;
    private RecyclerView mRecyclerView;
    private ScanAdapter mScanAdapter;
    private  List<SignList.DataListBean> dataList=new ArrayList<>();
    private TextView mTextView,mTextView1,mTextView2,mTextView3,mTextView4;
    private boolean isMounth=false;
//    private ScanFragment mScanFragment;
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_XIANSUO = 2002;
    private LocalDate time=null;
    private String token_id=null;
    private String resourceName=null;
    private View head=null;
    private MainActivity mMainActivity;
    private boolean isFirst=true;
    private int page=1;
    private boolean isClick=false;
    private SuspensionFab fabTop;
    private Handler fabHandler=new Handler();
    private LinearLayout ll_nodata;
    private static final int REQUEST_CODE_SCAN = 2002;
    private String activityId=null;
    private String activityCode=null;
    private String activityName=null;
    List<Activity.DataListBean> activityData;
    private ActivityAdapter mActivityAdapter;
    private List<String> name=new ArrayList<>();

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_data);
        mTextView=rootView.findViewById(R.id.this_month);
        fabTop= (SuspensionFab) rootView.findViewById(R.id.fab_top);
        ncalendar = (WeekCalendar) rootView.findViewById(R.id.weekCalendar);
        ll_nodata=rootView.findViewById(R.id.ll_no_data);
        ncalendar.setOnWeekCalendarChangedListener(this);
        mTextView.setOnClickListener(this);
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.note))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes xiansuo = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.xiansuo))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(3)
                .build();

        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.people_add))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

        FabAttributes email2 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_activity))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(4)
                .build();

        FabAttributes email3 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_4_coloring))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(5)
                .build();
        if(resourceName!=null&&resourceName.equals("1116")){
            fabTop.addFab(collection,xiansuo,email,email2,email3);
        }
        else {
            fabTop.addFab(collection,email,email2,email3);
        }
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
        fabTop.setFabClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_scan, null);
        mSwipeRefreshLayout=view.findViewById(R.id.srl_sign);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        MainActivity2 mainActivity = (MainActivity2) getActivity();
//        mScanFragment= (ScanFragment)mainActivity.getSupportFragmentManager().findFragmentByTag("tag2");
    }

    @Override
    public void initData() {
        super.initData();
        token_id= PreferencesUtils.getString(getActivity(),"token_id");
        resourceName= PreferencesUtils.getString(mContext,"ResourceName");
//        getActivityData(token_id);

    }


//    public void getActivityData(String token_id){
//        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getActivitys&token_id="+ this.token_id;
//        Log.d("BaseUrl",url);
//        RestClient.builder()
//                .url(url)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        Log.d("活动列表",response);
//                        if(response!=null){
//                            try {
//                                Activity activity = JSONUtil.fromJson(response, Activity.class);
//                                if(activity.getDataList()!=null){
//                                    if(activity.getRet().equals("200")){
//                                        activityData = activity.getDataList();
//                                        getActivityNameData(activityData);
//                                    }
//                                    else {
//                                        ToastUtils.showLong(""+activity.getMsg());
//                                    }
//                                }
//                            }
//                            catch (Exception e){
//
//                            }
//                        }
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//
//                        Toast.makeText(getActivity(), "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                    }
//                }).error(new IError() {
//            @Override
//            public void onError(int code, String msg) {
//                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
//            }
//        })
//                .build()
//                .get();
//    }
//
//    private void getActivityNameData(List<Activity.DataListBean> activityData) {
//        for(int i=0;i<activityData.size();i++){
//            name.add(activityData.get(i).getActivityName());
//        }
//    }

    public void getData(final LocalDate time, final String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getManagerSignins&token_id="+token_id+"&date="+time+"&page=1&rows=10&type=0";
        mSwipeRefreshLayout.setRefreshing(true);
        Log.d("6666666666666",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                Log.d("6666666666666",response);
                                if(response!=null){
                                    mSwipeRefreshLayout.setRefreshing(false);
                                    SignList signList = JSONUtil.fromJson(response, SignList.class);
                                    if(signList.getRet().equals("101")){
                                        Toast.makeText(mContext, ""+signList.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(mContext,"token_id",null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    }
                                    else if(signList.getRet().equals("200")){
                                        if(signList.getDataList()!=null){
                                            dataList = signList.getDataList();
                                            getTabData(time,token_id);
                                            if(dataList.size()!=0){
                                                showTabData(dataList);
                                                ll_nodata.setVisibility(View.GONE);
                                            }
                                            else {
                                                showTabData(dataList);
                                                ll_nodata.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                    else if(signList.getRet().equals("201")){
                                        Toast.makeText(mContext, ""+signList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            catch (Exception e){

                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
        }

    }


    private void getTabData(LocalDate time,String token_id){
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getSigninCount&token_id="+token_id+"&date="+time+"&type=0";
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                Log.d("wocaocaocacoacoa",response);
                                if(response!=null){
                                    SignTabData signTabData = JSONUtil.fromJson(response, SignTabData.class);
                                    if(signTabData.getRet().equals("200")){
                                        if(signTabData.getData()!=null){
                                            mTextView1.setText(String.valueOf(signTabData.getData().getTargetNum()));
                                            mTextView2.setText(String.valueOf(signTabData.getData().getDoNum()));
                                            mTextView3.setText(String.valueOf(Math.round(Float.parseFloat(signTabData.getData().getDoRate())*100))+"%");
                                            mTextView4.setText(String.valueOf(Math.round(Float.parseFloat(signTabData.getData().getEfficiencyRatio())))+"%");

                                        }
                                    }
                                    else if(signTabData.getRet().equals("201")){
                                        Toast.makeText(mContext, ""+signTabData.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){
                                ToastUtils.showLong(e+"");
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    private void getTabMounthData(LocalDate time,String token_id){
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getSigninCount&token_id="+token_id+"&date="+time+"&type=1";
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                Log.d("wocaocaocacoacoa",response);
                                if(response!=null){
                                    SignTabData signTabData = JSONUtil.fromJson(response, SignTabData.class);
                                    if(signTabData.getRet().equals("200")){
                                        if(signTabData.getData()!=null){
                                            mTextView1.setText(String.valueOf(signTabData.getData().getTargetNum()));
                                            mTextView2.setText(String.valueOf(signTabData.getData().getDoNum()));
                                            mTextView3.setText(String.valueOf(Math.round(Float.parseFloat(signTabData.getData().getDoRate())*100))+"%");
                                            mTextView4.setText(String.valueOf(Math.round(Float.parseFloat(signTabData.getData().getEfficiencyRatio())))+"%");
                                        }
                                    }
                                    else if(signTabData.getRet().equals("201")){
                                        Toast.makeText(mContext, ""+signTabData.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
        }

    }




    private void showTabData(List<SignList.DataListBean> dataList) {
        try {
            head = View.inflate(getActivity(), R.layout.headerview2, null);
            mTextView1=head.findViewById(R.id.num1);
            mTextView2=head.findViewById(R.id.num2);
            mTextView3=head.findViewById(R.id.num3);
            mTextView4=head.findViewById(R.id.num4);
            mScanAdapter = new ScanAdapter(R.layout.item_tongji, dataList);
            final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(linermanager);
            mScanAdapter.addHeaderView(head);
            mScanAdapter.setOnLoadMoreListener(this, mRecyclerView);
            //重复执行动画
            mScanAdapter.isFirstOnly(false);
            mRecyclerView.setAdapter(mScanAdapter);
        }
        catch (Exception e){
             ToastUtils.showLong(""+e);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        String type=null;
        if(isMounth){
            type="1";
        }else {
            type="0";
        }
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getManagerSignins&token_id="+token_id+"&date="+time+"&page="+page+"&rows=10&type="+type;
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                if(response!=null){
                                    SignList signList = JSONUtil.fromJson(response, SignList.class);
                                    if(signList.getRet().equals("200")){
                                        if(page>signList.getIntmaxPage()){
                                            page=1;
                                            mScanAdapter.loadMoreComplete();
                                            mScanAdapter.loadMoreEnd();
                                        }
                                        else if(signList.getDataList().size()!=0){
                                            List<SignList.DataListBean> dataList = signList.getDataList();
                                            LoadMoreData(dataList);
                                            mScanAdapter.loadMoreComplete();
                                        }
                                    }
                                    else if(signList.getDataList().size()==0){
                                        mScanAdapter.loadMoreComplete();
                                        mScanAdapter.loadMoreEnd();
                                    }
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getActivity(), "错误了", Toast.LENGTH_SHORT).show();
                            mScanAdapter.loadMoreComplete();
                            mScanAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(getActivity(), "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mScanAdapter.loadMoreComplete();
                            mScanAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){
            Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
        }


    }

    private void LoadMoreData(List<SignList.DataListBean> dataList) {
        if(isMounth){
            if(dataList.size()!=0){
                mScanAdapter.addData(dataList);
                mScanAdapter.loadMoreComplete();
            }
        }
        else {
            if(dataList.size()!=0){
                mScanAdapter.addData(dataList);
                mScanAdapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void onRefresh() {
        if(isMounth){
            getMounthData(time,token_id);
        }
        else {
            getData(time,token_id);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.this_month:
                isMounth=true;
                isClick=true;
                ncalendar.toToday();
                getMounthData(time,token_id);
        }
    }

    private void getMounthData(final LocalDate time, final String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getManagerSignins&token_id="+token_id+"&date="+time+"&page=1&rows=10&type=1";
        mSwipeRefreshLayout.setRefreshing(true);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Log.d("77777777",response);
                            if(response!=null){
                                isClick=false;
                                mSwipeRefreshLayout.setRefreshing(false);
                                try {
                                    SignList signList = JSONUtil.fromJson(response, SignList.class);
                                    if(signList.getRet().equals("101")){
                                        Toast.makeText(mContext, ""+signList.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(mContext,"token_id",null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    }
                                    else if(signList.getRet().equals("200")){
                                        if(signList.getDataList()!=null){
                                            dataList = signList.getDataList();
                                            getTabMounthData(time,token_id);
                                            if(dataList.size()!=0){
                                                showTabData(dataList);
                                                ll_nodata.setVisibility(View.GONE);
                                            }
                                            else {
                                                showTabData(dataList);
                                                ll_nodata.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                    else if(signList.getRet().equals("201")){
                                        Toast.makeText(mContext, ""+signList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    Toast.makeText(mContext, ""+e, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(mContext, "获取数据错误了"+msg, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(mContext, "获取数据错误了！", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){
            ToastUtils.showLong(e+"");
        }

    }



    public void setDate(View view) {
        ncalendar.setDate("2017-12-31");
    }

    public void toToday(View view) {
        ncalendar.toToday();
    }

    public void toNextPager(View view) {
        ncalendar.toNextPager();
    }

    public void toLastPager(View view) {
        ncalendar.toLastPager();
    }

    @Override
    public void onWeekCalendarChanged(LocalDate date) {
        MyLog.d("dateTime::" + date);
        time=date;
        if(!isClick){
            isMounth=false;
            getData(time,token_id);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("陌拜签到")){
            if(isMounth){
                getMounthData(time,token_id);
            }
            else {
                getData(time,token_id);
            }
        }
    }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), SignActivity.class),REQUEST_CODE_SIGN);
        }else if (tag.equals(2)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
//            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(3)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), XiansuoActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(4)){
            fabTop.closeAnimate();
            //表示所有权限都授权了
            Intent openCameraIntent = new Intent(getActivity(), ProActivityActivity2.class);
            openCameraIntent.putExtra("type", "3");
            openCameraIntent.putExtra("event","6");
            startActivityForResult(openCameraIntent, REQUEST_CODE_ACTIVITY );
        }
        else if (tag.equals(5)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), DailyOrderActivity.class),REQUEST_CODE_DAILY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN) {
            if(isMounth){
                getMounthData(time,token_id);
            }
            else {
                getData(time,token_id);
            }
        }else if(requestCode==REQUEST_CODE_ADD){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fabHandler.removeCallbacksAndMessages(getActivity());
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

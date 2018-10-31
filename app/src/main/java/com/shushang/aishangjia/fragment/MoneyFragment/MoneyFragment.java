package com.shushang.aishangjia.fragment.MoneyFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codingending.popuplayout.PopupLayout;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter2;
import com.shushang.aishangjia.fragment.MoneyFragment.refreshHandler.MoneyDataRefreshHandler;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class MoneyFragment extends BaseFragment {
    private static final int REQUEST_CODE_SCAN = 2002;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView,mSignPeopleRecyclerView,mRecyclerView2;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private TextView mTextView;
    private ImageView mSearch,tabTextView;
    private ImageView mImageView;
//    private FrameLayout mFrameLayout;
    private TabRecyclerViewAdapter tabRecyclerViewAdapter;
    private TabRecyclerViewAdapter2 mTabRecyclerViewAdapter;
    private MoneyDataRefreshHandler mMoneyDataRefreshHandler;
    private RelativeLayout mLoading;
    private Button mQuit;
    private List<TabList.DataListBean> data;
    private boolean isFirst=true;
    private String allData="100";
    private MainActivity mMainActivity;
    private LinearLayout llnodata;
    private String base_url= BaseUrl.BASE_URL;
    private PopupLayout popupLayout;
    private View mView;
    private boolean useRadius=true;//是否使用圆角特性
    private LinearLayout mLinearLayout;
    private String  lianmengtype= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "type");
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
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_money, null);
        return view;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rl_tab);
        mSignPeopleRecyclerView=rootView.findViewById(R.id.rv_sign);
        mView=View.inflate(getActivity(),R.layout.tablist,null);
        mRecyclerView2=mView.findViewById(R.id.rv_sign2);
        tabTextView=mView.findViewById(R.id.quit_item);
        mLinearLayout=rootView.findViewById(R.id.scan_code);
        mSwipeRefreshLayout=rootView.findViewById(R.id.srl_home);
        mToolbar=rootView.findViewById(R.id.toolbar);
        llnodata=rootView.findViewById(R.id.ll_no_data);
        mImageView=rootView.findViewById(R.id.more);
//        mFrameLayout=rootView.findViewById(R.id.boom);
        popupLayout=PopupLayout.init(getActivity(),mView);
        popupLayout.setUseRadius(useRadius);
        popupLayout.setHeight(850,true);//手动设置弹出布局的高度
        mToolbar.setTitle("");
        mTextView=rootView.findViewById(R.id.mounth);
        mLoading=rootView.findViewById(R.id.loading);
        if(lianmengtype==null||lianmengtype.equals("")){
            mLinearLayout.setVisibility(View.GONE);
        }
        else {
            mLinearLayout.setVisibility(View.VISIBLE);
        }
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表示所有权限都授权了
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                openCameraIntent.putExtra("type", PreferencesUtils.getString(mContext,"roleType"));
//                openCameraIntent.putExtra("type", "1");
                startActivityForResult(openCameraIntent, REQUEST_CODE_SCAN );
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTabData(allData);
                mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                tabRecyclerViewAdapter.setThisPosition(100);
                tabRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        tabTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupLayout!=null){
                    popupLayout.dismiss();
                }
            }
        });

        //设置支持toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        initDataRecyclerView();
        mMoneyDataRefreshHandler= MoneyDataRefreshHandler.create(mMainActivity,mSwipeRefreshLayout,mSignPeopleRecyclerView,mLoading,mHandler,llnodata);
//        mTranslationAnimate=new TranslationAnimate(SourseLocation,targetLocation,mFrameLayout,mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupLayout.show();//默认从底部弹出
            }
        });

        EventBus.getDefault().register(this);

    }

    private void initDataRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
        mSignPeopleRecyclerView.setLayoutManager(linermanager);
    }

    @Override
    public void initData() {
        super.initData();
        getTabData("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity= (MainActivity) context;
        mMainActivity.setHandler(mHandler);
    }

    //获取tab栏数据
    private void getTabData(final String allData) {
        String token_id= PreferencesUtils.getString(mContext,"token_id");
        String activity_id= PreferencesUtils.getString(mContext,"activityId");
//        String url="https://raw.githubusercontent.com/ntrer/JSONApi/master/TabApi2.json";
        String url=base_url+"phoneApi/activityController.do?method=getMerchants&token_id="+token_id+"&activity_id="+activity_id;
        Log.d("url",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Log.d("response",response);
                            try {
                                TabList tabList = JSONUtil.fromJson(response, TabList.class);
                                if(tabList.getRet().equals("101")){
                                    Toast.makeText(mContext, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(mContext,"token_id",null);
                                    startActivity(new Intent(getActivity(), LoginActivity2.class));
                                    getActivity().finish();
                                }
                                else if(tabList.getRet().equals("200")){
                                    data = tabList.getDataList();
                                    if(data.size()!=0){
                                        if (data.size() > 15) {
                                            mImageView.setVisibility(View.VISIBLE);
                                            showTabData(data);
                                            showTabData2(data);
                                        } else {
                                            mImageView.setVisibility(View.GONE);
                                            showTabData(data);
                                        }
                                        isFirst=false;
                                        if(!allData.equals("")){
                                            mMoneyDataRefreshHandler.getTab(allData);
                                        }else {
                                            mMoneyDataRefreshHandler.onRefresh();
                                        }
                                    }
                                }
                                else if(tabList.getRet().equals("201")){
                                    Toast.makeText(mContext, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }



    private void showTabData(List<TabList.DataListBean>  data) {
        if(isFirst){
            tabRecyclerViewAdapter=new TabRecyclerViewAdapter(R.layout.tab_items,data);
            tabRecyclerViewAdapter.setThisPosition(100);
//            mTabRecyclerViewAdapter=new TabRecyclerViewAdapter2(R.layout.tab_items2,data);
//            mTabRecyclerViewAdapter.setThisPosition(100);
        }
        initRecyclerView(tabRecyclerViewAdapter);
//        initRecyclerView2(mTabRecyclerViewAdapter);
    }

    private void showTabData2(List<TabList.DataListBean>  data) {
        if(isFirst){
            mTabRecyclerViewAdapter=new TabRecyclerViewAdapter2(R.layout.tab_items2,data);
            mTabRecyclerViewAdapter.setThisPosition(100);

        }
        initRecyclerView2(mTabRecyclerViewAdapter,data);
    }


    private void initRecyclerView2(final TabRecyclerViewAdapter2 tabRecyclerViewAdapter2, final List<TabList.DataListBean> dataList) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        mRecyclerView2.setLayoutManager(gridLayoutManager);
        mRecyclerView2.setAdapter(tabRecyclerViewAdapter2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//               if(Math.round(getTextWidth(dataList.get(position).getMerchant_name()))>80){
//                    return 2;
//                }
//                return 1;
//            }
//        });
        tabRecyclerViewAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextView.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                mMoneyDataRefreshHandler.switchData(data.get(position).getMerchant_id());
                mRecyclerView.scrollToPosition(position);
                popupLayout.dismiss();
            }
        });
    }

    private void initRecyclerView(final TabRecyclerViewAdapter tabRecyclerViewAdapter) {
        final LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(manager);
        //解决嵌套滑动冲突
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(tabRecyclerViewAdapter);
        tabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextView.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                mMoneyDataRefreshHandler.switchData(data.get(position).getMerchant_id());
            }
        });
    }


     public void getData(){
         mMoneyDataRefreshHandler.onRefresh();
     }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MoneyFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MoneyFragment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        getData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(popupLayout!=null){
            popupLayout.dismiss();
        }
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public float getTextWidth(String text) {
        TextPaint textPaint = new TextPaint();
        return textPaint.measureText(text);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SCAN){
            getData();
        }
    }


}

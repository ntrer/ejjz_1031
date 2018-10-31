package com.shushang.aishangjia.fragment.HomeFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codingending.popuplayout.PopupLayout;
import com.shushang.aishangjia.Bean.ActionCustomersBean;
import com.shushang.aishangjia.Bean.ActivityBean;
import com.shushang.aishangjia.Bean.CustomersBean;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.Bean.UserData;
import com.shushang.aishangjia.Bean.info;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.NoOnLineActivity;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter2;
import com.shushang.aishangjia.fragment.HomeFragment.refreshHandler.HomeDataRefreshHandler;
import com.shushang.aishangjia.greendao.ActionCustomersBeanDao;
import com.shushang.aishangjia.greendao.ActivityBeanDao;
import com.shushang.aishangjia.greendao.CustomersBeanDao;
import com.shushang.aishangjia.greendao.DaoMaster;
import com.shushang.aishangjia.greendao.DaoSession;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class HomeFragment extends BaseFragment {
    private static final int REQUEST_CODE_SCAN = 2002;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView,mSignPeopleRecyclerView,mRecyclerView2;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private ImageView mImageView,tabTextView;
    private TextView mTextView;
    private TabRecyclerViewAdapter tabRecyclerViewAdapter;
    private TabRecyclerViewAdapter2 mTabRecyclerViewAdapter;
    private HomeDataRefreshHandler mHomeDataRefreshHandler;
    private RelativeLayout mLoading;
    private TextView mLiXian;
    private List<TabList.DataListBean> data;
    private  List<ActionCustomersBean> actionCustomers=new ArrayList<>();
    private List<CustomersBean> customers=new ArrayList<>();
    private ActivityBean mActivityBean;
    private ActionCustomersBeanDao actionCustomersBeanDao;
    private  CustomersBeanDao customersBeanDao;
    private ActivityBeanDao activityBeanDao;
    private boolean isFirst=true;
    private String allData="100";
    private LinearLayout llnodata;
    private MainActivity mMainActivity;
    private View mView;
    private PopupLayout popupLayout;
    private Dialog mRequestDialog;
    private String sync_time;
    private boolean useRadius=true;//是否使用圆角特性
    private List<info> mInfos=new ArrayList<>();
    private String infos;
    private static DaoSession daoSession;
    private String token_id=null;
    private LinearLayout mLinearLayout;
    private String  lianmengtype= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "type");
    public Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    getData();
                    break;
            }
            return false;
        }
    });

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity= (MainActivity) context;
        mMainActivity.setHandler(mHandler);
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rl_tab);
        mRecyclerView2=rootView.findViewById(R.id.rv_sign2);
        mSignPeopleRecyclerView=rootView.findViewById(R.id.rv_sign);
        mSwipeRefreshLayout=rootView.findViewById(R.id.srl_home);
        mLinearLayout=rootView.findViewById(R.id.scan_code);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(getActivity(), getString(R.string.getDataBase));
        mRequestDialog.setCancelable(false);
        mLiXian=rootView.findViewById(R.id.lixian);
        llnodata=rootView.findViewById(R.id.ll_no_data);
        mImageView=rootView.findViewById(R.id.more);
        mToolbar=rootView.findViewById(R.id.toolbar);
        mView=View.inflate(getActivity(),R.layout.tablist,null);
        tabTextView=mView.findViewById(R.id.quit_item);
        mRecyclerView2=mView.findViewById(R.id.rv_sign2);
        popupLayout=PopupLayout.init(getActivity(),mView);
        popupLayout.setUseRadius(useRadius);
        popupLayout.setHeight(850,true);//手动设置弹出布局的高度
//        popupLayout.setWidth(320,true);//手动设置弹出布局的宽度
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
        mLiXian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlertDialog.showSureDlg(getActivity(), "进入离线模式", getString(R.string.message), getString(R.string.sure), new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if(result==1){
                            sync_time=PreferencesUtils.getString(getActivity(),"dataSync");
                            mRequestDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getUserData();
                                }
                            },1000);
                        }
                    }
                });


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
        mHomeDataRefreshHandler=HomeDataRefreshHandler.create(mMainActivity,mSwipeRefreshLayout,mSignPeopleRecyclerView,mLoading,mHandler,llnodata);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        setUpDataBase();
        boolean networkAvailable = isNetworkAvailable(getActivity());
        if(networkAvailable){
            List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();
            getUpdateInfo(actionCustomersBeans);
            if(infos!=null&&!infos.equals("")){
                UpDate();
            }
        }
        else {
            ToastUtils.showLong("当前网络不可用");
        }
        getTabData("");
    }

    private void getUserData() {
        String token_id= PreferencesUtils.getString(mContext,"token_id");
        String activity_id= PreferencesUtils.getString(mContext,"activityId");
        String url= BaseUrl.BASE_URL+"phoneApi/outLineController.do?method=downloadData&token_id="+token_id+"&activityId="+activity_id+"&time=";
        Log.d("userDaata",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                if(response!=null){
                                   try {
                                       UserData userData = JSONUtil.fromJson(response, UserData.class);
                                       if (userData.getRet().equals("101")) {
                                           Toast.makeText(mContext, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                           PreferencesUtils.putString(mContext, "token_id", null);
                                           startActivity(new Intent(getActivity(), LoginActivity2.class));
                                           getActivity().finish();
                                       } else {
                                           if (userData.getRet().equals("200")) {
                                               Log.d("用户数据",response);
                                               actionCustomers = userData.getData().getActionCustomers();
                                               customers = userData.getData().getCustomers();
                                               mActivityBean=userData.getData().getActivity();
                                               insertInToDataBase(actionCustomers,customers,mActivityBean);
                                           }
                                           else if(userData.getRet().equals("201")){
                                               Toast.makeText(mContext, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
                                   catch (Exception e){
                                       if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                           mRequestDialog.dismiss();
                                       }
                                       ToastUtils.showLong(""+e);

                                   }
                                }
                            }
                            catch (Exception e){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                ToastUtils.showLong(""+e);

                            }

                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            if(sync_time!=null&&!sync_time.equals("")){
                                ExtAlertDialog.showSureDlg(getActivity(), "提醒","本次数据未同步成功,本地数据库会以您上次同步的数据为基准," +
                                                "您上次同步的时间为："+sync_time+",您是否确定继续进入离线模式(如您需要同步,请先检查网络情况是否良好,如仍未解决,请联系客服", getString(R.string.sure), new ExtAlertDialog.IExtDlgClick() {
                                    @Override
                                    public void Oclick(int result) {
                                        if(result==1){
                                            Intent openCameraIntent = new Intent(getActivity(), NoOnLineActivity.class);
                                            startActivity(openCameraIntent);
                                        }
                                    }
                                });
                            }
                            else {
                                try {
                                    ToastUtils.showLong(sync_time);
                                }
                                catch (Exception e){
                                    ToastUtils.showLong("您当前无法进入离线模式，请先连接到网络，同步数据后再进入");
                                }
                            }

                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            if(sync_time!=null&&!sync_time.equals("")){
                                ExtAlertDialog.showSureDlg(getActivity(), "提醒","本次数据未同步成功,本地数据库会以您上次同步的数据为基准," +
                                        "您上次同步的时间为："+sync_time+",您是否确定继续进入离线模式(如您需要同步,请先检查网络情况是否良好,如仍未解决,请联系客服", getString(R.string.sure), new ExtAlertDialog.IExtDlgClick() {
                                    @Override
                                    public void Oclick(int result) {
                                        if(result==1){
                                            Intent openCameraIntent = new Intent(getActivity(), NoOnLineActivity.class);
                                            startActivity(openCameraIntent);
                                        }
                                    }
                                });
                            }
                            else {
                                try {
                                    ToastUtils.showLong(sync_time);
                                }
                                catch (Exception e){
                                    ToastUtils.showLong("您当前无法进入离线模式，请先同步数据后再进入");
                                }
                            }
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(""+e);
        }

    }

    private void insertInToDataBase(List<ActionCustomersBean> actionCustomers, List<CustomersBean> customers, ActivityBean activityBean) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            PreferencesUtils.putString(getActivity(),"dataSync",df.format(new Date())+"");
            actionCustomersBeanDao = MyApplication.getDaoInstant().getActionCustomersBeanDao();
            customersBeanDao = MyApplication.getDaoInstant().getCustomersBeanDao();
            activityBeanDao = MyApplication.getDaoInstant().getActivityBeanDao();
            activityBeanDao.insert(activityBean);
            actionCustomersBeanDao.deleteAll();
            customersBeanDao.deleteAll();
            activityBeanDao.deleteAll();
            activityBeanDao.insert(activityBean);
            if(actionCustomers !=null&& actionCustomers.size()>0) {
                for (int i = 0; i< actionCustomers.size(); i++){
                    ActionCustomersBean actionCustomersBean = actionCustomers.get(i);
                    actionCustomersBeanDao.insert(actionCustomersBean);
                }
            }


            if(customers !=null&& customers.size()>0) {
                customersBeanDao.insertInTx(customers);
//                for (int i = 0; i< customers.size(); i++){
//                    CustomersBean customersBean = customers.get(i);
//                    customersBeanDao.insert(customersBean);
//                }
            }

            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }

            Intent openCameraIntent = new Intent(getActivity(), NoOnLineActivity.class);
            startActivity(openCameraIntent);
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
            Log.d("数据库错误",e.toString());
        }

    }



    //获取tab栏数据
    private void getTabData(final String allData) {
        String token_id= PreferencesUtils.getString(mContext,"token_id");
        String activity_id= PreferencesUtils.getString(mContext,"activityId");
//        String url="https://raw.githubusercontent.com/ntrer/JSONApi/master/TabApi2.json";
        String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getMerchants&token_id="+token_id+"&activity_id="+activity_id;
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            if(response!=null) {
                                try {
                                    TabList tabList = JSONUtil.fromJson(response, TabList.class);
                                    if (tabList.getRet().equals("101")) {
                                        Toast.makeText(mContext, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(mContext, "token_id", null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    } else {
                                        Log.d("TabList", response);
                                        if (tabList.getRet().equals("200")) {
                                            if (tabList.getDataList() != null) {
                                                data = tabList.getDataList();
                                                if (data.size() != 0) {
                                                    if (data.size() > 15) {
                                                        mImageView.setVisibility(View.VISIBLE);
                                                        showTabData(data);
                                                        showTabData2(data);
                                                    } else {
                                                        mImageView.setVisibility(View.GONE);
                                                        showTabData(data);
                                                    }
                                                    isFirst = false;
                                                    if (!allData.equals("")) {
                                                        mHomeDataRefreshHandler.getTab(allData);
                                                    } else {
                                                        mHomeDataRefreshHandler.onRefresh();
                                                    }
                                                }
                                            }
                                        }
                                        else if(tabList.getRet().equals("201")){
                                            Toast.makeText(mContext, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                catch (Exception e){

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

        }
        initRecyclerView(tabRecyclerViewAdapter);
    }

    private void showTabData2(List<TabList.DataListBean>  data) {
        if(isFirst){
            mTabRecyclerViewAdapter=new TabRecyclerViewAdapter2(R.layout.tab_items2,data);
            mTabRecyclerViewAdapter.setThisPosition(100);

        }
        initRecyclerView2(mTabRecyclerViewAdapter);
    }



    private void initRecyclerView2(final TabRecyclerViewAdapter2 tabRecyclerViewAdapter2) {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        mRecyclerView2.setLayoutManager(gridLayoutManager);
        mRecyclerView2.setAdapter(tabRecyclerViewAdapter2);
        tabRecyclerViewAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mTextView.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                mHomeDataRefreshHandler.switchData(data.get(position).getMerchant_id());
                mRecyclerView.scrollToPosition(position);
                popupLayout.dismiss();
//                mTranslationAnimate.close();
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
                mHomeDataRefreshHandler.switchData(data.get(position).getMerchant_id());
            }
        });
    }


    public void getData(){
//        List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();
//        ToastUtils.showLong(actionCustomersBeans.get(1).getCustomerName());
        mHomeDataRefreshHandler.onRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HomeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HomeFragment");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("离线签到")){
           getData();
        }
    }




    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    private void setUpDataBase() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(getActivity(),"dsx.db",null);
        SQLiteDatabase db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
        actionCustomersBeanDao = daoSession.getActionCustomersBeanDao();
        customersBeanDao =daoSession.getCustomersBeanDao();
        activityBeanDao=daoSession.getActivityBeanDao();
    }

    private void getUpdateInfo(List<ActionCustomersBean> actionCustomersBeans) {
        if(actionCustomersBeans.size()!=0){
            try {
                actionCustomers.clear();
                mInfos.clear();
                for (int i=0;i<actionCustomersBeans.size();i++){
                    if(actionCustomersBeans.get(i).getQdsucess().equals("1")&&actionCustomersBeans.get(i).getIsSign()!=null){
                        actionCustomers.add(actionCustomersBeans.get(i));
                    }
                }

                for (int i=0;i<actionCustomers.size();i++){
                    info info=new info();
                    info.setCustomerActionId(actionCustomers.get(i).getCustomerActionId());
                    info.setQdsj(String.valueOf(actionCustomers.get(i).getQdsj()));
                    info.setQdsuccess(actionCustomers.get(i).getQdsucess());
                    info.setLqsj(String.valueOf(actionCustomers.get(i).getLqsj()));
                    info.setLqsuccess(actionCustomers.get(i).getLqsuccess());
                    mInfos.add(info);
                }

                infos = JSONUtil.toJSON(mInfos);
                Log.d("Updateinfos",infos+"");
            }
            catch (Exception e){
                ToastUtils.showLong(e.toString());
                Log.d("推出了","5");
            }
        }

    }


    private void UpDate() {
            String token_id= PreferencesUtils.getString(mContext,"token_id");
            String url = BaseUrl.BASE_URL+"phoneApi/outLineController.do?method=uploadData&token_id="+token_id;
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("info", infos);
            Log.d("创建活动1",infos);
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    ToastUtils.showLong("当前网络链接有问题，同步数据库失败，无法退出离线模式");
                }

                @Override
                public void onResponse(String response) {
                    Log.d("创建活动2",response);
                    if(response!=null){
                        try {
                            Response response1 = JSONUtil.fromJson(response, Response.class);
                            if(response1.getRet().equals("200")){

                            }
                            else if(response1.getRet().equals("201")){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(getActivity(), ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response1.getRet().equals("101")) {
                                Toast.makeText(getActivity(), ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(getActivity(), "token_id", null);
                                startActivity(new Intent(getActivity(), LoginActivity2.class));
                                getActivity().finish();
                            }
                            else {
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(getActivity(), ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            ToastUtils.showLong(e.toString());
                            Log.d("推出了","6");
                        }

                    }

                }
            });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SCAN){
            getData();
        }
    }
}

package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.ActionCustomersBean;
import com.shushang.aishangjia.Bean.ActivityBean;
import com.shushang.aishangjia.Bean.ActivityList;
import com.shushang.aishangjia.Bean.CustomersBean;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.Bean.UserData;
import com.shushang.aishangjia.Bean.info;
import com.shushang.aishangjia.MainActivity;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ActivityListAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
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
import com.shushang.aishangjia.utils.ActivityManager.ActivityStackManager;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class ActivityListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private String token_id = null;
    private Button mButton,mButton2;
    private LinearLayout ll_nodata;
    private ActivityListAdapter mActivityListAdapter;
    private List<ActivityList.DataListBean> dataList=new ArrayList<>();
    private List<ActionCustomersBean> actionCustomers3=new ArrayList<>();
    private List<info> mInfos=new ArrayList<>();
    private String infos;
    private String type;
    private Toolbar mToolbar;
    //退出时的时间
    private long mExitTime;
    private ProgressBar mProgressBar;
    private Dialog mRequestDialog;
    private  List<ActionCustomersBean> actionCustomers=new ArrayList<>();
    private List<CustomersBean> customers=new ArrayList<>();
    private ActivityBean mActivityBean;
    private ActionCustomersBeanDao actionCustomersBeanDao;
    private CustomersBeanDao customersBeanDao;
    private ActivityBeanDao activityBeanDao;
    private static DaoSession daoSession;
    @Override
    public int setLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void init() {
        mToolbar=findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity);
        ll_nodata= (LinearLayout) findViewById(R.id.ll_no_data);
        mProgressBar=findViewById(R.id.loading);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(ActivityListActivity.this, getString(R.string.getDataBase));
        mRequestDialog.setCancelable(false);
        Intent intent=getIntent();
        token_id = PreferencesUtils.getString(this, "token_id");
        type=PreferencesUtils.getString(this, "type");
        mButton= (Button) findViewById(R.id.btn_quit);
        mButton2=findViewById(R.id.btn_lianmeng);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.putString(getApplicationContext(), "company", null);
                PreferencesUtils.putString(getApplicationContext(), "user_name", null);
                PreferencesUtils.putString(getApplicationContext(), "password", null);
                PreferencesUtils.putString(getApplicationContext(), "token_id",null);
                PreferencesUtils.putString(getApplicationContext(),"type",null);
                startActivity(new Intent(ActivityListActivity.this, LoginActivity2.class));
                finish();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityListActivity.this,MainActivity.class);
                intent.putExtra("noActivityLianmeng","true");
                startActivity(intent);
            }
        });
        initData();
        initRecyclerView();
        List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();
        getUpdateInfo(actionCustomersBeans);
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData() {
        mProgressBar.setVisibility(View.VISIBLE);
        setUpDataBase();
        String url = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getActivitys&token_id=" + token_id;
        Log.d("BaseUrl", url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null){
                                try {
                                    ActivityList activityList = JSONUtil.fromJson(response, ActivityList.class);
                                    if(activityList.getRet().equals("200")){
                                        dataList = activityList.getDataList();
                                        if(dataList.size()!=0){
                                            mProgressBar.setVisibility(View.GONE);
                                            ll_nodata.setVisibility(View.GONE);
                                            mButton.setVisibility(View.GONE);
                                            mButton2.setVisibility(View.GONE);
                                            showData(dataList);
                                        }
                                        else {
                                            mProgressBar.setVisibility(View.GONE);
                                            ll_nodata.setVisibility(View.VISIBLE);
                                            mButton.setVisibility(View.VISIBLE);
                                            if(type.equals("7")){
                                                mButton2.setVisibility(View.VISIBLE);
                                            }
                                            else {
                                                mButton2.setVisibility(View.GONE);
                                            }

//                                    PreferencesUtils.putString(ActivityListActivity.this, "token_id",null);
//                                    startActivity(new Intent(ActivityListActivity.this,LoginActivity2.class));
//                                    finish();
                                        }
                                    }
                                    else {
                                        mProgressBar.setVisibility(View.GONE);
                                        PreferencesUtils.putString(ActivityListActivity.this, "token_id",null);
                                        startActivity(new Intent(ActivityListActivity.this,LoginActivity2.class));
                                        finish();
                                    }
                                }
                                catch (Exception e){
                                    mProgressBar.setVisibility(View.GONE);
                                    ll_nodata.setVisibility(View.VISIBLE);
                                    Toast.makeText(ActivityListActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mProgressBar.setVisibility(View.GONE);
                            ll_nodata.setVisibility(View.VISIBLE);
                            mButton.setVisibility(View.VISIBLE);
                            Toast.makeText(ActivityListActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    ll_nodata.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(ActivityListActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            ToastUtils.showLong("获取数据错误了");
        }

    }

    private void showData(final List<ActivityList.DataListBean> dataList) {
        mActivityListAdapter=new ActivityListAdapter(R.layout.item_activitylist,dataList);
        mRecyclerView.setAdapter(mActivityListAdapter);
        mRecyclerView.scrollToPosition(0);
        mActivityListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String activityId =dataList.get(position).getActivityId();
                String roleType=dataList.get(position).getRoleType();
                PreferencesUtils.putString(ActivityListActivity.this,"activityId",activityId);
                Log.d("wocaoyd",activityId+"");
                PreferencesUtils.putString(ActivityListActivity.this,"roleType",roleType);
                UpDate();
            }
        });
    }

    private void getUpdateInfo(List<ActionCustomersBean> actionCustomersBeans) {
        if(actionCustomersBeans.size()!=0){
            try {
                for (int i=0;i<actionCustomersBeans.size();i++){
                    if(actionCustomersBeans.get(i).getQdsucess().equals("1")&&actionCustomersBeans.get(i).getIsSign()!=null){
                        actionCustomers3.add(actionCustomersBeans.get(i));
                    }
                }

                for (int i=0;i<actionCustomers3.size();i++){
                    info info=new info();
                    info.setCustomerActionId(actionCustomers3.get(i).getCustomerActionId());
                    info.setQdsj(String.valueOf(actionCustomers3.get(i).getQdsj()));
                    info.setQdsuccess(actionCustomers3.get(i).getQdsucess());
                    info.setLqsj(String.valueOf(actionCustomers3.get(i).getLqsj()));
                    info.setLqsuccess(actionCustomers3.get(i).getLqsuccess());
                    mInfos.add(info);
                }

                infos = JSONUtil.toJSON(mInfos);
                Log.d("Updateinfos",infos+"");
            }
            catch (Exception e){
                ToastUtils.showLong("操作失败，请重试");
            }
        }

    }


    private void UpDate() {
        if(infos==null||infos.equals("")){
            mRequestDialog.show();
            getUserData();
        }
        else {
            mRequestDialog.show();
            String url = BaseUrl.BASE_URL+"phoneApi/outLineController.do?method=uploadData&token_id="+token_id;
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("info", infos);
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
                    Log.d("创建活动",response);
                    if(response!=null){
                        try {
                            Response response1 = com.shushang.aishangjia.utils.Json.JSONUtil.fromJson(response, Response.class);
                            if(response1.getRet().equals("200")){
                                getUserData();
                            }
                            else if(response1.getRet().equals("201")){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(ActivityListActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response1.getRet().equals("101")) {
                                Toast.makeText(ActivityListActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(ActivityListActivity.this, "token_id", null);
                                startActivity(new Intent(ActivityListActivity.this, LoginActivity2.class));
                                finish();
                            }
                            else {
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(ActivityListActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            ToastUtils.showLong(e.toString());
                        }

                    }

                }
            });

        }

    }





    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //退出应用
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getActivityStackManager().popAllActivity();//remove all activity.
            finish();
            System.exit(0);
        }
    }


    private void setUpDataBase() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(ActivityListActivity.this,"dsx.db",null);
        SQLiteDatabase db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
        actionCustomersBeanDao = daoSession.getActionCustomersBeanDao();
        customersBeanDao =daoSession.getCustomersBeanDao();
        activityBeanDao=daoSession.getActivityBeanDao();
    }

    private void getUserData() {
        String token_id= PreferencesUtils.getString(ActivityListActivity.this,"token_id");
        String activity_id= PreferencesUtils.getString(ActivityListActivity.this,"activityId");
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
                                        UserData userData = com.shushang.aishangjia.utils.Json.JSONUtil.fromJson(response, UserData.class);
                                        if (userData.getRet().equals("101")) {
                                            Toast.makeText(ActivityListActivity.this, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                            com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils.putString(ActivityListActivity.this, "token_id", null);
                                            startActivity(new Intent(ActivityListActivity.this, LoginActivity2.class));
                                            finish();
                                        } else {
                                            if (userData.getRet().equals("200")) {
                                                Log.d("用户数据",response);
                                                actionCustomers = userData.getData().getActionCustomers();
                                                customers = userData.getData().getCustomers();
                                                mActivityBean=userData.getData().getActivity();
                                                insertInToDataBase(actionCustomers,customers,mActivityBean);
                                            }
                                            else if(userData.getRet().equals("201")){
                                                Toast.makeText(ActivityListActivity.this, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
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
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
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
            PreferencesUtils.putString(ActivityListActivity.this,"dataSync",df.format(new Date())+"");
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

            if(type.equals("")||type==null){
                startActivity(new Intent(ActivityListActivity.this, MainActivity.class));
            }
            else if(type.equals("7")){
                Intent intent=new Intent(ActivityListActivity.this,MainActivity.class);
                intent.putExtra("noActivityLianmeng","false");
                startActivity(intent);
            }

            finish();
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
            Log.d("数据库错误",e.toString());
        }

    }



}

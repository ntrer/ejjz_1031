package com.shushang.aishangjia.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.ActionCustomersBean;
import com.shushang.aishangjia.Bean.ActivityBean;
import com.shushang.aishangjia.Bean.CustomersBean;
import com.shushang.aishangjia.Bean.Response;
import com.shushang.aishangjia.Bean.UserData;
import com.shushang.aishangjia.Bean.info;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.NoOnLineSignAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.base.PermissionListener;
import com.shushang.aishangjia.greendao.ActionCustomersBeanDao;
import com.shushang.aishangjia.greendao.ActivityBeanDao;
import com.shushang.aishangjia.greendao.CustomersBeanDao;
import com.shushang.aishangjia.greendao.DaoMaster;
import com.shushang.aishangjia.greendao.DaoSession;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.CallBackUtil;
import com.shushang.aishangjia.utils.OkhttpUtils.OkhttpUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class NoOnLineActivity extends BaseActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ImageView mImageView,tabTextView;
    private TextView sync;
    private RelativeLayout mSign;
    private TextView quit;
    private List<ActionCustomersBean> actionCustomers=new ArrayList<>();
    private List<ActionCustomersBean> actionCustomers2=new ArrayList<>();
    private List<ActionCustomersBean> actionCustomers3=new ArrayList<>();
    private List<info> mInfos=new ArrayList<>();
    private List<CustomersBean> customers;
    private ActionCustomersBeanDao actionCustomersBeanDao;
    private CustomersBeanDao customersBeanDao;
    private LinearLayout llnodata;
    private View mView;
    private static final int REQUEST_CODE_LIXIAN_SIGN = 4001;
    private NoOnLineSignAdapter mNoOnLineSignAdapter;
    private static DaoSession daoSession;
    private String token_id = null;
    private Dialog mRequestDialog;
    private String infos;
    private ActivityBeanDao activityBeanDao;
    private ActivityBean mActivityBean;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5;
    @Override
    public int setLayout() {
        return R.layout.activity_no_on_line;
    }

    @Override
    public void init() {
        mRecyclerView=findViewById(R.id.rv_sign);
        llnodata=findViewById(R.id.ll_no_data);
        mSign=findViewById(R.id.sign);
        quit=findViewById(R.id.quit);
        sync=findViewById(R.id.sync);
        mToolbar=findViewById(R.id.toolbar);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(NoOnLineActivity.this, getString(R.string.getDataBase));
        mRequestDialog.setCancelable(false);
        mToolbar.setTitle("");
        token_id = PreferencesUtils.getString(this, "token_id");
        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionCamera();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpDate();
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncData();
            }
        });
        setUpDataBase();
        initRecyclerView();
    }


    //请求相机权限
    private void permissionCamera(){
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onGranted() {
                Intent openCameraIntent = new Intent(NoOnLineActivity.this, CaptureActivity.class);
                openCameraIntent.putExtra("type", "5");
                startActivityForResult(openCameraIntent,REQUEST_CODE_LIXIAN_SIGN);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for(String permission : deniedPermission){
                    reGetPermission();
                }
            }
        });
    }


    private void reGetPermission() {
        ExtAlertDialog.showSureDlg(NoOnLineActivity.this, "警告", "权限被拒绝，部分功能将无法使用，请重新授予权限", "确定", new ExtAlertDialog.IExtDlgClick() {
            @Override
            public void Oclick(int result) {
                if(result==1){
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void SyncData() {
        if(actionCustomers3.size()==0){
            mRequestDialog.show();
           ToastUtils.showLong("无新数据上传");
           getUserData("sync");
        }
        else {
            mRequestDialog.show();
            String url = BaseUrl.BASE_URL+"phoneApi/outLineController.do?method=uploadData&token_id="+token_id;
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("info", infos);
            Log.d("创建活动",infos);
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
                            Response response1 = JSONUtil.fromJson(response, Response.class);
                            if(response1.getRet().equals("200")){
                                actionCustomers3.clear();
                                getUserData("sync");
                            }
                            else if(response1.getRet().equals("201")){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response1.getRet().equals("101")) {
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(NoOnLineActivity.this, "token_id", null);
                                startActivity(new Intent(NoOnLineActivity.this, LoginActivity2.class));
                                finish();
                            }
                            else {
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void UpDate() {
        if(actionCustomers3.size()==0){
            finish();
        }
        else {
            mRequestDialog.show();
            String url = BaseUrl.BASE_URL+"phoneApi/outLineController.do?method=uploadData&token_id="+token_id;
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("info", infos);
            Log.d("创建活动",infos);
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
                            Response response1 = JSONUtil.fromJson(response, Response.class);
                            if(response1.getRet().equals("200")){
                                getUserData("update");
                            }
                            else if(response1.getRet().equals("201")){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else if (response1.getRet().equals("101")) {
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
                                PreferencesUtils.putString(NoOnLineActivity.this, "token_id", null);
                                startActivity(new Intent(NoOnLineActivity.this, LoginActivity2.class));
                                finish();
                            }
                            else {
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(NoOnLineActivity.this, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
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



    private void initRecyclerView() {
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
    }

    private void initData(ActionCustomersBeanDao actionCustomersBeanDao, CustomersBeanDao customersBeanDao) {
        actionCustomers.clear();
        List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();
        for (int i=0;i<actionCustomersBeans.size();i++){
            if(actionCustomersBeans.get(i).getQdsucess().equals("1")){
                actionCustomers.add(actionCustomersBeans.get(i));
            }
        }
        if(actionCustomers==null||actionCustomers.size()==0){
            llnodata.setVisibility(View.VISIBLE);
        }
        else {
            llnodata.setVisibility(View.GONE);
            showData(actionCustomers);
        }
    }

    private void showData(List<ActionCustomersBean> actionCustomersBeans) {
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview6,null);
        mTextView1=mView.findViewById(R.id.num1);
        mTextView2=mView.findViewById(R.id.num2);
        mTextView3=mView.findViewById(R.id.num3);
        mTextView4=mView.findViewById(R.id.num4);
        mTextView5=mView.findViewById(R.id.num5);
        mNoOnLineSignAdapter = new NoOnLineSignAdapter(R.layout.item_sign,actionCustomersBeans);
        mNoOnLineSignAdapter.addHeaderView(mView);
        mNoOnLineSignAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mNoOnLineSignAdapter);
        mRecyclerView.scrollToPosition(0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_LIXIAN_SIGN){
            if(PreferencesUtils.getString(this,"signed")!=null&&PreferencesUtils.getString(this,"signed").equals("true")){
                actionCustomers2.clear();
                Log.d("actionCustomers2",PreferencesUtils.getString(this,"signed")+"");
                List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();
                if(actionCustomersBeans!=null){
                    Log.d("actionCustomerssize",actionCustomersBeans.size()+"");
                    for (int i=0;i<actionCustomersBeans.size();i++){
                        if(actionCustomersBeans.get(i).getQdsucess().equals("1")&&actionCustomersBeans.get(i).getIsSign()!=null){
                            actionCustomers2.add(actionCustomersBeans.get(i));

                        }
                    }
                    Log.d("actionCustomers2",actionCustomers2.size()+"");
                    if(actionCustomers2==null||actionCustomers2.size()==0){
                        ToastUtils.showLong("无人签到");
                    }
                    else {
                       for (int i=0;i<actionCustomers.size();i++){
                           if(actionCustomers.get(i).getIsSign()!=null){
                               actionCustomers.remove(i);
                           }
                       }
                        actionCustomers.addAll(actionCustomers2);
                        llnodata.setVisibility(View.GONE);
                        showData(actionCustomers);
                        getUpdateInfo(actionCustomersBeans);
                    }
                }
            }
        }
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


    private void setUpDataBase() {
            DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"dsx.db",null);
            SQLiteDatabase db=helper.getWritableDatabase();
            DaoMaster daoMaster=new DaoMaster(db);
            daoSession=daoMaster.newSession();
            actionCustomersBeanDao = daoSession.getActionCustomersBeanDao();
            customersBeanDao =daoSession.getCustomersBeanDao();
            activityBeanDao=daoSession.getActivityBeanDao();
            initData(actionCustomersBeanDao,customersBeanDao);
    }


    private void getUserData(final String flag) {
        String activity_id= PreferencesUtils.getString(NoOnLineActivity.this,"activityId");
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
                                            Toast.makeText(NoOnLineActivity.this, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                            com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils.putString(NoOnLineActivity.this, "token_id", null);
                                            startActivity(new Intent(NoOnLineActivity.this, LoginActivity2.class));
                                            finish();
                                        } else {
                                            if (userData.getRet().equals("200")) {
                                                actionCustomers = userData.getData().getActionCustomers();
                                                customers = userData.getData().getCustomers();
                                                mActivityBean=userData.getData().getActivity();
                                                if(flag.equals("sync")){
                                                    insertInToDataBase2(actionCustomers,customers,mActivityBean);
                                                }
                                                else if(flag.equals("update")){
                                                    insertInToDataBase(actionCustomers,customers,mActivityBean);
                                                }
                                            }
                                            else if(userData.getRet().equals("201")){
                                                Toast.makeText(NoOnLineActivity.this, ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(NoOnLineActivity.this, "同步数据库失败，请检查网络连接情况", Toast.LENGTH_SHORT).show();
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
        Log.d("窝上传了1","66666");
        try {
            Log.d("窝上传了4","66666");
            activityBeanDao.insert(activityBean);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            PreferencesUtils.putString(this,"dataSync",df.format(new Date())+"");
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

            ToastUtils.showLong("同步数据成功");
            EventBus.getDefault().post(new MessageEvent("离线签到"));
            finish();
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
        }

    }


    private void insertInToDataBase2(List<ActionCustomersBean> actionCustomers, List<CustomersBean> customers, ActivityBean activityBean) {
        Log.d("窝上传了1","66666");
        try {
            Log.d("窝上传了4","66666");
            activityBeanDao.insert(activityBean);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            PreferencesUtils.putString(this,"dataSync",df.format(new Date())+"");
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

            ToastUtils.showLong("同步数据成功");
            EventBus.getDefault().post(new MessageEvent("离线签到"));
            initData(actionCustomersBeanDao,customersBeanDao);
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
        }

    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            UpDate();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

}

package com.shushang.aishangjia.fragment.MyFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.shushang.aishangjia.activity.ActivityListActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ResetPwdActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
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
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;

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
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private String token_id=null;
    private String username=null;
    private String shangjia=null;
    private LinearLayout ll_reset;
    private LinearLayout ll_quit;
    private LinearLayout ll_activity;
    private LinearLayout ll_check;
    private TextView mTextView1,mTextView2;
    private TextView mVersionCode;
    private String versionCode;
    private List<ActionCustomersBean> actionCustomers=new ArrayList<>();
    private List<CustomersBean> customers;
    private ActionCustomersBeanDao actionCustomersBeanDao;
    private CustomersBeanDao customersBeanDao;
    private static DaoSession daoSession;
    private Dialog mRequestDialog;
    private List<info> mInfos=new ArrayList<>();
    private String infos;
    private ActivityBeanDao activityBeanDao;
    private ActivityBean mActivityBean;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ll_reset=rootView.findViewById(R.id.re_password);
        ll_quit=rootView.findViewById(R.id.quit);
        ll_check=rootView.findViewById(R.id.check_update);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(getActivity(), getString(R.string.getDataBase));
        mRequestDialog.setCancelable(false);
        mTextView1=rootView.findViewById(R.id.login_info_view);
        mTextView2=rootView.findViewById(R.id.login_view);
        mVersionCode=rootView.findViewById(R.id.version);
        if (null != String.valueOf(versionCode) && !"".equals(String.valueOf(versionCode))){
            mVersionCode.setText(versionCode);
        }

        if (null != String.valueOf(username) && !"".equals(String.valueOf(username))){
            mTextView1.setText(username);
        }

        if (null != String.valueOf(shangjia) && !"".equals(String.valueOf(shangjia))){
            mTextView2.setText(shangjia);
        }

        ll_activity=rootView.findViewById(R.id.switch_activity);
        ll_reset.setOnClickListener(this);
        ll_quit.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_check.setOnClickListener(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_my, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        token_id= PreferencesUtils.getString(getActivity(),"token_id");
        username= PreferencesUtils.getString(mContext, "xingming");
        shangjia= PreferencesUtils.getString(mContext, "shangjia_name");
        versionCode = getVersionCode(mContext);
        setUpDataBase();
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MyFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MyFragment");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_password:
                startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                break;
            case R.id.switch_activity:
                startActivity(new Intent(getActivity(), ActivityListActivity.class));
                getActivity().finish();
                break;
            case R.id.check_update:
                Beta.checkUpgrade();
                break;
            case R.id.quit:
                ExtAlertDialog.showSureDlg(getActivity(), null, getString(R.string.logout_tip), getString(R.string.exit_login), new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if (result == 1) {
//                            String url= BaseUrl.BASE_URL+"phoneApi/activityLogin.do?method=logout&token_id="+token_id;
//                            Log.d("BaseUrl",url);
//                            try {
//                                RestClient.builder()
//                                        .url(url)
//                                        .success(new ISuccess() {
//                                            @Override
//                                            public void onSuccess(String response) {
//                                                if(response!=null){
//                                                    try {
//                                                        Response response1 = JSONUtil.fromJson(response, Response.class);
//                                                        if(response1.getRet().equals("200")){
//                                                            Log.d("退出",response);
//                                                            Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
//                                                            PreferencesUtils.putString(mContext, "company", null);
//                                                            PreferencesUtils.putString(mContext, "user_name", null);
//                                                            PreferencesUtils.putString(mContext, "password", null);
//                                                            PreferencesUtils.putString(mContext, "token_id",null);
//                                                            PreferencesUtils.putString(mContext,"roleType",null);
//                                                            startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                                            getActivity().finish();
//                                                        }
//                                                        else {
//                                                            Toast.makeText(mContext, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
//                                                            PreferencesUtils.putString(mContext, "company", null);
//                                                            PreferencesUtils.putString(mContext, "user_name", null);
//                                                            PreferencesUtils.putString(mContext, "password", null);
//                                                            PreferencesUtils.putString(mContext, "token_id",null);
//                                                            PreferencesUtils.putString(mContext,"roleType",null);
//                                                            startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                                            getActivity().finish();
//                                                        }
//                                                    }
//                                                    catch (Exception e){
//                                                        Log.d("e",e.toString());
//                                                    }
//                                                }
//                                            }
//                                        })
//                                        .failure(new IFailure() {
//                                            @Override
//                                            public void onFailure() {
//                                                Toast.makeText(mContext, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).error(new IError() {
//                                    @Override
//                                    public void onError(int code, String msg) {
//                                        Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                        .build()
//                                        .get();
//                            }
//                            catch (Exception e){
//                                PreferencesUtils.putString(mContext, "company", null);
//                                PreferencesUtils.putString(mContext, "user_name", null);
//                                PreferencesUtils.putString(mContext, "password", null);
//                                PreferencesUtils.putString(mContext, "token_id",null);
//                                PreferencesUtils.putString(mContext,"roleType",null);
//                                startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                getActivity().finish();
//                            }
                            List<ActionCustomersBean> actionCustomersBeans = actionCustomersBeanDao.loadAll();

                            getUpdateInfo(actionCustomersBeans);

                            if(infos!=null&&!infos.equals("")){
                                UpDate();
                            }
                            else {
                                PreferencesUtils.putString(mContext, "company", null);
                                PreferencesUtils.putString(mContext, "user_name", null);
                                PreferencesUtils.putString(mContext, "password", null);
                                PreferencesUtils.putString(mContext, "token_id",null);
                                PreferencesUtils.putString(mContext,"roleType",null);
                                PreferencesUtils.putString(mContext,"type",null);
                                PreferencesUtils.putString(mContext,"shangjia_id",null);
                                startActivity(new Intent(getActivity(), LoginActivity2.class));
                                Log.d("推出了","7");
                                getActivity().finish();
                            }

                        }

                    }
                });
                break;
        }
    }

    public static String getVersionCode(Context mContext) {
        String versionCode = null;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
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


    private void UpDate() {
        if(actionCustomers.size()==0){
            PreferencesUtils.putString(mContext, "company", null);
            PreferencesUtils.putString(mContext, "user_name", null);
            PreferencesUtils.putString(mContext, "password", null);
            PreferencesUtils.putString(mContext, "token_id",null);
            PreferencesUtils.putString(mContext,"roleType",null);
            startActivity(new Intent(getActivity(), LoginActivity2.class));
            Log.d("推出了","7");
            getActivity().finish();
        }
        else {
            mRequestDialog.show();
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
                                getUserData();
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


    private void getUserData() {
        String activity_id= com.xys.libzxing.zxing.utils.PreferencesUtils.getString(getActivity(),"activityId");
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
                                            Toast.makeText(getActivity(), ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                            com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils.putString(getActivity(), "token_id", null);
                                            startActivity(new Intent(getActivity(), LoginActivity2.class));
                                            getActivity().finish();
                                        } else {
                                            if (userData.getRet().equals("200")) {
                                                actionCustomers = userData.getData().getActionCustomers();
                                                customers = userData.getData().getCustomers();
                                                mActivityBean=userData.getData().getActivity();
                                                insertInToDataBase(actionCustomers,customers,mActivityBean);
                                            }
                                            else if(userData.getRet().equals("201")){
                                                Toast.makeText(getActivity(), ""+userData.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    catch (Exception e){
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }
                                        ToastUtils.showLong(""+e);
                                        Log.d("推出了","4");
                                    }
                                }
                            }
                            catch (Exception e){
                                if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                    mRequestDialog.dismiss();
                                }
                                ToastUtils.showLong(""+e);
                                Log.d("推出了","3");
                            }

                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(getActivity(), "同步数据库失败，请检查网络连接情况", Toast.LENGTH_SHORT).show();
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
            Log.d("推出了","2");
        }

    }


    private void insertInToDataBase(List<ActionCustomersBean> actionCustomers, List<CustomersBean> customers, ActivityBean activityBean) {
        Log.d("窝上传了1","66666");
        try {
            Log.d("窝上传了4","66666");
            activityBeanDao.insert(activityBean);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            PreferencesUtils.putString(getActivity(),"dataSync",df.format(new Date())+"");
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
                for (int i = 0; i< customers.size(); i++){
                    CustomersBean customersBean = customers.get(i);
                    customersBeanDao.insert(customersBean);
                }
            }

            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }

            ToastUtils.showLong("同步数据成功");
            PreferencesUtils.putString(mContext, "company", null);
            PreferencesUtils.putString(mContext, "user_name", null);
            PreferencesUtils.putString(mContext, "password", null);
            PreferencesUtils.putString(mContext, "token_id",null);
            PreferencesUtils.putString(mContext,"roleType",null);
            startActivity(new Intent(getActivity(), LoginActivity2.class));
            getActivity().finish();
        }
        catch (Exception e){
            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                mRequestDialog.dismiss();
            }
            ToastUtils.showLong(e.toString());
            Log.d("推出了","1");
        }

    }



}

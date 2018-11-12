package com.shushang.aishangjia.fragment.MyFragment2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.GoodsActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.ResetPwdActivity;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 17/02/02
 *     desc  : demo about FragmentUtils
 * </pre>
 */
public class MyFragment2 extends BaseFragment implements View.OnClickListener,OnFabClickListener {
    private String token_id=null;
    private LinearLayout ll_reset;
    private LinearLayout ll_quit;
    private LinearLayout ll_check;
    private String username=null;
    private String shangjia=null;
    private String resourceName=null;
    private  SuspensionFab fabTop;
    private TextView mTextView1,mTextView2;
    private TextView mVersionCode;
    private String versionCode;
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_XIANSUO = 2002;
    private Handler mFabHandler=new Handler();
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        fabTop= rootView.findViewById(R.id.fab_top);
        ll_reset=rootView.findViewById(R.id.re_password);
        ll_quit=rootView.findViewById(R.id.quit);
        ll_check=rootView.findViewById(R.id.check_update);
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
        ll_quit.setOnClickListener(this);
        ll_check.setOnClickListener(this);
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

        FabAttributes email4 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.dingdan))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(6)
                .build();

        if(resourceName!=null&&resourceName.equals("1116")){
            fabTop.addFab(collection,xiansuo,email,email2,email3,email4);
        }
        else {
            fabTop.addFab(collection,email,email2,email3,email4);
        }
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
        fabTop.setFabClickListener(this);
        ll_reset.setOnClickListener(this);
}

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_my2, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        token_id= PreferencesUtils.getString(getActivity(),"token_id");
        username= PreferencesUtils.getString(mContext, "xingming");
        shangjia= PreferencesUtils.getString(mContext, "shangjia_name");
        resourceName=PreferencesUtils.getString(mContext,"ResourceName");
        versionCode = getVersionCode(mContext);
        Log.d("versionCode",versionCode+""+resourceName);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_password:
                startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                break;
            case R.id.check_update:
                Beta.checkUpgrade();
                break;
            case R.id.quit:
                ExtAlertDialog.showSureDlg(getActivity(), null, getString(R.string.logout_tip), getString(R.string.exit_login), new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if(result==1){
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
//                                                            Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
//                                                            PreferencesUtils.putString(mContext, "company", null);
//                                                            PreferencesUtils.putString(mContext, "user_name", null);
//                                                            PreferencesUtils.putString(mContext, "password", null);
//                                                            PreferencesUtils.putString(mContext, "token_id",null);
//                                                            PreferencesUtils.putString(mContext,"type",null);
//                                                            startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                                            getActivity().finish();
//                                                        }
//                                                        else {
//                                                            Toast.makeText(mContext, ""+response1.getMsg(), Toast.LENGTH_SHORT).show();
//                                                            PreferencesUtils.putString(mContext, "company", null);
//                                                            PreferencesUtils.putString(mContext, "user_name", null);
//                                                            PreferencesUtils.putString(mContext, "password", null);
//                                                            PreferencesUtils.putString(mContext, "token_id",null);
//                                                            PreferencesUtils.putString(mContext,"type",null);
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
//                                                Toast.makeText(getActivity(), "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }).error(new IError() {
//                                    @Override
//                                    public void onError(int code, String msg) {
//                                        Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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
//                                PreferencesUtils.putString(mContext,"type",null);
//                                startActivity(new Intent(getActivity(), LoginActivity2.class));
//                                getActivity().finish();
//                            }

                            PreferencesUtils.putString(mContext, "company", null);
                            PreferencesUtils.putString(mContext, "user_name", null);
                            PreferencesUtils.putString(mContext, "password", null);
                            PreferencesUtils.putString(mContext, "token_id",null);
                            PreferencesUtils.putString(mContext,"type",null);
                            startActivity(new Intent(getActivity(), LoginActivity2.class));
                            getActivity().finish();

                        }


                    }
                });
                break;
        }
    }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivity(new Intent(getActivity(), SignActivity.class));
        }else if (tag.equals(2)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(3)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), XiansuoActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(4)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            //表示所有权限都授权了
            Intent openCameraIntent = new Intent(getActivity(), ProActivityActivity2.class);
            openCameraIntent.putExtra("type", "3");
            openCameraIntent.putExtra("event","6");
            startActivityForResult(openCameraIntent, REQUEST_CODE_ACTIVITY );
        }
        else if (tag.equals(5)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), DailyOrderActivity.class),REQUEST_CODE_DAILY);
        }
        else if (tag.equals(6)){
            mFabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), GoodsActivity.class),REQUEST_CODE_DAILY);
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
}

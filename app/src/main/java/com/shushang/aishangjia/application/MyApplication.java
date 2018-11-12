package com.shushang.aishangjia.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.shushang.AppContext;
import com.shushang.aishangjia.greendao.DaoMaster;
import com.shushang.aishangjia.greendao.DaoSession;
import com.shushang.aishangjia.utils.MediaLoader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.commonsdk.UMConfigure;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;
import java.util.Set;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by YD on 2018/7/10.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication=null;
    private Set<Activity> allActivities;
    private static DaoSession daoSession;
    private int	mScreenWidth, mScreenHeight;// 屏幕尺寸
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        initOKHttp();
        setUpDataBase();
        //工具类初始化
        Utils.init(myApplication);
        AppContext.getInstance().init(myApplication);
//        CrashReport.initCrashReport(getApplicationContext(), "a0626d9293", false);
        Beta.autoCheckUpgrade=true;
        //公网
        Bugly.init(getApplicationContext(), "a0626d9293", true);

        //测试
//        Bugly.init(getApplicationContext(), "22095be631", true);

//        热更新
//        Bugly.init(getApplicationContext(), "2065c1323c", true);


        //字体图标初始化
        Iconify
                .with(new FontAwesomeModule())
                .with(new IoniconsModule());
        UMConfigure.init(this, "5b685121f43e483ac200041d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
    }

    private void setUpDataBase() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"dsx.db",null);
        SQLiteDatabase db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
    }

    public static DaoSession getDaoInstant(){
        return daoSession;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    private void initOKHttp() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker
        Beta.installTinker();
    }


}

package com.shushang.aishangjia.fragment.AppFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.shushang.aishangjia.Bean.GongGao;
import com.shushang.aishangjia.Bean.MenuItem;
import com.shushang.aishangjia.Bean.OutTime;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.GongGaoActivity;
import com.shushang.aishangjia.activity.GoodsActivity;
import com.shushang.aishangjia.activity.JiZhangActivity;
import com.shushang.aishangjia.activity.LianMengActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XianSuoChiActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.activity.ZhangDanActivity;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.fragment.AppFragment.adapter.AppAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.OnMultiClickListener;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class AppFragment extends BaseFragment implements OnFabClickListener {

    private SuspensionFab fabTop;
    private int page=1;
    private String resourceName=null;
    private Handler fabHandler=new Handler();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_GONGGAO= 2013;
    private static final int REQUEST_CODE_MYXIANSUO = 9090;
    private static final int REQUEST_CODE_NEW_PEOPLE =2662;
    private Toolbar mToolbar;
    private TextView mTextView1,mTextView2;
    private RecyclerView mRecyclerView;
    private AppAdapter adapter;
    private List<MenuItem> list;
    private TextView mTextView;
    private Vibrator vibrator;
    private String type=null;
    private String lianmengtype= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "type");
    private String ResourceName2= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),"ResourceName2");
    private LinearLayout mLinearLayout1,mLinearLayout2,mLinearLayout3,mLinearLayout4,mLinearLayout5;
    private View mView;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private Dialog mRequestDialog;
    private String leagueFlag=null;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        mRecyclerView=rootView.findViewById(R.id.rv_app);
        mToolbar=rootView.findViewById(R.id.toolbar);
        fabTop= rootView.findViewById(R.id.fab_top);
        type=PreferencesUtils.getString(getActivity(), "type");
        leagueFlag=PreferencesUtils.getString(mContext,"leagueFlag");
        resourceName= com.xys.libzxing.zxing.utils.PreferencesUtils.getString(mContext,"ResourceName");
        if(type.equals("7")){
            fabTop.setVisibility(View.GONE);
        }
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
        mLinearLayout1=rootView.findViewById(R.id.lianmeng_zhangdan);
        mLinearLayout2=rootView.findViewById(R.id.lianmeng_gonggao);
        mLinearLayout3=rootView.findViewById(R.id.lianmeng_yonghu);
        mLinearLayout4=rootView.findViewById(R.id.lianmeng_jizhang);
        mLinearLayout5=rootView.findViewById(R.id.lianmeng_xiansuo);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(getActivity(), getString(R.string.request));
        mRequestDialog.setCancelable(false);
        mTextView1=rootView.findViewById(R.id.unread);
        mTextView2=rootView.findViewById(R.id.outtime);
        mView=rootView.findViewById(R.id.jizhang_line);
        if(lianmengtype!=null&&lianmengtype.equals("7")){
            if(ResourceName2!=null){
                mLinearLayout4.setVisibility(View.VISIBLE);
                mView.setVisibility(View.VISIBLE);
            }
            else {
                mLinearLayout4.setVisibility(View.GONE);
                mView.setVisibility(View.GONE);
            }
        }


        mLinearLayout1.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if(leagueFlag.equals("1")){
                    startActivity(new Intent(getActivity(),ZhangDanActivity.class));
                }
                else {
                    ToastUtils.showLong("您没有参加联盟");
                }
            }
        });

        mLinearLayout2.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if(leagueFlag.equals("1")){
                    startActivityForResult(new Intent(getActivity(),GongGaoActivity.class),REQUEST_CODE_GONGGAO);
                }
                else {
                    ToastUtils.showLong("您没有参加联盟");
                }
            }
        });



        mLinearLayout3.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if(leagueFlag.equals("1")){
                    startActivity(new Intent(getActivity(),LianMengActivity.class));
                }
                else {
                    ToastUtils.showLong("您没有参加联盟");
                }
            }
        });

       mLinearLayout4.setOnClickListener(new OnMultiClickListener() {
           @Override
           public void onMultiClick(View v) {
               if(leagueFlag.equals("1")){
                   startActivity(new Intent(getActivity(),JiZhangActivity.class));
               }
               else {
                   ToastUtils.showLong("您没有参加联盟");
               }
           }
       });


       mLinearLayout5.setOnClickListener(new OnMultiClickListener() {
           @Override
           public void onMultiClick(View v) {
               startActivityForResult(new Intent(getActivity(),XianSuoChiActivity.class),REQUEST_CODE_MYXIANSUO);
           }
       });


//        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
//        adapter=new AppAdapter(list);
//        mRecyclerView.setAdapter(adapter);
//        //为RecycleView绑定触摸事件
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                //首先回调的方法 返回int表示是否监听该方向
//                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//拖拽
//                int swipeFlags =0;//侧滑删除
//                return makeMovementFlags(dragFlags,swipeFlags);
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                //滑动事件
//                Collections.swap(list,viewHolder.getAdapterPosition(),target.getAdapterPosition());
//                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                //侧滑事件
//                list.remove(viewHolder.getAdapterPosition());
//                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//            }
//
//            @Override
//            public boolean isLongPressDragEnabled() {
//                //是否可拖拽
//                return true;
//            }
//        });
//
//        helper.attachToRecyclerView(mRecyclerView);
//        adapter.setItemClickListener(new AppAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(String name, int position) {
//               if(name.equals("联盟财务")){
//                   startActivity(new Intent(getActivity(),ZhangDanActivity.class));
//               }
//               else if(name.equals("联盟公告")){
//                   startActivity(new Intent(getActivity(),GongGaoActivity.class));
//               }
//               else if(name.equals("联盟用户")){
//                 startActivity(new Intent(getActivity(),LianMengActivity.class));
//               }
//               else if(name.equals("联盟记账")){
//                   startActivity(new Intent(getActivity(),JiZhangActivity.class));
//               }
//            }
//        });
//
//        adapter.setOnItemLongClickListener(new AppAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick() {
//                vibrator.vibrate(300);
//            }
//        });
        mRequestDialog.show();
        getData(token_id);
        getData2(token_id);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_app, null);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
//        list = new ArrayList<>();
//        list.add(new MenuItem("联盟财务", R.mipmap.caiwu));
//        list.add(new MenuItem("联盟公告", R.mipmap.gonggao));
//        list.add(new MenuItem("联盟用户", R.mipmap.lianmeng));
//        if(lianmengtype!=null&&lianmengtype.equals("7")){
//            if(ResourceName2!=null){
//                list.add(new MenuItem("联盟记账", R.mipmap.caiwu));
//            }
//        }
    }

    public void getData(String token_id) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getNoticesByMerchantId&token_id="+token_id+"&page=1&rows=10&isRead=0";
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            if(response!=null){
                                Log.d("AppPeopleActivity",response);
                                try {
                                    GongGao test = JSONUtil.fromJson(response, GongGao.class);
                                    if(test.getRet().equals("101")){
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }
                                        Toast.makeText(getActivity(), ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(getActivity(),"token_id",null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    }
                                    else if(test.getRet().equals("200")){
                                        int size = test.getDataList().size();
                                        if(size>0){
                                            mTextView1.setVisibility(View.VISIBLE);
                                            mTextView1.setText(size+"");
                                        }
                                        else {
                                            mTextView1.setVisibility(View.GONE);
                                        }
                                    }
                                    else if(test.getRet().equals("201")){
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }
                                        Toast.makeText(getActivity(), ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                        mRequestDialog.dismiss();
                                    }
                                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(getActivity(), "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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



    public void getData2(String token_id) {
        String url= BaseUrl.BASE_URL+"phoneApi/clueController.do?method=getMyOutTimeCluesCounts&token_id="+token_id;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            if(response!=null){
                                Log.d("AppPeopleActivity",response);
                                try {
                                    OutTime test = JSONUtil.fromJson(response, OutTime.class);
                                    if(test.getRet().equals("101")){
                                        if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                            mRequestDialog.dismiss();
                                        }
                                        Toast.makeText(getActivity(), ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                        PreferencesUtils.putString(getActivity(),"token_id",null);
                                        startActivity(new Intent(getActivity(), LoginActivity2.class));
                                        getActivity().finish();
                                    }
                                    else if(test.getRet().equals("200")){
                                        int size = test.getData();
                                        if(size>0){
                                            mTextView2.setVisibility(View.VISIBLE);
                                            mTextView2.setText(size+"");
                                            EventBus.getDefault().post(new MessageEvent("有线索"));
                                        }
                                        else {
                                            mTextView2.setVisibility(View.GONE);
                                            EventBus.getDefault().post(new MessageEvent("无线索"));
                                        }
                                    }
                                    else if(test.getRet().equals("201")){
                                        Toast.makeText(getActivity(), ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                        mRequestDialog.dismiss();
                                    }
                                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                                mRequestDialog.dismiss();
                            }
                            Toast.makeText(getActivity(), "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    if(mRequestDialog!=null&&mRequestDialog.isShowing()){
                        mRequestDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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
        else if (tag.equals(6)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), GoodsActivity.class),REQUEST_CODE_DAILY);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_GONGGAO){
            getData(token_id);
        }
        else if(requestCode==REQUEST_CODE_MYXIANSUO){
            getData2(token_id);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        vibrator.cancel();
    }
}

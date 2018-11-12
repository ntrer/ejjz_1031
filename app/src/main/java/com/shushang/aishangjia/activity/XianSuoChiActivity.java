package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Xiansuo;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ListDropDownAdapter;
import com.shushang.aishangjia.activity.adapter.XiansuoAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.SignFragment.adapter.SignAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XianSuoChiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String headers[] = {"客户意向", "客户来源"};
    private List<View> popupViews = new ArrayList<>();
    private SignAdapter mScanAdapter;
    private ListDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private List<Xiansuo.DataListBean> mDataListBeen;
    private XiansuoAdapter mXiansuoAdapter;
    private  View mView;
    private RecyclerView mRecyclerView;
    private Dialog gloablDialog;
    private String token_id=null;
    private String Intent[] = {"客户意向","未知", "无意向", "有需求暂无意向", "有意向，需考虑竞品", "有意向，需考虑价格", "考虑成交"};
    private String Source[] = {"客户来源","微信引流","客户介绍", "广告", "销售拜访", "电话", "自然进店","网上宣传","朋友宣传","其他"};
    private String intent=null;
    private String source=null;
    private String allOrself=null;
    private int page=1;
    private RelativeLayout mLinearLayout;
    private LinearLayout mLinearLayout2;
    private ImageView mImageView;
    private static final int REQUEST_CODE_XIANSUOCUSTOMER = 6060;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xian_suo_chi);
        ButterKnife.bind(this);
        token_id= PreferencesUtils.getString(this,"token_id");
        initView();
        getData(token_id,intent,source,allOrself);
    }

    private void getData(String token_id,String intent,String source,String allOrself) {
        mSwipeRefreshLayout.setRefreshing(true);
        if(intent==null){
            intent="";
        }

        if(source==null){
            source="";
        }

        if(allOrself==null){
            allOrself="";
        }
        String url= BaseUrl.BASE_URL+"phoneApi/clueController.do?method=getMyClues&token_id="+token_id+"&page=1&rows=10&intent="+intent+"&source="+source+"&allOrself="+allOrself;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                Log.d("response666",response);
                                mSwipeRefreshLayout.setRefreshing(false);
                                if(response!=null){
                                    Xiansuo xiansuo = JSONUtil.fromJson(response, Xiansuo.class);
                                    if(xiansuo.getRet().equals("200")){
                                        mDataListBeen = xiansuo.getDataList();
                                        if(mDataListBeen.size()!=0){
                                            showData(mDataListBeen);
                                            mLinearLayout2.setVisibility(View.GONE);
                                        }
                                        else{
                                            showData(mDataListBeen);
                                            mLinearLayout2.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else if(xiansuo.getRet().equals("101")){
                                        Toast.makeText(XianSuoChiActivity.this, ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
                                        com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils.putString(XianSuoChiActivity.this,"token_id",null);
                                        startActivity(new Intent(XianSuoChiActivity.this, LoginActivity2.class));
                                        finish();
                                    }
                                    else if(xiansuo.getRet().equals("201")){
                                        Toast.makeText(XianSuoChiActivity.this, ""+xiansuo.getMsg(), Toast.LENGTH_SHORT).show();
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
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            })
                    .build()
                    .get();
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }

    }

    private void showData(final List<Xiansuo.DataListBean> dataListBeen) {
        try {
            mXiansuoAdapter=new XiansuoAdapter(R.layout.item_xiansuo,dataListBeen);
            final LinearLayoutManager linermanager=new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linermanager);
            //init dropdownview
            mRecyclerView.setAdapter(mXiansuoAdapter);
            mXiansuoAdapter.setOnLoadMoreListener(this, mRecyclerView);
            mXiansuoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(XianSuoChiActivity.this,XianSuoCustomerActivity.class);
                    intent.putExtra("clueId",dataListBeen.get(position).getClueId());
                    intent.putExtra("phone",dataListBeen.get(position).getTelephone());
                    startActivityForResult(intent,REQUEST_CODE_XIANSUOCUSTOMER);
                }
            });
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }

    }


    private void initView() {
        mView=View.inflate(this,R.layout.menu_recyclerview,null);
        mLinearLayout=mView.findViewById(R.id.ll_parent);
        mRecyclerView=mView.findViewById(R.id.rv_menu);
        mSwipeRefreshLayout=mView.findViewById(R.id.srl_home);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLinearLayout2=mView.findViewById(R.id.ll_no_data);
        mImageView=findViewById(R.id.shaixuan);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new ListDropDownAdapter(this, Arrays.asList(Intent));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(Source));
        ageView.setAdapter(ageAdapter);


        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mLinearLayout);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(Intent[position]);
                mDropDownMenu.closeMenu();
                if(position==0){
                    intent="";
                }
                else {
                    intent=position+"";
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(token_id,intent,source,allOrself);
                    }
                },500);
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(Source[position]);
                mDropDownMenu.closeMenu();
                if(position==0){
                    source="";
                }
                else {
                    source=position+"";
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(token_id,intent,source,allOrself);
                    }
                },500);
            }
        });


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ActionSheetDialog(XianSuoChiActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("全部", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        allOrself="";
                                        getData(token_id,intent,source,allOrself);
                                    }
                                })
                        .addSheetItem("未分配", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        allOrself="0";
                                        getData(token_id,intent,source,allOrself);
                                    }
                                })
                        .addSheetItem("我的", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        allOrself="1";
                                        getData(token_id,intent,source,allOrself);
                                    }
                                })
                        .show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_XIANSUOCUSTOMER){
            getData(token_id,intent,source,allOrself);
        }
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }

        if (gloablDialog != null && gloablDialog.isShowing()) {
            gloablDialog.dismiss();

        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public void onRefresh() {
        getData(token_id,intent,source,allOrself);
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore(token_id,intent,source,allOrself);
    }


    private void loadMore(String token_id, String intent, String source,String allOrself) {
        if(intent==null){
            intent="";
        }

        if(source==null){
            source="";
        }

        if(allOrself==null){
            allOrself="";
        }
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneApi/clueController.do?method=getMyClues&token_id="+ token_id +"&page="+page+"&rows=10&intent="+ intent +"&source="+ source+"&allOrself="+allOrself;
        Log.d("BaseUrl",url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null) {
                                Xiansuo yiXiangJin = JSONUtil.fromJson(response, Xiansuo.class);
                                if(yiXiangJin.getRet().equals("200")){
                                    if(page>yiXiangJin.getIntmaxPage()){
                                        page=1;
                                        mXiansuoAdapter.loadMoreComplete();
                                        mXiansuoAdapter.loadMoreEnd();
                                    }
                                    else if(yiXiangJin.getDataList().size()!=0){
                                        List<Xiansuo.DataListBean> dataList = yiXiangJin.getDataList();
                                        LoadMoreData(dataList);
                                        Log.d("load","loadMoreComplete");
                                        mXiansuoAdapter.loadMoreComplete();
                                    }
                                    else if(yiXiangJin.getDataList().size()==0){
                                        Log.d("load","loadMoreEnd");
                                        mXiansuoAdapter.loadMoreComplete();
                                        mXiansuoAdapter.loadMoreEnd();
                                    }
                                }
                                else {
                                    mXiansuoAdapter.loadMoreComplete();
                                    mXiansuoAdapter.loadMoreEnd();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(XianSuoChiActivity.this, "错误了", Toast.LENGTH_SHORT).show();
                            mXiansuoAdapter.loadMoreComplete();
                            mXiansuoAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(XianSuoChiActivity.this, "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mXiansuoAdapter.loadMoreComplete();
                            mXiansuoAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){
              ToastUtils.showLong(e.toString());
        }


    }


    private void LoadMoreData(List<Xiansuo.DataListBean> dataList) {
        if(dataList.size()!=0){
            mXiansuoAdapter.addData(dataList);
            mXiansuoAdapter.loadMoreComplete();
        }else {
            mXiansuoAdapter.loadMoreComplete();
        }
    }


}



package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.lib.MyItemDialogListener;
import com.hss01248.lib.StytledDialog;
import com.shushang.aishangjia.Bean.Sheng;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ListDropDownAdapter;
import com.shushang.aishangjia.activity.adapter.ShengAdapter;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.SignFragment.adapter.SignAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XianSuoChiActivity extends AppCompatActivity {

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @BindView(R.id.shaixuan)
    ImageView mShaixun;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String headers[] = {"客户意向", "客户来源", "结单日期"};
    private List<View> popupViews = new ArrayList<>();
    private SignAdapter mScanAdapter;
    private ListDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private List<Sheng.DataListBean> mDataListBeen;
    private ShengAdapter mShengAdapter;
    private  View mView;
    private RecyclerView mRecyclerView;
    private Dialog gloablDialog;
    private String token_id=null;
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xian_suo_chi);
        ButterKnife.bind(this);
        token_id= PreferencesUtils.getString(this,"token_id");
        initView();
        getData(token_id);
    }

    private void getData(String token_id) {
        String url= BaseUrl.BASE_URL+"addressController.do?method=getSheng&token_id="+token_id;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Sheng sheng = JSONUtil.fromJson(response, Sheng.class);
                            if(sheng.getRet().equals("200")){
                                mDataListBeen = sheng.getDataList();
                                showData(mDataListBeen);
                            }
                            else {
                                ToastUtils.showLong(""+sheng.getMsg());
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

            }
        })
                .build()
                .get();
    }

    private void showData(List<Sheng.DataListBean> dataListBeen) {
        mShengAdapter=new ShengAdapter(R.layout.item_sheng,dataListBeen);
        mView=View.inflate(this,R.layout.menu_recyclerview,null);
        mRecyclerView=mView.findViewById(R.id.rv_menu);
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linermanager);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mRecyclerView);
        mRecyclerView.setAdapter(mShengAdapter);
        mShengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(XianSuoChiActivity.this,XianSuoCustomerActivity.class));
            }
        });
    }


    private void initView() {
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new ListDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                Toast.makeText(XianSuoChiActivity.this, "" + citys[position], Toast.LENGTH_SHORT).show();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
                Toast.makeText(XianSuoChiActivity.this, "" + ages[position], Toast.LENGTH_SHORT).show();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
                Toast.makeText(XianSuoChiActivity.this, "" + sexs[position], Toast.LENGTH_SHORT).show();
            }
        });

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

    @OnClick(R.id.shaixuan)
    void shaixun() {
        final List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
        strings.add("7");
        strings.add("8");

        StytledDialog.showBottomItemDialog(this, strings, "cancle", true, true, new MyItemDialogListener() {
            @Override
            public void onItemClick(String text, int position) {
                ToastUtils.showLong(text);
            }

            @Override
            public void onBottomBtnClick() {
                ToastUtils.showLong("取消");
            }
        });
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


}



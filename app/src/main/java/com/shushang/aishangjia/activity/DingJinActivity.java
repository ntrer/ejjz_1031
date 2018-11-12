package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Diko;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.DingjinAdapter;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingJinActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_dingjin)
    RecyclerView mRvDingjin;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.dingjinsure)
    TextView mDingjinsure;
    private String token_id = null;
    private String phone = null;
    private List<Diko.DataListBean> mDataListBeen;
    private DingjinAdapter mDingjinAdapter;
    private static final int REQUEST_CODE_CHILDCITY = 2011;
    private List<Diko.DataListBean> selectDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_jin);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        token_id = PreferencesUtils.getString(this, "token_id");
        PreferencesUtils.putString(DingJinActivity.this, "dingjindikou", null);
        PreferencesUtils.putString(DingJinActivity.this, "orderIntentIds", null);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(token_id, phone);
        initRecyclerView();
    }

    private void initData(String tokenId, String phone) {
        mLoading.setVisibility(View.VISIBLE);
        String url = BaseUrl.BASE_URL + "phoneApi/goodsOrderController.do?method=getIntentByCustomerPhone&token_id=" + tokenId + "&phone=" + phone;
        Log.d("BaseUrl", url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if (response != null) {
                                Diko sheng = JSONUtil.fromJson(response, Diko.class);
                                if (sheng.getRet().equals("200")) {
                                    mLoading.setVisibility(View.GONE);
                                    mDataListBeen = sheng.getDataList();
                                    showData(mDataListBeen);
                                } else {
                                    mLoading.setVisibility(View.GONE);
                                    ToastUtils.showLong("" + sheng.getMsg());
                                }
                            } else {
                                mLoading.setVisibility(View.GONE);
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mLoading.setVisibility(View.GONE);
                            Toast.makeText(DingJinActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    mLoading.setVisibility(View.GONE);
                    Toast.makeText(DingJinActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        } catch (Exception e) {

        }
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRvDingjin.setLayoutManager(linermanager);
    }

    private void showData(final List<Diko.DataListBean> dataListBeen) {
        mDingjinAdapter = new DingjinAdapter(R.layout.item_dingjin, dataListBeen);
        mRvDingjin.setAdapter(mDingjinAdapter);
        mRvDingjin.scrollToPosition(0);
        mDingjinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mDingjinAdapter.isSelected.get(position)) {
                    mDingjinAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mDingjinAdapter.notifyItemChanged(position);
                    selectDatas.add(dataListBeen.get(position));

                } else {
                    mDingjinAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mDingjinAdapter.notifyItemChanged(position);
                    selectDatas.remove(dataListBeen.get(position));
                }

                //PreferencesUtils.putString(DingJinActivity.this,"dingjindikou",dataListBeen.get(position).getTotalPrice()+"");
//                PreferencesUtils.putString(DingJinActivity.this,"orderIntentIds",dataListBeen.get(position).getOrderId());
//                finish();

            }
        });

        mDingjinAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.checkbox){
                    if (!mDingjinAdapter.isSelected.get(position)) {
                        mDingjinAdapter.isSelected.put(position, true); // 修改map的值保存状态
                        mDingjinAdapter.notifyItemChanged(position);
                        selectDatas.add(dataListBeen.get(position));

                    } else {
                        mDingjinAdapter.isSelected.put(position, false); // 修改map的值保存状态
                        mDingjinAdapter.notifyItemChanged(position);
                        selectDatas.remove(dataListBeen.get(position));
                    }
                }
            }
        });
    }


    @OnClick(R.id.dingjinsure)void sure(){
        Intent intent = getIntent();
        intent.putExtra("dingjinData", (Serializable) selectDatas);
        setResult(1, intent);
        finish();
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

package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.GoodsAdapter;
import com.shushang.aishangjia.activity.adapter.MultiAdapter;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.next)
    TextView mNext;
    @BindView(R.id.ll_no_data)
    LinearLayout mLlNoData;
    private String token_id = null;
    private List<Good.DataListBean> mDataListBeen;
    private List<Good.DataListBean> selectDatas = new ArrayList<>();
    //    private GoodsAdapter mGoodsAdapter;
    private MultiAdapter mGoodsAdapter;
    private GoodsAdapter mGoodsAdapter2;
    private Dialog modifyPasswordOkDialog;
    private List<Integer> position2 = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        token_id = PreferencesUtils.getString(this, "token_id");
        modifyPasswordOkDialog = ExtAlertDialog.createModifyPasswordOkDialog3(GoodsActivity.this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(token_id);
        initRecyclerView();
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectDatas.size() == 0) {
                    ToastUtils.showLong("请先选择商品");
                } else {
                    try {
                        if (GoodsActivity.this != null && !GoodsActivity.this.isDestroyed() && !GoodsActivity.this.isFinishing()) {
                            modifyPasswordOkDialog.show();
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (modifyPasswordOkDialog != null && modifyPasswordOkDialog.isShowing()) {
                                    modifyPasswordOkDialog.dismiss();
                                    Intent intent = new Intent(GoodsActivity.this, DingDanActivity.class);
                                    intent.putExtra("gooddata", (Serializable) selectDatas);
                                    startActivity(intent);
                                    Log.d("selectDatasss", selectDatas.size() + "");

                                    for (int i = 0; i < position2.size(); i++) {
                                        if (mGoodsAdapter2.isSelected.get(position2.get(i))) {
                                            mGoodsAdapter2.isSelected.put(position2.get(i), false); // 修改map的值保存状态
                                            mGoodsAdapter2.notifyItemChanged(position2.get(i));
//                                            selectDatas.remove(mDataListBeen.get(i));
                                        }
                                    }
                                    selectDatas.clear();
                                }
                            }
                        }, 1000);
                    } catch (Exception e) {
                        ToastUtils.showLong(e.toString());
                    }
                }

            }
        });
        EventBus.getDefault().register(this);
    }


    private void initData(String token_id) {
        mLoading.setVisibility(View.VISIBLE);
        String url = BaseUrl.BASE_URL + "phoneApi/goodsOrderController.do?method=getGoods&token_id=" + token_id;
        Log.d("BaseUrl", url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            mLoading.setVisibility(View.GONE);
                            if (response != null) {
                                Good sheng = JSONUtil.fromJson(response, Good.class);
                                if (sheng.getRet().equals("200")) {
                                    mDataListBeen = sheng.getDataList();
                                    if(mDataListBeen.size()!=0){
                                        showData(mDataListBeen);
                                    }
                                    else {
                                        mLlNoData.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    mLoading.setVisibility(View.GONE);
                                    ToastUtils.showLong("" + sheng.getMsg());
                                    mLlNoData.setVisibility(View.VISIBLE);
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
                            Toast.makeText(GoodsActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    mLoading.setVisibility(View.GONE);
                    Toast.makeText(GoodsActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        } catch (Exception e) {

        }
    }

    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRvGoods.setLayoutManager(linermanager);
    }

    private void showData(final List<Good.DataListBean> dataListBeen) {
//        mGoodsAdapter = new MultiAdapter(dataListBeen);
        mGoodsAdapter2 = new GoodsAdapter(R.layout.item_goods, dataListBeen);
//        mRvGoods.setAdapter(mGoodsAdapter);
        mRvGoods.setAdapter(mGoodsAdapter2);
        mGoodsAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                position2.add(position);
                if (!mGoodsAdapter2.isSelected.get(position)) {
                    mGoodsAdapter2.isSelected.put(position, true); // 修改map的值保存状态
                    mGoodsAdapter2.notifyItemChanged(position);
                    selectDatas.add(dataListBeen.get(position));

                } else {
                    mGoodsAdapter2.isSelected.put(position, false); // 修改map的值保存状态
                    mGoodsAdapter2.notifyItemChanged(position);
                    selectDatas.remove(dataListBeen.get(position));
                }
            }
        });

        mGoodsAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.checkbox) {
                    if (!mGoodsAdapter2.isSelected.get(position)) {
                        mGoodsAdapter2.isSelected.put(position, true); // 修改map的值保存状态
                        mGoodsAdapter2.notifyItemChanged(position);
                        selectDatas.add(dataListBeen.get(position));

                    } else {
                        mGoodsAdapter2.isSelected.put(position, false); // 修改map的值保存状态
                        mGoodsAdapter2.notifyItemChanged(position);
                        selectDatas.remove(dataListBeen.get(position));
                    }
                }
            }
        });

//        mGoodsAdapter2.setOnItemClickLitener(new OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (!mGoodsAdapter.isSelected.get(position)) {
//                    mGoodsAdapter.isSelected.put(position, true); // 修改map的值保存状态
//                    mGoodsAdapter.notifyItemChanged(position);
//                    selectDatas.add(dataListBeen.get(position));
//
//                } else {
//                    mGoodsAdapter.isSelected.put(position, false); // 修改map的值保存状态
//                    mGoodsAdapter.notifyItemChanged(position);
//                    selectDatas.remove(dataListBeen.get(position));
//                }
//
//
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });

//        mGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Good.DataListBean dataListBean = dataListBeen.get(position);
//                String Gooddata = JSONUtil.toJSON(dataListBean);
//                PreferencesUtils.putString(getApplicationContext(),"gooddata",Gooddata);
//
//                if(GoodsActivity.this!=null&&!GoodsActivity.this.isDestroyed()&&!GoodsActivity.this.isFinishing()){
//                    modifyPasswordOkDialog.show();
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(modifyPasswordOkDialog!=null&&modifyPasswordOkDialog.isShowing()){
//                            modifyPasswordOkDialog.dismiss();
//                            finish();
//                        }
//                    }
//                },1000);
//            }
//        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("商品")) {
            EventBus.getDefault().post(new MessageEvent("结束"));
            finish();
        }
    }


    /**
     * 取消已选
     *
     * @param view
     */
    public void cancel(View view) {
        for (int i = 0; i < mDataListBeen.size(); i++) {
            if (mGoodsAdapter.isSelected.get(i)) {
                mGoodsAdapter.isSelected.put(i, false);
                selectDatas.remove(mDataListBeen.get(i));
            }
        }
        mGoodsAdapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

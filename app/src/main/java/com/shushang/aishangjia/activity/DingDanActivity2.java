package com.shushang.aishangjia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.Bean.goods;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.DingDanAdapter2;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.xys.libzxing.zxing.utils.JSONUtil;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingDanActivity2 extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_dingdan)
    RecyclerView mRvDingdan;
    @BindView(R.id.ll_no_data)
    LinearLayout mLlNoData;
    @BindView(R.id.tv_shopcart_total)
    TextView mTvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button mBtnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout mLlCheckAll;
    @BindView(R.id.goto_pay)
    TextView mGotoPay;
    @BindView(R.id.srl_dingdan)
    SwipeRefreshLayout mSrlDingdan;
    @BindView(R.id.rl_products)
    RelativeLayout mRlProducts;
    private double totalPrice;
    private TextView mTextView, mTextView2, mTextView3;
    private static final int REQUEST_CODE_GOODS = 6011;
    private List<Good.DataListBean> mDataListBeans = new ArrayList<>();
    private List<Good.DataListBean> mGoodsLists = new ArrayList<>();
    private DingDanAdapter2 mDingDanAdapter;
    double price = 0;
    private int count = 1;
    private Button mButton;
    private boolean isYouHui = false;
    private goods mGoods = new goods();
    private List<goods> mGoodsList = new ArrayList<>();
    private String token_id = null;
    private List<Good.DataListBean> selectDatas = new ArrayList<>();
    private ImageView mImageView1,mImageView2;
    @Override
    public int setLayout() {
        return R.layout.activity_ding_dan2;
    }

    @Override
    public void init() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        token_id = PreferencesUtils.getString(this, "token_id");
        initRecyclerView();
        initData(token_id);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initData(String token_id) {
        mSrlDingdan.setRefreshing(true);
        String url = BaseUrl.BASE_URL + "phoneApi/goodsOrderController.do?method=getGoods&token_id=" + token_id;
        Log.d("BaseUrl", url);
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            mSrlDingdan.setRefreshing(false);
                            if (response != null) {
                                Good sheng = JSONUtil.fromJson(response, Good.class);
                                if (sheng.getRet().equals("200")) {
                                    mDataListBeans = sheng.getDataList();
                                    showData(mDataListBeans);
                                } else {
                                    mSrlDingdan.setRefreshing(false);
                                    ToastUtils.showLong("" + sheng.getMsg());
                                }
                            } else {
                                mSrlDingdan.setRefreshing(false);
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            mSrlDingdan.setRefreshing(false);
                            Toast.makeText(DingDanActivity2.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                        }
                    }).error(new IError() {
                @Override
                public void onError(int code, String msg) {
                    mSrlDingdan.setRefreshing(false);
                    Toast.makeText(DingDanActivity2.this, "" + msg, Toast.LENGTH_SHORT).show();
                }
            })
                    .build()
                    .get();
        } catch (Exception e) {
            mSrlDingdan.setRefreshing(false);
        }
    }


    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRvDingdan.setLayoutManager(linermanager);
    }


    @OnClick(R.id.goto_pay)
    void pay() {
        Intent intent=new Intent(DingDanActivity2.this,GoodsActivity2.class);
        intent.putExtra("gooddata",(Serializable)selectDatas);
        startActivity(intent);
    }


    private void showData(final List<Good.DataListBean> dataListBeans) {
        mDingDanAdapter = new DingDanAdapter2(R.layout.item_products2, dataListBeans, mLlCheckAll, price, this);
        mRvDingdan.setAdapter(mDingDanAdapter);
        mRvDingdan.scrollToPosition(0);
//        mDingDanAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        mDingDanAdapter.isFirstOnly(false);

//        mDingDanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mTextView = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.total_money_text);
//                mTextView2 = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.youhui_danjia_price);
//                mTextView3 = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.tv_count);
//                mImageView1= (ImageView) adapter.getViewByPosition(mRvDingdan,position,R.id.btn_reduce);
//                mImageView2= (ImageView) adapter.getViewByPosition(mRvDingdan,position,R.id.btn_add);
//                mButton = (Button) adapter.getViewByPosition(mRvDingdan, position, R.id.youhui_btn);
//
//            }
//        });

        mDingDanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.checkbox){
                    switchSelected(position);
                }
            }
        });


        mDingDanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, final View view, final int position) {
                final int itemViewId = view.getId();
                mTextView = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.total_money_text);
                mTextView2 = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.youhui_danjia_price);
                mTextView3 = (TextView) adapter.getViewByPosition(mRvDingdan, position, R.id.tv_count);
                mButton = (Button) adapter.getViewByPosition(mRvDingdan, position, R.id.youhui_btn);
                int value = Integer.parseInt(mTextView3.getText().toString());
                    switch (itemViewId) {
                        case R.id.btn_add:
                            value++;
                            mTextView3.setText(value + "");
                            mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) * value) + "");
//                        mTextView.setText(dataListBeans.get(position).getGoodsRetailPrice()*count+"");
                            break;

                        case R.id.btn_reduce:
                            if (value > 1) {
                                value--;
                                mTextView3.setText(value + "");
                                mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) * (value + 1) - Double.parseDouble(mTextView2.getText().toString())) + "");
                            }
                            break;
                        case R.id.youhui_btn:
                            new ActionSheetDialog(DingDanActivity2.this)
                                    .builder()
                                    .setTitle("选择优惠额度")
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(true)
                                    .addSheetItem("1.0", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("1.0");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");
                                                }
                                            })
                                    .addSheetItem("0.9", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.9");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");
                                                }
                                            })
                                    .addSheetItem("0.8", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.8");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.7", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.7");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.6", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.6");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.5", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.5");

                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.4", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.4");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.3", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.3");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.2", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.2");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .addSheetItem("0.1", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui = true;
                                                    mButton.setText("0.1");
                                                    mTextView2.setText(Math.round(dataListBeans.get(position).getGoodsRetailPrice()
                                                            * (Double.parseDouble(mButton.getText().toString()))) + "");
                                                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString()) *
                                                            Integer.parseInt(mTextView3.getText().toString())) + "");

                                                }
                                            })
                                    .show();
                            break;
                    }


            }
        });
    }

    private void switchSelected(int position) {
        if (!mDingDanAdapter.isSelected.get(position)) {
            mDingDanAdapter.isSelected.put(position, true); // 修改map的值保存状态
            mDingDanAdapter.notifyItemChanged(position);
            selectDatas.add(mDataListBeans.get(position));
//                    mButton.setEnabled(true);
//                    mImageView1.setEnabled(true);
//                    mImageView2.setEnabled(true);
//                    mTextView3.setText("1");
//                    mTextView2.setText(mDataListBeans.get(position).getGoodsRetailPrice()+"");
//                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString())) + "");
//                    mButton.setText("优惠1.0");

        }
        else {
            mDingDanAdapter.isSelected.put(position, false); // 修改map的值保存状态
            mDingDanAdapter.notifyItemChanged(position);
            selectDatas.remove(mDataListBeans.get(position));
//            mButton.setEnabled(false);
//            mImageView1.setEnabled(false);
//            mImageView2.setEnabled(false);
//                    mTextView3.setText("1");
//                    mTextView2.setText(mDataListBeans.get(position).getGoodsRetailPrice()+"");
//                    mTextView.setText(Math.round(Double.parseDouble(mTextView2.getText().toString())) + "");
//                    mButton.setText("优惠1.0");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ExtAlertDialog.showSureDlg(DingDanActivity2.this, "退出订单界面？", "退出后将不保留此次的订单", "确定", new ExtAlertDialog.IExtDlgClick() {
                    @Override
                    public void Oclick(int result) {
                        if (result == 1) {
                            finish();
                        }
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ExtAlertDialog.showSureDlg(DingDanActivity2.this, "退出订单界面？", "退出后将不保留此次的订单", "确定", new ExtAlertDialog.IExtDlgClick() {
            @Override
            public void Oclick(int result) {
                if (result == 1) {
                    finish();
                }
            }
        });
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}

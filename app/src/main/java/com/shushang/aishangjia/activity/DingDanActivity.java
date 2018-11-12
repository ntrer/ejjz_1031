package com.shushang.aishangjia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.Bean.goods;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.DingDanAdapter;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.MessageEvent;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingDanActivity extends BaseActivity {


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
    private double totalPrice;
    private TextView mTextView,mTextView2,mTextView3;
    private static final int REQUEST_CODE_GOODS = 6011;
    private List<Good.DataListBean> mDataListBeans = new ArrayList<>();
    private List<Good.DataListBean> mDataListBeans2 = new ArrayList<>();
    private int position2=0;
    private List<Good.DataListBean> mGoodsLists = new ArrayList<>();
    private DingDanAdapter mDingDanAdapter;
    double price = 0;
    private int count=1;
    private Button mButton;
    private boolean isYouHui=false;
    private goods mGoods=new goods();
    private List<goods> mGoodsList=new ArrayList<>();
    private boolean isDelete=false;
    private int value,value2;
    @Override
    public int setLayout() {
        return R.layout.activity_ding_dan;
    }

    @Override
    public void init() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mGoodsLists = (List<Good.DataListBean>) getIntent().getSerializableExtra("gooddata");
        mDataListBeans.addAll(mGoodsLists);
        mDataListBeans2.addAll(mGoodsLists);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView(mDataListBeans);
        EventBus.getDefault().register(this);
    }

    private void initRecyclerView(List<Good.DataListBean> goodsLists) {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRvDingdan.setLayoutManager(linermanager);
        showData(goodsLists);
    }


    @OnClick(R.id.goto_pay)
    void pay() {
        totalPrice=0;
        try {
            for (int i = 0; i < mRvDingdan.getChildCount(); i++) {
                mGoods=new goods();
                LinearLayout rl = (LinearLayout) mRvDingdan.getChildAt(i);
                TextView textView = rl.findViewById(R.id.total_money_text);
                Button button=rl.findViewById(R.id.youhui_btn);
                TextView textView1=rl.findViewById(R.id.youhui_danjia_price);
                TextView textView2=rl.findViewById(R.id.tv_count);
                totalPrice = totalPrice + Double.parseDouble(textView.getText().toString());
                mGoods.setGoodsTotalPrice(textView.getText().toString());
                if(button.getText().equals("优惠1.0")){
                    mGoods.setDiscount("1.0");
                }
                else {
                    mGoods.setDiscount(button.getText().toString());
                }
                mGoods.setDiscountPrice(textView1.getText().toString());
                mGoods.setGoodsCount(textView2.getText().toString());
                mGoods.setGoodsId(mDataListBeans.get(i).getGoodsId());
                mGoodsList.add(mGoods);
            }
            Intent intent=new Intent(DingDanActivity.this, PayActivity.class);
            intent.putExtra("totalprice",formatDouble4(totalPrice));
            intent.putExtra("goodsData", (Serializable) mGoodsList);
            startActivity(intent);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }

    }


    private void showData(final List<Good.DataListBean> dataListBeans) {
        for (int i = 0; i < dataListBeans.size(); i++) {
            price = price + dataListBeans.get(i).getGoodsRetailPrice();
//              price = price + 100;
        }
        mTvShopcartTotal.setText("￥" + price + "元");
        mDingDanAdapter = new DingDanAdapter(R.layout.item_products, dataListBeans, mLlCheckAll, price, this);
        mRvDingdan.setAdapter(mDingDanAdapter);
        mRvDingdan.scrollToPosition(0);
//        mDingDanAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        mDingDanAdapter.isFirstOnly(false);
        mDingDanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, final View view, final int position) {
                try {
                    final int itemViewId = view.getId();
                    mTextView= (TextView) adapter.getViewByPosition(mRvDingdan,position,R.id.total_money_text);
                    mTextView2= (TextView) adapter.getViewByPosition(mRvDingdan,position,R.id.youhui_danjia_price);
                    mTextView3= (TextView) adapter.getViewByPosition(mRvDingdan,position,R.id.tv_count);
                    mButton= (Button) adapter.getViewByPosition(mRvDingdan,position,R.id.youhui_btn);
                    value = Integer.parseInt(mTextView3.getText().toString());
                    switch (itemViewId) {
                    case R.id.delete:
                        ExtAlertDialog.showSureDlg(DingDanActivity.this, "注意！", "要删除此商品吗?", "确定", new ExtAlertDialog.IExtDlgClick() {
                            @Override
                            public void Oclick(int result) {
                                if (result == 1) {
                                    mDingDanAdapter.remove(position);
                                    if (mDataListBeans.size() == 0) {
                                        finish();
                                    }
                                }
                            }
                        });
                        break;
                        case R.id.btn_add:
                            value++;
                            mTextView3.setText(value+"");
                            mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*value)+"");
                            dataListBeans.get(position).setValue(String.valueOf(value));
                            dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView.getText().toString()))));
                            // mTextView.setText(dataListBeans.get(position).getGoodsRetailPrice()*count+"");
                            break;

                        case R.id.btn_reduce:
                            if(value>1){
                                value--;
                                mTextView3.setText(value+"");
                                mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*(value+1)-Double.parseDouble(mTextView2.getText().toString()))+"");
                                dataListBeans.get(position).setValue(String.valueOf(value));
                                dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView.getText().toString()))));
                            }
                            break;
                        case R.id.youhui_btn:
                            new ActionSheetDialog(DingDanActivity.this)
                                    .builder()
                                    .setTitle("选择优惠额度")
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(true)
                                    .addSheetItem("1.0", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("1.0");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                     dataListBeans.get(position).setYouhui("1.0");
                                                     dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                             *(Double.parseDouble(mButton.getText().toString())))+"");
                                                     dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                             Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.9", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.9");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.9");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.8", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.8");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.8");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.7", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.7");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.7");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.6", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.6");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.6");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.5", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.5");

                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.5");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.4", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.4");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.4");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.3", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.3");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.3");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));

                                                }
                                            })
                                    .addSheetItem("0.2", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.2");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.2");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));
                                                }
                                            })
                                    .addSheetItem("0.1", ActionSheetDialog.SheetItemColor.Blue,
                                            new ActionSheetDialog.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    isYouHui=true;
                                                    mButton.setText("0.1");
                                                    mTextView2.setText(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    mTextView.setText(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))+"");
                                                    dataListBeans.get(position).setYouhui("0.1");
                                                    dataListBeans.get(position).setYouhuidanjia(formatDouble4(mDataListBeans.get(position).getGoodsRetailPrice()
                                                            *(Double.parseDouble(mButton.getText().toString())))+"");
                                                    dataListBeans.get(position).setGoodsTotalPrice(Double.parseDouble(formatDouble4(Double.parseDouble(mTextView2.getText().toString())*
                                                            Integer.parseInt(mTextView3.getText().toString()))));
                                                }
                                            })
                                    .show();
                            break;
                    }
                }
                catch (Exception e){
                    ToastUtils.showLong(e.toString());
                }

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ExtAlertDialog.showSureDlg(DingDanActivity.this, "退出订单界面？", "退出后将不保留此次的订单", "确定", new ExtAlertDialog.IExtDlgClick() {
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("提交订单")){
            EventBus.getDefault().post(new MessageEvent("商品"));
            finish();
        }
    }



    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("#.00");


        return df.format(d);
    }



//    对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ExtAlertDialog.showSureDlg(DingDanActivity.this, "退出订单界面？", "退出后将不保留此次的订单", "确定", new ExtAlertDialog.IExtDlgClick() {
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
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}

package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.Good;
import com.shushang.aishangjia.Bean.goods;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.GoodsAdapter;
import com.shushang.aishangjia.activity.adapter.MultiAdapter;
import com.shushang.aishangjia.ui.ExtAlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsActivity2 extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.next)
    TextView mNext;
    private String token_id = null;
    private List<Good.DataListBean> mDataListBeen;
    private List<Good.DataListBean> selectDatas = new ArrayList<>();
    //    private GoodsAdapter mGoodsAdapter;
    private MultiAdapter mGoodsAdapter;
    private GoodsAdapter mGoodsAdapter2;
    private Dialog modifyPasswordOkDialog;
    private goods mGoods=new goods();
    private List<goods> mGoodsList=new ArrayList<>();
    private double totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        mDataListBeen = (List<Good.DataListBean>) getIntent().getSerializableExtra("gooddata");
        modifyPasswordOkDialog = ExtAlertDialog.createModifyPasswordOkDialog3(GoodsActivity2.this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
        showData(mDataListBeen);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectDatas.size()==0){
                    ToastUtils.showLong("请先选择商品");
                }
                else {
                    if(GoodsActivity2.this!=null&&!GoodsActivity2.this.isDestroyed()&&!GoodsActivity2.this.isFinishing()){
                        modifyPasswordOkDialog.show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(modifyPasswordOkDialog!=null&&modifyPasswordOkDialog.isShowing()){
                                modifyPasswordOkDialog.dismiss();
                                for (int i = 0; i < mDataListBeen.size(); i++) {
                                    mGoods=new goods();
                                    LinearLayout rl = (LinearLayout) mRvGoods.getChildAt(i);
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
                                    mGoods.setGoodsId(mDataListBeen.get(i).getGoodsId());
                                    mGoodsList.add(mGoods);
                                }
                                Intent intent=new Intent(GoodsActivity2.this, PayActivity.class);
                                intent.putExtra("totalprice",totalPrice+"");
                                intent.putExtra("goodsData", (Serializable) mGoodsList);
                                startActivity(intent);
                            }
                        }
                    },1000);
                }

            }
        });
    }


    private void initRecyclerView() {
        final LinearLayoutManager linermanager = new LinearLayoutManager(this);
        mRvGoods.setLayoutManager(linermanager);
    }

    private void showData(final List<Good.DataListBean> dataListBeen) {
        mGoodsAdapter2=new GoodsAdapter(R.layout.item_goods,dataListBeen);
        mRvGoods.setAdapter(mGoodsAdapter2);
        mGoodsAdapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
             mGoodsAdapter2.remove(position);
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

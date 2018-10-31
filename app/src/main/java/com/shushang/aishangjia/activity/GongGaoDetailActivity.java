package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shushang.aishangjia.Bean.GongGao;
import com.shushang.aishangjia.Bean.GongGaoDetail;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GongGaoDetailActivity extends BaseActivity {


    private EditText mEditText;
    private String info;
    private Toolbar mToolbar;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private String leagueNoticeId=null;
    @Override
    public int setLayout() {
        return R.layout.activity_gong_gao_detail;
    }

    @Override
    public void init() {
        mEditText= (EditText) findViewById(R.id.add_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView1=findViewById(R.id.cj_time);
        mTextView2=findViewById(R.id.cj_people);
        mTextView3=findViewById(R.id.start_time);
        mTextView4=findViewById(R.id.end_time);
        mTextView5=findViewById(R.id.gonggao_title);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        GongGao.DataListBean dataListBean = (GongGao.DataListBean) intent.getSerializableExtra("data");
        leagueNoticeId=dataListBean.getLeagueNoticeId();
        initData(token_id,leagueNoticeId);
    }

    private void initData(String token_id, String leagueNoticeId) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=getNoticeByLeagueNoticeId&token_id="+token_id+"&leagueNoticeId="+leagueNoticeId;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Log.d("AppPeopleActivity",response);
                            try {
                                GongGaoDetail test = JSONUtil.fromJson(response, GongGaoDetail.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(GongGaoDetailActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(GongGaoDetailActivity.this,"token_id",null);
                                    startActivity(new Intent(GongGaoDetailActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                                   if(test.getData().getLeagueTitle()!=null){
                                       mTextView5.setText(test.getData().getLeagueTitle());
                                   }

                                    if(String.valueOf(test.getData().getCjsj())!=null){
                                        //获取当前时间
                                        Date date = new Date(test.getData().getCjsj());
                                        mTextView1.setText("创建时间:"+simpleDateFormat.format(date));
                                    }

                                    if(String.valueOf(test.getData().getStartTime())!=null){
                                        //获取当前时间
                                        Date date = new Date(test.getData().getStartTime());
                                        mTextView3.setText("开始时间:"+simpleDateFormat.format(date));
                                    }

                                    if(String.valueOf(test.getData().getEndTime())!=null){
                                        //获取当前时间
                                        Date date = new Date(test.getData().getEndTime());
                                        mTextView4.setText("结束时间:"+simpleDateFormat.format(date));
                                    }

                                    if(test.getData().getChuangjianren()!=null){
                                        mTextView2.setText("创建人:"+test.getData().getChuangjianren());
                                    }

                                    if(test.getData().getLeagueNotice()!=null){
                                        mEditText.setText(test.getData().getLeagueNotice());
                                    }

                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(GongGaoDetailActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(GongGaoDetailActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(GongGaoDetailActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

                Toast.makeText(GongGaoDetailActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

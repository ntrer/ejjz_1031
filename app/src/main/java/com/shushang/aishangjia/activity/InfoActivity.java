package com.shushang.aishangjia.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

public class InfoActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private String info;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mEditText= (EditText) findViewById(R.id.add_content);
        mButton= (Button) findViewById(R.id.btn_submit);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info=mEditText.getText().toString();
                PreferencesUtils.putString(getApplicationContext(),"info",info);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mEditText.getText().toString().equals("")){
                    PreferencesUtils.putString(getApplicationContext(),"info",null);
                }
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
        if(mEditText.getText().toString().equals("")){
            PreferencesUtils.putString(getApplicationContext(),"info",null);
        }
    }
}

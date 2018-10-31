package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.GongGao;
import com.shushang.aishangjia.Bean.Leagues;
import com.shushang.aishangjia.Bean.LeaguesTongji;
import com.shushang.aishangjia.Bean.Upload;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.TabRecyclerViewAdapter3;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.dialog.ActionSheetDialog;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JiZhangActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private Toolbar mToolbar;
    private ArrayList<AlbumFile> mAlbumFiles;
    private ImageView mImageView, mImageView2;
    private ImageView mGive1,mGive2,mGet1,mGet2;
    private TextView mTextView, mPicText, mTimeText, mProve, mYongTu;
    private RecyclerView mRlTab;
    private EditText mEditText1,mEditText2;
    private TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5, mTextView6, mTextView7, mTextView8,mTextView9,mTextView10,mTextView11,mTextView12;
    private TabRecyclerViewAdapter3 tabRecyclerViewAdapter;
    private List<Leagues.DataListBean> data;
    private boolean isFirst = true;
    private String token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private String leagueId = null;
    private Dialog mRequestDialog;
    private Button mButton;
    private LinearLayout mLinearLayout,mLinearLayout2;
    private Uri fileUri = null;
    private File imgFile;
    private Bitmap mBitmap;
    private Bitmap newBitmap;
    private List<Bitmap> mapList = new ArrayList<>();
    private List<Bitmap> NewmapList = new ArrayList<>();
    private List<File> mapFile = new ArrayList<>();
    private RelativeLayout mRelativeLayout;
    private String type="1";
    private List<String> imgIds=new ArrayList<>();
    private static final int REQUEST_CODE_PROVEPEOPLE =9662;
    private String leagueInOut,userCase,beizhu,reterenceName,time,imageIds;
    private Upload upload;
    private String shangjia_id= PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "shangjia_id");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public int setLayout() {
        return R.layout.activity_ji_zhang;
    }

    @Override
    public void init() {
        mLinearLayout=findViewById(R.id.TabLayout);
        mLinearLayout2=findViewById(R.id.shouzhi);
        mRlTab = findViewById(R.id.rl_tab);
        mImageView = findViewById(R.id.time_select);
        mImageView2 = findViewById(R.id.pic_select);
        mPicText = findViewById(R.id.pic_select_text);
        mTimeText = findViewById(R.id.time_select_text);
        mButton = findViewById(R.id.btn_submit);
        mProve = findViewById(R.id.et_prove_people);
        mEditText1=findViewById(R.id.et_money);
        mEditText2=findViewById(R.id.et_beizhu);
        mYongTu = findViewById(R.id.et_yongtu);
        mRelativeLayout = findViewById(R.id.zhangdan_type);
        mGive1=findViewById(R.id.give1);
        mGive2=findViewById(R.id.give2);
        mProve.setOnClickListener(this);
        mYongTu.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
        mTextView = findViewById(R.id.history);
        mToolbar = findViewById(R.id.toolbar);
        mTextView1 = findViewById(R.id.week_get);
        mTextView2 = findViewById(R.id.mounth_get);
        mTextView3 = findViewById(R.id.year_get);
        mTextView4 = findViewById(R.id.total_get);
        mTextView5 = findViewById(R.id.week_pay);
        mTextView6 = findViewById(R.id.mounth_pay);
        mTextView7 = findViewById(R.id.year_pay);
        mTextView8 = findViewById(R.id.total_pay);
        mTextView9=findViewById(R.id.weekBalance);
        mTextView10=findViewById(R.id.monthBalance);
        mTextView11=findViewById(R.id.yearBalance);
        mTextView12=findViewById(R.id.totalBalance);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.getData));
        mRequestDialog.setCancelable(false);
        // HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Log.d("mTvIntention",String.valueOf(System.currentTimeMillis()));
        mTimeText.setText(simpleDateFormat.format(date));


        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 300);
        getTabData("");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JiZhangActivity.this, ZhangDanActivity.class));
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        JiZhangActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setAutoHighlight(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.image(JiZhangActivity.this) // Image selection.
                        .multipleChoice()
                        .camera(true)
                        .columnCount(3)
                        .selectCount(9)
                        .checkedList(mAlbumFiles)
                        .onResult(new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                mAlbumFiles = result;
                                getBitmap(mAlbumFiles);
                                mPicText.setText("已选择" + mAlbumFiles.size() + "张图片");
                            }
                        })
                        .onCancel(new Action<String>() {
                            @Override
                            public void onAction(@NonNull String result) {
                            }
                        })
                        .start();
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leagueInOut=mEditText1.getText().toString().replace(" ", "");
                userCase=mYongTu.getText().toString().replace(" ", "");
                beizhu=mEditText2.getText().toString().replace(" ", "");
                reterenceName=mProve.getText().toString().replace(" ", "");
                time=mTimeText.getText().toString().replace(" ", "");
                if(imgIds.size()!=mapFile.size()){
                    ToastUtils.showLong("上传图片失败");
                }
                else {
                    String img=null;
                    StringBuilder stringBuilder = dataToString(imgIds);
                    img=stringBuilder.toString();
                    if(leagueInOut.equals("")||userCase.equals("")||reterenceName.equals("")||type.equals("")||time.equals("")){
                        ToastUtils.showLong("除了图片和备注，其他均为必填");
                    }
                    else {
                        Log.d("imageIds",img);
                        if(shangjia_id.equals("")||shangjia_id==null){
                            UpLoadData(token_id,leagueId,leagueInOut,userCase,beizhu,reterenceName,time,type,img);
                            ToastUtils.showLong("上传成功");
                        }
                        else {
                            UpLoadData(token_id,shangjia_id,leagueInOut,userCase,beizhu,reterenceName,time,type,img);
                            ToastUtils.showLong("上传成功");
                        }
                    }
                }
            }
        });
    }

    private void UpLoadData(String tokenId, String leagueId, String leagueInOut, String userCase, String beizhu, String reterenceName, String time, String type, String img) {
        String url= BaseUrl.BASE_URL+"phoneLeagueController.do?method=saveOrUpdateFinance&token_id="+tokenId+"&leagueId="
                +leagueId+"&leagueInOut="+leagueInOut+"&useCase="+userCase+"&beizhu="+beizhu+"&reterenceName="+reterenceName+"&time="+time+"&type="+type+"&imageIds="+img;
        Log.d("BaseUrl",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            Log.d("AppPeopleActivity",response);
                            try {
                                GongGao test = JSONUtil.fromJson(response, GongGao.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(JiZhangActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(JiZhangActivity.this,"token_id",null);
                                    startActivity(new Intent(JiZhangActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    getTabData("");
                                    mEditText1.setText("");
                                    mEditText2.setText("");
                                    if(mAlbumFiles.size()!=0){
                                        mAlbumFiles.clear();
                                    }
                                    mPicText.setText("选择图片");
                                    //获取当前时间
                                    Date date = new Date(System.currentTimeMillis());
                                    Log.d("mTvIntention",String.valueOf(System.currentTimeMillis()));
                                    mTimeText.setText(simpleDateFormat.format(date));
                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(JiZhangActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                                Toast.makeText(JiZhangActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                        Toast.makeText(JiZhangActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

                Toast.makeText(JiZhangActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }



    private static StringBuilder dataToString(List<String> imgIds) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < imgIds.size(); i++) {
            if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                sb.append(",");
            }
            sb.append(imgIds.get(i));
        }

        return sb;
    }


    private void getBitmap(ArrayList<AlbumFile> albumFiles) {
        try {
            NewmapList.clear();
            mapFile.clear();
            for (int i = 0; i < albumFiles.size(); i++) {
                mBitmap = getSmallBitmap(albumFiles.get(i).getPath());
                int bitmapDegree = getBitmapDegree(albumFiles.get(i).getPath());
                newBitmap = rotateBitmapByDegree(mBitmap, bitmapDegree);
                NewmapList.add(newBitmap);
            }

            for (int j = 0; j < NewmapList.size(); j++) {
                File file = getFile(NewmapList.get(j), j);
                mapFile.add(file);
            }
            upLoadImg(mapFile);
        } catch (Exception e) {
            ToastUtils.showLong("" + e);
        }
    }

    private void upLoadImg(List<File> mapFile) {
        imgIds.clear();
        Log.d("mapFile", mapFile.size() + "");
        String url = BaseUrl.BASE_URL + "fileController.do?method=upload&token_id=" + token_id + "&type=cw";
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        for (int i = 0; i < mapFile.size(); i++) {
            Log.d("上传", mapFile.get(i).getName());
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("zhaopian", mapFile.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), mapFile.get(i)));
            RequestBody body = builder.build();
            final Request request = new Request.Builder().url(url).post(body).build();
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        upload = JSONUtil.fromJson(response.body().string(), Upload.class);
                        if (upload.getRet().equals("200")) {
                            if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                mRequestDialog.dismiss();
                            }
                            imgIds.add(upload.getData());
                            Log.d("上传", imgIds.size()+"");
                        }
                        else if(upload.getRet().equals("201")){
                            if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                mRequestDialog.dismiss();
                            }
                            ToastUtils.showLong(upload.getMsg());
                        }
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                            mRequestDialog.dismiss();
                        }
                        Log.d("上传失败1", e.toString());
                    }
                });
            } catch (Exception e) {
                if (mRequestDialog != null && mRequestDialog.isShowing()) {
                    mRequestDialog.dismiss();
                }
                ToastUtils.showLong("上传失败");
            }

        }

    }


    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            // 根据旋转角度，生成旋转矩阵
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        if (bitmap != null & !bitmap.isRecycled()) {
//            bitmap.recycle();
//        }
            return newBitmap;
        } else {
            return null;
        }

    }


    private File getFile(Bitmap bitmap, int j) {
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        File file = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String saveDir = Environment.getExternalStorageDirectory()
                    + "/dreamtownImage";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(saveDir, "photo" + j + ".jpg");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return file;
    }


    //获取tab栏数据
    private void getTabData(final String allData) {
        mRequestDialog.show();
        String url = BaseUrl.BASE_URL + "phoneLeagueController.do?method=getLeaguesByMerchant&token_id=" + token_id;
        Log.d("TabList", url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("TabList", response);
                        if (response != null) {
                            try {
                                Leagues tabList = JSONUtil.fromJson(response, Leagues.class);
                                if (tabList.getRet().equals("101")) {
                                    Toast.makeText(JiZhangActivity.this, "" + tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(JiZhangActivity.this, "token_id", null);
                                    startActivity(new Intent(JiZhangActivity.this, LoginActivity2.class));
                                    finish();
                                } else {
                                    if (tabList.getRet().equals("200")) {
                                        data = tabList.getDataList();
                                        if (data.size() != 0) {
                                            showTabData(data);
                                            isFirst = false;
                                            getTongjiData(data.get(0).getMerchantId());
//                                            getTongjiData(data.get(0).getMerchantId());
                                        }
                                        else {
                                            mLinearLayout2.setVisibility(View.GONE);
                                            mLinearLayout.setVisibility(View.GONE);
                                            getTongjiData(shangjia_id);
                                        }
                                    } else if (tabList.getRet().equals("201")) {
                                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                            mRequestDialog.dismiss();
                                        }
                                        Toast.makeText(JiZhangActivity.this, "" + tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                    mRequestDialog.dismiss();
                                }
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                            mRequestDialog.dismiss();
                        }
                        Toast.makeText(JiZhangActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                            mRequestDialog.dismiss();
                        }
                        Toast.makeText(JiZhangActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }


    private void showTabData(List<Leagues.DataListBean> data) {
        if (isFirst) {
            tabRecyclerViewAdapter = new TabRecyclerViewAdapter3(R.layout.tab_items, data);
            tabRecyclerViewAdapter.setThisPosition(0);
            leagueId = data.get(0).getMerchantId();
        }
        initRecyclerView(tabRecyclerViewAdapter);
    }


    private void initRecyclerView(final TabRecyclerViewAdapter3 tabRecyclerViewAdapter) {
        final LinearLayoutManager manager = new LinearLayoutManager(JiZhangActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRlTab.setLayoutManager(manager);
        //解决嵌套滑动冲突
        mRlTab.setNestedScrollingEnabled(false);
        mRlTab.setAdapter(tabRecyclerViewAdapter);
        tabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
                leagueId = data.get(position).getMerchantId();
                getTongjiData(data.get(position).getMerchantId());
            }
        });
    }


    private void getTongjiData(String leagueId) {
        String url = BaseUrl.BASE_URL + "phoneLeagueController.do?method=getFinancesCount&token_id=" + token_id + "&leagueId=" + leagueId;
        Log.d("TabList", url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                            mRequestDialog.dismiss();
                        }
                        mEditText1.setFocusable(true);
                        mEditText1.setFocusableInTouchMode(true);
                        mEditText1.requestFocus();
                        Log.d("TabList", response);
                        if (response != null) {
                            try {
                                LeaguesTongji tabList = JSONUtil.fromJson(response, LeaguesTongji.class);
                                if (tabList.getRet().equals("101")) {
                                    Toast.makeText(JiZhangActivity.this, "" + tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(JiZhangActivity.this, "token_id", null);
                                    startActivity(new Intent(JiZhangActivity.this, LoginActivity2.class));
                                    finish();
                                } else {
                                    if (tabList.getRet().equals("200")) {

                                        if (String.valueOf(tabList.getData().getWeekIn()) == null || String.valueOf(tabList.getData().getWeekIn()).equals("0.0")) {
                                            mTextView1.setText("0.0");
                                        } else {
                                            mTextView1.setText(String.valueOf(tabList.getData().getWeekIn()));
                                        }

                                        if (String.valueOf(tabList.getData().getMonthIn()) == null || String.valueOf(tabList.getData().getMonthIn()).equals("0.0")) {
                                            mTextView2.setText("0.0");
                                        } else {
                                            mTextView2.setText(String.valueOf(tabList.getData().getMonthIn()));
                                        }

                                        if (String.valueOf(tabList.getData().getYearIn()) == null || String.valueOf(tabList.getData().getYearIn()).equals("0.0")) {
                                            mTextView3.setText("0.0");
                                        } else {
                                            mTextView3.setText(String.valueOf(tabList.getData().getYearIn()));
                                        }

                                        if (String.valueOf(tabList.getData().getTotalIn()) == null || String.valueOf(tabList.getData().getTotalIn()).equals("0.0")) {
                                            mTextView4.setText("0.0");
                                        } else {
                                            mTextView4.setText(String.valueOf(tabList.getData().getTotalIn()));
                                        }

                                        if (String.valueOf(tabList.getData().getWeekOut()) == null || String.valueOf(tabList.getData().getWeekOut()).equals("0.0")) {
                                            mTextView5.setText("0.0");
                                        } else {
                                            mTextView5.setText(String.valueOf(tabList.getData().getWeekOut()));
                                        }

                                        if (String.valueOf(tabList.getData().getMonthOut()) == null || String.valueOf(tabList.getData().getMonthOut()).equals("0.0")) {
                                            mTextView6.setText("0.0");
                                        } else {
                                            mTextView6.setText(String.valueOf(tabList.getData().getMonthOut()));
                                        }

                                        if (String.valueOf(tabList.getData().getYearOut()) == null || String.valueOf(tabList.getData().getYearOut()).equals("0.0")) {
                                            mTextView7.setText("0.0");
                                        } else {
                                            mTextView7.setText(String.valueOf(tabList.getData().getYearOut()));
                                        }

                                        if (String.valueOf(tabList.getData().getTotalOut()) == null || String.valueOf(tabList.getData().getTotalOut()).equals("0.0")) {
                                            mTextView8.setText("0.0");
                                        } else {
                                            mTextView8.setText(String.valueOf(tabList.getData().getTotalOut()));
                                        }
                                        if(String.valueOf(tabList.getData().getWeekBalance())==null||String.valueOf(tabList.getData().getWeekBalance()).equals("0.0")){
                                            mTextView9.setText("0.0");
                                        }
                                        else {
                                            mTextView9.setText(String.valueOf(tabList.getData().getWeekBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getMonthBalance())==null||String.valueOf(tabList.getData().getMonthBalance()).equals("0.0")){
                                            mTextView10.setText("0.0");
                                        }
                                        else {
                                            mTextView10.setText(String.valueOf(tabList.getData().getMonthBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getYearBalance())==null||String.valueOf(tabList.getData().getYearBalance()).equals("0.0")){
                                            mTextView11.setText("0.0");
                                        }
                                        else {
                                            mTextView11.setText(String.valueOf(tabList.getData().getYearBalance()));
                                        }
                                        if(String.valueOf(tabList.getData().getTotalBalance())==null||String.valueOf(tabList.getData().getTotalBalance()).equals("0.0")){
                                            mTextView12.setText("0.0");
                                        }
                                        else {
                                            mTextView12.setText(String.valueOf(tabList.getData().getTotalBalance()));
                                        }
                                    } else if (tabList.getRet().equals("201")) {
                                        Toast.makeText(JiZhangActivity.this, "" + tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                    mRequestDialog.dismiss();
                                }
                                Toast.makeText(JiZhangActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        if (mRequestDialog != null && mRequestDialog.isShowing()) {
                            mRequestDialog.dismiss();
                        }
                        Toast.makeText(JiZhangActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");// HH:mm:ss
        mTimeText.setText(simpleDateFormat.format(date));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_prove_people:
                startActivityForResult(new Intent(JiZhangActivity.this,ProvePeopleActivity.class),REQUEST_CODE_PROVEPEOPLE);
                break;
            case R.id.et_yongtu:
                getYongTu();
                break;
            case R.id.zhangdan_type:
                getZhangdanType();
                break;
        }
    }

    private void getZhangdanType() {
        new ActionSheetDialog(JiZhangActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("支出", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mGive1.setVisibility(View.VISIBLE);
                                mGive2.setVisibility(View.GONE);
                                type="0";
                            }
                        })
                .addSheetItem("收入", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mGive1.setVisibility(View.GONE);
                                mGive2.setVisibility(View.VISIBLE);
                                type="1";
                            }
                        })
                .show();

    }

    private void getYongTu() {

        new ActionSheetDialog(JiZhangActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("日常生活", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mYongTu.setText("日常生活");
                            }
                        })
                .addSheetItem("文化娱乐", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mYongTu.setText("文化娱乐");
                            }
                        })
                .addSheetItem("企业工作", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mYongTu.setText("企业工作");
                            }
                        })
                .addSheetItem("其他收支", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mYongTu.setText("其他收支");
                            }
                        })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_PROVEPEOPLE){
            mProve.setText(PreferencesUtils.getString(JiZhangActivity.this,"userName"));
        }
    }
}

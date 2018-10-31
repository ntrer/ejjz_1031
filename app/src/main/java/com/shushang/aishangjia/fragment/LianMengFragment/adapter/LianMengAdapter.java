package com.shushang.aishangjia.fragment.LianMengFragment.adapter;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.LianMeng;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LianMengActivity;
import com.shushang.aishangjia.base.PermissionListener;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.permissionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class LianMengAdapter extends BaseQuickAdapter<LianMeng.DataListBean,BaseViewHolder> {

    private LianMengActivity mActivity2;

    public LianMengAdapter(@LayoutRes int layoutResId, @Nullable List<LianMeng.DataListBean> data, LianMengActivity mainActivity2) {
        super(layoutResId, data);
        this.mActivity2=mainActivity2;
    }

    @Override
    protected void convert(BaseViewHolder helper, final LianMeng.DataListBean item) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(item.getCjsj());
        helper.setText(R.id.people,item.getCustomerName());
        helper.setText(R.id.label,item.getMerchantName());
        helper.setText(R.id.date,String.valueOf(simpleDateFormat.format(date)));
        helper.setText(R.id.phone,item.getCustomerMobile());
        helper.getView(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionPhone(item.getCustomerMobile());
            }
        });
    }


    //请求相机权限
    private void permissionPhone(final String phone){

        if(isMobileNO(phone)){
            mActivity2.requestRunPermisssion(new String[]{Manifest.permission.CALL_PHONE,}, new PermissionListener() {
                @Override
                public void onGranted() {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        startActivity(intent);
                    }
                    catch (SecurityException e){
                        ToastUtils.showLong(e.toString());
                    }
                }

                @Override
                public void onDenied(List<String> deniedPermission) {
                    for(String permission : deniedPermission){
                        reGetPermission();
                    }
                }

            });
        }
        else {
            ToastUtils.showLong("该号码无法拨打");
        }

    }


    private void reGetPermission() {
        ExtAlertDialog.showSureDlg(mActivity2, "警告", "权限被拒绝，部分功能将无法使用，请重新授予权限", "确定", new ExtAlertDialog.IExtDlgClick() {
            @Override
            public void Oclick(int result) {
                if(result==1){
                    permissionUtil.GoToSetting(mActivity2);
                }
            }
        });
    }

    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][123456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


}

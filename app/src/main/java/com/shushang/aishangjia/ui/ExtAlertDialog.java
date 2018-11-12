package com.shushang.aishangjia.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shushang.AppContext;
import com.shushang.aishangjia.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author WH
 * @Title: ExtAlertDialog.java
 * @Package duoduo.app.widgets
 * @Description: 自定义alertDialog
 * @date 2015年3月7日 下午12:58:14
 */
public class ExtAlertDialog {

    /**
     * @param @param context 结构上下文
     * @param @param title 对话框标题
     * @param @param msg 显示类容
     * @return void
     * @Description: 显示自定义AlertDialog
     */
    public static void showDialog(Context context, int title, int msg) {
        Resources res = context.getResources();
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_alert_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.ext_dialog_content);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        View hasTitle = dlg.findViewById(R.id.ext_dialog_hasTitle);
        if (TextUtils.isEmpty(res.getString(title)))
            hasTitle.setVisibility(View.GONE);
        else
            titleTxt.setText(res.getString(title));
        contentTxt.setText(res.getString(msg));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        dlg.setCancelable(false);
    }

    public static void showDialog(Context context, String title, String msg) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_alert_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.ext_dialog_content);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        View hasTitle = dlg.findViewById(R.id.ext_dialog_hasTitle);
        if (TextUtils.isEmpty(title))
            hasTitle.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        contentTxt.setText(msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

    }

    public static void showDialog(Context context, String title, String msg,
                                  final IExtDlgClick onclickListenser) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_alert_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dlg.setCancelable(false);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.ext_dialog_content);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        View hasTitle = dlg.findViewById(R.id.ext_dialog_hasTitle);
        if (TextUtils.isEmpty(title))
            hasTitle.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        contentTxt.setText(msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (onclickListenser != null)
                    onclickListenser.Oclick(0);
            }
        });
        dlg.setCancelable(false);
    }

    /**
     * @param @param  context
     * @param @param  tip 显示的内容
     * @param @return
     * @return Dialog
     * @Description: 显示等待对话框
     */
    public static Dialog creatRequestDialog(final Context context, String tip) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.alert_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = (int) (0.3 * width);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0) {
            tvTitle.setText(R.string.sending_request);
        } else {
            tvTitle.setText(tip);
        }

        return dialog;
    }

    /**
     * @param context 结构上下文
     * @param title   对话框标题
     * @param msg     显示类容
     * @description : 显示自定义的确认取消对话框
     */
    public static void showSureDlg(Context context, int title, int msg, int btnTxt, final IExtDlgClick onclickListener) {
        showSureDlg(context, title, msg, btnTxt, true, onclickListener);
    }

    public static void showSureDlg(Context context, int title, int msg, int btnTxt, boolean isCancelable, final IExtDlgClick onclickListener) {
        String t;
        String m;
        String btnStr;
        Resources res = context.getResources();
        if (title == -1)
            t = null;
        else
            t = res.getString(title);
        m = res.getString(msg);
        btnStr = res.getString(btnTxt);
        showSureDlg(context, t, m, btnStr, isCancelable, onclickListener);
    }

    public static void showSureDlg(Context context, String title, String msg, String btnTxt, final IExtDlgClick onclickListener) {
        showSureDlg(context, title, msg, btnTxt, true, onclickListener);
    }

    public static void showSureDlg(Context context, String title, String msg, String btnTxt, boolean isCancelable, final IExtDlgClick onclickListener) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_cancel_sure_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        View titleLayout = dlg.findViewById(R.id.title_layout);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        if (TextUtils.isEmpty(title))
            titleLayout.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.ext_dialog_content);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        contentTxt.setText(msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(0);
                dlg.dismiss();
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(1);
                dlg.dismiss();
            }
        });
        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }


    public static void showSureDlg2(Context context, String title, String msg, String btnTxt, boolean isCancelable, final IExtDlgClick onclickListener) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_cancel_sure_dialog2);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        View titleLayout = dlg.findViewById(R.id.title_layout);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        if (TextUtils.isEmpty(title))
            titleLayout.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.ext_dialog_content);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        contentTxt.setText(msg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(0);
                dlg.dismiss();
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(1);
                dlg.dismiss();
            }
        });
        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }




    public static void showEditDlg(Context context, String title,String btnTxt, boolean isCancelable, final IExtDlgClick2 onclickListener) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setView(new EditText(context));
        dlg.show();
        dlg.setContentView(R.layout.ext_edit_dialog);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        View titleLayout = dlg.findViewById(R.id.title_layout2);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title2);
        if (TextUtils.isEmpty(title))
            titleLayout.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        final EditText contentTxt =  dlg
                .findViewById(R.id.ext_dialog_content2);
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(0,null);
                dlg.dismiss();
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure2);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null)
                    onclickListener.Oclick(1,contentTxt.getText());
                dlg.dismiss();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                contentTxt.setFocusable(true);
                contentTxt.setFocusableInTouchMode(true);
                //请求获得焦点
                contentTxt.requestFocus();
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) contentTxt
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(contentTxt, 0);
            }
        }, 200);

        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }








    /**
     * @param @param  context
     * @param @param  tip 显示的内容
     * @param @return
     * @return Dialog
     * @Description: 显示等待对话框
     */
    public static Dialog createRequestDialog(final Context context, String tip) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.alert_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = lp.width;

        TextView tvLoad = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0)
            tvLoad.setText(R.string.sending_request);
        else
            tvLoad.setText(tip);
        return dialog;
    }

    /**
     * @param @param  context
     * @param @param  tip 显示的内容
     * @param @return
     * @return Dialog
     * @Description: 显示加载对话框
     */
    public static Dialog createLoadingDialog(final Context context, String tip) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        dialog.setContentView(R.layout.alert_loading_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = lp.width;

        TextView tvLoad = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0)
            tvLoad.setText(R.string.loading);
        else
            tvLoad.setText(tip);
        return dialog;
    }

    /**
     * @param @param  context
     * @param @param  tip 显示的内容
     * @param @return
     * @return Dialog
     * @Description: 显示加载对话框
     */
    public static Dialog createLoadingDialog(final Context context) {
        return createLoadingDialog(context, null);
    }

    public interface IExtDlgClick {
        public void Oclick(int result);
    }

    public interface IExtDlgClick2 {
        public void Oclick(int result, Editable editText);
    }

    /**
     * 显示默认请求成功对话框
     *
     * @param context
     * @return
     */
    public static Dialog createOkDialog(final Context context, String tip_txt) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_add_friend_ok);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_ok_txt);
        if (!TextUtils.isEmpty(tip_txt))
            textView.setText(tip_txt);
        else
            textView.setText("请求成功");
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.6 * width);

        return dialog;
    }



    /**
     * 用户修改密码成功对话框
     *
     * @param context
     * @return
     */
    public static Dialog createModifyPasswordOkDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_modify_password_ok);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.5 * width);
        lp.height = (int) (0.5 * width * 71 / 179);

        return dialog;
    }


    /**
     * 用户修改密码成功对话框
     *
     * @param context
     * @return
     */
    public static Dialog createModifyPasswordOkDialog2(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_modify_password_ok2);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.5 * width);
        lp.height = (int) (0.5 * width * 71 / 179);

        return dialog;
    }

    public static Dialog createModifyPasswordOkDialog3(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_modify_password_ok3);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.5 * width);
        lp.height = (int) (0.5 * width * 71 / 179);

        return dialog;
    }


}

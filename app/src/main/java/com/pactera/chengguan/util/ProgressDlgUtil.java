package com.pactera.chengguan.util;

import android.content.DialogInterface;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.dialog.RequestProgressDialog;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * Created by lijun on 2015/12/9.
 */
public class ProgressDlgUtil {
   private static RequestProgressDialog progressDlg = null;

    /**
     * 显示进度条
     * @param strMessage 显示的标题
     * @param ctx
     * @param request
     * @param isExit
     */
    public static void showProgressDlg(String strMessage,final BaseActivity ctx,final RequestCall request,final boolean isExit) {
        if (null == progressDlg) {
            progressDlg = new RequestProgressDialog(ctx, R.style.dialog);
            progressDlg.setTitle(strMessage);
        }else{
            stopProgressDlg();
            progressDlg.setTitle(strMessage);
        }
        progressDlg
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        progressDlg.stop();
                        if (request != null) {
                            request.cancel();
                        }
                        if (isExit) {
                            ctx.finish();
                        }
                    }
                });
        progressDlg.start();
    }

    /**
     * 关闭dialog
     *
     */
    public static void stopProgressDlg() {
        if (progressDlg != null && progressDlg.isShowing()) {
            progressDlg.stop();
        }
    }
}

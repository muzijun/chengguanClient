package com.pactera.chengguan.municipal.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pactera.chengguan.R;
/**
 * 应用的请求滚动条
 *
 * @author 00225075
 */
public class RequestProgressDialog extends Dialog {

    private Animation operatingAnim;
    private ImageView loadingImage;

    public RequestProgressDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public RequestProgressDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View mview = inflater.inflate(R.layout.request_progressdialog, null);// 得到加载view
        loadingImage = (ImageView) mview
                .findViewById(R.id.loading);
        operatingAnim = AnimationUtils.loadAnimation(context,
                R.anim.request_loading);
        this.setContentView(mview,
                new LinearLayout.LayoutParams((int) (com.pactera.chengguan.municipal.util.ScreenUtils.getScreenWidth(context) * 0.3),
                        (int) (com.pactera.chengguan.municipal.util.ScreenUtils.getScreenWidth(context) * 0.3)));// 设置布局
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        loadingImage.startAnimation(operatingAnim);
    }

    /**
     * 启动
     */
    public void start(Activity ctx) {
        if (!ctx.isFinishing()) {
            show();
            loadingImage.startAnimation(operatingAnim);
        }
    }

    /**
     * 关闭
     */
    public void stop() {
        dismiss();
        loadingImage.clearAnimation();
    }


}

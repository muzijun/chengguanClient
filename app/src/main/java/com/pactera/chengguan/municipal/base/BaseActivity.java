package com.pactera.chengguan.municipal.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.AppBaseActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/** 市政
 * Created by lijun on 2015/12/2.
 */
public class BaseActivity extends AppBaseActivity {
    protected BaseActivity mContext;
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initBarTint();
    }
    private void initBarTint() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // 全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorBule));
            } else {
                setTranslucentStatus(window);
            }

        }
        setTintResource(getTintResourceId());
    }

    @TargetApi(19)
    private void setTranslucentStatus(Window window) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        window.setAttributes(winParams);
    }

    protected void setTintResource(int res) {
        mTintManager.setTintResource(res);
    }

    protected int getTintResourceId() {
        return R.color.colorTransparent;
    }
}

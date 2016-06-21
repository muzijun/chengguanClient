package com.pactera.chengguan.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.pactera.chengguan.municipal.activity.LoginActivity;
import com.pactera.chengguan.municipal.base.BaseActivity;

/**
 * 空白页为了解决推送跳转
 * Created by lijun on 2016/6/1.
 */
public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent push_intent = new Intent(mContext,
                LoginActivity.class);
        startActivity(push_intent);
        finish();
    }

}

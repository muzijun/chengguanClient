package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.util.BaseCallback;

/**
 * 登陆界面
 * Created by lijun on 2015/12/22.
 */
public class LoginActivity extends BaseActivity implements RequestListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
       RequestPair j= new RequestPair();
        j.setRequest(new BaseCallback(String.class,this,this));
        j.setUrl("http://www.csdn.net/");
        ChenguanOkHttpManager.request(j);
    }


    /**
     * 登陆
     *
     * @param view
     */
    public void login(View view) {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void success(Object result) {

    }

    @Override
    public void fail() {

    }
}

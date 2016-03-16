package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.LoginBean;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.RequestParam;
import com.pactera.chengguan.util.BaseCallback;
import com.pactera.chengguan.util.ProgressDlgUtil;

import java.util.ArrayList;

/**
 * 登陆界面
 * Created by lijun on 2015/12/22.
 */
public class LoginActivity extends BaseActivity implements RequestListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        setLoginRequest("aaa", "bbb");
    }

    public void setLoginRequest(String user, String password){
        ArrayList<RequestParam> params = new ArrayList<RequestParam>();
        params.add(new RequestParam("user", user));
        params.add(new RequestParam("pwd", password));
        RequestPair j= new RequestPair();
        j.setContext(this);
        j.setRequest(new BaseCallback(LoginBean.class,this,this));
        j.setMethod(Contants.Post);
        j.setLoadingShow(true);
        j.setUrl(Contants.USER_LOGIN);
        j.setParams(params);
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
        LoginBean loginBean = (LoginBean) result;
        loginBean.checkResult(loginHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler loginHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            LoginBean loginBean = (LoginBean) baseBean;
            Toast.makeText(LoginActivity.this, "登录成功:"+message
                    +" | token:"+loginBean.access_token, Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "登录失败:"+message, Toast.LENGTH_LONG).show();
        }
    };
}

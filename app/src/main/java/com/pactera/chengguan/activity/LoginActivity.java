package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.ChengApplication;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.LoginBean;
import com.pactera.chengguan.bean.municipal.StandardDataBean;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.MunicipalCache;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.RequestParam;
import com.pactera.chengguan.util.BaseCallback;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.util.ProgressDlgUtil;
import com.pactera.chengguan.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登陆界面
 * Created by lijun on 2015/12/22.
 */
public class LoginActivity extends BaseActivity implements RequestListener {

    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        freeLogin();
    }

    /**
     * 免登录步骤
     */
    private void freeLogin(){
        String token = (String)SPUtils.get(this, MunicipalCache.SP_TOKEN, "");
        if(token.length() > 0){
            ChengApplication.instance.access_token = token;
            requestStandardData();
        }
    }

    /**
     * 获取动态筛选项内容
     */
    private void requestStandardData(){
        MunicipalRequest.requestCheckStandardData(this, this);
    }

    /**
     * 检查账号密码是否为空
     * @param user
     * @param password
     * @return
     */
    private boolean checkAccountAndPassword(String user, String password){
        if(user == null || user.isEmpty()){
            Toast.makeText(this, R.string.account_null_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if(password == null || password.isEmpty()){
            Toast.makeText(this, R.string.password_null_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    /**
     * 登陆
     *
     * @param view
     */
    public void login(View view) {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if(!checkAccountAndPassword(account, password)){
            return;
        }
        MunicipalRequest.requestLogin(this, this, account, password);
    }

    /**
     * 保存token信息和权限信息
     */
    private void saveTokenAndValue(String token, List<String> value){
        ChengApplication.instance.access_token = token;
        ChengApplication.instance.authValue = value;
        SPUtils.put(this, MunicipalCache.SP_TOKEN, token);
    }

    /**
     * 界面跳转
     */
    private void loginToNext(){
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void success(String reqUrl, Object result) {
        if(reqUrl.equals(Contants.USER_LOGIN)) {
            LoginBean loginBean = (LoginBean) result;
            loginBean.checkResult(this, loginHandler);
        }else if(reqUrl.equals(Contants.SELECT_SCREEN_ITEM)){
            StandardDataBean standardDataBean = (StandardDataBean) result;
            standardDataBean.checkResult(this, standardDataHandler);
        }
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
            saveTokenAndValue(loginBean.access_token, loginBean.value);
            requestStandardData();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "登录失败:"+message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler standardDataHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            StandardDataBean standardDataBean = (StandardDataBean) baseBean;
            if(standardDataBean.datas != null) {
                MunicipalCache.sectionList = standardDataBean.datas.transformSection();
                MunicipalCache.roadwayList = standardDataBean.datas.transformBasicData(standardDataBean.datas.roadwayList);
                MunicipalCache.bridgeList = standardDataBean.datas.transformBasicData(standardDataBean.datas.bridgeList);
                MunicipalCache.levelList = standardDataBean.datas.transformBasicData(standardDataBean.datas.levelList);
                loginToNext();
            }
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "获取基本数据失败:"+message, Toast.LENGTH_LONG).show();
        }
    };

}

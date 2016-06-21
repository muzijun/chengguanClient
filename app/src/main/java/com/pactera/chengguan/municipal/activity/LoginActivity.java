package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chengguan.dao.DaoSession;
import com.chengguan.dao.checkclassify;
import com.chengguan.dao.checkclassifyDao;
import com.chengguan.dao.checkcontent;
import com.chengguan.dao.checkcontentDao;
import com.igexin.sdk.PushManager;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.app.base.ChenguanOkHttpManager;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.CheckLibBean;
import com.pactera.chengguan.municipal.bean.municipal.LoginBean;
import com.pactera.chengguan.municipal.bean.municipal.StandardDataBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.db.DBHelper;
import com.pactera.chengguan.municipal.model.PushData;
import com.pactera.chengguan.municipal.model.municipal.CheckLib;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.util.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

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
    private void freeLogin() {
        String token = (String) SPUtils.get(this, MunicipalCache.SP_TOKEN, "");
        if (token.length() > 0) {
            ChengApplication.instance.municipalApplication.access_token = token;
            requestCheckLib();
        }
    }

    /**
     * 获取动态筛选项内容
     */
    private void requestStandardData() {
        MunicipalRequest.requestCheckStandardData(this, this);
    }

    /**
     * 检查账号密码是否为空
     *
     * @param user
     * @param password
     * @return
     */
    private boolean checkAccountAndPassword(String user, String password) {
        if (user == null || user.isEmpty()) {
            Toast.makeText(this, R.string.account_null_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (password == null || password.isEmpty()) {
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
        if (!checkAccountAndPassword(account, password)) {
            return;
        }
        String clientid = "";
        if (PushManager.getInstance().getClientid(mContext) != null) {
            clientid = PushManager.getInstance().getClientid(mContext);
        }
        MunicipalRequest.requestLogin(this, this, account, password, clientid);
    }

    /**
     * 保存token信息和权限信息
     */
    private void saveTokenAndValue(String token, List<String> value) {
        ChengApplication.instance.municipalApplication.access_token = token;
        ChengApplication.instance.municipalApplication.authValue = value;
        SPUtils.put(this, MunicipalCache.SP_TOKEN, token);
    }

    /**
     * 获取考核库
     */
    private void requestCheckLib() {
        String version = (String) SPUtils.get(this, MunicipalCache.SP_CHECKLIB_VERSION, "0");
        MunicipalRequest.requestCheckLib(this, this, version);
    }

    /**
     * 界面跳转
     */
    private void loginToNext() {
        Intent intent = new Intent(mContext, MainActivity.class);
        if (getIntent().getSerializableExtra(Contants.PUSH_JSON) != null) {
            PushData push = (PushData) getIntent().getSerializableExtra(Contants.PUSH_JSON);
            intent.putExtra(Contants.PUSH_JSON, push);
        }
        startActivity(intent);
    }

    @Override
    public void success(String reqUrl, Object result) {
        if (reqUrl.equals(Contants.USER_LOGIN)) {
            LoginBean loginBean = (LoginBean) result;
            loginBean.checkResult(this, loginHandler);
        } else if (reqUrl.equals(Contants.SELECT_SCREEN_ITEM)) {
            StandardDataBean standardDataBean = (StandardDataBean) result;
            standardDataBean.checkResult(this, standardDataHandler);
        } else if (reqUrl.equals(Contants.SELECT_CHECKLIB)) {
            CheckLibBean checkLibBean = (CheckLibBean) result;
            checkLibBean.checkResult(this, checkLibHandler);
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
            Toast.makeText(LoginActivity.this, "登录成功:" + message
                    + " | token:" + loginBean.access_token, Toast.LENGTH_LONG).show();
            saveTokenAndValue(loginBean.access_token, loginBean.value);
            requestCheckLib();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "登录失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler checkLibHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            CheckLibBean checkLibBean = (CheckLibBean) baseBean;
            if (checkLibBean.datas != null) {
                SPUtils.put(mContext, MunicipalCache.SP_CHECKLIB_VERSION, checkLibBean.datas.version);
                if (checkLibBean.datas.datas != null && checkLibBean.datas.datas.size() > 0) {
                    MunicipalCache.checkLibList = checkLibBean.datas.transformToCheckLibList();
                    saveCheckLibToDB(MunicipalCache.checkLibList);
                }
            }
            requestStandardData();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "获取考核库失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler standardDataHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            StandardDataBean standardDataBean = (StandardDataBean) baseBean;
            if (standardDataBean.datas != null) {
                MunicipalCache.sectionList = standardDataBean.datas.transformSection();
                MunicipalCache.roadwayList = standardDataBean.datas.transformBasicData(standardDataBean.datas.roadwayList);
                MunicipalCache.bridgeList = standardDataBean.datas.transformBasicData(standardDataBean.datas.bridgeList);
                MunicipalCache.levelList = standardDataBean.datas.transformBasicData(standardDataBean.datas.levelList);
                MunicipalCache.organizationList = standardDataBean.datas.transformOrganization();
                if (MunicipalCache.sectionList.size() != 0) {
                    loginToNext();
                } else {
                    Toast.makeText(mContext, "作业单位获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(LoginActivity.this, "获取基本数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 保存考核库内容到数据库
     */
    private void saveCheckLibToDB(List<CheckLib> checkLibList) {
        DaoSession daoSession = DBHelper.getInstance(this).getDaoSession(this);
        final checkclassifyDao classifyDao = daoSession.getCheckclassifyDao();
        final checkcontentDao contentDao = daoSession.getCheckcontentDao();
        classifyDao.deleteAll();
        contentDao.deleteAll();
        List<checkclassify> checkclassifyList = new ArrayList<checkclassify>();
        List<checkcontent> checkcontentList = new ArrayList<checkcontent>();
        for (CheckLib checkLib : checkLibList) {
            checkclassify classify = new checkclassify();
            classify.setClassifyid(checkLib.getId());
            classify.setClassifyname(checkLib.getName());
            checkclassifyList.add(classify);
            List<CheckLib.Content> contents = checkLib.getContents();
            for (CheckLib.Content content : contents) {
                checkcontent ccontent = new checkcontent();
                ccontent.setClassifyid(checkLib.getId());
                ccontent.setStandardid(content.getId());
                ccontent.setStandard(content.getContent());
                ccontent.setPoint(content.getPoints());
                checkcontentList.add(ccontent);
            }
        }
        classifyDao.insertInTx(checkclassifyList);
        contentDao.insertInTx(checkcontentList);
    }

}

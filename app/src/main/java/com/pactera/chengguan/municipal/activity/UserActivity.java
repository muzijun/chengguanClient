package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.OperatorInfoBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.OperatorInfo;
import com.pactera.chengguan.municipal.util.MunicipalRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人信息
 * Created by lijun on 2016/5/17.
 */
public class UserActivity extends BaseActivity implements RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_loginname)
    TextView tvLoginname;
    @Bind(R.id.tv_organizationname)
    TextView tvOrganizationname;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.tv_telephone)
    TextView tvTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        title.setText("个人信息");
        MunicipalRequest.requestGetOperatorInfo(mContext, this);
    }

    @Override
    public void success(String reqUrl, Object result) {
        OperatorInfoBean operatorInfoBean = (OperatorInfoBean) result;
        operatorInfoBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }
    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(com.pactera.chengguan.municipal.bean.BaseBean baseBean, String message) {
            OperatorInfoBean operatorInfoBean = (OperatorInfoBean) baseBean;
            if(operatorInfoBean.datas!=null) {
                OperatorInfo info = operatorInfoBean.transformToOperatorInfo();
                tvName.setText(info.getOperatorDisplayname());
                tvLoginname.setText("账户: "+info.getOperatorLoginname());
                tvOrganizationname.setText("部门: "+info.getOrganizationname());
                tvEmail.setText("邮箱: "+info.getOperatorEmail());
                tvTelephone.setText("电话: "+info.getOperatorPhone());
            }
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "请求失败:" + result + " | msg:" + message
                    , Toast.LENGTH_LONG).show();
        }
    };
}

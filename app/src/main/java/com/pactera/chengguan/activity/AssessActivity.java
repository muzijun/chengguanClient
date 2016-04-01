package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.util.MunicipalRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 考核
 */
public class AssessActivity extends BaseActivity implements RequestListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.et_point)
    EditText etPoint;
    @Bind(R.id.tv_month)
    TextView tvMonth;

    private CaseInfo caseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        ButterKnife.bind(this);
        getIntentContent();
        init();
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
    }

    private void init() {
        title.setText("考核");
    }

    public void onClick(View view){
        String content = tvContent.getText().toString();
        int point = Integer.parseInt(etPoint.getText().toString());
        int month = CaseInfo.getMonthByText(tvMonth.getText().toString());
        if(month < 0){
            return;
        }
        MunicipalRequest.requestCaseCheck(this, this, caseInfo.getId(), content, point, month+1);
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, assessHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler assessHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(AssessActivity.this, "考核成功!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(AssessActivity.this, "考核失败:" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };
}

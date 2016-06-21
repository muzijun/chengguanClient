package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;

import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.rey.material.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 延期
 */
public class PostPoneActivity extends BaseActivity implements View.OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_minus)
    Button btMinus;
    @Bind(R.id.edt_days)
    EditText edtDays;
    @Bind(R.id.bt_plus)
    Button btPlus;
    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.commit)
    Button commit;

    private com.pactera.chengguan.municipal.model.municipal.CaseInfo caseInfo;
    private int days;   //延期天数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pone);
        ButterKnife.bind(this);
        getIntentContent();
        init();
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (com.pactera.chengguan.municipal.model.municipal.CaseInfo) bundle.get("case");
    }

    private void init() {
        title.setText("延期");
        btMinus.setOnClickListener(this);
        btPlus.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String point;
        int number;
        switch (v.getId()) {
            case R.id.bt_minus:
                point = edtDays.getText().toString();
                number = Integer.valueOf(point);
                if (0 < number) {
                    edtDays.setText(String.valueOf(number - 1));
                }
                break;
            case R.id.bt_plus:
                point = edtDays.getText().toString();
                number = Integer.valueOf(point);
                edtDays.setText(String.valueOf(number + 1));
                break;
            case R.id.commit:
                days = Integer.valueOf(edtDays.getText().toString());
                com.pactera.chengguan.municipal.util.MunicipalRequest.requestCaseDelay(this, this, caseInfo.getId(), days
                        , edit.getText().toString());
                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        com.pactera.chengguan.municipal.bean.BaseBean baseBean = (com.pactera.chengguan.municipal.bean.BaseBean) result;
        baseBean.checkResult(this, postPoneHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private com.pactera.chengguan.municipal.bean.BaseHandler postPoneHandler = new com.pactera.chengguan.municipal.bean.BaseHandler() {
        @Override
        public void doSuccess(com.pactera.chengguan.municipal.bean.BaseBean baseBean, String message) {
            Toast.makeText(PostPoneActivity.this, "延期成功!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(PostPoneActivity.this, "延期失败:"+result+" | msg:"+message, Toast.LENGTH_LONG).show();
        }
    };
}

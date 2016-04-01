package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * 返工通不通过界面
 * Created by lijun on 2016/3/24.
 */
public class ReworkActivity extends BaseActivity implements View.OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.edit)
    EditText edit;
    private CaseInfo caseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        addTitleView(lin);
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
    }

    private void init() {
        title.setText("返工");
    }

    private void requestCaseNotSecond(String opinion){
        MunicipalRequest.requestCaseNotSecond(this, this, caseInfo.getId(), opinion);
    }

    private void addTitleView(LinearLayout linearLayout) {
        TextView textView = new TextView(mContext);
        textView.setId(R.id.top);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("提交");
        textView.setTextColor(getResources().getColor(R.color.colorBule));
        textView.setOnClickListener(this);
        linearLayout.addView(textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                String opinion = edit.getText().toString();
                requestCaseNotSecond(opinion);
                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, reworkHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler reworkHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(ReworkActivity.this, "返工请求成功!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(ReworkActivity.this, "返工请求失败:"+result+" | msg:"+message
                    , Toast.LENGTH_LONG).show();
        }
    };

}

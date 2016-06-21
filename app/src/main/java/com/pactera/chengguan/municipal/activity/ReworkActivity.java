package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.pactera.chengguan.municipal.util.MunicipalRequest;

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

    private int statue;
    public static final int STATUS_CASE = 1;    //案件
    public static final int STATUS_ISSUE = 2;   //养护日志
    public static final int STATUS_TASKCONACT = 3;//任务联系单
    public static final int STATUS_TASK = 4;//任务单
    public static final String STATUS_TASK_NAME = "statue";   //传递参数名
    private CaseInfo caseInfo;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        addTitleView(lin);
    }

    private void getIntentContent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.get("case") != null) {
            statue = STATUS_CASE;
            caseInfo = (CaseInfo) bundle.get("case");
        } else if (bundle.get("statue") != null) {
            statue = bundle.getInt("statue", STATUS_ISSUE);
            id = bundle.getString("id");
        } else if (bundle.get(STATUS_TASK_NAME) != null) {
            statue = bundle.getInt(STATUS_TASK_NAME, 0);
            id = bundle.getString("id");
        }
    }

    private void init() {
        title.setText("返工");
    }

    /**
     * 请求案件返工
     *
     * @param opinion
     */
    private void requestCaseNotSecond(String opinion) {
        MunicipalRequest.requestCaseNotSecond(this, this, caseInfo.getId(), opinion);
    }

    /**
     * 请求养护日志返工
     *
     * @param opinion
     */
    private void requestRefuseIssue(String opinion) {
        MunicipalRequest.requestRefuseIssue(this, this, id, opinion);
    }

    /**
     * 请求任务联系单日志返工
     *
     * @param opinion
     */
    private void requestRefuseContact(String opinion) {
        MunicipalRequest.requestRefuseContact(this, this, id, opinion);
    }

    /**
     * 请求任务单日志返工
     *
     * @param opinion
     */
    private void requestRefuseTask(String opinion) {
        MunicipalRequest.requestRefuseTask(this, this, Integer.valueOf(id), opinion);
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
                if (statue == STATUS_CASE) {
                    requestCaseNotSecond(opinion);
                } else if (statue == STATUS_ISSUE) {
                    requestRefuseIssue(opinion);
                } else if (statue == STATUS_TASKCONACT) {
                    requestRefuseContact(opinion);
                }else if (statue == STATUS_TASK) {
                    requestRefuseTask(opinion);
                }
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
            setResult(Contants.TASK_REFRESH);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(ReworkActivity.this, "返工请求失败:" + result + " | msg:" + message
                    , Toast.LENGTH_LONG).show();
        }
    };

}

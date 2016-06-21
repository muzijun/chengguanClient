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
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.util.MunicipalRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 任务联系单不通过
 * Created by lijun on 2016/5/18.
 */
public class TaskContactPassActivity extends BaseActivity implements RequestListener, View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.edit)
    EditText edit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskcontact_pass);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        addTitleView(lin);
    }

    private void getIntentContent() {
        id = getIntent().getIntExtra("id", 0);

    }

    private void init() {
        title.setText("结案");
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
                if (!opinion.isEmpty()) {
                   MunicipalRequest.requestCloseContact(mContext, this, id, opinion);
                } else {
                    Toast.makeText(mContext,"请填写内容",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private com.pactera.chengguan.municipal.bean.BaseHandler Handler = new com.pactera.chengguan.municipal.bean.BaseHandler() {
        @Override
        public void doSuccess(com.pactera.chengguan.municipal.bean.BaseBean baseBean, String message) {
            Toast.makeText(mContext, "结案成功!", Toast.LENGTH_LONG).show();
            setResult(Contants.TASK_REFRESH);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "结案失败:" + result + " | msg:" + message
                    , Toast.LENGTH_LONG).show();
        }
    };

}

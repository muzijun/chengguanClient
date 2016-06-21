package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;
import com.pactera.chengguan.municipal.util.MunicipalRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 修改扣分
 * Created by lijun on 2016/4/13.
 */
public class PointsModifyActivity extends BaseActivity implements View.OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.edit_commit)
    EditText editCommit;
    private PatrolRecord patrolRecord;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_modify);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        patrolRecord = (PatrolRecord) getIntent().getSerializableExtra("patrolrecord");
        position=getIntent().getIntExtra("position",0);
        addTitleView(lin);
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
                String content=editCommit.getText().toString();
                if (!content.equals("")) {
                    MunicipalRequest.requestUpdatePatrolRecord(mContext, this, patrolRecord.getRecordId(), content, patrolRecord.getBaseType());
                }else{
                    Toast.makeText(mContext,"不能为空",Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(mContext, dataHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler dataHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            setResult(mContext.RESULT_OK);
            finish();
            Toast.makeText(mContext, "成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

}

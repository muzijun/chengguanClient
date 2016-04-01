package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ProcessRecordAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.CaseFlowBean;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 流程日志
 * Created by lijun on 2016/3/10.
 */
public class ProcessRecordActivity extends BaseActivity implements OnRefreshListener
        , OnLoadMoreListener, AdapterView.OnItemClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private CaseInfo caseInfo;
    private ProcessRecordAdapter processRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_record);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        getCaseFlowData();
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
    }

    private void init() {
        title.setText("流程日志");
        processRecordAdapter = new ProcessRecordAdapter(mContext, caseInfo);
        swipeTarget.setAdapter(processRecordAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void getCaseFlowData(){
        MunicipalRequest.requestCaseFlow(this, this, caseInfo.getId());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void success(String reqUrl, Object result) {
        CaseFlowBean flowBean = (CaseFlowBean) result;
        flowBean.checkResult(this, caseFlowHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler caseFlowHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            CaseFlowBean flowBean = (CaseFlowBean) baseBean;
            caseInfo.setProcessLogData(flowBean);
            processRecordAdapter.setNotifyDataChanged(caseInfo);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(ProcessRecordActivity.this, "获取流程日志失败:"+result+" | msg:"+message
                    , Toast.LENGTH_LONG).show();
        }
    };
}

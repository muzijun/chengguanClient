package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ContactsAdapter;
import com.pactera.chengguan.adapter.DelayRecordAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.CaseDelayRecordBean;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 延期记录
 * Created by lijun on 2016/3/24.
 */
public class DelayRecordActivity extends BaseActivity implements RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private CaseInfo caseInfo;
    private DelayRecordAdapter delayRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_record);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        requestDelayRecord();
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("延期记录");
        delayRecordAdapter = new DelayRecordAdapter(mContext, caseInfo);
        swipeTarget.setAdapter(delayRecordAdapter);
    }

    private void requestDelayRecord(){
        MunicipalRequest.requestCaseDelayRecord(this, this, caseInfo.getId());
    }

    @Override
    public void success(String reqUrl, Object result) {
        CaseDelayRecordBean delayRecordBean = (CaseDelayRecordBean) result;
        delayRecordBean.checkResult(this, delayRecordHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler delayRecordHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            CaseDelayRecordBean delayRecordBean = (CaseDelayRecordBean) baseBean;
            caseInfo.setExceedLogData(delayRecordBean);
            delayRecordAdapter.setNotifyDataChanged(caseInfo);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(DelayRecordActivity.this, "获取延期记录失败:"+result+" | msg:"+message
                    , Toast.LENGTH_LONG).show();
        }
    };
}

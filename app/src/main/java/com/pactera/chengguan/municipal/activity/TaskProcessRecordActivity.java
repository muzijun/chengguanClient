package com.pactera.chengguan.municipal.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.view.AppViewHolder;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.base.CommonAdapter;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.TaskFlowBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.TaskFlow;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
import com.pactera.chengguan.municipal.view.ViewHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**任务单流程日志
 * Created by lijun on 2016/5/16.
 */
public class TaskProcessRecordActivity extends BaseActivity implements OnRefreshListener
        , OnLoadMoreListener, AdapterView.OnItemClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private int id;
    private CommonAdapter<TaskFlow> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_record);
        ButterKnife.bind(this);
        getIntentContent();
        init();
        getTaskFlowList();
    }

    private void getIntentContent(){
       id=getIntent().getIntExtra("id",id);
    }

    private void init() {
        title.setText("流程日志");
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    private void getTaskFlowList(){
        MunicipalRequest.requestTaskFlowList(mContext, this, id);
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
        TaskFlowBean taskFlowBean = (TaskFlowBean) result;
        taskFlowBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            final TaskFlowBean flowBean = (TaskFlowBean) baseBean;
            final List<TaskFlow> taskFlowList=flowBean.transformToFlowList();
            commonAdapter = new CommonAdapter<TaskFlow>(mContext, taskFlowList,R.layout.processrecord_item) {
                @Override
                public void convert(AppViewHolder holder, int position) {
                    TaskFlow taskFlow=taskFlowList.get(position);
                    holder.setText(R.id.content,taskFlow.getOperatorname()+taskFlow.getCreateTime()+taskFlow.getNode());
                }
            };
            swipeTarget.setAdapter(commonAdapter);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext,"获取流程日志失败:"+result+" | msg:"+message
                    , Toast.LENGTH_LONG).show();
        }
    };
}

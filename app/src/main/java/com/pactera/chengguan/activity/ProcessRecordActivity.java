package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ProcessRecordAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

/**流程日志
 * Created by lijun on 2016/3/10.
 */
public class ProcessRecordActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener  {
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_record);
        init();
    }

    private void init() {
        ((TextView) findViewById(R.id.title)).setText("流程日志");
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        ListView mListView = (ListView) findViewById(R.id.swipe_target);
        ProcessRecordAdapter processRecordAdapter=new ProcessRecordAdapter(mContext);
        mListView.setAdapter(processRecordAdapter);
        mListView.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

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
}

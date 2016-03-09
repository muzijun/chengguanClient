package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ApprovalAdapter;
import com.pactera.chengguan.adapter.CaseListAdapter;
import com.pactera.chengguan.adapter.SpinnerAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.rey.material.widget.Spinner;

/**
 * 案件详情
 * Created by lijun on 2016/3/9.
 */
public class CaseListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private Spinner spinner_one, spinner_two, spinner_three, spinner_four;
    private static final String[] tab_one = {"待办", "不可办"};
    private static final String[] tab_two = {"月度", "季度"};
    private static final String[] tab_three = {"一月", "二月"};
    private static final String[] tab_four = {"升序", "降序"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caselist);
        init();
    }

    private void init() {
        ((TextView) findViewById(R.id.title)).setText("案件列表");
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        ListView mListView = (ListView) findViewById(R.id.swipe_target);
        CaseListAdapter caseListAdapter=new CaseListAdapter(mContext);
        mListView.setAdapter(caseListAdapter);
        mListView.setOnItemClickListener(this);
        spinner_one = (Spinner) findViewById(R.id.spinner_one);
        spinner_two = (Spinner) findViewById(R.id.spinner_two);
        spinner_three = (Spinner) findViewById(R.id.spinner_three);
        spinner_four = (Spinner) findViewById(R.id.spinner_four);
        SpinnerAdapter adapter_one = new SpinnerAdapter(mContext, tab_one);
        SpinnerAdapter adapter_two = new SpinnerAdapter(mContext, tab_two);
        SpinnerAdapter adapter_three = new SpinnerAdapter(mContext, tab_three);
        SpinnerAdapter adapter_four = new SpinnerAdapter(mContext, tab_four);
        spinner_one.setAdapter(adapter_one);
        spinner_two.setAdapter(adapter_two);
        spinner_three.setAdapter(adapter_three);
        spinner_four.setAdapter(adapter_four);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(mContext,CaseDetialsActivity.class);
        startActivity(intent);
    }
}

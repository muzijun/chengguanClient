package com.pactera.chengguan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.ApprovalActivity;
import com.pactera.chengguan.adapter.ApprovalAdapter;
import com.pactera.chengguan.adapter.SpinnerAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.rey.material.widget.Spinner;

/**巡查案件
 * Created by lijun on 2015/12/11.
 */
public class OneFrament extends BaseFragment implements OnRefreshListener, OnLoadMoreListener,AdapterView.OnItemClickListener {
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private Spinner spinner_one,spinner_two;
    private static final String[] mCountries = {"China345", "China2", "China3", "China4", "China5", "China6"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.approval_one, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        ListView mListView = (ListView) view.findViewById(R.id.swipe_target);
        ApprovalAdapter mAdapter = new ApprovalAdapter(mContext);
        mListView.addHeaderView(createView(), null, false);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        spinner_one=(Spinner)view.findViewById(R.id.spinner_one);
        spinner_two=(Spinner)view.findViewById(R.id.spinner_two);
        SpinnerAdapter adapter=new SpinnerAdapter(mContext,mCountries);
        spinner_one.setAdapter(adapter);
        spinner_two.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

    }

    private View createView() {
        return LayoutInflater.from(mContext).inflate(
                R.layout.header, null);
    }
    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);

     }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(mContext, ApprovalActivity.class);
        startActivity(intent);
    }

    @Override
    protected void lazyLoad() {
    }
}


package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.SearchAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.pactera.chengguan.view.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据搜索
 * Created by lijun on 2016/3/21.
 */
public class BdataSearchActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.tx_search)
    TextView txSearch;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_search);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        SearchAdapter searchAdapter = new SearchAdapter(mContext);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadingMore(false);
        swipeTarget.setAdapter(searchAdapter);
        swipeTarget.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

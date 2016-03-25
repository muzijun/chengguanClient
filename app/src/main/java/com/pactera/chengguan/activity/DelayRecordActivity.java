package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ContactsAdapter;
import com.pactera.chengguan.adapter.DelayRecordAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 延期记录
 * Created by lijun on 2016/3/24.
 */
public class DelayRecordActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_record);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("延期记录");
        swipeTarget.setAdapter(new DelayRecordAdapter(mContext));
    }


}

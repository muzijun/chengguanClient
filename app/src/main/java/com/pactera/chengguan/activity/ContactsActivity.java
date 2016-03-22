package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ContactsAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录
 * Created by lijun on 2016/3/22.
 */
public class ContactsActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("通讯录");
        swipeTarget.setAdapter(new ContactsAdapter(mContext));
    }
}

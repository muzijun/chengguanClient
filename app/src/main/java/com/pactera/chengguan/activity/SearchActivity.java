package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_search);
    }
}

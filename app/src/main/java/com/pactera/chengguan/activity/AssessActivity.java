package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 考核
 */
public class AssessActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("考核");
    }
}

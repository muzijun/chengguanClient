package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;

/**
 * 考核
 */
public class AssessActivity extends BaseActivity {
    private AQuery mAq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        init();
    }

    private void init() {
        mAq = new AQuery(mContext);
        mAq.id(R.id.title).text("考核");
    }
}

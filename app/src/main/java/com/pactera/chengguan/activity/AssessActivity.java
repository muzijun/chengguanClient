package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.pactera.chengguan.R;

public class AssessActivity extends AppCompatActivity {
    private AQuery mAq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        init();
    }

    private void init() {
        mAq = new AQuery(AssessActivity.this);
        mAq.id(R.id.title).text("考核");
    }
}

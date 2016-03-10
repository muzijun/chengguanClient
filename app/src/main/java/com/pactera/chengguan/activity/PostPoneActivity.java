package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidquery.AQuery;
import com.pactera.chengguan.R;

public class PostPoneActivity extends AppCompatActivity {
    private AQuery mAq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pone);
        init();
    }

    private void init() {
        mAq = new AQuery(PostPoneActivity.this);
        mAq.id(R.id.title).text("延期");
    }
}

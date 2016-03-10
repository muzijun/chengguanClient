package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.pactera.chengguan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostPoneActivity extends AppCompatActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.edt_days)
    EditText edtDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pone);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("延期");
    }
}

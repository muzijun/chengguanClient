package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostPoneActivity extends BaseActivity implements View.OnClickListener {
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

    @Override
    public void onClick(View v) {

    }
}

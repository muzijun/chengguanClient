package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.rey.material.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostPoneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_minus)
    Button btMinus;
    @Bind(R.id.edt_days)
    EditText edtDays;
    @Bind(R.id.bt_plus)
    Button btPlus;
    @Bind(R.id.edit)
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_pone);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("延期");
        btMinus.setOnClickListener(this);
        btPlus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String point;
        int number;
        switch (v.getId()) {
            case R.id.bt_minus:
                point = edtDays.getText().toString();
                number = Integer.valueOf(point);
                if (0 < number) {
                    edtDays.setText(String.valueOf(number - 1));
                }
                break;
            case R.id.bt_plus:
                point = edtDays.getText().toString();
                number = Integer.valueOf(point);
                edtDays.setText(String.valueOf(number + 1));
                break;
        }
    }
}

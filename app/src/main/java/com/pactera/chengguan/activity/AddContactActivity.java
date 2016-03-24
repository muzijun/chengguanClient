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

/**
 * 添加联系人
 * Created by lijun on 2016/3/23.
 */
public class AddContactActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.edit_email)
    EditText editEmail;
    @Bind(R.id.edit_unit)
    EditText editUnit;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.commit)
    Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("添加");
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:

                break;
        }
    }
}

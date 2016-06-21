package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.rey.material.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 工期
 * Created by lijun on 2016/3/23.
 */
public class TimeActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_minus)
    Button btMinus;
    @Bind(R.id.edt_days)
    EditText edtDays;
    @Bind(R.id.bt_plus)
    Button btPlus;
    @Bind(R.id.commit)
    Button commit;

    private String  mData;
    private String type;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("工期");
        type = getIntent().getStringExtra("type");
        mData = getIntent().getStringExtra("data");
        address=getIntent().getStringExtra("address");
        edtDays.setText(mData);
        commit.setOnClickListener(this);
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
            case R.id.commit:
                SelectEvent selectEvent = new SelectEvent();
                selectEvent.setmMsg(edtDays.getEditableText().toString());
                selectEvent.setType(type);
                selectEvent.setAddress(address);
                EventBus.getDefault().post(selectEvent);
                finish();
                break;
        }
    }
}
package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.SelectEvent;
import com.pactera.chengguan.model.event.PointData;
import com.rey.material.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 扣分
 */
public class PointsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_minus)
    Button btMinus;
    @Bind(R.id.edt_point)
    EditText edtPoint;
    @Bind(R.id.bt_plus)
    Button btPlus;
    @Bind(R.id.tx_standard)
    TextView txStandard;
    @Bind(R.id.commit)
    Button commit;
    @Bind(R.id.tx_type)
    TextView txType;
    private String type;
    private String address;
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("扣分");
        type = getIntent().getStringExtra("type");
        address = getIntent().getStringExtra("address");
        mData = getIntent().getStringExtra("data");
        edtPoint.setText(mData);
        btMinus.setOnClickListener(this);
        btPlus.setOnClickListener(this);
        commit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String point;
        int number;
        switch (v.getId()) {
            case R.id.bt_minus:
                point = edtPoint.getText().toString();
                number = Integer.valueOf(point);
                if (0 < number) {
                    edtPoint.setText(String.valueOf(number - 1));
                }
                break;
            case R.id.bt_plus:
                point = edtPoint.getText().toString();
                number = Integer.valueOf(point);
                edtPoint.setText(String.valueOf(number + 1));
                break;
            case R.id.commit:
                PointData pointData = new PointData();
                pointData.setType(txType.getText().toString());
                pointData.setNumber(edtPoint.getEditableText().toString());
                pointData.setStandard(txStandard.getText().toString());
                SelectEvent selectEvent = new SelectEvent();
                selectEvent.setType(type);
                selectEvent.setAddress(address);
                selectEvent.setObject(pointData);
                EventBus.getDefault().post(selectEvent);
                finish();
                break;
        }
    }
}
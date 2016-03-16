package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.SelectEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 输入文本
 * Created by lijun on 2016/3/16.
 */
public class InputActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.edit)
    EditText edit;
    private String type;
    private String address;
    //描述
    public static final  String DESCRIPTION = "DESCRIPTION";
    //地址
    public static final  String ADDRESS = "ADDRESS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
        init();
        addTitleView(lin);

    }


    private void init() {
        title.setText(getIntent().getStringExtra("title"));
        edit.setText(getIntent().getStringExtra("content"));
        type=getIntent().getStringExtra("type");
        address=getIntent().getStringExtra("address");

    }

    private void addTitleView(LinearLayout linearLayout) {
        TextView textView = new TextView(mContext);
        textView.setId(R.id.top);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("完成");
        textView.setTextColor(getResources().getColor(R.color.colorBule));
        textView.setOnClickListener(this);
        linearLayout.addView(textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                SelectEvent selectEvent = new SelectEvent();
                selectEvent.setmMsg(edit.getEditableText().toString());
                selectEvent.setType(type);
                selectEvent.setAddress(address);
                EventBus.getDefault().post(selectEvent);
                finish();
                break;
        }
    }
}

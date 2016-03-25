package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 返工通不通过界面
 * Created by lijun on 2016/3/24.
 */
public class ReworkActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.edit)
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rework);
        ButterKnife.bind(this);
        init();
        addTitleView(lin);
    }


    private void init() {
     title.setText("返工");

    }



    private void addTitleView(LinearLayout linearLayout) {
        TextView textView = new TextView(mContext);
        textView.setId(R.id.top);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("提交");
        textView.setTextColor(getResources().getColor(R.color.colorBule));
        textView.setOnClickListener(this);
        linearLayout.addView(textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                break;
        }

    }

}

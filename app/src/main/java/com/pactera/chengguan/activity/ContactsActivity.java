package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ContactsAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.util.DensityUtils;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录
 * Created by lijun on 2016/3/22.
 */
public class ContactsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("通讯录");
        addView(lin);
        swipeTarget.setAdapter(new ContactsAdapter(mContext));
    }

    private void addView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.mipmap.icon_add);
        imageView.setOnClickListener(this);
        layoutParams.setMargins(0,0, DensityUtils.dp2px(mContext,10),0);
        linearLayout.addView(imageView);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
         case R.id.top:
            intent=new Intent(mContext,AddContactActivity.class);
             startActivity(intent);
            break;
        }
    }
}

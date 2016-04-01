package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.PictureCompareAdapter;
import com.pactera.chengguan.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片对比
 * Created by lijun on 2016/3/29.
 */
public class PictureCompareActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.grid)
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_compare);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
       title.setText("照片对比");
        grid.setAdapter(new PictureCompareAdapter(mContext));
    }
}

package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.PictureCompareAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.view.NoScrollGridView;

import java.util.ArrayList;

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
    @Bind(R.id.gridview_before)
    NoScrollGridView gridviewBefore;
    @Bind(R.id.gridview_after)
    NoScrollGridView gridviewAfter;
    public static final String EXTRA_IMAGE_BEFORE = "image_before";
    public static final String EXTRA_IMAGE_AFTER = "image_after";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_compare);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        title.setText("照片对比");
        final ArrayList<PicData> befores = (ArrayList<PicData>) getIntent().getSerializableExtra(EXTRA_IMAGE_BEFORE);
        final ArrayList<PicData> afters = (ArrayList<PicData>) getIntent().getSerializableExtra(EXTRA_IMAGE_AFTER);
        if (befores != null) {
            gridviewBefore.setAdapter(new PictureCompareAdapter(mContext, befores, true));
            gridviewBefore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,
                            position);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, befores);
                    mContext.startActivity(intent);
                }
            });
        }
        if (afters != null) {
            gridviewAfter.setAdapter(new PictureCompareAdapter(mContext, afters, false));
            gridviewAfter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext,
                            ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,
                            position);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, afters);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}

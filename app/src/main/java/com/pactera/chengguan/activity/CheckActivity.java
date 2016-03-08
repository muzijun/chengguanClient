package com.pactera.chengguan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ImagePublishAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.model.PhotoEvent;
import com.pactera.chengguan.view.NoScrollGridView;

import java.io.Serializable;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lijun on 2015/12/3.
 */
public class CheckActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_IMAGE = 2;
    private NoScrollGridView mGridView;
    private ImagePublishAdapter mAdapter;
    private ArrayList<String> mDataList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approval_check);
        init();
    }

    protected void init() {
        mGridView = (NoScrollGridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(mContext, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        EventBus.getDefault().register(this);
    }

    private void setMode() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, Contants.MAX_IMAGE_SIZE);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mDataList != null && mDataList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mDataList);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        mAdapter.setmDataList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }
    public void onEventMainThread(PhotoEvent event) {
        mDataList = event.getMsg();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == mContext.RESULT_OK) {
                mDataList.clear();
                mDataList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == getDataSize()) {
            setMode();
        } else {
            Intent intent = new Intent(mContext,
                    ImageZoomActivity.class);
            intent.putExtra(Contants.EXTRA_IMAGE_LIST,
                    (Serializable) mDataList);
            intent.putExtra(Contants.EXTRA_CURRENT_IMG_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

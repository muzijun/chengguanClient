package com.pactera.chengguan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.CaseListActivity;
import com.pactera.chengguan.activity.ImageZoomActivity;
import com.pactera.chengguan.adapter.ImagePublishAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.model.PhotoEvent;
import com.pactera.chengguan.view.NoScrollGridView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 考核案件
 * Created by lijun on 2016/3/8.
 */
public class ExcaseFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int REQUEST_IMAGE = 2;
    //案件详情按钮
    @Bind(R.id.case_list)
    TextView caseList;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    private ImagePublishAdapter mAdapter;

    private ArrayList<String> mDataList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_excase, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(mContext, mDataList);
        gridview.setAdapter(mAdapter);
        caseList.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
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
        getRootFragment().startActivityForResult(intent, REQUEST_IMAGE);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.case_list:
                Intent intent = new Intent(mContext, CaseListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

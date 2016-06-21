package com.pactera.chengguan.municipal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.util.FileDownloadUtils;
import com.pactera.chengguan.municipal.view.ImageItemCycle;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lijun on 2016/3/4.
 */
public class ImageDetailFragment extends BaseFragment {
    @Bind(R.id.image)
    GestureImageView image;
    @Bind(R.id.loading)
    ProgressView loading;
    private PicData mImageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.image_detail_fragment, inflater);
        return view;

    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        mImageUrl = getArguments() != null ? (PicData) getArguments().getSerializable("url") : null;
        loading.start();
        FileDownloadUtils.downloadLauncher(mContext, mCycleViewListener, image, ChengApplication.instance.municipalApplication.access_token, mImageUrl);
    }


    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(int postion, View imageView) {
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            loading.stop();
            Glide.with(mContext).load(imageURL).placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };

    public static ImageDetailFragment newInstance(PicData picData) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putSerializable("url", picData);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

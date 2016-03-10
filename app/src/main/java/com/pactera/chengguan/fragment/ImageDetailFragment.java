package com.pactera.chengguan.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseFragment;
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
    private String mImageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.image_detail_fragment, inflater);
        return view;

    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        Glide.with(getContext()).load(mImageUrl).into(new GlideDrawableImageViewTarget(image) {
            @Override
            public void onStart() {
                loading.start();
                super.onStart();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                loading.stop();
                super.onLoadFailed(e, errorDrawable);
            }


            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                loading.stop();

            }
        });
    }


    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
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

package com.pactera.chengguan.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseFragment;
import com.rey.material.widget.ProgressView;

/**
 * Created by lijun on 2016/3/4.
 */
public class ImageDetailFragment extends BaseFragment {
    private String mImageUrl;
    private GestureImageView mImageView;
    private ProgressView progressBar;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(R.layout.image_detail_fragment, inflater);

    }

    @Override
    public void initContentView(View view) {
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        mImageView = (GestureImageView) view.findViewById(R.id.image);
        progressBar = (ProgressView) view.findViewById(R.id.loading);
        Glide.with(getContext()).load(mImageUrl).into(new GlideDrawableImageViewTarget(mImageView) {
            @Override
            public void onStart() {
                progressBar.start();
                super.onStart();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(getContext(), mContext.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                progressBar.stop();
                super.onLoadFailed(e, errorDrawable);
            }


            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                progressBar.stop();

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
}

package com.pactera.chengguan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseFragment;

/**
 * 广告
 * Created by lijun on 2015/12/2.
 */
public class TabThreeFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.tab_three, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }
}

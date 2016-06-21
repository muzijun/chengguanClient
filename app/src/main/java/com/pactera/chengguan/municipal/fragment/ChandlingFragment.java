package com.pactera.chengguan.municipal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseFragment;

/**投诉处理
 * Created by lijun on 2016/3/8.
 */
public class ChandlingFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.approval_one, inflater);
        return view;
    }
    @Override
    public void initContentView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }
}

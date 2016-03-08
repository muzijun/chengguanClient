package com.pactera.chengguan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseFragment;

/**
 * Created by lijun on 2015/12/2.
 */
public class TabFourFragment extends BaseFragment {

    private TextView mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(R.layout.tab_four, inflater);
    }

    @Override
    public void initContentView(View view) {
        mTitle = (TextView) view.findViewById(R.id.title);
        mTitle.setText(mRes.getString(R.string.title_more));
    }

    @Override
    protected void lazyLoad() {

    }

}

package com.pactera.chengguan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseFragment;

/**基础数据(河道栏杆)
 * Created by lijun on 2016/4/5.
 */
public class DataRiverFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_river, inflater);
        return view;

    }
    @Override
    public void initContentView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }
}

package com.pactera.chengguan.municipal.fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.TitlePagerAdapter;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 养护管理
 * Created by lijun on 2016/3/8.
 */
public class MaintainManageFragment extends BaseFragment {

    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.pager)
    ViewPager pager;
    private final String[] titles = {"投诉处理", "考核案件"};
    private ArrayList<BaseFragment> listFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_maintain, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        ChandlingFragment chandlingFragment = new ChandlingFragment();
        ExcaseFragment excaseFragment = new ExcaseFragment();
        listFragment = new ArrayList<BaseFragment>();
        listFragment.add(chandlingFragment);
        listFragment.add(excaseFragment);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setAdapter(new TitlePagerAdapter(getChildFragmentManager(), listFragment, titles));
        tabs.setViewPager(pager);
        tabs.setTextSize(DensityUtils.sp2px(mContext, 13));
        tabs.setTextColorResource(R.color.color999999);
        tabs.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC), Typeface.NORMAL);
        pager.setCurrentItem(1);
        pager.setOffscreenPageLimit(1);


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

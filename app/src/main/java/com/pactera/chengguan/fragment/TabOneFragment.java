package com.pactera.chengguan.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.TitlePagerAdapter;
import com.pactera.chengguan.base.BaseFragment;

import java.util.ArrayList;

/** 案件
 * Created by lijun on 2015/12/2.
 */
public class TabOneFragment extends BaseFragment {
    private final String[] titles = {"1", "2","3"};
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ArrayList<BaseFragment> listFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_case, inflater);
        return view;
    }
    @Override
    public void initContentView(View view) {
        OneFrament oneFrament=new OneFrament();
        TwoFragment twoFragment=new TwoFragment();
        ThreeFragment threeFragment=new ThreeFragment();
        listFragment=new ArrayList<BaseFragment>();
        listFragment.add(oneFrament);
        listFragment.add(twoFragment);
        listFragment.add(threeFragment);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        tabs = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new TitlePagerAdapter(getChildFragmentManager(), listFragment,titles));
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(1);

    }

    @Override
    protected void lazyLoad() {

    }
}


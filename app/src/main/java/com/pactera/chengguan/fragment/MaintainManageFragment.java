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

/**养护管理
 * Created by lijun on 2016/3/8.
 */
public class MaintainManageFragment extends BaseFragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private final String[] titles = {"投诉处理", "考核案件"};
    private ArrayList<BaseFragment> listFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_maintain, inflater);
        return view;
    }
    @Override
    public void initContentView(View view) {
        ChandlingFragment chandlingFragment=new ChandlingFragment();
        ExcaseFragment excaseFragment=new ExcaseFragment();
        listFragment=new ArrayList<BaseFragment>();
        listFragment.add(chandlingFragment);
        listFragment.add(excaseFragment);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        tabs = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new TitlePagerAdapter(getChildFragmentManager(), listFragment, titles));
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(1);

    }

    @Override
    protected void lazyLoad() {

    }
}

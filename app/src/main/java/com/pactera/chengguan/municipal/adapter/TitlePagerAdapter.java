package com.pactera.chengguan.municipal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pactera.chengguan.municipal.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by lijun on 2015/12/11.
 */
public class TitlePagerAdapter extends FragmentPagerAdapter {

    private String[] TITLES;
    private ArrayList<BaseFragment> mList;

    public TitlePagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list,String[] titles) {
        super(fm);
        mList=list;
        TITLES=titles;

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

}

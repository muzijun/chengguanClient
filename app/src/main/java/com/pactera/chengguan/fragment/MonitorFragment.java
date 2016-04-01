package com.pactera.chengguan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 巡查监控
 * Created by lijun on 2015/12/2.
 */
public class MonitorFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener
        , AdapterView.OnItemClickListener {
    private static final String[] tab_one = {"待办", "处理中", "办结"};
    private static final String[] tab_two = {"月度", "季度", "年度", "日常"};
    private static final String[] tab_three = {"一月", "二月", "三月", "四月", "五月", "六月"
            , "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private static final String[] tab_four = {"时间排序", "超限排序"};
    @Bind(R.id.left_lin)
    LinearLayout leftLin;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private String headers[] = {"待办", "月度", "一月", "时间排序"};
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private GirdDropDownAdapter tab_four_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_monitor, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        title.setText("案件列表");
        final ListView one_View = new ListView(mContext);
        tab_one_Adapter = new GirdDropDownAdapter(mContext, Arrays.asList(tab_one));
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(mContext);
        tab_two_Adapter = new GirdDropDownAdapter(mContext, Arrays.asList(tab_two));
        two_View.setDividerHeight(0);
        two_View.setAdapter(tab_two_Adapter);
        final ListView three_View = new ListView(mContext);
        tab_three_Adapter = new GirdDropDownAdapter(mContext, Arrays.asList(tab_three));
        three_View.setDividerHeight(0);
        three_View.setAdapter(tab_three_Adapter);
        final ListView four_View = new ListView(mContext);
        tab_four_Adapter = new GirdDropDownAdapter(mContext, Arrays.asList(tab_four));
        four_View.setDividerHeight(0);
        four_View.setAdapter(tab_four_Adapter);
        popupViews.add(one_View);
        popupViews.add(two_View);
        popupViews.add(three_View);
        popupViews.add(four_View);
        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_one[position]);
                dropDownMenu.setTabText(tab_one[position]);
                dropDownMenu.closeMenu();
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_two[position]);
                dropDownMenu.setTabText(tab_two[position]);
                dropDownMenu.closeMenu();
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_three[position]);
                dropDownMenu.setTabText(tab_three[position]);
                dropDownMenu.closeMenu();
            }
        });
        four_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_four_Adapter.setCheckItem(position);
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_four[position]);
                dropDownMenu.setTabText(tab_four[position]);
                dropDownMenu.closeMenu();
            }
        });
    }



    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.monitor_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

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

package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.CaseListAdapter;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.fragment.TaskContactFragment;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工作联系单列表
 * Created by lijun on 2016/3/24.
 */
public class ContactSheetActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener
        , AdapterView.OnItemClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private static final String[] tab_one = {"待办", "处理中", "办结"};
    private static final String[] tab_two = {"任务联系单", "任务工作单"};
    private static final String[] tab_three = {"一月", "二月", "三月", "四月", "五月", "六月"
            , "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private String headers[] = {"待办", "任务联系单", "一月"};
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private FragmentManager fm;
     // 开启Fragment事务
     private FragmentTransaction transaction;
     private TaskContactFragment mWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_sheet);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("工作联系单");
        final ListView one_View = new ListView(this);
        tab_one_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_one));
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(this);
        tab_two_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_two));
        two_View.setDividerHeight(0);
        two_View.setAdapter(tab_two_Adapter);
        final ListView three_View = new ListView(this);
        tab_three_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_three));
        three_View.setDividerHeight(0);
        three_View.setAdapter(tab_three_Adapter);
        popupViews.add(one_View);
        popupViews.add(two_View);
        popupViews.add(three_View);
        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(tab_one[position]);
                dropDownMenu.closeMenu();
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(tab_two[position]);
                dropDownMenu.closeMenu();
                if (position == 0) {

                }
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(tab_three[position]);
                dropDownMenu.closeMenu();
            }
        });
        setDefaultFragment();

    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.contactsheet_content, null);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        mWeixin = new TaskContactFragment();
        transaction.replace(R.id.swipe_target, mWeixin);
        transaction.commit();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

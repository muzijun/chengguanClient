package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.CaseListAdapter;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 案件详情
 * Created by lijun on 2016/3/9.
 */
public class CaseListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    private static final String[] tab_one = {"不限","待办", "不可办"};
    private static final String[] tab_two = {"不限","月度", "季度"};
    private static final String[] tab_three = {"不限","一月", "二月"};
    private static final String[] tab_four = {"不限","升序", "降序"};
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private String headers[] = {"不限", "不限", "不限", "不限"};
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private GirdDropDownAdapter tab_four_Adapter;
    private List<View> popupViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caselist);
        ButterKnife.inject(this);
        init();

    }

    private void init() {
        title.setText("案件列表");
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
        final ListView four_View = new ListView(this);
        tab_four_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_four));
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
                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_one[position]);
                dropDownMenu.closeMenu();
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_two[position]);
                dropDownMenu.closeMenu();
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_three[position]);
                dropDownMenu.closeMenu();
            }
        });
        four_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_four_Adapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_four[position]);
                dropDownMenu.closeMenu();
            }
        });
    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.caselist_content, null);
        ListView swipeTarget=(ListView)view.findViewById(R.id.swipe_target);
        ChenguanSwipeToLoadLayout swipeToLoadLayout = (ChenguanSwipeToLoadLayout)view.findViewById(R.id.swipeToLoadLayout);
        CaseListAdapter caseListAdapter = new CaseListAdapter(mContext);
        swipeTarget.setAdapter(caseListAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(mContext, CaseDetialsActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(mContext, CaseFinishActivity.class);
                intent.putExtra(CaseFinishActivity.STATE, 1);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(mContext, CaseFinishActivity.class);
                intent.putExtra(CaseFinishActivity.STATE, 2);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(mContext, CaseFinishActivity.class);
                intent.putExtra(CaseFinishActivity.STATE, 3);
                startActivity(intent);
                break;

        }

    }
}

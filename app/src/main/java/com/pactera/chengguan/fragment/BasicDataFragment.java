package com.pactera.chengguan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.BdataSearchActivity;
import com.pactera.chengguan.adapter.CaseListAdapter;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.adapter.SearchAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.pactera.chengguan.view.PopMenu;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据
 * Created by lijun on 2015/12/2.
 */
public class BasicDataFragment extends BaseFragment implements PopMenu.OnItemClickListener, View.OnClickListener, OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    @Bind(R.id.left_lin)
    LinearLayout leftLin;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @Bind(R.id.back_img)
    ImageView backImg;
    private PopMenu popMenu;
    private String headers[] = {"不限", "不限", "不限"};
    private static final String[] tab_one = {"不限", "标准养护单位"};
    private static final String[] tab_two = {"不限", "养护等级"};
    private static final String[] tab_three = {"不限", "桥梁类别"};
    private String[] option = {"道路", "泵站", "广场", "雨水管道", "河道栏杆", "街道设施"};
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private List<View> popupViews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_basic_data, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        title.setText("桥梁");
        backImg.setVisibility(View.GONE);
        popMenu = new PopMenu(mContext);
        View popmenu_background = ButterKnife.findById(popMenu.getview(), R.id.popmenu_background);
        popmenu_background.setBackgroundResource(R.drawable.drop_down_menuleft_normal);
        popMenu.addItems(option);
        popMenu.setOnItemClickListener(this);
        addMenuView(leftLin);
        addSearchView(lin);
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

    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.basic_data_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        ChenguanSwipeToLoadLayout swipeToLoadLayout = ButterKnife.findById(view, R.id.swipeToLoadLayout);
        SearchAdapter searchAdapter = new SearchAdapter(mContext);
        swipeTarget.setAdapter(searchAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    /**
     * 添加选项
     *
     * @param linearLayout
     */
    private void addMenuView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    /**
     * 添加搜索
     *
     * @param linearLayout
     */
    private void addSearchView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.content);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_search);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.content:
                Intent intent = new Intent(mContext, BdataSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //popmenu点击事件
    @Override
    public void onItemClick(int index) {
        switch (index) {
            case 0:
                title.setText(popMenu.getItemList().get(0));
                break;
            case 1:
                title.setText(popMenu.getItemList().get(1));
                break;
            case 2:
                title.setText(popMenu.getItemList().get(2));
                break;
            case 3:
                title.setText(popMenu.getItemList().get(3));
                break;
            case 4:
                title.setText(popMenu.getItemList().get(4));
                break;
            case 5:
                title.setText(popMenu.getItemList().get(5));
                break;


        }

    }

    //listview点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}

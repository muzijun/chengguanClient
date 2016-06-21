package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.municipal.adapter.MaintainListAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.PageIssueBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.PageIssue;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 养护日志列表
 * Created by lijun on 2016/4/15.
 */
public class MaintainListAcivity extends BaseActivity implements AdapterView.OnItemClickListener
        , OnRefreshListener, OnLoadMoreListener, RequestListener {

    private static final String[] tab_one = {"待办", "处理中", "办结"};
    private static final String[] tab_three = {"全部", "一月", "二月", "三月", "四月", "五月", "六月"
            , "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private static final String[] tab_four = {"时间排序", "状态排序"};
    private List<String> tab_two;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
//    private String headers[] = {"待办", "月度", "一月", "时间排序"};
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private GirdDropDownAdapter tab_four_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private int selectTabTwoIndex = 0;          //tab_two
    private int selectTabThreeIndex = 0;        //tab_three
    private int selectTabFourIndex = 0;         //tab_four
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<PageIssue> pageIssueList = new ArrayList<PageIssue>(); //养护日志集合
    private MaintainListAdapter maintainListAdapter;

    private int requestStatus;
    public static final int STATUS_INIT = 0;       //初始化获取数据
    public static final int STATUS_REFRESH = 1;    //刷新
    public static final int STATUS_MORE = 2;       //获取更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dropdownmenu);
        ButterKnife.bind(this);
        init();
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    private void requestPageIssueList(int requestStatus){
        this.requestStatus = requestStatus;
        int sectionId = MunicipalCache.sectionList.get(selectTabTwoIndex).getId();
        Integer month = null;
        if(selectTabThreeIndex > 0){
            month = selectTabThreeIndex;
        }
        MunicipalRequest.requestPageIssueList(this, this, selectTabOneIndex + 1, sectionId, month
                , selectTabFourIndex + 1, PAGE_COUNT, getLastId(requestStatus), requestStatus);
    }

    private List<String> getSectionList(){
        List<String> secList = new ArrayList<String>();
        for(MunicipalCache.DataObject dataObject : MunicipalCache.sectionList){
            secList.add(dataObject.getName());
        }
        return secList;
    }

    private void init() {
        title.setText("养护日志");
        final ListView one_View = new ListView(this);
        tab_one_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_one));
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(this);
        tab_two = getSectionList();
        tab_two_Adapter = new GirdDropDownAdapter(this, tab_two);
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
        List<String> headers = new ArrayList<String>();
        headers.add(tab_one[0]);
        headers.add(tab_two.get(0));
        headers.add(tab_three[0]);
        headers.add(tab_four[0]);
        //init dropdownview
        dropDownMenu.setDropDownMenu(headers, popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
                selectTabOneIndex = position;
                dropDownMenu.setTabText(tab_one[position]);
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
                selectTabTwoIndex = position;
                dropDownMenu.setTabText(tab_two.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                selectTabThreeIndex = position;
                dropDownMenu.setTabText(tab_three[position]);
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        four_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_four_Adapter.setCheckItem(position);
                selectTabFourIndex = position;
                dropDownMenu.setTabText(tab_four[position]);
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.base_swipeload, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        swipeTarget.setDivider(new ColorDrawable(Color.TRANSPARENT));
        swipeTarget.setDividerHeight(DensityUtils.dp2px(mContext, 10));
        maintainListAdapter = new MaintainListAdapter(mContext, pageIssueList);
        swipeTarget.setAdapter(maintainListAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PageIssue pageIssue = pageIssueList.get(position);
        Intent intent=new Intent(mContext,MaintainDetialActivity.class);
        intent.putExtra("state",getPageIssueStatue(pageIssue));
        intent.putExtra("id", ""+pageIssue.getId());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode > 0) {
            swipeToLoadLayout.setRefreshing(true);
        }
    }

    @Override
    public void onLoadMore() {
        requestPageIssueList(STATUS_MORE);
    }

    @Override
    public void onRefresh() {
        requestPageIssueList(STATUS_REFRESH);
    }

    @Override
    public void success(String reqUrl, Object result) {
        if(requestStatus == STATUS_REFRESH){
            swipeToLoadLayout.setRefreshing(false);
        }else if(requestStatus == STATUS_MORE){
            swipeToLoadLayout.setLoadingMore(false);
        }
        PageIssueBean pageIssueBean = (PageIssueBean) result;
        pageIssueBean.checkResult(this, pageIssueHandler);
    }

    @Override
    public void fail() {
        if(requestStatus == STATUS_REFRESH){
            swipeToLoadLayout.setRefreshing(false);
        }else if(requestStatus == STATUS_MORE){
            swipeToLoadLayout.setLoadingMore(false);
        }
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler pageIssueHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            PageIssueBean pageIssueBean = (PageIssueBean) baseBean;
            List<PageIssue> pageIssues = pageIssueBean.transformPageIssueList();
            if(requestStatus == STATUS_REFRESH || requestStatus == STATUS_INIT){
                pageIssueList = pageIssues;
            }else{
                pageIssueList.addAll(pageIssues);
            }
            maintainListAdapter.setNotifyChanged(pageIssueList);
            Toast.makeText(mContext, "获取列表数据成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取列表数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 获取列表最后一项的id
     * @return
     */
    private long getLastId(int requestStatus){
        if(requestStatus == STATUS_REFRESH || pageIssueList == null || pageIssueList.size() <= 0){
            return 0;
        }
        return pageIssueList.get(pageIssueList.size()-1).getId();
    }

    private int getPageIssueStatue(PageIssue pageIssue){
        if(pageIssue.getIssueStatue().equals(PageIssue.ISSUE_STATUS_CONFIRM)){
            return 4;
        }else if(pageIssue.getIssueStatue().equals(PageIssue.ISSUE_STATUS_PROCESS)){
            //处理中并且无处理后图片
            if(pageIssue.getAfterTreatment() == null || pageIssue.getAfterTreatment().size() <= 0) {
                return 0;
            }
            //处理中并且有处理后图片
            else{
                return 1;
            }
        }else if(pageIssue.getIssueStatue().equals(PageIssue.ISSUE_STATUS_CENSOR)){
            return 2;
        }else{
            return 3;
        }
    }
}

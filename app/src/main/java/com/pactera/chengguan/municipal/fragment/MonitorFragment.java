package com.pactera.chengguan.municipal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.activity.PointsModifyActivity;
import com.pactera.chengguan.municipal.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.municipal.adapter.MonitorAdapter;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.PatrolRecordBridgeBean;
import com.pactera.chengguan.municipal.bean.municipal.PatrolRecordRoadBean;
import com.pactera.chengguan.municipal.config.MonitorListener;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
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
        , AdapterView.OnItemClickListener, MonitorListener, RequestListener {
    private static final String[] TAB_ONE = {"本周期", "上周期", "历史"};
    private static final String[] TAB_TWO = {"道路", "桥梁"};
    private static final String[] TAB_FOUR = {"时间排序", "状态排序"};
    private List<String> tab_one = new ArrayList<String>();
    private List<String> tab_two = new ArrayList<String>();
    private List<String> tab_three = new ArrayList<String>();
    private List<String> tab_four = new ArrayList<String>();
    @Bind(R.id.left_lin)
    LinearLayout leftLin;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private GirdDropDownAdapter tab_four_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private MonitorAdapter monitorAdapter;
    private int selectTabOneIndex = 0;          //tab_one
    //0是道路接口，1是桥梁接口
    private int selectTabTwoIndex = 0;          //tab_two
    private int selectTabThreeIndex = 0;        //tab_three
    private int selectTabFourIndex = 0;        //tab_four
    private List<PatrolRecord> PatrolRecordList = new ArrayList<PatrolRecord>();        //数据集合
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    public static final int REQUEST_ACTIVITY = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_monitor, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        transition();
        leftLin.setVisibility(View.GONE);
        title.setText("巡查监控");
        final ListView one_View = new ListView(mContext);
        tab_one_Adapter = new GirdDropDownAdapter(mContext, tab_one);
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(mContext);
        tab_two_Adapter = new GirdDropDownAdapter(mContext, tab_two);
        two_View.setDividerHeight(0);
        two_View.setAdapter(tab_two_Adapter);
        final ListView three_View = new ListView(mContext);
        tab_three_Adapter = new GirdDropDownAdapter(mContext, tab_three);
        three_View.setDividerHeight(0);
        three_View.setAdapter(tab_three_Adapter);
        final ListView four_View = new ListView(mContext);
        tab_four_Adapter = new GirdDropDownAdapter(mContext, tab_four);
        four_View.setDividerHeight(0);
        four_View.setAdapter(tab_four_Adapter);
        popupViews.add(one_View);
        popupViews.add(two_View);
        popupViews.add(three_View);
        popupViews.add(four_View);
        //init dropdownview
        dropDownMenu.setDropDownMenu(headers, popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
                selectTabOneIndex = position + 1;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_one[position]);
                dropDownMenu.setTabText(tab_one.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
                selectTabTwoIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_two[position]);
                dropDownMenu.setTabText(tab_two.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                selectTabThreeIndex = MunicipalCache.levelList.get(position).getId();
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_three[position]);
                dropDownMenu.setTabText(tab_three.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        four_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_four_Adapter.setCheckItem(position);
                selectTabFourIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_four[position]);
                dropDownMenu.setTabText(tab_four.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);

            }
        });
        selectTabOneIndex = 1;
        selectTabTwoIndex = 0;
        selectTabThreeIndex = MunicipalCache.levelList.get(0).getId();
        selectTabFourIndex = 0;
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }


    private View createView() {
        ExpandableLayout l = null;
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.monitor_content, null);
        ExpandableLayoutListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeTarget.setOnItemClickListener(this);
        monitorAdapter = new MonitorAdapter(mContext, this, PatrolRecordList);
        swipeTarget.setAdapter(monitorAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    /**
     * 取出刷选
     */
    private void transition() {
        headers.add("本周期");
        headers.add("道路");
        headers.add(MunicipalCache.levelList.get(0).getName());
        headers.add("时间排序");
        tab_one = Arrays.asList(TAB_ONE);
        tab_two = Arrays.asList(TAB_TWO);
        tab_four = Arrays.asList(TAB_FOUR);
        for (MunicipalCache.DataObject dataObject : MunicipalCache.levelList) {
            tab_three.add(dataObject.getName());
        }

    }

    /**
     * 巡查道路列表请求
     */
    private void requestroadListData() {
        MunicipalRequest.requestPatrolRecordRoad(mContext, this, selectTabOneIndex, selectTabThreeIndex, selectTabFourIndex, PAGE_COUNT, getLastId());
    }

    /**
     * 巡查桥梁列表请求
     */
    private void requestBridgeListData() {
        MunicipalRequest.requestPatrolRecordBridge(mContext, this, selectTabOneIndex, selectTabThreeIndex, selectTabFourIndex, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || PatrolRecordList == null || PatrolRecordList.size() <= 0) {
            return 0;
        }
        return PatrolRecordList.get(PatrolRecordList.size() - 1).getRecordId();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore() {
        if (selectTabTwoIndex == 0) {
            requestroadListData();

        } else if (selectTabTwoIndex == 1) {
            requestBridgeListData();
        }
    }

    @Override
    public void onRefresh() {
        if (selectTabTwoIndex == 0) {
            requestroadListData();

        } else if (selectTabTwoIndex == 1) {
            requestBridgeListData();
        }
    }

    @Override
    public void Clickstate(PatrolRecord patrolRecord, int position) {
        Intent intent = new Intent(mContext, PointsModifyActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("patrolrecord", patrolRecord);
        startActivityForResult(intent, REQUEST_ACTIVITY);
    }

    @Override
    public void Clickpoints(PatrolRecord patrolRecord) {
        MunicipalRequest.requestPointsPatrolRecord(mContext, this, patrolRecord);
    }

    @Override
    public void success(String reqUrl, Object result) {
        if (reqUrl.equals(Contants.POINTS_PATROL_RECORD)) {
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(mContext, dataHandler);
        } else if (reqUrl.equals(Contants.PATROL_RECORD_BRIDGE)) {
            PatrolRecordBridgeBean patrolRecordBridgeBean = (PatrolRecordBridgeBean) result;
            patrolRecordBridgeBean.checkResult(mContext, dataHandler);
        } else if (reqUrl.equals(Contants.PATROL_RECORD_ROAD)) {
            PatrolRecordRoadBean patrolRecordRoadBean = (PatrolRecordRoadBean) result;
            patrolRecordRoadBean.checkResult(mContext, dataHandler);
        }
    }

    @Override
    public void fail() {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        } else if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void lazyLoad() {

    }

    private BaseHandler dataHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            if (baseBean instanceof PatrolRecordRoadBean || baseBean instanceof PatrolRecordBridgeBean) {
                List<PatrolRecord> list = null;
                if (baseBean instanceof PatrolRecordRoadBean) {
                    PatrolRecordRoadBean patrolRecordRoadBean = (PatrolRecordRoadBean) baseBean;
                    list = patrolRecordRoadBean.transformPatrolRecord();
                } else if (baseBean instanceof PatrolRecordBridgeBean) {
                    PatrolRecordBridgeBean patrolRecordBridgeBean = (PatrolRecordBridgeBean) baseBean;
                    list = patrolRecordBridgeBean.transformPatrolRecord();
                }

                if (swipeToLoadLayout.isRefreshing()) {
                    PatrolRecordList = list;
                } else {
                    PatrolRecordList.addAll(list);
                }
                if (swipeToLoadLayout.isRefreshing()) {
                    swipeToLoadLayout.setRefreshing(false);
                } else if (swipeToLoadLayout.isLoadingMore()) {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                monitorAdapter.setNotifyChanged(PatrolRecordList);
                Toast.makeText(mContext, "获取列表数据成功", Toast.LENGTH_LONG).show();
            } else if(baseBean instanceof BaseBean) {
                swipeToLoadLayout.setRefreshing(true);
            }
        }

        @Override
        public void doError(int result, String message) {
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            Toast.makeText(mContext, "获取数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACTIVITY:
                if (resultCode == mContext.RESULT_OK) {
                    swipeToLoadLayout.setRefreshing(true);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}


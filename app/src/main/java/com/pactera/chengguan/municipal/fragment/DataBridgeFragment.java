package com.pactera.chengguan.municipal.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.BasicBridgeInfoBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.Bridge;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据(桥梁)
 * Created by lijun on 2016/4/5.
 */
public class DataBridgeFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, RequestListener {
    private List<String> tab_one = new ArrayList<String>();
    private List<String> tab_two = new ArrayList<String>();
    private List<String> tab_three = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private int selectTabTwoIndex = 0;          //tab_two
    private int selectTabThreeIndex = 0;        //tab_three
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<Bridge> bridgeList = new ArrayList<Bridge>();        //桥梁数据集合
    private BridgeAdapter bridgeAdapter;
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_bridge, inflater);
        return view;

    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        transition();
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
        popupViews.add(one_View);
        popupViews.add(two_View);
        popupViews.add(three_View);
        //init dropdownview
        dropDownMenu.setDropDownMenu(headers, popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
                selectTabOneIndex = MunicipalCache.sectionList.get(position).getId();
                dropDownMenu.setTabText(tab_one.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        two_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_two_Adapter.setCheckItem(position);
                selectTabTwoIndex = MunicipalCache.levelList.get(position).getId();
                dropDownMenu.setTabText(tab_two.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                selectTabThreeIndex = MunicipalCache.bridgeList.get(position).getId();
                dropDownMenu.setTabText(tab_three.get(position));
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        selectTabOneIndex = MunicipalCache.sectionList.get(0).getId();
        selectTabTwoIndex = MunicipalCache.levelList.get(0).getId();
        selectTabThreeIndex = MunicipalCache.bridgeList.get(0).getId();
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });


    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.fragment_bridge_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        swipeTarget.setDivider(new ColorDrawable(Color.TRANSPARENT));
        swipeTarget.setDividerHeight(DensityUtils.dp2px(mContext, 10));
        bridgeAdapter = new BridgeAdapter(mContext, bridgeList);
        swipeTarget.setAdapter(bridgeAdapter);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    /**
     * 取出刷选
     */
    private void transition() {
        headers.add(MunicipalCache.sectionList.get(0).getName());
        headers.add(MunicipalCache.levelList.get(0).getName());
        headers.add(MunicipalCache.bridgeList.get(0).getName());
        for (MunicipalCache.DataObject dataObject : MunicipalCache.sectionList) {
            tab_one.add(dataObject.getName());
        }
        for (MunicipalCache.DataObject dataObject : MunicipalCache.levelList) {
            tab_two.add(dataObject.getName());
        }
        for (MunicipalCache.DataObject dataObject : MunicipalCache.bridgeList) {
            tab_three.add(dataObject.getName());
        }

    }


    /**
     * 道路列表请求
     */
    private void requestBridgeListData() {
        MunicipalRequest.requestBasicInfo(mContext, this, MunicipalContants.BASIC_BRIDGE_ID, selectTabOneIndex, selectTabTwoIndex, selectTabThreeIndex, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || bridgeList == null || bridgeList.size() <= 0) {
            return 0;
        }
        return bridgeList.get(bridgeList.size() - 1).getId();
    }

    @Override
    public void success(String reqUrl, Object result) {
        BasicBridgeInfoBean basicBridgeInfoBean = (BasicBridgeInfoBean) result;
        basicBridgeInfoBean.checkResult(mContext, dataBrideHandler);
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

    @Override
    public void onRefresh() {
        requestBridgeListData();
    }

    @Override
    public void onLoadMore() {
        requestBridgeListData();
    }

    private BaseHandler dataBrideHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            BasicBridgeInfoBean basicBridgeInfoBean = (BasicBridgeInfoBean) baseBean;
            List<Bridge> list = basicBridgeInfoBean.transformDatas();
            if (swipeToLoadLayout.isRefreshing()) {
                bridgeList = list;
            } else {
                bridgeList.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            bridgeAdapter.setNotifyChanged(bridgeList);
            Toast.makeText(mContext, "获取列表数据成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            Toast.makeText(mContext, "获取列表数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 桥梁适配器
     */
    class BridgeAdapter extends BaseAdapter {
        private Context mContext;
        private List<Bridge> bridgeList;

        public BridgeAdapter(Context context, List<Bridge> bridgeList) {
            mContext = context;
            this.bridgeList = bridgeList;
        }

        @Override
        public int getCount() {
            return bridgeList.size();
        }

        @Override
        public Object getItem(int position) {
            return bridgeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Bridge bridge = bridgeList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_bride, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(bridge.getName());
            holder.tvBride.setText(bridge.getName());
            for (MunicipalCache.DataObject dataObject : MunicipalCache.levelList) {
                if (bridge.getLevel() == dataObject.getId()) {
                    holder.tvLevel.setText("养护等级: " + dataObject.getName());
                    break;
                }
            }
            return convertView;
        }

        public void setNotifyChanged(List<Bridge> bridgeList) {
            this.bridgeList = bridgeList;
            notifyDataSetChanged();
        }

        class ViewHolder {
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_bride)
            TextView tvBride;
            @Bind(R.id.tv_level)
            TextView tvLevel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

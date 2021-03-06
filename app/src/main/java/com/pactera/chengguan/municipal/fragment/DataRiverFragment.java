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
import com.pactera.chengguan.municipal.bean.municipal.BasicRailingInfoBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.Railing;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据(河道栏杆)
 * Created by lijun on 2016/4/5.
 */
public class DataRiverFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener,RequestListener {
    private List<String> tab_one = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private RiverAdapter riverAdapter;
    private int selectTabOneIndex = 0;          //tab_one
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<Railing> railingList = new ArrayList<Railing>();        //栏杆数据集合
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_river, inflater);
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
        popupViews.add(one_View);
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
        selectTabOneIndex = MunicipalCache.sectionList.get(0).getId();
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.fragment_river_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        swipeTarget.setDivider(new ColorDrawable(Color.TRANSPARENT));
        swipeTarget.setDividerHeight(DensityUtils.dp2px(mContext, 10));
        riverAdapter = new RiverAdapter(mContext, railingList);
        swipeTarget.setAdapter(riverAdapter);
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
        for (MunicipalCache.DataObject dataObject : MunicipalCache.sectionList) {
            tab_one.add(dataObject.getName());
        }

    }

    /**
     * 道路列表请求
     */
    private void requestRiverListData() {
        MunicipalRequest.requestBasicInfo(mContext, this, MunicipalContants.BASIC_RAILING_ID, selectTabOneIndex, 0, 0, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || railingList == null || railingList.size() <= 0) {
            return 0;
        }
        return railingList.get(railingList.size() - 1).getId();
    }

    @Override
    public void success(String reqUrl, Object result) {
        BasicRailingInfoBean basicRailingInfoBean = (BasicRailingInfoBean) result;
        basicRailingInfoBean.checkResult(mContext, dataRiverHandler);
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
        requestRiverListData();
    }

    @Override
    public void onLoadMore() {
        requestRiverListData();
    }

    private BaseHandler dataRiverHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            BasicRailingInfoBean basicRailingInfoBean = (BasicRailingInfoBean) baseBean;
            List<Railing> list = basicRailingInfoBean.transformDatas();
            if (swipeToLoadLayout.isRefreshing()) {
                railingList = list;
            } else {
                railingList.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            riverAdapter.setNotifyChanged(railingList);
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

    /**
     * 河道栏杆适配器
     */
    class RiverAdapter extends BaseAdapter {
        private Context mContext;
        private List<Railing> railingList;

        public RiverAdapter(Context context, List<Railing> railingList) {
            mContext = context;
            this.railingList = railingList;
        }

        @Override
        public int getCount() {
            return railingList.size();
        }

        @Override
        public Object getItem(int position) {
            return railingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Railing railing = railingList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_river, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvNumber.setText(railing.getNumber());
            holder.tvName.setText(railing.getName());
            holder.tvStartPos.setText("起点: " + railing.getStartPos());
            holder.tvLength.setText("长度: " + railing.getLength());
            holder.tvBuildUnit.setText("建设单位: " + railing.getUnit());
            return convertView;
        }

        public void setNotifyChanged(List<Railing> railingList) {
            this.railingList = railingList;
            notifyDataSetChanged();
        }


        class ViewHolder {
            @Bind(R.id.tv_number)
            TextView tvNumber;
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_startPos)
            TextView tvStartPos;
            @Bind(R.id.tv_length)
            TextView tvLength;
            @Bind(R.id.tv_buildUnit)
            TextView tvBuildUnit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

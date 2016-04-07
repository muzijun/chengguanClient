package com.pactera.chengguan.fragment;

import android.content.Context;
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
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.BasicSewerInfoBean;
import com.pactera.chengguan.config.MunicipalCache;
import com.pactera.chengguan.config.MunicipalContants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.Sewer;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据(雨水管道)
 * Created by lijun on 2016/4/5.
 */
public class DataChannelFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener,RequestListener {
    private List<String> tab_one = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private ChannelAdapter channelAdapter;
    private int selectTabOneIndex = 0;          //tab_one
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<Sewer> sewerList = new ArrayList<Sewer>();        //雨水管道数据集合
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_channel, inflater);
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
                R.layout.fragment_bridge_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        channelAdapter=new ChannelAdapter(mContext, sewerList);
        swipeTarget.setAdapter(channelAdapter);
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
    private void requestChannelListData() {
        MunicipalRequest.requestBasicInfo(mContext, this, MunicipalContants.BASIC_SEWER_ID, selectTabOneIndex, 0, 0, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || sewerList == null || sewerList.size() <= 0) {
            return 0;
        }
        return sewerList.get(sewerList.size() - 1).getId();
    }

    @Override
    public void success(String reqUrl, Object result) {
        BasicSewerInfoBean basicSewerInfoBean = (BasicSewerInfoBean) result;
        basicSewerInfoBean.checkResult(mContext, dataSewerHandler);
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
        requestChannelListData();
    }

    @Override
    public void onLoadMore() {
        requestChannelListData();
    }

    private BaseHandler dataSewerHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            BasicSewerInfoBean basicSewerInfoBean = (BasicSewerInfoBean) baseBean;
            List<Sewer> list = basicSewerInfoBean.transformDatas();
            if (swipeToLoadLayout.isRefreshing()) {
                sewerList = list;
            } else {
                sewerList.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            channelAdapter.setNotifyChanged(sewerList);
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
     * 雨水管道适配器
     */
    class ChannelAdapter extends BaseAdapter {
        private Context mContext;
        private List<Sewer> sewerList;

        public ChannelAdapter(Context context, List<Sewer> sewerList) {
            mContext = context;
            this.sewerList = sewerList;
        }

        @Override
        public int getCount() {
            return sewerList.size();
        }

        @Override
        public Object getItem(int position) {
            return sewerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Sewer sewer = sewerList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_sewer, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvSewer.setText(sewer.getPosition());
            holder.tvLength.setText("长度 (双向) :" + sewer.getLength());
            return convertView;

        }

        public void setNotifyChanged(List<Sewer> sewerList) {
            this.sewerList = sewerList;
            notifyDataSetChanged();
        }


    }


    class ViewHolder {
        @Bind(R.id.tv_sewer)
        TextView tvSewer;
        @Bind(R.id.tv_length)
        TextView tvLength;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

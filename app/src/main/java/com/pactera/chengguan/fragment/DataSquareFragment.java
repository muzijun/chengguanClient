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
import com.pactera.chengguan.bean.municipal.BasicPumpInfoBean;
import com.pactera.chengguan.bean.municipal.BasicSquareInfoBean;
import com.pactera.chengguan.config.MunicipalCache;
import com.pactera.chengguan.config.MunicipalContants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.Square;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据(广场)
 * Created by lijun on 2016/4/5.
 */
public class DataSquareFragment extends BaseFragment  implements OnRefreshListener, OnLoadMoreListener,RequestListener {

    private List<String> tab_one = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<Square> squareList = new ArrayList<Square>();        //广场数据集合
    private SquareAdapter squareAdapter;
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_square, inflater);
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
                R.layout.fragment_square_content, null);
        ListView swipeTarget = ButterKnife.findById(view, R.id.swipe_target);
        squareAdapter = new SquareAdapter(mContext, squareList);
        swipeTarget.setAdapter(squareAdapter);
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
    private void requestSquareListData() {
        MunicipalRequest.requestBasicInfo(mContext, this, MunicipalContants.BASIC_SQUARE_ID, selectTabOneIndex, 0, 0, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || squareList == null || squareList.size() <= 0) {
            return 0;
        }
        return squareList.get(squareList.size() - 1).getId();
    }

    @Override
    public void success(String reqUrl, Object result) {
        BasicPumpInfoBean basicPumpInfoBean = (BasicPumpInfoBean) result;
        basicPumpInfoBean.checkResult(mContext, dataPumpHandler);
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
        requestSquareListData();
    }

    @Override
    public void onLoadMore() {
        requestSquareListData();
    }

    private BaseHandler dataPumpHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            BasicSquareInfoBean basicSquareInfoBean = (BasicSquareInfoBean) baseBean;
            List<Square> list = basicSquareInfoBean.transformDatas();
            if (swipeToLoadLayout.isRefreshing()) {
                squareList = list;
            } else {
                squareList.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            squareAdapter.setNotifyChanged(squareList);
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
     * 广场适配器
     */
    class SquareAdapter extends BaseAdapter {
        private Context mContext;
        private List<Square> squareList;

        public SquareAdapter(Context context, List<Square> squareList) {
            mContext = context;
            this.squareList = squareList;
        }

        @Override
        public int getCount() {
            return squareList.size();
        }

        @Override
        public Object getItem(int position) {
            return squareList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Square square = squareList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_square, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(square.getName());
            holder.tvPosition.setText(square.getPosition());
            holder.tvArea.setText("面积：" + square.getArea());
            holder.tvDuty.setText("责任主体: " + square.getDuty());
            holder.tvUnit.setText("建设单位：" + square.getUnit());
            return convertView;
        }

        public void setNotifyChanged(List<Square> squareList) {
            this.squareList = squareList;
            notifyDataSetChanged();
        }

        class ViewHolder {
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_position)
            TextView tvPosition;
            @Bind(R.id.tv_area)
            TextView tvArea;
            @Bind(R.id.tv_duty)
            TextView tvDuty;
            @Bind(R.id.tv_unit)
            TextView tvUnit;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}




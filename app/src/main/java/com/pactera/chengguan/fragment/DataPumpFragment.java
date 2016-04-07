package com.pactera.chengguan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.model.municipal.PumpStation;
import com.pactera.chengguan.model.municipal.Sewer;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 基础数据(泵站)
 * Created by lijun on 2016/4/5.
 */
public class DataPumpFragment extends BaseFragment {
    private List<String> tab_one = new ArrayList<String>();
    private List<String> tab_two = new ArrayList<String>();
    private List<String> tab_three = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<String> headers = new ArrayList<String>();
    private GirdDropDownAdapter tab_one_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private List<Sewer> sewerList = new ArrayList<Sewer>();        //雨水管道数据集合
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_data_pump, inflater);
        return view;

    }

    @Override
    public void initContentView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }

    /**
     * 泵站适配器
     */
    class PumpAdapter extends BaseAdapter {
        private Context mContext;
        private List<PumpStation> pumpStationList;

        public PumpAdapter(Context context, List<PumpStation> pumpStationList) {
            mContext = context;
            this.pumpStationList = pumpStationList;
        }

        @Override
        public int getCount() {
            return pumpStationList.size();
        }

        @Override
        public Object getItem(int position) {
            return pumpStationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return convertView;
        }


    }


}

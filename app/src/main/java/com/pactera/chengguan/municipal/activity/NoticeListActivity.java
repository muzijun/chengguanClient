package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.municipal.adapter.NoticeListAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.PageNoticeBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.PageNotice;
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
 * 通知列表
 * Created by lijun on 2016/4/20.
 */
public class NoticeListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private static final String[] tab_one = {"全部", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private static final String[] tab_two = {"时间排序", "状态排序"};
    private String headers[] = {"全部", "时间排序"};
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private int selectTabTwoIndex = 0;          //tab_two
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private NoticeListAdapter noticeListAdapter;
    private List<PageNotice> pageNoticeList = new ArrayList<PageNotice>();        //通知列表数据集合
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dropdownmenu);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        title.setText("通知");
        addTitleView(lin);
        final ListView one_View = new ListView(this);
        tab_one_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_one));
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(this);
        tab_two_Adapter = new GirdDropDownAdapter(this, Arrays.asList(tab_two));
        two_View.setDividerHeight(0);
        two_View.setAdapter(tab_two_Adapter);
        popupViews.add(one_View);
        popupViews.add(two_View);
        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, createView());
        //add item click event
        one_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_one_Adapter.setCheckItem(position);
                selectTabOneIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_one[position]);
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
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_two[position]);
                dropDownMenu.setTabText(tab_two[position]);
                dropDownMenu.closeMenu();
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
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
        noticeListAdapter = new NoticeListAdapter(mContext, pageNoticeList);
        swipeTarget.setAdapter(noticeListAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }


    private void addTitleView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_add);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    /**
     * 发送通知列表请求
     */
    private void requestNoticeListData() {
        Integer month = null;
        if(selectTabOneIndex > 0){
            month = selectTabOneIndex;
        }
        MunicipalRequest.requestPageNoticeList(mContext, this, month, selectTabTwoIndex + 1, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private String getLastId() {
        if (swipeToLoadLayout.isRefreshing() || pageNoticeList == null || pageNoticeList.size() <= 0) {
            return "0";
        }
        return pageNoticeList.get(pageNoticeList.size() - 1).getNoticeMessageId();
    }

    @Override
    public void onRefresh() {
        requestNoticeListData();
    }

    @Override
    public void onLoadMore() {
        requestNoticeListData();
    }

    @Override
    public void success(String reqUrl, Object result) {
        PageNoticeBean pageNoticeBean = (PageNoticeBean) result;
        pageNoticeBean.checkResult(mContext, Handler);
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


    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            PageNoticeBean pageNoticeBean = (PageNoticeBean) baseBean;
            List<PageNotice> list = pageNoticeBean.transformToPageNoticeList();
            if (swipeToLoadLayout.isRefreshing()) {
                pageNoticeList = list;
            } else {
                pageNoticeList.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            noticeListAdapter.setNotifyChanged(pageNoticeList);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PageNotice pageNotice = pageNoticeList.get(position);
        //保存没有下发的
        if (pageNotice.getStatus().equals(PageNotice.NOTICE_NOT_SENT)) {
            Intent intent = new Intent(mContext, NewNoticeActivity.class);
            intent.putExtra("notice", pageNotice);
            startActivityForResult(intent, NewNoticeActivity.REQUEST_REFRESH);

        }
        //已下发的
        else if (pageNotice.getStatus().equals(PageNotice.NOTICE_SENT)) {
            Intent intent = new Intent(mContext, NoticeDetialsActivity.class);
            intent.putExtra("notice",pageNotice.getNoticeMessageId());
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                intent = new Intent(mContext, NewNoticeActivity.class);
                startActivityForResult(intent, NewNoticeActivity.REQUEST_REFRESH);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewNoticeActivity.REQUEST_REFRESH) {
            if (resultCode == mContext.RESULT_OK) {
                swipeToLoadLayout.setRefreshing(true);
            }
        }
    }
}

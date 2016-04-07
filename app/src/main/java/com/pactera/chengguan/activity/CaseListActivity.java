package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.CaseListAdapter;
import com.pactera.chengguan.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.adapter.ListDialogAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.CaseListBean;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ChenguanSwipeToLoadLayout;
import com.pactera.chengguan.view.dialog.ListDialog;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 案件详情
 * Created by lijun on 2016/3/9.
 */
public class CaseListActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener
        , AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener,RequestListener {
    private static final String[] tab_one = {"待办", "处理中", "办结"};
    private static final String[] tab_two = {"月度", "季度", "年度", "日常"};
    private static final String[] tab_three = {"一月", "二月", "三月", "四月", "五月", "六月"
            , "七月", "八月", "九月", "十月", "十一月", "十二月"};
    private static final String[] tab_four = {"时间排序", "超限排序"};
    private String headers[] = {"待办", "月度", "一月", "时间排序"};

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private GirdDropDownAdapter tab_four_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private int selectTabTwoIndex = 0;          //tab_two
    private int selectTabThreeIndex = 0;        //tab_three
    private int selectTabFourIndex = 0;         //tab_four
    private List<CaseInfo> caseInfoList = new ArrayList<>();        //案件数据集合
    private CaseListAdapter caseListAdapter;
    private static final int PAGE_COUNT = 10;   //单页显示数量

    private int requestStatus;
    public static final int STATUS_INIT = 0;       //初始化获取数据
    public static final int STATUS_REFRESH = 1;    //刷新
    public static final int STATUS_MORE = 2;       //获取更多
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;

    public static final int REQUEST_ACTIVITY_DETAIL = 1;
    public static final int REQUEST_ACTIVITY_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caselist);
        ButterKnife.bind(this);
        init();
        requestCaseListData(STATUS_INIT);
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
                selectTabOneIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_one[position]);
                dropDownMenu.setTabText(tab_one[position]);
                dropDownMenu.closeMenu();
                requestCaseListData(STATUS_REFRESH);
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
                requestCaseListData(STATUS_REFRESH);
            }
        });
        three_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_three_Adapter.setCheckItem(position);
                selectTabThreeIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_three[position]);
                dropDownMenu.setTabText(tab_three[position]);
                dropDownMenu.closeMenu();
                requestCaseListData(STATUS_REFRESH);
            }
        });
        four_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tab_four_Adapter.setCheckItem(position);
                selectTabFourIndex = position;
//                dropDownMenu.setTabText(position == 0 ? headers[0] : tab_four[position]);
                dropDownMenu.setTabText(tab_four[position]);
                dropDownMenu.closeMenu();
                requestCaseListData(STATUS_REFRESH);
            }
        });
    }

    private View createView() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.caselist_content, null);
        ListView swipeTarget = ButterKnife.findById(view,R.id.swipe_target);
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        caseListAdapter = new CaseListAdapter(mContext, caseInfoList);
        swipeTarget.setAdapter(caseListAdapter);
        swipeTarget.setOnItemClickListener(this);
        swipeTarget.setOnItemLongClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        requestCaseListData(STATUS_REFRESH);
    }

    @Override
    public void onLoadMore() {
        requestCaseListData(STATUS_MORE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CaseInfo caseInfo = caseInfoList.get(position);
        Intent intent;
        Bundle bundle = new Bundle();
        switch(caseInfo.getCaseStatus()){
            case CaseInfo.CASE_NEW:
                intent = new Intent(mContext, CaseDetialsActivity.class);
                bundle.putSerializable("case", caseInfo);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_ACTIVITY_DETAIL);
                break;
            case CaseInfo.CASE_PROCESS:
                intent = new Intent(mContext, CaseFinishActivity.class);
                bundle.putSerializable("case", caseInfo);
                bundle.putInt(CaseFinishActivity.STATE, 2);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_ACTIVITY_FINISH);
                break;
            case CaseInfo.CASE_CHECK:
                intent = new Intent(mContext, CaseFinishActivity.class);
                bundle.putSerializable("case", caseInfo);
                bundle.putInt(CaseFinishActivity.STATE, 3);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_ACTIVITY_FINISH);
                break;
            case CaseInfo.CASE_FINISH:
                intent = new Intent(mContext, CaseFinishActivity.class);
                bundle.putSerializable("case", caseInfo);
                bundle.putInt(CaseFinishActivity.STATE, 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String content[]={"测试1","测试2","测试3"};
        ListDialog listDialog=new ListDialog(mContext, R.style.dialog_dimenable,new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listDialog.show(new ListDialogAdapter(mContext,content));
        return true;
    }


    /**
     * 发送案件列表请求
     */
    private void requestCaseListData(int requestStatus){
        this.requestStatus = requestStatus;
        MunicipalRequest.requestCaseList(this, this, selectTabOneIndex+1, selectTabTwoIndex+1, selectTabThreeIndex+1
                , selectTabFourIndex+1, PAGE_COUNT, getLastId(requestStatus), requestStatus);
    }

    /**
     * 获取列表最后一项的id
     * @return
     */
    private int getLastId(int requestStatus){
        if(requestStatus == STATUS_REFRESH || caseInfoList == null || caseInfoList.size() <= 0){
            return 0;
        }
        return caseInfoList.get(caseInfoList.size()-1).getId();
    }

    @Override
    public void success(String reqUrl, Object result) {
        if(requestStatus == STATUS_REFRESH){
            swipeToLoadLayout.setRefreshing(false);
        }else if(requestStatus == STATUS_MORE){
            swipeToLoadLayout.setLoadingMore(false);
        }
        CaseListBean caseListBean = (CaseListBean)result;
        caseListBean.checkResult(this, caseListHandler);
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

    private BaseHandler caseListHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            CaseListBean caseListBean = (CaseListBean)baseBean;
            List<CaseInfo> list = caseListBean.transformCaseInfo();
            if(requestStatus == STATUS_REFRESH || requestStatus == STATUS_INIT){
                caseInfoList = list;
            }else{
                caseInfoList.addAll(list);
            }
            caseListAdapter.setNotifyChanged(caseInfoList);
            Toast.makeText(CaseListActivity.this, "获取列表数据成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(CaseListActivity.this, "获取列表数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_ACTIVITY_DETAIL:
                if(resultCode == CaseDetialsActivity.STATUS_ISSUE){
                    mHandler.sendEmptyMessage(0);
                }
                break;
            case REQUEST_ACTIVITY_FINISH:
                if(resultCode > 0) {
                    mHandler.sendEmptyMessage(0);
                }
                break;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            swipeToLoadLayout.setRefreshing(true);
        }
    };
}

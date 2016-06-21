package com.pactera.chengguan.municipal.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.activity.ReworkActivity;
import com.pactera.chengguan.municipal.activity.TaskFinishActivity;
import com.pactera.chengguan.municipal.activity.TaskProcessRecordActivity;
import com.pactera.chengguan.municipal.adapter.GirdDropDownAdapter;
import com.pactera.chengguan.municipal.adapter.ListDialogAdapter;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.TaskListBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.ListContact;
import com.pactera.chengguan.municipal.model.municipal.ListTask;
import com.pactera.chengguan.municipal.util.AppUtils;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;
import com.pactera.chengguan.municipal.view.dialog.ListDialog;
import com.yyydjk.library.DropDownMenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 任务单
 * Created by lijun on 2016/3/25.
 */
public class TaskListFragment extends BaseFragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener, RequestListener,AdapterView.OnItemLongClickListener {

    private static final String[] tab_one = {"待办", "处理中", "办结"};
    private List<String> tab_two = new ArrayList<String>();
    private static final String[] tab_three = {"全部", "一月", "二月", "三月", "四月", "五月", "六月"
            , "七月", "八月", "九月", "十月", "十一月", "十二月"};
    @Bind(R.id.titlbar)
    RelativeLayout titlbar;
    private List<String> headers = new ArrayList<String>();
    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private GirdDropDownAdapter tab_one_Adapter;
    private GirdDropDownAdapter tab_two_Adapter;
    private GirdDropDownAdapter tab_three_Adapter;
    private List<View> popupViews = new ArrayList<>();
    private int selectTabOneIndex = 0;          //tab_one
    private int selectTabTwoIndex = 0;          //tab_two
    private int selectTabThreeIndex = 0;        //tab_three
    private List<ListTask> listTasks = new ArrayList<ListTask>();        //任务单列表数据集合
    private static final int PAGE_COUNT = 10;   //单页显示数量
    private ChenguanSwipeToLoadLayout swipeToLoadLayout;
    private TaskListAdapter taskListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.base_dropdownmenu, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        transition();
        titlbar.setVisibility(View.GONE);
        final ListView one_View = new ListView(mContext);
        tab_one_Adapter = new GirdDropDownAdapter(mContext, Arrays.asList(tab_one));
        one_View.setDividerHeight(0);
        one_View.setAdapter(tab_one_Adapter);
        final ListView two_View = new ListView(mContext);
        tab_two_Adapter = new GirdDropDownAdapter(mContext, tab_two);
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
                selectTabTwoIndex = MunicipalCache.sectionList.get(position).getId();
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
        selectTabOneIndex=0;
        selectTabTwoIndex=MunicipalCache.sectionList.get(0).getId();
        selectTabThreeIndex=0;
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
        swipeToLoadLayout = (ChenguanSwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeTarget.setDivider(new ColorDrawable(Color.TRANSPARENT));
        swipeTarget.setDividerHeight(DensityUtils.dp2px(mContext, 10));
        swipeTarget.setOnItemClickListener(this);
        swipeTarget.setOnItemLongClickListener(this);
        taskListAdapter = new TaskListAdapter(mContext, listTasks);
        swipeTarget.setAdapter(taskListAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        return view;
    }

    /**
     * 取出刷选
     */
    private void transition() {
        headers.add("待办");
        headers.add(MunicipalCache.sectionList.get(0).getName());
        for (MunicipalCache.DataObject dataObject : MunicipalCache.sectionList) {
            tab_two.add(dataObject.getName());
        }
        headers.add("全部");

    }

    /**
     * 请求任务单列表
     *
     * @param month
     * @param option
     */
    public void requestPageTaskList(int sectionId, int month, int option) {
        Integer monthObj = null;
        if(month > 0){
            monthObj = month;
        }
        MunicipalRequest.requestPageTaskList(mContext, this, sectionId, monthObj, option, PAGE_COUNT, getLastId());
    }

    /**
     * 获取列表最后一项的id
     *
     * @return
     */
    private int getLastId() {
        if (swipeToLoadLayout.isRefreshing() || listTasks == null || listTasks.size() <= 0) {
            return 0;
        }
        return listTasks.get(listTasks.size() - 1).getWorkFormId();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ListTask listTask = listTasks.get(position);
        final ArrayList<String> listdatas = new ArrayList<String>();
        String status = listTask.getStatus();
        final ListDialog listDialog = new ListDialog(mContext, R.style.dialog_dimenable, new ListDialog.OnClickDialogListener() {
            @Override
            public void onClickItem(AdapterView<?> parent, View view, int position, long id) {
                final  Intent intent;
                if (listdatas.get(position).equals("通过")) {
                    MunicipalRequest.requestCloseTask(mContext, new RequestListener() {
                        @Override
                        public void success(String reqUrl, Object result) {
                            BaseBean baseBean = (BaseBean) result;
                            baseBean.checkResult(mContext, finishHandler);
                        }

                        @Override
                        public void fail() {
                            Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
                        }
                    }, listTask.getWorkFormId(), "");
                } else if (listdatas.get(position).equals("返工")) {
                    intent = new Intent(mContext, ReworkActivity.class);
                    intent.putExtra(ReworkActivity.STATUS_TASK_NAME, ReworkActivity.STATUS_TASK);
                    intent.putExtra("id", "" + id);
                    startActivityForResult(intent, Contants.TASK_REFRESH);
                }else if (listdatas.get(position).equals("流程日志")) {
                    intent = new Intent(mContext, TaskProcessRecordActivity.class);
                    intent.putExtra("id",listTask.getWorkFormId());
                    startActivity(intent);
                }
            }
        });
        ListDialogAdapter listDialogAdapter = new ListDialogAdapter(mContext, listdatas);
        //施工
        if (status.equals(ListContact.CONTACTFORM_STATUS_BUILDE)) {
            listdatas.add("流程日志");
            listDialog.show(listDialogAdapter);
        }
        //待验收
        else if (status.equals(ListContact.CONTACTFORM_STATUS_ACCEPT)) {
            listdatas.add("通过");
            listdatas.add("返工");
            listdatas.add("流程日志");
            listDialog.show(listDialogAdapter);
        } //办结
        else if (status.equals(ListContact.CONTACTFORM_STATUS_FINISH)) {
            listdatas.add("流程日志");
            listDialog.show(listDialogAdapter);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListTask listTask = listTasks.get(position);
        //新增
        if (listTask.getStatus().equals(ListTask.TASKFORM_STATUS_NEW)) {

        } else {
            Intent intent = new Intent(mContext, TaskFinishActivity.class);
            intent.putExtra("id", listTask.getWorkFormId());
            startActivityForResult(intent, Contants.TASK_REFRESH);
        }

    }


    @Override
    public void onLoadMore() {
        requestPageTaskList(selectTabTwoIndex, selectTabThreeIndex, selectTabOneIndex + 1);
    }

    @Override
    public void onRefresh() {
        requestPageTaskList(selectTabTwoIndex, selectTabThreeIndex, selectTabOneIndex + 1);
    }

    @Override
    public void success(String reqUrl, Object result) {
        TaskListBean taskListBean = (TaskListBean) result;
        taskListBean.checkResult(mContext, ListHandler);
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

    private BaseHandler ListHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            TaskListBean taskListBean = (TaskListBean) baseBean;
            List<ListTask> list = taskListBean.transformToTaskList();
            if (swipeToLoadLayout.isRefreshing()) {
                listTasks = list;
            } else {
                listTasks.addAll(list);
            }
            if (swipeToLoadLayout.isRefreshing()) {
                swipeToLoadLayout.setRefreshing(false);
            } else if (swipeToLoadLayout.isLoadingMore()) {
                swipeToLoadLayout.setLoadingMore(false);
            }
            taskListAdapter.setNotifyChanged(listTasks);
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

    class TaskListAdapter extends BaseAdapter {

        private Context mContext;
        private List<ListTask> listTasks;

        public TaskListAdapter(Context context, List<ListTask> listTasks) {
            mContext = context;
            this.listTasks = listTasks;
        }

        @Override
        public int getCount() {
            return listTasks.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListTask listTask = listTasks.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tasklist, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvDate.setText(listTask.getFinishDate());
            holder.tvContent.setText(listTask.getDescription());
            holder.tvUnit.setText(AppUtils.getsectionname(listTask.getSectionId()));
            if (listTask.getIssuedDate() == null) {
                holder.tvReportDate.setVisibility(View.GONE);
            } else {
                holder.tvReportDate.setVisibility(View.VISIBLE);
                holder.tvReportDate.setText(listTask.getIssuedDate());
            }


            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.image_state)
            ImageView imageState;
            @Bind(R.id.tv_date)
            TextView tvDate;
            @Bind(R.id.tv_content)
            TextView tvContent;
            @Bind(R.id.tv_unit)
            TextView tvUnit;
            @Bind(R.id.tv_reportDate)
            TextView tvReportDate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        public void setNotifyChanged(List<ListTask> listTasks) {
            this.listTasks = listTasks;
            notifyDataSetChanged();
        }
    }

    //通过
    private BaseHandler finishHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "成功!", Toast.LENGTH_LONG).show();
            swipeToLoadLayout.setRefreshing(true);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "结案失败:" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==Contants.TASK_REFRESH) {
            if (resultCode ==Contants.TASK_REFRESH) {
                swipeToLoadLayout.setRefreshing(true);
            }
        }
    }
}

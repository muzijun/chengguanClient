package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.fragment.TaskListContactFragment;
import com.pactera.chengguan.municipal.fragment.TaskListFragment;
import com.pactera.chengguan.municipal.model.PushData;
import com.pactera.chengguan.municipal.view.PopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工作联系单列表
 * Created by lijun on 2016/3/24.
 */
public class ContactSheetActivity extends BaseActivity implements View.OnClickListener, PopMenu.OnItemClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.content)
    FrameLayout content;
    private PopMenu popMenu;
    //新建任务单
    private String[] popmenu_new = {"任务单"};
    //任务单
    private String[] popmenu = {"新建", "任务联系单"};
    private TaskListContactFragment taskListContactFragment;
    private TaskListFragment taskListFragment;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private ImageView imageview_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_sheet);
        ButterKnife.bind(this);
        init();
        pushIntent();
    }

    private void init() {
        title.setText("任务联系单");
        fragmentManager = getSupportFragmentManager();
        addView(lin);
        setSelection(0);

    }


    private void addView(LinearLayout linearLayout) {
        imageview_add = new ImageView(mContext);
        imageview_add.setId(R.id.top);
        imageview_add.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageview_add.setImageResource(R.mipmap.icon_menu);
        imageview_add.setOnClickListener(this);
        linearLayout.addView(imageview_add);

    }

    /**
     * 收到推送逻辑跳转
     */
    private void pushIntent() {
        PushData push = (PushData) getIntent().getSerializableExtra(Contants.PUSH_JSON);
        if (push != null) {
            String type = push.getType();
            if (type.equals(Contants.WORK_CONTACT_FORM)) {
                setSelection(0);
            } else if (type.equals(Contants.WORK_TASK_FORM)) {
                setSelection(1);
            }


        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    public void setSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                //任务联系单
                title.setText("任务联系单");
                if (taskListContactFragment == null) {
                    taskListContactFragment = new TaskListContactFragment();
                    transaction.add(R.id.content, taskListContactFragment);
                } else {
                    transaction.show(taskListContactFragment);
                }
                setstatue(index);
                break;
            case 1:
                //任务单
                title.setText("任务单");
                if (taskListFragment == null) {
                    taskListFragment = new TaskListFragment();
                    transaction.add(R.id.content, taskListFragment);
                } else {
                    transaction.show(taskListFragment);
                }
                setstatue(index);
                break;

        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */

    private void hideFragments(FragmentTransaction transaction) {
        if (taskListContactFragment != null) {
            transaction.hide(taskListContactFragment);
        }
        if (taskListFragment != null) {
            transaction.hide(taskListFragment);
        }


    }

    //设置状态
    private void setstatue(int position) {
        switch (position) {
            case 0:
                popMenu = new PopMenu(this);
                popMenu.addItems(popmenu_new);
                popMenu.setOnItemClickListener(this);
                break;
            case 1:
                popMenu = new PopMenu(this);
                popMenu.addItems(popmenu);
                popMenu.setOnItemClickListener(this);
                break;
        }
    }

    @Override
    public void onItemClick(int index) {
        //任务单
        if (taskListContactFragment.isHidden()) {
            switch (index) {
                case 0:
                    //新建任务单
                    Intent intent = new Intent(mContext, AddTaskActivity.class);
                    taskListFragment.startActivityForResult(intent, Contants.TASK_REFRESH);
                    break;
                case 1:
                    //任务联系单
                    setSelection(0);
                    break;

            }
        } else {
            switch (index) {
                case 0:
                    //任务单
                    setSelection(1);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
        }
    }

}

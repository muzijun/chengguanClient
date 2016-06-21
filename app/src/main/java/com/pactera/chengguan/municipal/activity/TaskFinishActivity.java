package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.adapter.FileDownAdapter;
import com.pactera.chengguan.municipal.adapter.ReworkAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.TaskDetailBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.FileDown;
import com.pactera.chengguan.municipal.model.Refuseparam;
import com.pactera.chengguan.municipal.model.municipal.ListTask;
import com.pactera.chengguan.municipal.model.municipal.TaskDetail;
import com.pactera.chengguan.municipal.util.AppUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ImageItemCycle;
import com.pactera.chengguan.municipal.view.NoScrollListView;
import com.pactera.chengguan.municipal.view.PopMenu;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 任务单详情
 * Created by lijun on 2016/5/13.
 */
public class TaskFinishActivity extends BaseActivity implements View.OnClickListener, PopMenu.OnItemClickListener, RequestListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.imagecycle)
    ImageItemCycle imagecycle;
    @Bind(R.id.protime)
    TextView protime;
    @Bind(R.id.tast_site_content)
    TextView tastSiteContent;
    @Bind(R.id.noscrolllistview_file)
    NoScrollListView noscrolllistviewFile;
    @Bind(R.id.reportingunitname)
    TextView reportingunitname;
    @Bind(R.id.res)
    TextView res;
    @Bind(R.id.resname)
    TextView resname;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.imagecycle_end)
    ImageItemCycle imagecycleEnd;
    @Bind(R.id.finish_pic)
    LinearLayout finishPic;
    @Bind(R.id.task_finish_cotent)
    TextView taskFinishCotent;
    @Bind(R.id.finish_pic_content)
    LinearLayout finishPicContent;
    @Bind(R.id.noscrolllistview_rework)
    NoScrollListView noscrolllistviewRework;
    @Bind(R.id.lin_rework_opinion)
    LinearLayout linReworkOpinion;
    private PopMenu popMenu;
    private int id;
    private String state = "";
    //施工
    private String[] option_build = {"流程日志"};
    //待验收
    private String[] option_accept = {"通过", "返工", "流程日志"};
    //办结
    private String[] option_finish = {"流程日志"};
    private static final int REQUEST_ACTIVITY_REWORK = 3;   //返工
    private TaskDetail Detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_finish);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        title.setText("任务单");
        addRightMenuView(lin);
        id = getIntent().getIntExtra("id", 0);
        popMenu = new PopMenu(mContext);
        popMenu.setOnItemClickListener(this);
        MunicipalRequest.requestTaskDetail(mContext, this, id);
    }

    /**
     * 添加右选项Right
     *
     * @param linearLayout
     */
    private void addRightMenuView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
        }
    }

    @Override
    public void onItemClick(int index) {
        Intent intent;
        //施工
        if (state.equals(ListTask.TASKFORM_STATUS_BUILDE)) {
            switch (index) {
                case 0://流程日志
                    intent = new Intent(mContext, TaskProcessRecordActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
            }
        }
        //待验收
        else if (state.equals(ListTask.TASKFORM_STATUS_ACCEPT)) {
            switch (index) {
                case 0://通过
                    MunicipalRequest.requestCloseTask(mContext, this, id, "");
                    break;
                case 1://返工
                    intent = new Intent(mContext, ReworkActivity.class);
                    intent.putExtra(ReworkActivity.STATUS_TASK_NAME, ReworkActivity.STATUS_TASK);
                    intent.putExtra("id", "" + id);
                    startActivityForResult(intent, Contants.TASK_REFRESH);
                    break;
                case 2://流程日志
                    intent = new Intent(mContext, TaskProcessRecordActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
            }
        }
        //办结
        else if (state.equals(ListTask.TASKFORM_STATUS_FINISH)) {
            switch (index) {
                case 0://流程日志
                    intent = new Intent(mContext, TaskProcessRecordActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 根据状态显示内容
     */
    private void setstate(TaskDetail taskDetail) {
        String STATE = taskDetail.getStatus();
        state = STATE;
        //施工
        if (STATE.equals(ListTask.TASKFORM_STATUS_BUILDE)) {
            popMenu.addItems(option_build);
            if (taskDetail.getAfterTreatment().size() == 0) {
                finishPic.setVisibility(View.GONE);
                finishPicContent.setVisibility(View.GONE);
                linReworkOpinion.setVisibility(View.GONE);
            } else {
                finishPic.setVisibility(View.VISIBLE);
                finishPicContent.setVisibility(View.VISIBLE);
                if (taskDetail.getRefuseLogList().size() == 0) {
                    linReworkOpinion.setVisibility(View.GONE);
                } else {
                    linReworkOpinion.setVisibility(View.VISIBLE);
                }
            }
        }
        //待验收
        else if (STATE.equals(ListTask.TASKFORM_STATUS_ACCEPT)) {
            popMenu.addItems(option_accept);
            finishPic.setVisibility(View.VISIBLE);
            finishPicContent.setVisibility(View.VISIBLE);
            if (taskDetail.getRefuseLogList().size() == 0) {
                linReworkOpinion.setVisibility(View.GONE);
            } else {
                linReworkOpinion.setVisibility(View.VISIBLE);
            }
        }
        //办结
        else if (STATE.equals(ListTask.TASKFORM_STATUS_FINISH)) {
            popMenu.addItems(option_finish);
            finishPic.setVisibility(View.VISIBLE);
            finishPicContent.setVisibility(View.VISIBLE);
            linReworkOpinion.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        if (result instanceof TaskDetailBean) {
            TaskDetailBean taskDetailBean = (TaskDetailBean) result;
            taskDetailBean.checkResult(mContext, Handler);
        } else {
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(mContext, finishHandler);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {


        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(getApplicationContext()).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }

        @Override
        public void onImageClick(int postion, View imageView) {
            if (Detail != null) {
                Intent intent = new Intent(mContext, PictureCompareActivity.class);
                intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_BEFORE, Detail.getBeforeTreatment());
                intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_AFTER, Detail.getAfterTreatment());
                startActivity(intent);
            }
        }
    };


    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            TaskDetailBean taskDetailBean = (TaskDetailBean) baseBean;
            if (taskDetailBean.datas != null) {
                TaskDetail taskDetail = taskDetailBean.datas.transformToTaskDetail();
                Detail=taskDetail;
                tastSiteContent.setText(taskDetail.getDescription());
                protime.setText(taskDetail.getFinishDate());
                reportingunitname.setText(AppUtils.getsectionname(taskDetail.getSectionId()));
                time.setText(taskDetail.getIssuedDate());
                resname.setText(taskDetail.getSourcesFund());
                imagecycle.setImageResources(mContext, taskDetail.getBeforeTreatment(), mCycleViewListener, ChengApplication.instance.municipalApplication.access_token, true);
                imagecycleEnd.setImageResources(mContext, taskDetail.getAfterTreatment(), mCycleViewListener, ChengApplication.instance.municipalApplication.access_token, true);
                taskFinishCotent.setText(taskDetail.getProcessContext());
                ArrayList<TaskDetail.FileData> attachmentList = taskDetail.getAttachmentFileList();
                ArrayList<FileDown> fileDowns = new ArrayList<FileDown>();
                for (TaskDetail.FileData fileData : attachmentList) {
                    FileDown fileDown = new FileDown();
                    fileDown.setPhotoName(fileData.getFileName());
                    fileDown.setPhotoPath(fileData.getFilePath());
                    fileDowns.add(fileDown);
                }
                ArrayList<Refuseparam> refuses = new ArrayList<Refuseparam>();
                for (TaskDetail.RefuseLog refuseLog : taskDetail.getRefuseLogList()) {
                    Refuseparam refuse = new Refuseparam();
                    refuse.setCreateTime(refuseLog.getCreateTime());
                    refuse.setRefuseOpinion(refuseLog.getRefuseOpinion());
                    refuses.add(refuse);
                }
                noscrolllistviewRework.setAdapter(new ReworkAdapter(mContext, refuses));
                noscrolllistviewFile.setAdapter(new FileDownAdapter(fileDowns, mContext));
                setstate(taskDetail);
            }

        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取数据失败:" + message, Toast.LENGTH_LONG).show();
            finish();
        }
    };
    //通过
    private BaseHandler finishHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "成功!", Toast.LENGTH_LONG).show();
            setResult(Contants.TASK_REFRESH);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "结案失败:" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.TASK_REFRESH) {
            if (resultCode ==Contants.TASK_REFRESH) {
                setResult(Contants.TASK_REFRESH);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imagecycle.pushImageCycle(true);
        imagecycleEnd.pushImageCycle(true);
    }
}

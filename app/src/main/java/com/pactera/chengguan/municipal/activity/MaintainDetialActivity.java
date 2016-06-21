package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.pactera.chengguan.municipal.adapter.ReworkAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.IssueDetailBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.Refuseparam;
import com.pactera.chengguan.municipal.model.municipal.IssueDetail;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ImageItemCycle;
import com.pactera.chengguan.municipal.view.NoScrollListView;
import com.pactera.chengguan.municipal.view.PopMenu;
import com.pactera.chengguan.municipal.view.dialog.CommonDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 养护日志详细
 * Created by lijun on 2016/4/18.
 */
public class MaintainDetialActivity extends BaseActivity implements View.OnClickListener
        , PopMenu.OnItemClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.imagecycle)
    ImageItemCycle imagecycle;
    @Bind(R.id.tv_describe)
    TextView tvDescribe;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.imagecycle_end)
    ImageItemCycle imagecycleEnd;
    @Bind(R.id.lin_imagecycle_end)
    LinearLayout linImagecycleEnd;
    @Bind(R.id.noscrolllistview)
    NoScrollListView noscrolllistview;
    @Bind(R.id.lin_rework)
    LinearLayout linRework;
    @Bind(R.id.tv_operation)
    TextView tvOperation;

    private String[] state_one = {"通过"};
    private String[] state_two = {"通过","养护对比"};
    private String[] state_three = {"通过","返工","养护对比"};
    private String[] state_four = {"通过", "不通过"};
    private PopMenu popMenu;
    private int state;
    private IssueDetail mIssueDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        state = getIntent().getIntExtra("state", 0);
        String issueId = getIntent().getStringExtra("id");
        requestIssueDetail(issueId);
        if(state != 3) {
            // 初始化弹出菜单
            popMenu = new PopMenu(this);
            popMenu.setOnItemClickListener(this);
            addTitleView(lin);
        }
        title.setText("养护日志");
        setstate();
    }

    private void addTitleView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);
    }

    /**
     * 根据状态设置view
     */
    private void setstate() {
        if (state == 4){
            popMenu.addItems(state_four);
        } else if (state == 0) {
            popMenu.addItems(state_one);
        } else if (state == 1) {
            popMenu.addItems(state_two);
        } else if (state == 2) {
            popMenu.addItems(state_three);
        }
    }

    /**
     * 刷新配置界面数据
     */
    private void refreshView(){
        imagecycle.setImageResources(mContext, mIssueDetail.getBeforeTreatment(), mCycleViewListener
                , ChengApplication.instance.municipalApplication.access_token, true);
        tvDescribe.setText(mIssueDetail.getIssueDescribe());
        tvAddress.setText(mIssueDetail.getLocation());
        if(mIssueDetail.getAfterTreatment() != null && mIssueDetail.getAfterTreatment().size() > 0){
            linImagecycleEnd.setVisibility(View.VISIBLE);
            imagecycleEnd.setImageResources(mContext, mIssueDetail.getAfterTreatment(), mCycleViewListener
                    , ChengApplication.instance.municipalApplication.access_token, true);
            tvOperation.setText(mIssueDetail.getResult());
        }else{
            linImagecycleEnd.setVisibility(View.GONE);
        }
        if(mIssueDetail.getRefuseList() != null && mIssueDetail.getRefuseList().size() > 0){
            linRework.setVisibility(View.VISIBLE);
            ArrayList<Refuseparam> refuses = new ArrayList<Refuseparam>();
            for (IssueDetail.Refuse refuse :mIssueDetail.getRefuseList()) {
                Refuseparam refuseparam = new Refuseparam();
                refuseparam.setCreateTime(refuse.getCreateTime());
                refuseparam.setRefuseOpinion(refuse.getRefuseOpinion());
                refuses.add(refuseparam);
            }
            noscrolllistview.setAdapter(new ReworkAdapter(mContext, refuses));
        }else{
            linRework.setVisibility(View.GONE);
        }
    }

    /**
     * 请求养护日志详情
     * @param issueId
     */
    private void requestIssueDetail(String issueId){
        MunicipalRequest.requestShowIssue(this, this, issueId);
    }

    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(getApplicationContext()).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }

        @Override
        public void onImageClick(int postion, View imageView) {
            Intent intent = new Intent(mContext, PictureCompareActivity.class);
            intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_BEFORE, mIssueDetail.getBeforeTreatment());
            intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_AFTER, mIssueDetail.getAfterTreatment());
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
        }
    }

    @Override
    public void onItemClick(int index) {
        Intent intent;
        if (state == 4) {
            switch (index){
                case 0: //通过
                    MunicipalRequest.requestConfirmIssue(mContext, this, "" + mIssueDetail.getId());
                    break;
                case 1: //不通过
                    MunicipalRequest.requestCloseIssue(mContext, this, "" + mIssueDetail.getId());
                    break;
            }
        }
        if (state == 0) {
            switch (index) {
                case 0: //通过
                    finishIssue();
                    break;

            }
        }

        if (state == 1) {
            switch (index) {
                case 0: //通过
                    finishIssue();
                    break;
                case 1: //养护对比
                    intent = new Intent(mContext, PictureCompareActivity.class);
                    intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_BEFORE, mIssueDetail.getBeforeTreatment());
                    intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_AFTER, mIssueDetail.getAfterTreatment());
                    startActivity(intent);
                    break;

            }
        }

        if (state == 2) {
            switch (index) {
                case 0: //通过
                    finishIssue();
                    break;
                case 1: //返工
                    intent = new Intent(mContext, ReworkActivity.class);
                    intent.putExtra("statue", ReworkActivity.STATUS_ISSUE);
                    intent.putExtra("id", ""+mIssueDetail.getId());
                    startActivityForResult(intent, 1);
                    break;
                case 2: //养护对比
                    intent = new Intent(mContext, PictureCompareActivity.class);
                    intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_BEFORE, mIssueDetail.getBeforeTreatment());
                    intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_AFTER, mIssueDetail.getAfterTreatment());
                    startActivity(intent);
                    break;

            }
        }

    }

    /**
     * 结束养护日志
     */
    private void finishIssue() {
        CommonDialog dialog = new CommonDialog(mContext, R.style.dialog_dimenable, new CommonDialog.OnClickDialogListener() {
            @Override
            public void onClickOkBtn() {
                MunicipalRequest.requestCloseIssue(mContext, MaintainDetialActivity.this, "" + mIssueDetail.getId());
            }

            @Override
            public void onClickCancelBtn() {
            }
        });
        dialog.show();
        dialog.setDialogTitle("通过提示");
        dialog.setDialogContent("确定对此养护日志进行通过处理吗？");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode > 0){
            setResult(1);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imagecycle.pushImageCycle(true);
        imagecycleEnd.pushImageCycle(true);
    }

    @Override
    public void success(String reqUrl, Object result) {
        if(reqUrl.equals(Contants.SHOW_ISSUE)) {
            IssueDetailBean issueDetailBean = (IssueDetailBean) result;
            issueDetailBean.checkResult(this, issueDetailHandler);
        }else if(reqUrl.equals(Contants.CLOSE_ISSUE)){
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(this, closeIssueHandler);
        }else if(reqUrl.equals(Contants.CONFIRM_ISSUE)){
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(this, confirmIssueHandler);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler issueDetailHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            IssueDetailBean issueDetailBean = (IssueDetailBean) baseBean;
            mIssueDetail = issueDetailBean.transformToIssueDetail();
            mHandler.sendEmptyMessage(0);
            Toast.makeText(mContext, "获取日志详情成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取日志详情失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler closeIssueHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "提交成功", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "提交失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler confirmIssueHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "养护日志审核通过!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "养护日志审核失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            refreshView();
        }
    };

}

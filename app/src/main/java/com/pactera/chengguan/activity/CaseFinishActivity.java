package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.bean.municipal.CaseDetailBean;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.ADInfo;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.model.municipal.PicData;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.view.ImageCycleView;
import com.pactera.chengguan.view.PopMenu;
import com.pactera.chengguan.view.dialog.CommonDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 办结or处理中or审核
 * Created by lijun on 2016/3/9.
 */
public class CaseFinishActivity extends BaseActivity implements PopMenu.OnItemClickListener
        , View.OnClickListener, RequestListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.imagecycle)
    ImageCycleView imagecycle;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_unit)
    TextView tvUnit;
    @Bind(R.id.imagecycle_end)
    ImageCycleView imagecycleEnd;
    @Bind(R.id.tv_operation)
    TextView tvOperation;
    @Bind(R.id.timer)
    TextView timer;
    private PopMenu popMenu;
    private ArrayList<ADInfo> beforeInfos = new ArrayList<ADInfo>();
    private ArrayList<ADInfo> afterInfos = new ArrayList<ADInfo>();
    //    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
//            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
//            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private ArrayList<String> beforeImageUrls = new ArrayList<String>();
    private ArrayList<String> afterImageUrls = new ArrayList<String>();
    public static final String STATE = "State";
    //状态1为办结，2为处理中，3为审核
    private int state;
    //办结
    private String[] state_one = {"流程日志", "延期记录"};
    //处理中
    private String[] state_two = {"延期", "结案", "延期记录"};
    //审核
    private String[] state_three = {"考核", "延期", "结案", "不通过", "流程日志", "延期记录"};
    //类型
    private static final String[] type_data = {"月度", "季度", "年度", "日常"};

    private CaseInfo caseInfo;

    private static final int REQUEST_ACTIVITY_POSTPONE = 1;     //延期
    private static final int REQUEST_ACTIVITY_ASSESS = 2;   //考核
    private static final int REQUEST_ACTIVITY_REWORK = 3;   //返工

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_finish);
        ButterKnife.bind(this);
        getIntentContent();
        init();
    }

    private void getIntentContent() {
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
        state = bundle.getInt(STATE, 0);
    }

    protected void init() {         // 初始化弹出菜单
        popMenu = new PopMenu(mContext);
        addView();
        popMenu.setOnItemClickListener(this);
        addTitleView(lin);
        title.setText("考核案件");
        refreshDataView();
    }

    private void refreshDataView(){
        List<PicData> beforeList = caseInfo.getBeforePic();
        for (PicData pic : beforeList) {
            String localUrl = pic.getLocalUrl();
            if (localUrl != null && localUrl.length() > 0) {
                beforeImageUrls.add(localUrl);
            }
        }
        List<PicData> afterList = caseInfo.getAfterPic();
        for (PicData pic : afterList) {
            String localUrl = pic.getLocalUrl();
            if (localUrl != null && localUrl.length() > 0) {
                afterImageUrls.add(localUrl);
            }
        }
        for (int i = 0; i < beforeImageUrls.size(); i++) {
            ADInfo info = new ADInfo();
            info.setUrl(beforeImageUrls.get(i));
            info.setContent("top-->" + i);
            beforeInfos.add(info);
        }
        for (int i = 0; i < afterImageUrls.size(); i++) {
            ADInfo info = new ADInfo();
            info.setUrl(afterImageUrls.get(i));
            info.setContent("top-->" + i);
            afterInfos.add(info);
        }
        imagecycle.setImageResources(beforeInfos, mCycleViewListener);
        imagecycleEnd.setImageResources(afterInfos, mCycleViewListener);
        tvPoint.setText("" + caseInfo.getCheckPoint());
        tvDescription.setText(caseInfo.getDescription());
        tvLocation.setText(caseInfo.getLocation());
        tvType.setText(type_data[caseInfo.getCategory() - 1] + File.separator + CaseInfo.MONTHS[caseInfo.getMonth() - 1]);
        tvDate.setText(caseInfo.getDate());
        tvOperation.setText(caseInfo.getDescription());
        timer.setText("倒计时: " + caseInfo.getTermTime() + "天");
    }

    /**
     * 根据传入的参数，加载不同的界面
     */
    private void addView() {
        if (state == 1) {
            popMenu.addItems(state_one);
        } else if (state == 2) {
            popMenu.addItems(state_two);
        } else if (state == 3) {
            popMenu.addItems(state_three);
        }
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

    private ImageCycleView.ImageCycleViewListener mCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Intent intent = new Intent(mContext, PictureCompareActivity.class);
            startActivity(intent);
            Toast.makeText(mContext, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };


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
        Bundle bundle;
        if (state == 1) {
            switch (index) {
                case 0: //流程日志
                    intent = new Intent(mContext, ProcessRecordActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 1: //延期记录
                    intent = new Intent(mContext, DelayRecordActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        } else if (state == 2) {
            switch (index) {
                case 0: //延期
                    intent = new Intent(mContext, PostPoneActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_ACTIVITY_POSTPONE);
                    break;
                case 1: //结案
                    finishCase();
                    break;
                case 2: //延期记录
                    intent = new Intent(mContext, DelayRecordActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }

        } else if (state == 3) {
            switch (index) {
                case 0: //考核
                    intent = new Intent(mContext, AssessActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_ACTIVITY_ASSESS);
                    break;
                case 1: //延期
                    intent = new Intent(mContext, PostPoneActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_ACTIVITY_POSTPONE);
                    break;
                case 2: //结案
                    finishCase();
                    break;
                case 3: //不通过返工
                    intent = new Intent(mContext, ReworkActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_ACTIVITY_REWORK);
                    break;
                case 4: //流程日志
                    intent = new Intent(mContext, ProcessRecordActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 5: //延期记录
                    intent = new Intent(mContext, DelayRecordActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("case", caseInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }

        }
    }




    /**
     * 结案
     */
    private void finishCase() {
        CommonDialog dialog = new CommonDialog(mContext, R.style.dialog_dimenable, new CommonDialog.OnClickDialogListener() {
            @Override
            public void onClickOkBtn() {
                MunicipalRequest.requestCaseFinish(CaseFinishActivity.this, CaseFinishActivity.this, caseInfo.getId());
            }

            @Override
            public void onClickCancelBtn() {
            }
        });
        dialog.show();
        dialog.setDialogTitle("结案提示");
        dialog.setDialogContent("确定对此案件进行结案处理吗？");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACTIVITY_POSTPONE:
                MunicipalRequest.requestCaseDetail(this, this, caseInfo.getId());
                break;
            case REQUEST_ACTIVITY_ASSESS:
                setResult(1);
                finish();
                break;
            case REQUEST_ACTIVITY_REWORK:
                setResult(1);
                finish();
                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        if (reqUrl.equals(Contants.CASE_FINISH)) {
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(this, finishCaseHandler);
        }else if(reqUrl.equals(Contants.CASE_DETAIL)){
            CaseDetailBean detailBean = (CaseDetailBean) result;
            detailBean.checkResult(this, caseDetailHander);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler finishCaseHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(CaseFinishActivity.this, "结案成功!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(CaseFinishActivity.this, "结案失败:" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private BaseHandler caseDetailHander = new BaseHandler(){
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            CaseDetailBean detailBean = (CaseDetailBean) baseBean;
            if(detailBean.data != null){
                detailBean.data.transformData(caseInfo);
                mHandler.sendEmptyMessage(0);
                Toast.makeText(CaseFinishActivity.this, "获取案件详情成功!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void doError(int result, String message) {
            String msg = "获取案件详情失败:" + result + " | msg:" + message;
            Toast.makeText(CaseFinishActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            refreshDataView();
        }
    };
}

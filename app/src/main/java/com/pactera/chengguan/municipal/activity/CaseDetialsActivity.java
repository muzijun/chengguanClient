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
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.model.event.PointData;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.pactera.chengguan.municipal.view.PopMenu;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 新建案件的案件详情（可编辑状态）
 */
public class CaseDetialsActivity extends BaseActivity implements PopMenu.OnItemClickListener
        , View.OnClickListener, com.pactera.chengguan.municipal.config.RequestListener {


    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.imagecycle)
    com.pactera.chengguan.municipal.view.ImageItemCycle imagecycle;
    @Bind(R.id.tx_describe)
    TextView txDescribe;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_type)
    TextView txType;
    @Bind(R.id.tx_unit)
    TextView txUnit;
    @Bind(R.id.tx_date_edit)
    TextView txDateEdit;
    @Bind(R.id.tx_deduct)
    TextView txDeduct;
    private com.pactera.chengguan.municipal.view.PopMenu popMenu;
    private ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData> infos = new ArrayList<com.pactera.chengguan.municipal.model.municipal.PicData>();
    //    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
//            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
//            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    //作业单位集合
    private ArrayList<String> mSelectData_unit = new ArrayList<String>();
    //考核类型集合
    private ArrayList<String> mSelectData_type = new ArrayList<String>();
    //    //作业单位
//    private String[] unit_data = {"无锡市政府", "无锡城管局"};
    //考核类型
    private String[] type_data = {"月度", "季度", "年度", "日常"};
    //事业单位
    private String STATE_UNIT = "STATE_UNIT";
    //考核类型
    private String STATE_TYPE = "STATE_TYPE";
    //描述
    private String DESCRIPTION = "DESCRIPTION";
    //地址
    private String ADDRESS = "ADDRESS";
    //工期
    private String TIME = "TIME";
    //分数
    private String POINT = "POINT";
    private PointData pointData;

    //案件信息
    private CaseInfo caseInfo;
    private int termTime;
    private String checkPoint;
    private String description = "";
    private String location = "";
    private int category;
    private int unitId;

    private int requestStatus;
    public static final int STATUS_UPDATE = 1;     //保存
    public static final int STATUS_ISSUE = 2;      //下派

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detials);
        ButterKnife.bind(this);
        getIntentContent();
        init();
    }

    private void getIntentContent() {
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
        if (caseInfo == null) {
            finish();
        }
        infos = caseInfo.getBeforePic();
    }

    protected void init() {
        addData();
        // 初始化弹出菜单
        popMenu = new com.pactera.chengguan.municipal.view.PopMenu(mContext);
        popMenu.addItems(new String[]{"下派", "保存"});
        popMenu.setOnItemClickListener(this);
        addView(lin);
        title.setText("考核案件");
        imagecycle.setImageResources(mContext, infos, mCycleViewListener, ChengApplication.instance.municipalApplication.access_token, true);
        txUnit.setOnClickListener(this);
        txType.setOnClickListener(this);
        txAddress.setOnClickListener(this);
        txDescribe.setOnClickListener(this);
        txDateEdit.setOnClickListener(this);
        txDeduct.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }


    private void addView(LinearLayout linearLayout) {
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
     * 填充数据
     */
    private void addData() {
        for (int i = 0; i < MunicipalCache.sectionList.size(); i++) {
            String unit = new String(MunicipalCache.sectionList.get(i).getName());
            mSelectData_unit.add(unit);
        }
        for (int i = 0; i < type_data.length; i++) {
            String type = new String(type_data[i]);
            mSelectData_type.add(type);
        }
        refreshDataView();
    }

    private void refreshDataView() {
        termTime = caseInfo.getTermTime();
        checkPoint = caseInfo.getCheckPoint();
        description = caseInfo.getDescription();
        location = caseInfo.getLocation();
        category = caseInfo.getCategory();
        unitId = caseInfo.getOperateUnitId();

        txDateEdit.setText(termTime + "天");
        txDeduct.setText("" + checkPoint);
        txDescribe.setText(description);
        txAddress.setText(location);
        txType.setText(type_data[caseInfo.getCategory() - 1]);
        txUnit.setText(com.pactera.chengguan.municipal.util.MunicipalUtils.getSectionNameById(unitId));
    }

    private com.pactera.chengguan.municipal.view.ImageItemCycle.ImageCycleViewListener mCycleViewListener = new com.pactera.chengguan.municipal.view.ImageItemCycle.ImageCycleViewListener() {


        @Override
        public void onImageClick(int postion, View imageView) {

        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(getApplicationContext()).load(imageURL).placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.tx_describe:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.InputActivity.class);
                intent.putExtra("type", DESCRIPTION);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("content", txDescribe.getText().toString());
                intent.putExtra("title", "描述");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_address:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.InputActivity.class);
                intent.putExtra("type", ADDRESS);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "地址");
                intent.putExtra("content", txAddress.getText().toString());
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_type:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "考核类型");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", txType.getText().toString());
                startActivity(intent);
                break;
            case R.id.tx_unit:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelectData_unit);
                intent.putExtra("title", "作业单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", txUnit.getText().toString());
                startActivity(intent);
                break;
            case R.id.tx_deduct:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.PointsActivity.class);
                intent.putExtra("type", POINT);
                intent.putExtra("data", txDeduct.getText().toString());
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_date_edit:
                intent = new Intent(mContext, com.pactera.chengguan.municipal.activity.TimeActivity.class);
                intent.putExtra("type", TIME);
                intent.putExtra("data", txDateEdit.getText().toString().replace("天", ""));
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(int index) {
        if(!checkContent()){
            return;
        }
        if (index == 0) { //下派
            requestStatus = STATUS_ISSUE;
            com.pactera.chengguan.municipal.util.MunicipalRequest.requestCreateCase(this, this, STATUS_ISSUE, "" + caseInfo.getId(), description
                    , checkPoint, termTime, location, 0, 0, unitId, category
                    , caseInfo.getMonth(), null);
        } else if (index == 1) { //保存
            requestStatus = STATUS_UPDATE;
            com.pactera.chengguan.municipal.util.MunicipalRequest.requestCreateCase(this, this, STATUS_UPDATE, "" + caseInfo.getId(), description
                    , checkPoint, termTime, location, 0, 0, unitId, category
                    , caseInfo.getMonth(), null);
        }
    }

    private boolean checkContent(){
        if(description == null || description.isEmpty()){
            Toast.makeText(this, "请输入案件描述!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(location == null || location.isEmpty()){
            Toast.makeText(this, "请输入案件地址!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onEventMainThread(com.pactera.chengguan.municipal.model.SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            String msg = event.getmMsg();
            //考核类型
            if (event.getType().equals(STATE_TYPE)) {
                category = getIndex(msg, type_data);
                txType.setText(msg);
            }
            //作业单位
            else if (event.getType().equals(STATE_UNIT)) {
                txUnit.setText(msg);
                unitId = com.pactera.chengguan.municipal.util.MunicipalUtils.getSectionIdByName(msg);
            } else if (event.getType().equals(DESCRIPTION)) {
                description = msg;
                txDescribe.setText(msg);
            } else if (event.getType().equals((ADDRESS))) {
                location = msg;
                txAddress.setText(msg);
            }
            //工期
            else if (event.getType().equals(TIME)) {
                termTime = Integer.parseInt(msg);
                txDateEdit.setText(msg + "天");
            }
            //分数
            else if (event.getType().equals(POINT)) {
                pointData = (com.pactera.chengguan.municipal.model.event.PointData) event.getObject();
                checkPoint = pointData.getNumber();
                txDeduct.setText(pointData.getNumber());
            }
        }
    }

    private int getIndex(String msg, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(msg)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        imagecycle.pushImageCycle(true);
    }

    private com.pactera.chengguan.municipal.bean.BaseHandler updateHandler = new com.pactera.chengguan.municipal.bean.BaseHandler() {
        @Override
        public void doSuccess(com.pactera.chengguan.municipal.bean.BaseBean baseBean, String message) {
            String msg;
            if (requestStatus == STATUS_UPDATE) {
                msg = "案件保存成功!";
            } else {
                msg = "案件下派成功!";
            }
            Toast.makeText(CaseDetialsActivity.this, msg, Toast.LENGTH_LONG).show();
            if (requestStatus == STATUS_UPDATE) {
//                MunicipalRequest.requestCaseDetail(CaseDetialsActivity.this, CaseDetialsActivity.this, caseInfo.getId());
                setResult(STATUS_UPDATE);
                finish();
            } else {
                setResult(STATUS_ISSUE);
                finish();
            }
        }

        @Override
        public void doError(int result, String message) {
            String msg;
            if (requestStatus == STATUS_UPDATE) {
                msg = "案件保存失败:" + result + " | msg:" + message;
            } else {
                msg = "案件下派失败:" + result + " | msg:" + message;
            }
            Toast.makeText(CaseDetialsActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };

    private com.pactera.chengguan.municipal.bean.BaseHandler caseDetailHander = new com.pactera.chengguan.municipal.bean.BaseHandler() {
        @Override
        public void doSuccess(com.pactera.chengguan.municipal.bean.BaseBean baseBean, String message) {
            com.pactera.chengguan.municipal.bean.municipal.CaseDetailBean detailBean = (com.pactera.chengguan.municipal.bean.municipal.CaseDetailBean) baseBean;
            if (detailBean.datas != null) {
                detailBean.datas.transformData(caseInfo);
                mHandler.sendEmptyMessage(0);
                Toast.makeText(CaseDetialsActivity.this, "获取案件详情成功!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void doError(int result, String message) {
            String msg = "获取案件详情失败:" + result + " | msg:" + message;
            Toast.makeText(CaseDetialsActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void success(String reqUrl, Object result) {
        if (reqUrl.equals(Contants.CASE_CREATE)) {
            com.pactera.chengguan.municipal.bean.BaseBean baseBean = (com.pactera.chengguan.municipal.bean.BaseBean) result;
            baseBean.checkResult(this, updateHandler);
        } else if (reqUrl.equals(Contants.CASE_DETAIL)) {
            com.pactera.chengguan.municipal.bean.municipal.CaseDetailBean detailBean = (com.pactera.chengguan.municipal.bean.municipal.CaseDetailBean) result;
            detailBean.checkResult(this, caseDetailHander);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            refreshDataView();
        }
    };
}

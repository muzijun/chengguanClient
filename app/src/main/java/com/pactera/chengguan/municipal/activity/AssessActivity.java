package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chengguan.dao.DaoSession;
import com.chengguan.dao.checkclassify;
import com.chengguan.dao.checkclassifyDao;
import com.chengguan.dao.checkcontent;
import com.chengguan.dao.checkcontentDao;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.db.DBHelper;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.pactera.chengguan.municipal.model.municipal.CheckLib;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 考核
 */
public class AssessActivity extends BaseActivity implements RequestListener, View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.et_point)
    EditText etPoint;
    @Bind(R.id.tv_month)
    TextView tvMonth;
    @Bind(R.id.commit)
    Button btnCommit;

    private CaseInfo caseInfo;
    //选择的依据
    private String mSelectType;
    //选择的月份
    private int mSelectMonth;

    //考核分类数据
    private ArrayList<String> mSelectData_type = new ArrayList<String>();
    //月份集合
    private ArrayList<String> mSelectData_month = new ArrayList<String>();
    //月份
    private String[] month_data = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    //考核分类
    private static final String STATE_TYPE = "TYPE";
    //考核内容
    private static final String STATE_MONTH = "MONTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        ButterKnife.bind(this);
        getIntentContent();
        initCheckLibData();
        initSelectData();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getIntentContent(){
        Bundle bundle = getIntent().getExtras();
        caseInfo = (CaseInfo) bundle.get("case");
    }

    /**
     * 加载考核库数据至 MunicipalCache.checkLibList
     */
    private void initCheckLibData(){
        if(MunicipalCache.checkLibList == null || MunicipalCache.checkLibList.size() <= 0){
            MunicipalCache.checkLibList = new ArrayList<CheckLib>();
            DaoSession daoSession = DBHelper.getInstance(this).getDaoSession(this);
            final checkclassifyDao classifyDao = daoSession.getCheckclassifyDao();
            final checkcontentDao contentDao = daoSession.getCheckcontentDao();
            List<checkclassify> checkclassifyList = classifyDao.loadAll();
            for(checkclassify classify : checkclassifyList){
                CheckLib checkLib = new CheckLib();
                checkLib.setId(classify.getClassifyid());
                checkLib.setName(classify.getClassifyname());
                List<checkcontent> checkcontents = contentDao.queryBuilder()
                        .where(checkcontentDao.Properties.Classifyid.eq(classify.getClassifyid())).list();
                List<CheckLib.Content> contentList = new ArrayList<CheckLib.Content>();
                for(checkcontent ccontent : checkcontents){
                    CheckLib.Content content = new CheckLib.Content();
                    content.setId(ccontent.getStandardid());
                    content.setContent(ccontent.getStandard());
                    content.setPoints(ccontent.getPoint());
                    contentList.add(content);
                }
                checkLib.setContents(contentList);
                MunicipalCache.checkLibList.add(checkLib);
            }
        }
    }

    private void initSelectData(){
        for(String month : month_data){
            mSelectData_month.add(month);
        }
        for(CheckLib checkLib : MunicipalCache.checkLibList){
            mSelectData_type.add(checkLib.getName());
        }
    }

    private void init() {
        title.setText("考核");
        tvContent.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_content:
                intent = new Intent(mContext,SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "考核依据");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvContent.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_month:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_MONTH);
                intent.putStringArrayListExtra("data", mSelectData_month);
                intent.putExtra("title", "月份");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvMonth.getText().toString());
                startActivity(intent);
                break;
            case R.id.commit:
                commitRequest();
                break;
        }
    }

    public void commitRequest(){
        String point = etPoint.getText().toString();
        MunicipalRequest.requestCaseCheck(this, this, caseInfo.getId(), mSelectType, point, mSelectMonth + 1);
    }

    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            String msg = event.getmMsg();
            //依据
            if (event.getType().equals(STATE_TYPE)) {
                mSelectType = msg;
                tvContent.setText(msg);
            }
            //月份
            else if(event.getType().equals(STATE_MONTH)){
                mSelectMonth = getIndexFromArray(msg, month_data);
                tvMonth.setText(msg);
            }
        }
    }

    private int getIndexFromArray(String msg, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(msg)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, assessHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler assessHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(AssessActivity.this, "考核成功!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(AssessActivity.this, "考核失败:" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };
}

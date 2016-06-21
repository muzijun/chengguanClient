package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.chengguan.dao.DaoSession;
import com.chengguan.dao.checkclassify;
import com.chengguan.dao.checkclassifyDao;
import com.chengguan.dao.checkcontent;
import com.chengguan.dao.checkcontentDao;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.db.DBHelper;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.model.event.PointData;
import com.pactera.chengguan.municipal.model.municipal.CheckLib;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 扣分
 */
public class PointsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bt_minus)
    Button btMinus;
    @Bind(R.id.edt_point)
    EditText edtPoint;
    @Bind(R.id.bt_plus)
    Button btPlus;
//    @Bind(R.id.tx_standard)
//    TextView txStandard;
    @Bind(R.id.commit)
    Button commit;
//    @Bind(R.id.tx_type)
//    TextView txType;
    private String type;
    private String address;
    private String mData;
    //考核分类数据
    private ArrayList<String> mSelectData_type = new ArrayList<String>();
    //考核分类
    private static final String STATE_TYPE = "TYPE";
    //考核内容
    private static final String STATE_STANDARD = "STANDARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);
        initCheckLibData();
        initSelectData();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        for(CheckLib checkLib : MunicipalCache.checkLibList){
            mSelectData_type.add(checkLib.getName());
        }
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("扣分");
        type = getIntent().getStringExtra("type");
        address = getIntent().getStringExtra("address");
        mData = getIntent().getStringExtra("data");
        //TODO 临时处理下 默认直接取整
        int pointData = (int) Double.parseDouble(mData);
        edtPoint.setText(""+pointData);
//        edtPoint.setText(mData);
        btMinus.setOnClickListener(this);
        btPlus.setOnClickListener(this);
        commit.setOnClickListener(this);
//        txStandard.setOnClickListener(this);
//        txType.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        String point;
        int number;
        Intent intent;
        switch (v.getId()) {
//            case R.id.tx_standard:
//                intent = new Intent(mContext, InputActivity.class);
//                intent.putExtra("type", STATE_STANDARD);
//                intent.putStringArrayListExtra("data", mSelectData_type);
//                intent.putExtra("title", "扣分标准");
//                intent.putExtra("content", txStandard.getText().toString());
//                intent.putExtra("address", this.getClass().getName());
//                startActivity(intent);
//                break;
            case R.id.bt_minus:
                point = edtPoint.getText().toString();
                number = Integer.valueOf(point);
                if (0 < number) {
                    edtPoint.setText(String.valueOf(number - 1));
                }
                break;
            case R.id.bt_plus:
                point = edtPoint.getText().toString();
                number = Integer.valueOf(point);
                edtPoint.setText(String.valueOf(number + 1));
                break;
            case R.id.commit:
                PointData pointData = new PointData();
//                pointData.setType(txType.getText().toString());
                pointData.setNumber(edtPoint.getEditableText().toString());
//                pointData.setStandard(txStandard.getText().toString());
                SelectEvent selectEvent = new SelectEvent();
                selectEvent.setType(type);
                selectEvent.setAddress(address);
                selectEvent.setObject(pointData);
                EventBus.getDefault().post(selectEvent);
                finish();
                break;
        }
    }

    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            String msg = event.getmMsg();
//            if (event.getType().equals(STATE_TYPE)) {
//                txType.setText(msg);
//            }
//            else if (event.getType().equals(STATE_STANDARD)) {
//                txStandard.setText(msg);
//            }
        }
    }
}

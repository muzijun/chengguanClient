package com.pactera.chengguan.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.CaseListActivity;
import com.pactera.chengguan.activity.ImageZoomActivity;
import com.pactera.chengguan.activity.SelectActivity;
import com.pactera.chengguan.adapter.ImagePublishAdapter;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.base.ChenguanOkHttpManager;
import com.pactera.chengguan.bean.BaseBean;
import com.pactera.chengguan.bean.BaseHandler;
import com.pactera.chengguan.config.Contants;
import com.pactera.chengguan.config.MunicipalCache;
import com.pactera.chengguan.config.RequestListener;
import com.pactera.chengguan.model.PhotoEvent;
import com.pactera.chengguan.model.RequestFile;
import com.pactera.chengguan.model.RequestPair;
import com.pactera.chengguan.model.SelectEvent;
import com.pactera.chengguan.util.BaseCallback;
import com.pactera.chengguan.util.MunicipalRequest;
import com.pactera.chengguan.util.MunicipalUtils;
import com.pactera.chengguan.view.NoScrollGridView;
import com.pactera.chengguan.view.dialog.CommonDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.SimpleDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 考核案件
 * Created by lijun on 2016/3/8.
 */
public class ExcaseFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener
        , RequestListener {
    private static final int REQUEST_IMAGE = 2;
    //案件详情按钮
    @Bind(R.id.case_list)
    TextView caseList;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.unit_text)
    TextView unitText;
    @Bind(R.id.unit_lin)
    LinearLayout unitLin;
    @Bind(R.id.type_text)
    TextView typeText;
    @Bind(R.id.type_lin)
    LinearLayout typeLin;
    @Bind(R.id.month_text)
    TextView monthText;
    @Bind(R.id.month_lin)
    LinearLayout monthLin;
    @Bind(R.id.commit)
    Button commit;

    private ImagePublishAdapter mAdapter;

    private ArrayList<String> mDataList = new ArrayList<String>();
    //作业单位集合
    private ArrayList<String> mSelectData_unit = new ArrayList<String>();
    //考核类型集合
    private ArrayList<String> mSelectData_type = new ArrayList<String>();
    //月份集合
    private ArrayList<String> mSelectData_month = new ArrayList<String>();

    //考核类型
    private String[] type_data = {"月度", "季度", "年度", "日常"};
    //月份
    private String[] month_data = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    //事业单位
    private String STATE_UNIT = "STATE_UNIT";
    //考核类型
    private String STATE_TYPE = "STATE_TYPE";
    //月份
    private String STATE_MONTH = "STATE_MONTH";

    private int selectSectionID, selectTypeIndex, selectMonthIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = initView(R.layout.fragment_excase, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        addData();
        unitText.setText(MunicipalCache.sectionList.get(0).getName());
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(mContext, mDataList);
        gridview.setAdapter(mAdapter);
        caseList.setOnClickListener(this);
        unitLin.setOnClickListener(this);
        monthLin.setOnClickListener(this);
        typeLin.setOnClickListener(this);
        commit.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
        EventBus.getDefault().register(this);
    }


    /**
     * 填充数据
     */
    private void addData() {
        for (int i = 0; i < MunicipalCache.sectionList.size(); i++) {
            String unit = MunicipalCache.sectionList.get(i).getName();
            mSelectData_unit.add(unit);
        }
        for (int i = 0; i < type_data.length; i++) {
            String type = new String(type_data[i]);
            mSelectData_type.add(type);
        }
        for (int i = 0; i < month_data.length; i++) {
            String type = new String(month_data[i]);
            mSelectData_month.add(type);
        }
    }

    private void setMode() {
        Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, Contants.MAX_IMAGE_SIZE);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mDataList != null && mDataList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mDataList);
        }
        getRootFragment().startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyDataChanged();
    }

    private void notifyDataChanged() {
        mAdapter.setmDataList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void onEventMainThread(PhotoEvent event) {
        mDataList = event.getMsg();
    }

    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            //月份
            if (event.getType().equals(STATE_MONTH)) {
                String msg = event.getmMsg();
                selectMonthIndex = getIndexFromArray(msg, month_data);
                monthText.setText(msg);
            }
            //考核类型
            else if (event.getType().equals(STATE_TYPE)) {
//                if (event.getmMsg().equals("日常")) {
//                    monthLin.setVisibility(View.GONE);
//                } else {
//                    monthLin.setVisibility(View.VISIBLE);
//                }
                String msg = event.getmMsg();
                selectTypeIndex = getIndexFromArray(msg, type_data);
                typeText.setText(msg);
            }
            //作业单位
            else if (event.getType().equals(STATE_UNIT)) {
                String msg = event.getmMsg();
                selectSectionID = MunicipalUtils.getSectionIdByName(msg);
                unitText.setText(msg);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == mContext.RESULT_OK) {
                mDataList.clear();
                mDataList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == getDataSize()) {
            setMode();
        } else {
            Intent intent = new Intent(mContext,
                    ImageZoomActivity.class);
            intent.putExtra(Contants.EXTRA_IMAGE_LIST,
                    (Serializable) mDataList);
            intent.putExtra(Contants.EXTRA_CURRENT_IMG_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.case_list:
                intent = new Intent(mContext, CaseListActivity.class);
                startActivity(intent);
                break;
            case R.id.unit_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelectData_unit);
                intent.putExtra("title", "作业单位");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.month_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_MONTH);
                intent.putStringArrayListExtra("data", mSelectData_month);
                intent.putExtra("title", "月份");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.type_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "考核类型");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.commit:
                commitCreate();
                break;
        }
    }

    /**
     * 提交
     */
    public void commitCreate() {
        CommonDialog dialog = new CommonDialog(mContext, R.style.dialog_dimenable,new CommonDialog.OnClickDialogListener() {
            @Override
            public void onClickOkBtn() {
                if(mDataList.size() <= 0){
                    Toast.makeText(mContext, "请添加图片!", Toast.LENGTH_LONG).show();
                    return;
                }
                MunicipalRequest.requestCreateCase(mContext, ExcaseFragment.this, 1, null, null, 0, 0, "震泽路18号", 0, 0
                        , selectSectionID, selectTypeIndex + 1, selectMonthIndex + 1, mDataList);
            }

            @Override
            public void onClickCancelBtn() {
            }
        });
        dialog.show();
        dialog.setDialogTitle("信息提示");
        dialog.setDialogContent("请点击确认信息无误并进行新案件");

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(mContext, newCaseHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler newCaseHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(getActivity(), "提交新案件成功!", Toast.LENGTH_LONG).show();
            resetImageAdapter();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(getActivity(), "提交新案件失败：" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 重置图片添加内容
     */
    private void resetImageAdapter(){
        mDataList.clear();
        mAdapter.notifyDataSetChanged();
    }
}

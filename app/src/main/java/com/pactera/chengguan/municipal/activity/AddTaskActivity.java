package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.util.ProgressDlgUtil;
import com.pactera.chengguan.municipal.adapter.ImagePublishAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.PhotoEvent;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.util.AppUtils;
import com.pactera.chengguan.municipal.util.FileUploadListener;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.util.UploadFileManager;
import com.pactera.chengguan.municipal.view.NoScrollGridView;
import com.pactera.chengguan.municipal.view.PopMenu;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddTaskActivity extends BaseActivity implements PopMenu.OnItemClickListener, OnClickListener, AdapterView.OnItemClickListener, RequestListener, FileUploadListener {

    private static final int REQUEST_IMAGE = 2;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.tv_contact_foundation)
    TextView tvContactFoundation;
    @Bind(R.id.tv_funds_sources)
    TextView tvFundsSources;
    @Bind(R.id.tv_finish_time)
    TextView tvFinishTime;
    @Bind(R.id.tv_contact_unit)
    TextView tvContactUnit;
    @Bind(R.id.top_inner)
    LinearLayout topInner;
    @Bind(R.id.top)
    LinearLayout top;
    @Bind(R.id.tv_contact_byAndContent)
    TextView tvContactByAndContent;
    @Bind(R.id.medium)
    LinearLayout medium;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.layout_addPic)
    LinearLayout layoutAddPic;
    @Bind(R.id.tv_upload_file)
    TextView tvUploadFile;
    @Bind(R.id.layout_upload)
    LinearLayout layoutUpload;

    private PopMenu popMenu;
    private String[] option = {"下发"};
    private ImagePublishAdapter mAdapter;
    private long time;
    //照片
    private ArrayList<String> mPhoneDataList = new ArrayList<String>();
    //文件
    private ArrayList<String> mFileList = new ArrayList<String>();
    //联系是由及内容
    private String STATE_BYANDCONTENT = "STATE_BYANDCONTENT";
    //联系依据
    private String STATE_FOUNDATION = "STATE_FOUNDATION";
    //资金来源
    private String STATE_FUNDS = "STATE_FUNDS";
    //事业单位
    private String STATE_UNIT = "STATE_UNIT";
    //联系单位集合
    private ArrayList<String> mSelect_unit = new ArrayList<String>();
    private static final int REQUEST_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);
        initTitleBar();
        initAddPic();
        addData();
        EventBus.getDefault().register(this);

    }

    private void initAddPic() {
        time = System.currentTimeMillis();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        tvFinishTime.setText(date);
        tvFinishTime.setOnClickListener(this);
        tvContactUnit.setText(MunicipalCache.sectionList.get(0).getName());
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(mContext, mPhoneDataList);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(this);
        tvContactByAndContent.setOnClickListener(this);
        tvFundsSources.setOnClickListener(this);
        tvContactFoundation.setOnClickListener(this);
        tvContactUnit.setOnClickListener(this);
        tvUploadFile.setOnClickListener(this);
    }

    private void initTitleBar() {
        title.setText("添加任务单");
        popMenu = new PopMenu(mContext);
        popMenu.addItems(option);
        popMenu.setOnItemClickListener(this);
        addMenuView(lin);
    }

    /**
     * 添加选项
     *
     * @param linearLayout
     */
    private void addMenuView(LinearLayout linearLayout) {
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
            mSelect_unit.add(unit);
        }
    }

    //popmenu点击事件
    @Override
    public void onItemClick(int index) {
        switch (index) {
            case 0:
                //下发
                if (isempty()) {
                    UploadFileManager uploadFileManager = new UploadFileManager(mContext, this
                            , MunicipalContants.UPLOAD_OBJECT_CONTACT, 0, "0");
                    List<UploadFileManager.UpLoadFileData> fileDataList = new ArrayList<UploadFileManager.UpLoadFileData>();
                    for(String path : mPhoneDataList){
                        UploadFileManager.UpLoadFileData fileData = new UploadFileManager.UpLoadFileData(path
                                , UploadFileManager.UPLOAD_TYPE_IMG);
                        fileDataList.add(fileData);
                    }
                    for(String path : mFileList){
                        UploadFileManager.UpLoadFileData fileData = new UploadFileManager.UpLoadFileData(path
                                , UploadFileManager.UPLOAD_TYPE_FILE);
                        fileDataList.add(fileData);
                    }
                    uploadFileManager.upLoadMulFiles(fileDataList);
                } else {
                    Toast.makeText(mContext, "请先补全内容!", Toast.LENGTH_LONG).show();
                }

                break;
        }

    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.tv_contact_foundation:
                //联系依据
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_FOUNDATION);
                intent.putExtra("content", tvContactFoundation.getText().toString());
                intent.putExtra("title", "联系依据");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_funds_sources:
                //资金来源
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_FUNDS);
                intent.putExtra("content", tvFundsSources.getText().toString());
                intent.putExtra("title", "资金来源");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_finish_time:
                //完成时间
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                showtime(day, month, year);
                break;
            case R.id.tv_contact_unit:
                //联系单位
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelect_unit);
                intent.putExtra("title", "联系单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvContactUnit.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_contact_byAndContent:
                //联系是由及内容
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_BYANDCONTENT);
                intent.putExtra("content", tvContactByAndContent.getText().toString());
                intent.putExtra("title", "内容");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_upload_file:
                //上传附件
                intent = new Intent(mContext, FileActivity.class);
                startActivityForResult(intent, REQUEST_FILE);
                break;
        }
    }


    private int getDataSize() {
        return mPhoneDataList == null ? 0 : mPhoneDataList.size();
    }


    /**
     * 显示日历时间
     *
     * @param day
     * @param month
     * @param year
     */
    private void showtime(int day, int month, int year) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        DatePickerDialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_DatePicker_Light : R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(new SimpleDateFormat("yyyy-MM-dd"));
                time = dialog.getDate();
                tvFinishTime.setText(date);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.date(day, month, year);
        builder.positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
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
        if (mPhoneDataList != null && mPhoneDataList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mPhoneDataList);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setmDataList(mPhoneDataList);
        mAdapter.notifyDataSetChanged();

    }

    public void onEventMainThread(PhotoEvent event) {
        mPhoneDataList = event.getMsg();
    }
    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            String msg = event.getmMsg();
            //联系是由及内容
            if (event.getType().equals(STATE_BYANDCONTENT)) {
                tvContactByAndContent.setText(msg);
            }
            //联系依据
            else if (event.getType().equals(STATE_FOUNDATION)) {
                tvContactFoundation.setText(msg);
            }
            //资金来源
            else if (event.getType().equals(STATE_FUNDS)) {
                tvFundsSources.setText(msg);
            }
            //联系单位
            else if (event.getType().equals(STATE_UNIT)) {
                tvContactUnit.setText(msg);
            }
        }

    }


    @Override
    public void onCompleted(List<String> fileUrlList, String Url) {
        ArrayList<String> phonelist = new ArrayList<String>();
        ArrayList<String> filelist = new ArrayList<String>();
        for (int i = 0; i < mPhoneDataList.size(); i++) {
            phonelist.add(fileUrlList.get(i));
        }
        for (int j = mPhoneDataList.size(); j < fileUrlList.size(); j++) {
            filelist.add(fileUrlList.get(j));
        }
        String id = "";
        int sectionId = AppUtils.getsectionid(tvContactUnit.getText().toString());
        String rationale = tvContactFoundation.getText().toString();
        String sourcesFund = tvFundsSources.getText().toString();
        String finishtime = tvFinishTime.getText().toString();
        String description = tvContactByAndContent.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(finishtime);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        MunicipalRequest.requestAddTaskForm(mContext, this, id, sectionId, rationale, sourcesFund, dt2.getTime(), description, "", "", "", phonelist, mFileList, 1);
    }

    @Override
    public void onError() {
        ProgressDlgUtil.stopProgressDlg();
        Toast.makeText(mContext, "文件上传失败, 请重新操作!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == mContext.RESULT_OK) {
                mPhoneDataList.clear();
                mPhoneDataList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == REQUEST_FILE) {
            if (resultCode == mContext.RESULT_OK) {
                mFileList = data.getStringArrayListExtra(FileActivity.EXTRA_RESULT);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == getDataSize()) {
            setMode();
        } else {
            Intent intent = new Intent(mContext,
                    ImageZoomActivity.class);
            intent.putExtra(Contants.EXTRA_IMAGE_LIST,
                    (Serializable) mPhoneDataList);
            intent.putExtra(Contants.EXTRA_CURRENT_IMG_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "提交成功!", Toast.LENGTH_LONG).show();
            setResult(Contants.TASK_REFRESH);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "提交失败:" + result + " | msg:" + message
                    , Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 判断是否全部填入数据
     *
     * @return false是没有，true是通过
     */
    private boolean isempty() {
        if (tvContactUnit.getText().toString().isEmpty()) {
            return false;
        }
        if (tvContactByAndContent.getText().toString().isEmpty()) {
            return false;
        }
        if (tvFundsSources.getText().toString().isEmpty()) {
            return false;
        }
        if (tvFinishTime.getText().toString().isEmpty()) {
            return false;
        }
        if (tvContactFoundation.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
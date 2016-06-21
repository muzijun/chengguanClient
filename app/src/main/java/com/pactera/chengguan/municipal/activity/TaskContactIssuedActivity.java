package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.ContactDetailBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.FileDown;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.model.municipal.ContactDetail;
import com.pactera.chengguan.municipal.model.municipal.ListContact;
import com.pactera.chengguan.municipal.util.AppUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ImageItemCycle;
import com.pactera.chengguan.municipal.view.NoScrollListView;
import com.pactera.chengguan.municipal.view.PopMenu;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 任务联系单待审核
 */
public class TaskContactIssuedActivity extends BaseActivity implements PopMenu.OnItemClickListener, OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.tv_operate_units)
    TextView tvOperateUnits;
    @Bind(R.id.tv_funds_sources)
    TextView tvFundsSources;
    @Bind(R.id.tv_task_duration)
    TextView tvTaskDuration;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.pm)
    TextView pm;
    @Bind(R.id.pmname)
    TextView pmname;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.imagecycle)
    ImageItemCycle imagecycle;
    @Bind(R.id.protime)
    TextView protime;
    @Bind(R.id.tast_site_content)
    TextView tastSiteContent;
    @Bind(R.id.noscrolllistview_file)
    NoScrollListView noscrolllistviewFile;
    @Bind(R.id.reportingunit)
    TextView reportingunit;
    @Bind(R.id.reportingunitname)
    TextView reportingunitname;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.layout_issued_content)
    LinearLayout layoutIssuedContent;
    @Bind(R.id.layout_issued)
    LinearLayout layoutIssued;
    @Bind(R.id.next_unit)
    TextView nextUnit;

    //类型集合
    private ArrayList<String> mType = new ArrayList<String>();
    //作业单位集合
    private ArrayList<String> mSelect_unit = new ArrayList<String>();
    //联系是由及内容
    private String STATE_BYANDCONTENT = "STATE_BYANDCONTENT";
    //资金来源
    private String STATE_FUNDS = "STATE_FUNDS";
    //事业单位
    private String STATE_UNIT = "STATE_UNIT";
    //下发单位
    private String STATE_NEXT_UNIT = "STATE_NEXT_UNIT";
    //类型
    private String STATE_TYPE = "STATE_TYPE";
    private PopMenu popMenu;
    private String[] option = {"保存", "下发", "不通过"};
    private long time_date;
    private int id;
    private ContactDetail Detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_contact_issued);
        ButterKnife.bind(this);
        initTitleBar();
        EventBus.getDefault().register(this);
        MunicipalRequest.requestContactDetail(mContext, this, id);

    }

    private void initTitleBar() {
        title.setText("任务联系单");
        popMenu = new PopMenu(mContext);
        popMenu.addItems(option);
        popMenu.setOnItemClickListener(this);
        addMenuView(lin);
        addData();
        id = getIntent().getIntExtra("id", 0);
        time_date = System.currentTimeMillis();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        tvTaskDuration.setText(date);
        tvOperateUnits.setText(MunicipalCache.sectionList.get(0).getName());
        tvOperateUnits.setText(MunicipalCache.sectionList.get(0).getName());
        tvType.setText("一般");
        tvTaskDuration.setOnClickListener(this);
        tvOperateUnits.setOnClickListener(this);
        tvFundsSources.setOnClickListener(this);
        tvDescription.setOnClickListener(this);
        nextUnit.setOnClickListener(this);
        tvType.setOnClickListener(this);
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
        mType.add("一般");
        mType.add("重大");
    }

    //popmenu点击事件
    @Override
    public void onItemClick(int index) {
        switch (index) {
            case 0:
                //保存
                if (isempty()) {
                    //下发单位
                    String next_unit = nextUnit.getText().toString();
                    //作业单位id
                    int issuedCompanyId = AppUtils.getsectionid(tvOperateUnits.getText().toString());
                    //资金来源
                    String fundsSource = tvFundsSources.getText().toString();
                    //工期
                    String taskDuration = tvTaskDuration.getText().toString();
                    //描述
                    String description = tvDescription.getText().toString();
                    //数组
                    ArrayList<String> mlist = new ArrayList<String>();
                    //类型
                    String tv_type = tvType.getText().toString();
                    String type = ListContact.CONTACTFORM_TYPE_COMMONLY;
                    if (tv_type.equals("一般")) {
                        type = ListContact.CONTACTFORM_TYPE_COMMONLY;
                    } else {
                        type = ListContact.CONTACTFORM_TYPE_IMPORTANT;
                    }
                    MunicipalRequest.requestUpdateContact(mContext, this, id, next_unit, description, String.valueOf(issuedCompanyId), taskDuration, "", fundsSource, "", type, mlist, 0);
                } else {
                    Toast.makeText(mContext, "请先补全内容!", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                //下发
                if (isempty()) {
                    //下发单位
                    String next_unit = nextUnit.getText().toString();
                    //作业单位id
                    int issuedCompanyId = AppUtils.getsectionid(tvOperateUnits.getText().toString());
                    //资金来源
                    String fundsSource = tvFundsSources.getText().toString();
                    //工期
                    String taskDuration = tvTaskDuration.getText().toString();
                    //描述
                    String description = tvDescription.getText().toString();
                    //数组
                    ArrayList<String> mlist = new ArrayList<String>();
                    //类型
                    String tv_type = tvType.getText().toString();
                    String type = ListContact.CONTACTFORM_TYPE_COMMONLY;
                    if (tv_type.equals("一般")) {
                        type = ListContact.CONTACTFORM_TYPE_COMMONLY;
                    } else {
                        type = ListContact.CONTACTFORM_TYPE_IMPORTANT;
                    }
                    MunicipalRequest.requestUpdateContact(mContext, this, id, next_unit, description, String.valueOf(issuedCompanyId), taskDuration, "", fundsSource, "", type, mlist, 1);
                } else {
                    Toast.makeText(mContext, "请先补全内容!", Toast.LENGTH_LONG).show();
                }

                break;
            case 2:
                //结案
                Intent intent = new Intent(mContext, TaskContactPassActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, Contants.TASK_REFRESH);
                break;

        }


    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.next_unit:
                //下发单位
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_NEXT_UNIT);
                intent.putExtra("content", nextUnit.getText().toString());
                intent.putExtra("title", "下发单位");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_operate_units:
                //作业单位
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelect_unit);
                intent.putExtra("title", "作业单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvOperateUnits.getText().toString());
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
            case R.id.tv_task_duration:
                //工期
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time_date);
                int year = mCalendar.get(Calendar.YEAR);
                int month = mCalendar.get(Calendar.MONTH);
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                showtime(day, month, year);
                break;
            case R.id.tv_description:
                //描述
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_BYANDCONTENT);
                intent.putExtra("content", tvDescription.getText().toString());
                intent.putExtra("title", "描述");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_type:
                //类型
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mType);
                intent.putExtra("title", "类型");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvType.getText().toString());
                startActivity(intent);
                break;
        }
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
                time_date = dialog.getDate();
                tvTaskDuration.setText(date);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.date(day, month, year);
        builder.positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }


    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            String msg = event.getmMsg();
            //联系是由及内容
            if (event.getType().equals(STATE_BYANDCONTENT)) {
                tvDescription.setText(msg);
            }
            //资金来源
            else if (event.getType().equals(STATE_FUNDS)) {
                tvFundsSources.setText(msg);
            }
            //联系单位
            else if (event.getType().equals(STATE_UNIT)) {
                tvOperateUnits.setText(msg);
            }
            //类型
            else if (event.getType().equals(STATE_TYPE)) {
                tvType.setText(msg);
            }
            //下发单位
            else if (event.getType().equals(STATE_NEXT_UNIT)) {
                nextUnit.setText(msg);
            }
        }

    }

    @Override
    public void success(String reqUrl, Object result) {
        if (result instanceof ContactDetailBean) {
            ContactDetailBean contactDetailBean = (ContactDetailBean) result;
            contactDetailBean.checkResult(mContext, Handler);
        } else {
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(mContext, Handler);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            if (baseBean instanceof ContactDetailBean) {
                ContactDetailBean contactDetailBean = (ContactDetailBean) baseBean;
                if (contactDetailBean.datas != null) {
                    ContactDetail contactDetail = contactDetailBean.datas.transformToDetail();
                    Detail=contactDetail;
                    imagecycle.setImageResources(mContext, contactDetail.getBeforeTreatment(), mCycleViewListener, ChengApplication.instance.municipalApplication.access_token, true);
                    protime.setText(contactDetail.getSchedule());
                    tastSiteContent.setText(contactDetail.getDescription());
                    reportingunitname.setText(AppUtils.getsectionname(contactDetail.getSectionId()));
                    pmname.setText(contactDetail.getProjectManager());
                    time.setText(contactDetail.getReportDate());
                    nextUnit.setText(contactDetail.getOwner());
                    if (!contactDetail.getIssuedCompanyId().equals("")) {
                        tvOperateUnits.setText(AppUtils.getsectionname(Integer.valueOf(contactDetail.getIssuedCompanyId())));
                    }
                    tvFundsSources.setText(contactDetail.getSourcesFund());
                    tvTaskDuration.setText(contactDetail.getOwnerSchedule());
                    tvDescription.setText(contactDetail.getOwnerDescription());
                    if (contactDetail.getContractType().equals(ListContact.CONTACTFORM_TYPE_COMMONLY)) {
                        tvType.setText("一般");
                    } else {
                        tvType.setText("重大");
                    }
                    ArrayList<ContactDetail.FileData> attachmentList = contactDetail.getAttachmentList();
                    ArrayList<FileDown> fileDowns = new ArrayList<FileDown>();
                    for (ContactDetail.FileData fileData : attachmentList) {
                        FileDown fileDown = new FileDown();
                        fileDown.setPhotoName(fileData.getFileName());
                        fileDown.setPhotoPath(fileData.getFilePath());
                        fileDowns.add(fileDown);
                    }
                    noscrolllistviewFile.setAdapter(new FileDownAdapter(fileDowns, mContext));


                }
            } else {
                setResult(Contants.TASK_REFRESH);
                finish();
                Toast.makeText(mContext, "提交成功", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {


        @Override
        public void onImageClick(int postion, View imageView) {
            if (Detail != null) {
                Intent intent = new Intent(mContext, PictureCompareActivity.class);
                intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_BEFORE, Detail.getBeforeTreatment());
                intent.putExtra(PictureCompareActivity.EXTRA_IMAGE_AFTER, Detail.getAfterTreatment());
                startActivity(intent);
            }
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(getApplicationContext()).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);

        }
    };


    /**
     * 判断是否全部填入数据
     *
     * @return false是没有，true是通过
     */
    private boolean isempty() {
        if (nextUnit.getText().toString().isEmpty()) {
            return false;
        }
        if (tvDescription.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.TASK_REFRESH) {
            if (resultCode == Contants.TASK_REFRESH) {
                setResult(Contants.TASK_REFRESH);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        imagecycle.pushImageCycle(true);
    }
}
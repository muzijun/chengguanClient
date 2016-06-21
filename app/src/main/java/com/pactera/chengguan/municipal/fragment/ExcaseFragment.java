package com.pactera.chengguan.municipal.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.app.util.ProgressDlgUtil;
import com.pactera.chengguan.municipal.activity.CaseListActivity;
import com.pactera.chengguan.municipal.activity.ImageZoomActivity;
import com.pactera.chengguan.municipal.activity.InputActivity;
import com.pactera.chengguan.municipal.activity.SelectActivity;
import com.pactera.chengguan.municipal.adapter.ImagePublishAdapter;
import com.pactera.chengguan.municipal.base.BaseFragment;
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
import com.pactera.chengguan.municipal.util.MunicipalUtils;
import com.pactera.chengguan.municipal.util.UploadFileManager;
import com.pactera.chengguan.municipal.view.NoScrollGridView;
import com.pactera.chengguan.municipal.view.dialog.CommonDialog;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 考核案件
 * Created by lijun on 2016/3/8.
 */
public class ExcaseFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener
        , RequestListener, FileUploadListener {
    private static final int REQUEST_IMAGE = 2;
    //案件详情按钮
    @Bind(R.id.case_list)
    TextView caseList;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.start_location)
    ImageView startLocation;
    @Bind(R.id.tv_location)
    TextView tvLocation;
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
    @Bind(R.id.mapview)
    MapView mMapView;

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

    //当前位置
    private String STATE_LOCATION = "STATE_LOCATION";
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
        mMapView.displayZoomControls(true);
        mMapView.setVisibility(View.GONE);
        unitText.setText(MunicipalCache.sectionList.get(0).getName());
        selectSectionID = MunicipalCache.sectionList.get(0).getId();
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(mContext, mDataList);
        gridview.setAdapter(mAdapter);
        caseList.setOnClickListener(this);
        startLocation.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
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
        if (MunicipalCache.sectionList != null) {
            for (int i = 0; i < MunicipalCache.sectionList.size(); i++) {
                String unit = MunicipalCache.sectionList.get(i).getName();
                mSelectData_unit.add(unit);
            }
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
        getLocationByTianDitu();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(locationListener);
            mLocationManager = null;
        }
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
            //位置
            else if (event.getType().equals(STATE_LOCATION)) {
                String msg = event.getmMsg();
                tvLocation.setText(msg);
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
            case R.id.start_location:
                isSearchPos = false;
                break;
            case R.id.tv_location:
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_LOCATION);
                intent.putExtra("content", tvLocation.getText().toString());
                intent.putExtra("title", "案件位置");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.unit_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelectData_unit);
                intent.putExtra("title", "作业单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", unitText.getText().toString());
                startActivity(intent);
                break;
            case R.id.month_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_MONTH);
                intent.putStringArrayListExtra("data", mSelectData_month);
                intent.putExtra("title", "月份");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", monthText.getText().toString());
                startActivity(intent);
                break;
            case R.id.type_lin:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "考核类型");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", typeText.getText().toString());
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
        CommonDialog dialog = new CommonDialog(mContext, R.style.dialog_dimenable, new CommonDialog.OnClickDialogListener() {
            @Override
            public void onClickOkBtn() {
                if (mDataList.size() <= 0) {
                    Toast.makeText(mContext, "请添加图片!", Toast.LENGTH_LONG).show();
                    return;
                }
                UploadFileManager uploadFileManager = new UploadFileManager(mContext, ExcaseFragment.this
                        , MunicipalContants.UPLOAD_OBJECT_CASE, 0, "0");
                uploadFileManager.upLoadMulFiles(transformToFileDataList(mDataList));
                AppUtils.setClickable(mContext, commit, false);
            }

            @Override
            public void onClickCancelBtn() {
            }
        });
        dialog.show();
        dialog.setDialogTitle("信息提示");
        dialog.setDialogContent("保存信息并开始新案件");

    }

    private List<UploadFileManager.UpLoadFileData> transformToFileDataList(List<String> dataList) {
        List<UploadFileManager.UpLoadFileData> fileDataList = new ArrayList<UploadFileManager.UpLoadFileData>();
        for (String path : dataList) {
            UploadFileManager.UpLoadFileData fileData = new UploadFileManager.UpLoadFileData(path
                    , UploadFileManager.UPLOAD_TYPE_IMG);
            fileDataList.add(fileData);
        }
        return fileDataList;
    }

    @Override
    public void onCompleted(List<String> fileUrlList, String Url) {
        Message msg = Message.obtain(mHandler, 2, fileUrlList);
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError() {
        AppUtils.setClickable(mContext, commit, true);
        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.onPause();
        } else {
            this.onResume();
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(mContext, newCaseHandler);
    }

    @Override
    public void fail() {
        AppUtils.setClickable(mContext, commit, true);
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler newCaseHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            AppUtils.setClickable(mContext, commit, true);
            Toast.makeText(getActivity(), "提交新案件成功!", Toast.LENGTH_LONG).show();
            resetImageAdapter();
            mHandler.sendEmptyMessage(3);
        }

        @Override
        public void doError(int result, String message) {
            AppUtils.setClickable(mContext, commit, true);
            Toast.makeText(getActivity(), "提交新案件失败：" + result + " | msg:" + message, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 重置图片添加内容
     */
    private void resetImageAdapter() {
        mDataList.clear();
        mAdapter.notifyDataSetChanged();
    }

    private LocationManager mLocationManager;
    private Location mLastLocation;
    private Location mPostLocation;
    private boolean isSearchPos = false;

    private void getLocationByTianDitu() throws SecurityException {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (mLastLocation == null && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (mLocationManager != null) {
            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000L, 0, locationListener);
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000L, 0, locationListener);
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null && !isSearchPos) {
                isSearchPos = true;
                mLastLocation = location;
                int lat = (int) (mLastLocation.getLatitude() * 1000000);
                int lon = (int) (mLastLocation.getLongitude() * 1000000);
                GeoPoint geoPoint = new GeoPoint(lat, lon);
                searchPos(geoPoint);
                mPostLocation = mLastLocation;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) throws SecurityException {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l != null && !isSearchPos) {
                isSearchPos = true;
                mLastLocation = l;
                int lat = (int) (mLastLocation.getLatitude() * 1000000);
                int lon = (int) (mLastLocation.getLongitude() * 1000000);
                GeoPoint geoPoint = new GeoPoint(lat, lon);
                searchPos(geoPoint);
                mPostLocation = mLastLocation;
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void searchPos(GeoPoint gPoint) {
        TGeoDecode tGeoDecode = new TGeoDecode(new TGeoDecode.OnGeoResultListener() {
            @Override
            public void onGeoDecodeResult(TGeoAddress tGeoAddress, int i) {
                if (i == 0 && tGeoAddress != null) {
                    mHandler.sendMessage(Message.obtain(mHandler, 0, tGeoAddress));
                }
            }
        });
        tGeoDecode.search(gPoint);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    TGeoAddress tGeoAddress = (TGeoAddress) msg.obj;
                    tvLocation.setText(tGeoAddress.getFullName());
                    break;
                case 1:
                    ProgressDlgUtil.stopProgressDlg();
                    Toast.makeText(mContext, "图片上传失败, 请重新操作!", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    List<String> fileUrlList = (List<String>) msg.obj;
                    ProgressDlgUtil.stopProgressDlg();
                    double lon = 0, lat = 0;
                    if (mPostLocation != null) {
                        lon = mPostLocation.getLongitude();
                        lat = mPostLocation.getLatitude();
                    }
                    MunicipalRequest.requestCreateCase(mContext, ExcaseFragment.this, 1, null, null, "0", 0
                            , tvLocation.getText().toString(), lon, lat, selectSectionID, selectTypeIndex + 1
                            , selectMonthIndex + 1, fileUrlList);
                    break;
                case 3:
                    isSearchPos = false;
                    tvLocation.setText("");
                    break;
            }
        }
    };

}

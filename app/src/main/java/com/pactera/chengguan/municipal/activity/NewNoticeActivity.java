package com.pactera.chengguan.municipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.util.ProgressDlgUtil;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.model.municipal.PageNotice;
import com.pactera.chengguan.municipal.util.AppUtils;
import com.pactera.chengguan.municipal.util.FileUploadListener;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.util.UploadFileManager;
import com.pactera.chengguan.municipal.view.PopMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 新建通知
 * Created by lijun on 2016/4/21.
 */
public class NewNoticeActivity extends BaseActivity implements PopMenu.OnItemClickListener, View.OnClickListener, FileUploadListener, RequestListener {
    @Bind(R.id.lin_file)
    LinearLayout linFile;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_unit)
    TextView tvUnit;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.title)
    TextView title;
    //标题
    private String STATE_TITLE = "STATE_TITLE";
    //单位
    private String STATE_UNIT = "STATE_UNIT";
    //内容
    private String STATE_CONTENT = "STATE_CONTENT";
    private PopMenu popMenu;
    public static final int REQUEST_REFRESH = 3;
    private static final int REQUEST_FILE = 2;
    private PageNotice pageNotice;
    //作业单位集合
    private ArrayList<String> mSelectData_unit = new ArrayList<String>();
    private ArrayList<String> mDataList = new ArrayList<String>();
    private String unitId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnotice);
        ButterKnife.bind(this);
        init();
        EventBus.getDefault().register(this);
    }

    private void init() {
        title.setText("通知");
        popMenu = new PopMenu(mContext);
        pageNotice = (PageNotice) getIntent().getSerializableExtra("notice");
        if (pageNotice != null) {
            tvTitle.setText(pageNotice.getNoticeTitle());
            tvContent.setText(pageNotice.getNoticeContent());
            tvUnit.setText(pageNotice.getSections());
        }
        for (int i = 0; i < MunicipalCache.sectionList.size(); i++) {
            String unit = new String(MunicipalCache.sectionList.get(i).getName());
            mSelectData_unit.add(unit);
        }
        popMenu.addItems(new String[]{"保存", "下发"});
        popMenu.setOnItemClickListener(this);
        addView(lin);
        linFile.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvUnit.setOnClickListener(this);
        tvContent.setOnClickListener(this);
    }


    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            //标题
            if (event.getType().equals(STATE_TITLE)) {
                String msg = event.getmMsg();
                tvTitle.setText(msg);

            }
            //单位
            else if (event.getType().equals(STATE_UNIT)) {
                String msg = event.getmMsg();
                tvUnit.setText(msg);
                unitId="";
                List<String> stringList = AppUtils.convertStrToArray(msg);
                for (int i = 0; i < stringList.size(); i++) {
                    unitId = unitId + AppUtils.getsectionid(stringList.get(i)) + ",";
                }
                if (unitId.length() != 0) {
                    unitId = unitId.substring(0, unitId.length() - 1);
                }
            }
            //内容
            else if (event.getType().equals(STATE_CONTENT)) {
                String msg = event.getmMsg();
                tvContent.setText(msg);
            }
        }
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.lin_file:
                intent = new Intent(mContext, FileActivity.class);
                startActivityForResult(intent, REQUEST_FILE);
                break;
            case R.id.tv_title:
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_TITLE);
                intent.putExtra("content", tvTitle.getText().toString());
                intent.putExtra("title", "通知标题");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tv_unit:
                intent = new Intent(mContext, SelectNoticeActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelectData_unit);
                intent.putExtra("title", "下发单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvUnit.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_content:
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", STATE_CONTENT);
                intent.putExtra("content", tvContent.getText().toString());
                intent.putExtra("title", "通知内容");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onItemClick(int index) {
        switch (index) {
            //保存
            case 0:
                if (iscancommit()) {
                    UploadFileManager uploadFileManager = new UploadFileManager(this, this
                            , MunicipalContants.UPLOAD_OBJECT_NOTICE, 0, "0");
                    uploadFileManager.upLoadMulFiles(transformToFileDataList(mDataList));
                }
                break;
            //下发
            case 1:
                if (iscancommit()) {
                    UploadFileManager uploadFileManager = new UploadFileManager(this, this
                            , MunicipalContants.UPLOAD_OBJECT_NOTICE, 0, "1");
                    uploadFileManager.upLoadMulFiles(transformToFileDataList(mDataList));
                }
                break;

        }
    }

    private List<UploadFileManager.UpLoadFileData> transformToFileDataList(List<String> dataList) {
        List<UploadFileManager.UpLoadFileData> fileDataList = new ArrayList<UploadFileManager.UpLoadFileData>();
        for (String path : dataList) {
            UploadFileManager.UpLoadFileData fileData = new UploadFileManager.UpLoadFileData(path
                    , UploadFileManager.UPLOAD_TYPE_FILE);
            fileDataList.add(fileData);
        }
        return fileDataList;
    }

    @Override
    public void onCompleted(List<String> fileUrlList, String param) {
        String title = tvTitle.getText().toString();
        String content = tvContent.getText().toString();
        String unit = tvUnit.getText().toString();
        String id = "";
        if (pageNotice != null) {
            id = pageNotice.getNoticeMessageId();
        }
        MunicipalRequest.requestAddUpdateNotice(mContext, this, id, title, content, unitId, fileUrlList, Integer.valueOf(param));
    }

    @Override
    public void onError() {
        ProgressDlgUtil.stopProgressDlg();
        Toast.makeText(mContext, "文件上传失败, 请重新操作!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "提交成功!", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "提交失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 提交前的判断
     */
    private boolean iscancommit() {
        String title = tvTitle.getText().toString();
        String content = tvContent.getText().toString();
        String unit = tvUnit.getText().toString();
        if (title.equals("") || content.equals("") || unit.equals("")) {
            Toast.makeText(mContext, "请先补全数据", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILE) {
            if (resultCode == mContext.RESULT_OK) {
                mDataList = data.getStringArrayListExtra(FileActivity.EXTRA_RESULT);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

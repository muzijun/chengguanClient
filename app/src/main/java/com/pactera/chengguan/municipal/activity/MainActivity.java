package com.pactera.chengguan.municipal.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.AppVersionBean;
import com.pactera.chengguan.municipal.fragment.BasicDataFragment;
import com.pactera.chengguan.municipal.fragment.MaintainManageFragment;
import com.pactera.chengguan.municipal.fragment.MonitorFragment;
import com.pactera.chengguan.municipal.fragment.MoreFragment;
import com.pactera.chengguan.municipal.model.PushData;
import com.pactera.chengguan.municipal.util.FileDownloadUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.DoubleClickExitHelper;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by lijun on 2015/12/2.
 */
public class MainActivity extends com.pactera.chengguan.municipal.base.BaseActivity implements View.OnClickListener, com.pactera.chengguan.municipal.config.RequestListener {

    @Bind(R.id.content)
    FrameLayout content;

    /**
     * 在Tab布局上显示案件图片的控件
     */
    @Bind(R.id.case_image)
    ImageView caseImage;
    /**
     * 在Tab布局上显示案件的控件
     */
    @Bind(R.id.case_text)
    TextView caseText;
    /**
     * 案件界面布局
     */
    @Bind(R.id.case_layout)
    RelativeLayout caseLayout;
    /**
     * 在Tab布局上显示通知图片的控件
     */
    @Bind(R.id.notice_image)
    ImageView noticeImage;
    /**
     * 在Tab布局上显示通知的控件
     */
    @Bind(R.id.notice_text)
    TextView noticeText;
    /**
     * 通知界面布局
     */
    @Bind(R.id.notice_layout)
    RelativeLayout noticeLayout;
    /**
     * 在Tab布局上显示广告图片的控件
     */
    @Bind(R.id.advertise_image)
    ImageView advertiseImage;
    /**
     * 在Tab布局上显示广告的控件
     */
    @Bind(R.id.advertise_text)
    TextView advertiseText;
    /**
     * 广告界面布局
     */
    @Bind(R.id.advertise_layout)
    RelativeLayout advertiseLayout;
    /**
     * 在Tab布局上显示更多的控件
     */
    @Bind(R.id.more_image)
    ImageView moreImage;
    /**
     * 在Tab布局上显示更多的控件
     */
    @Bind(R.id.more_text)
    TextView moreText;
    /**
     * 更多界面布局
     */
    @Bind(R.id.more_layout)
    RelativeLayout moreLayout;
    private DoubleClickExitHelper mDoubleClickExit;

    /**
     * 用于案件的Fragment
     */
    private MaintainManageFragment tabOneFragment;

    /**
     * 用于广告的Fragment
     */
    private BasicDataFragment basicDataFragment;

    /**
     * 用于通知的Fragment
     */
    private MonitorFragment tabTwoFragment;
    /**
     * 更多Fragment
     */
    private MoreFragment tabFourFragment;


    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 初始化布局元素
        initViews();
        fragmentManager = getSupportFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        checkAppVersion();
        pushIntent();
    }

    private void checkAppVersion() {
        ChengApplication.instance.hasNewVersion = false;
        MunicipalRequest.requestGetAppVersion(this, this);
    }

    /**
     * 收到推送逻辑跳转
     */
    private void pushIntent() {
        PushData push = (PushData) getIntent().getSerializableExtra(Contants.PUSH_JSON);
        if (push != null) {
            String type = push.getType();
            if (type.equals(Contants.NOTICE)) {
                Intent intent = new Intent(mContext, NoticeListActivity.class);
                startActivity(intent);
            } else if (type.equals(Contants.ISSUE)) {
                Intent intent = new Intent(mContext, MaintainListAcivity.class);
                startActivity(intent);
            } else if (type.equals(Contants.WORK_CONTACT_FORM) || type.equals(Contants.WORK_TASK_FORM)) {
                Intent intent = new Intent(mContext, ContactSheetActivity.class);
                intent.putExtra(Contants.PUSH_JSON, push);
                startActivity(intent);
            } else if (type.equals(Contants.CASE_POINTS_TYPE)) {
                Intent intent = new Intent(mContext, CaseListActivity.class);
                startActivity(intent);
            }


        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        AppVersionBean versionBean = (AppVersionBean) result;
        versionBean.checkResult(this, versionHandler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler versionHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "获取app新版本成功!", Toast.LENGTH_LONG).show();
            AppVersionBean versionBean = (AppVersionBean) baseBean;
            if (versionBean.datas != null && !versionBean.datas.url.isEmpty()) {
                ChengApplication.instance.hasNewVersion = true;
                appUrl = versionBean.datas.url;
                appDesc = versionBean.datas.desc;
                fileSize = versionBean.datas.fileSize;
                mHandler.sendEmptyMessage(0);
            }
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取app新版本失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    private String appUrl, appDesc;
    private long fileSize;
    private ProgressDialog pBar = null;
    public static final int DOWN_NOTIFY = 1;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateVersion(appUrl, appDesc, fileSize);
                    break;
                case DOWN_NOTIFY:
                    String message = (String) msg.obj;
                    if (pBar != null) {
                        pBar.setProgress(Integer.parseInt(message));
                        if ("100".equalsIgnoreCase(message)) {
                            pBar.cancel();
                            pBar = null;
                        }
                    }
                    break;
            }
        }
    };

    private void updateVersion(final String url, final String desc, final long size) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("新版本升级");
        dialog.setMessage(Html.fromHtml(desc));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pBar = new ProgressDialog(MainActivity.this);
                pBar.setCancelable(false);
                pBar.setCanceledOnTouchOutside(false);
                pBar.setTitle("进度条");
                pBar.setMessage("版本升级中请稍候...");
                pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                downFile(url, size);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "下次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void downFile(String url, long size) {
        pBar.show();
        FileDownloadUtils.versionUpdate(this, new FileCallBack(FileDownloadUtils.getAppDir(), "update.apk") {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                pBar.cancel();
                pBar = null;
            }

            @Override
            public void onResponse(File response, int id) {
                pBar.cancel();
                pBar = null;
                if (response.exists()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(response), "application/vnd.android.package-archive");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                float number = 100 * progress;
                pBar.setProgress((int) number);
            }

        }, url);
    }
//    private void downFile(String url, long size) {
//        pBar.show();
//        FileDownloadUtils.versionUpdate(this, mHandler, url, size);
//    }


    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        mDoubleClickExit = new com.pactera.chengguan.municipal.view.DoubleClickExitHelper(this);
        caseLayout.setOnClickListener(this);
        advertiseLayout.setOnClickListener(this);
        noticeLayout.setOnClickListener(this);
        moreLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.case_layout:
                // 选中第1个tab
                setTabSelection(0);
                break;
            case R.id.notice_layout:
                // 选中第2个tab
                setTabSelection(1);
                break;
            case R.id.advertise_layout:
                // 选中第3个tab
                setTabSelection(2);
                break;
            case R.id.more_layout:
                //选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                caseImage.setImageResource(R.mipmap.ico_case_on);
                caseText.setTextColor(getResources().getColor(R.color.color21b6ec));
                if (tabOneFragment == null) {
                    // TabOneFragment，则创建一个并添加到界面上
                    tabOneFragment = new MaintainManageFragment();
                    transaction.add(R.id.content, tabOneFragment);
                } else {
                    // 如果CaseFragment不为空，则直接将它显示出来
                    transaction.show(tabOneFragment);
                }
                break;
            case 1:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                noticeImage.setImageResource(R.mipmap.ico_notice_on);
                noticeText.setTextColor(getResources().getColor(R.color.color21b6ec));
                if (tabTwoFragment == null) {
                    // 如果NoticeFragment为空，则创建一个并添加到界面上
                    tabTwoFragment = new MonitorFragment();
                    transaction.add(R.id.content, tabTwoFragment);
                } else {
                    // 如果NoticeFragment不为空，则直接将它显示出来
                    transaction.show(tabTwoFragment);
                }
                break;
            case 2:

                // 当点击了动态tab时，改变控件的图片和文字颜色
                advertiseImage.setImageResource(R.mipmap.ico_advertise_on);
                advertiseText.setTextColor(getResources().getColor(R.color.color21b6ec));
                if (basicDataFragment == null) {
                    // BasicDataFragment，则创建一个并添加到界面上
                    basicDataFragment = new BasicDataFragment();
                    transaction.add(R.id.content, basicDataFragment);
                } else {
                    // BasicDataFragment，则直接将它显示出来
                    transaction.show(basicDataFragment);
                }
                break;

            case 3:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                moreImage.setImageResource(R.mipmap.ico_more_on);
                moreText.setTextColor(getResources().getColor(R.color.color21b6ec));
                if (tabFourFragment == null) {
                    // PersonalFragment，则创建一个并添加到界面上
                    tabFourFragment = new MoreFragment();
                    transaction.add(R.id.content, tabFourFragment);
                } else {
                    // PersonalFragment，则直接将它显示出来
                    transaction.show(tabFourFragment);
                }
                break;
        }

        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        caseImage.setImageResource(R.mipmap.ico_case);
        caseText.setTextColor(Color.parseColor("#82858b"));
        advertiseImage.setImageResource(R.mipmap.ico_advertise);
        advertiseText.setTextColor(Color.parseColor("#82858b"));
        noticeImage.setImageResource(R.mipmap.ico_notice);
        noticeText.setTextColor(Color.parseColor("#82858b"));
        moreImage.setImageResource(R.mipmap.ico_more);
        moreText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (tabOneFragment != null) {
            transaction.hide(tabOneFragment);
        }
        if (basicDataFragment != null) {
            transaction.hide(basicDataFragment);
        }
        if (tabTwoFragment != null) {
            transaction.hide(tabTwoFragment);
        }
        if (tabFourFragment != null) {
            transaction.hide(tabFourFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}

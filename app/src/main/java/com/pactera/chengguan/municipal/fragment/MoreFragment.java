package com.pactera.chengguan.municipal.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.activity.ContactSheetActivity;
import com.pactera.chengguan.municipal.activity.ContactsActivity;
import com.pactera.chengguan.municipal.activity.LoginActivity;
import com.pactera.chengguan.municipal.activity.MaintainListAcivity;
import com.pactera.chengguan.municipal.activity.NoticeListActivity;
import com.pactera.chengguan.municipal.activity.UserActivity;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.AppVersionBean;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.util.FileDownloadUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.util.SPUtils;
import com.pactera.chengguan.municipal.view.PushNotification;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 更多
 * Created by lijun on 2015/12/2.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener, RequestListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.tv_contacts)
    TextView tvContacts;
    @Bind(R.id.tv_contactsheet)
    TextView tvContactsheet;
    @Bind(R.id.tv_maintenance)
    TextView tvMaintenance;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.tv_clearcache)
    TextView tvClearCache;
    @Bind(R.id.logoff)
    Button btnLogoff;
    @Bind(R.id.logo_new_version)
    ImageView ivNewVersion;
    @Bind(R.id.layout_update_version)
    RelativeLayout updateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_more, inflater);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        title.setText(mRes.getString(R.string.title_more));
        backImg.setVisibility(View.GONE);
        if(ChengApplication.instance.hasNewVersion){
            ivNewVersion.setVisibility(View.VISIBLE);
        }else{
            ivNewVersion.setVisibility(View.GONE);
        }
        tvContactsheet.setOnClickListener(this);
        tvContacts.setOnClickListener(this);
        tvMaintenance.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tvUser.setOnClickListener(this);
        tvClearCache.setOnClickListener(this);
        btnLogoff.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_contactsheet:
                intent = new Intent(mContext, ContactSheetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_contacts:
                intent = new Intent(mContext, ContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_maintenance:
                intent = new Intent(mContext, MaintainListAcivity.class);
                startActivity(intent);
                break;
            case R.id.tv_notice:
                intent = new Intent(mContext, NoticeListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_user:
                intent = new Intent(mContext, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.logoff:
                MunicipalRequest.requestUserLogout(mContext, this);
                break;
            case R.id.layout_update_version:
                MunicipalRequest.requestGetAppVersion(mContext, this);
                break;
            case R.id.tv_clearcache:
                clearCache();
                break;
        }
    }

    /**
     * 清除缓存，主要清除图片缓存
     */
    private void clearCache(){
        new ClearCacheTask().execute();
    }

    private class ClearCacheTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                clearCache(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mHandler.sendEmptyMessage(CLEAR_CACHE_DONE);
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        if(reqUrl.equals(Contants.USER_LOGOUT)) {
            BaseBean baseBean = (BaseBean) result;
            baseBean.checkResult(mContext, logoutHandler);
        }else if(reqUrl.equals(Contants.GET_APP_VERSION)){
            AppVersionBean versionBean = (AppVersionBean) result;
            versionBean.checkResult(mContext, versionHandler);
        }
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler logoutHandler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            PushNotification.clearAllNotify(mContext);
            Toast.makeText(mContext, "注销成功!", Toast.LENGTH_LONG).show();
            SPUtils.remove(mContext, MunicipalCache.SP_TOKEN);
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "注销失败:"+message, Toast.LENGTH_LONG).show();
        }
    };

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
    public static final int CLEAR_CACHE_DONE = 2;

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
                case CLEAR_CACHE_DONE:
                    Toast.makeText(mContext, "缓存清除完毕!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void updateVersion(final String url, final String desc, final long size) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("新版本升级");
        dialog.setMessage(Html.fromHtml(desc));
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pBar = new ProgressDialog(mContext);
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
        FileDownloadUtils.versionUpdate(mContext, new FileCallBack(FileDownloadUtils.getAppDir(), "update.apk") {
            @Override
            public void inProgress(float progress, long total, int id) {
                float number = 100 * progress;
                pBar.setProgress((int) number);
            }

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
                    mContext.finish();
                }
            }
        }, url);
    }


//    private void downFile(String url, long size) {
//        pBar.show();
//        FileDownloadUtils.versionUpdate(mContext, mHandler, url, size);
//    }

    /**
     * 获得需要清理的缓存地址
     * @return
     */
    public File[] getCacheNeed2Clear(Context context){
        String imgCompressPath = FileDownloadUtils.makeRootDirectory(Contants.PATH_SZ
                + Contants.PATH_IMG_COMPRESS);
        String imgDownloadPath = FileDownloadUtils.makeRootDirectory(Contants.PATH_SZ
                + Contants.PATH_IMG_CACHE);
        File[] file = new File[2];
        file[0] = new File(imgCompressPath);
        file[1] = new File(imgDownloadPath);
        return file;
    }

    /**
     * 清除缓存
     * @param context
     */
    public void clearCache(Context context) {
        File[] file = getCacheNeed2Clear(context);
        for (int index = 0; index < file.length; index++) {
            File individualCacheDir = file[index];
            if (individualCacheDir.exists()) {
                File[] files = individualCacheDir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].exists()) {
                        deleteFile(files[i]);
                    }
                }
            }
        }
    }

    public void deleteFile(File file){
        if(!file.isDirectory()){
            file.delete();
        }else{
            File[] files = file.listFiles();
            for(File mFile : files){
                deleteFile(mFile);
            }
            file.delete();
        }
    }
}

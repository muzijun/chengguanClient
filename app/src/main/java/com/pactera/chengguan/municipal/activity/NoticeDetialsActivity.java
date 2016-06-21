package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.FileDownAdapter;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.municipal.NoticeDetailBean;
import com.pactera.chengguan.municipal.model.FileDown;
import com.pactera.chengguan.municipal.model.municipal.NoticeDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知详细
 * Created by lijun on 2016/5/3.
 */
public class NoticeDetialsActivity extends com.pactera.chengguan.municipal.base.BaseActivity implements com.pactera.chengguan.municipal.config.RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.noscrolllistview_file)
    com.pactera.chengguan.municipal.view.NoScrollListView noscrolllistviewFile;
    @Bind(R.id.tv_unit)
    TextView tvUnit;
    @Bind(R.id.noscrolllistview_reply)
    com.pactera.chengguan.municipal.view.NoScrollListView noscrolllistviewReply;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticedetials);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("通知");
        id = getIntent().getStringExtra("notice");
        noscrolllistviewFile.setDividerHeight(0);
        com.pactera.chengguan.municipal.util.MunicipalRequest.requestShowNotice(mContext, this, id);
    }


    @Override
    public void success(String reqUrl, Object result) {
        NoticeDetailBean noticeDetailBean = (NoticeDetailBean) result;
        noticeDetailBean.checkResult(mContext, Handler);
    }

    @Override
    public void fail() {

    }

    private com.pactera.chengguan.municipal.bean.BaseHandler Handler = new com.pactera.chengguan.municipal.bean.BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            NoticeDetailBean noticeDetailBean = (NoticeDetailBean) baseBean;
            if (noticeDetailBean.datas != null) {
                NoticeDetail noticeDetail = noticeDetailBean.datas.transformToNoticeDetail();
                tvTitle.setText(noticeDetail.getNoticeTitle());
                tvDate.setText(noticeDetail.getIssuedTime());
                tvContent.setText(noticeDetail.getNoticeContent());
                ArrayList<FileDown> fileDowns = new ArrayList<FileDown>();
                for (NoticeDetail.FileData fileData : noticeDetail.getPhotoList()) {
                    FileDown fileDown = new FileDown();
                    fileDown.setPhotoName(fileData.getPhotoName());
                    fileDown.setPhotoPath(fileData.getPhotoPath());
                    fileDowns.add(fileDown);
                }
                noscrolllistviewFile.setAdapter(new FileDownAdapter(fileDowns, mContext));
                noscrolllistviewReply.setAdapter(new ReplyAdapter(noticeDetail.getNoticeRespondList()));
                tvUnit.setText(noticeDetail.getSections());
            }
            Toast.makeText(mContext, "获取数据成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };


    /**
     * 通知回复的适配器
     */
    class ReplyAdapter extends BaseAdapter {
        private List<com.pactera.chengguan.municipal.model.municipal.NoticeDetail.Respond> respondList;

        public ReplyAdapter(List<com.pactera.chengguan.municipal.model.municipal.NoticeDetail.Respond> respondList) {
            this.respondList = respondList;

        }

        @Override
        public int getCount() {
            return respondList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            com.pactera.chengguan.municipal.model.municipal.NoticeDetail.Respond respond = respondList.get(position);
            if (convertView == null) {
                convertView = LayoutInflater
                        .from(mContext).inflate(R.layout.item_notice_reply, null, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(respond.getSectionName() + ":" + respond.getRespondContent());
            holder.tvDate.setText("时间:" + respond.getRespondTime());

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_date)
            TextView tvDate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

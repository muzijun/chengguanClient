package com.pactera.chengguan.municipal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.PageNotice;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知列表适配器
 * Created by lijun on 2016/4/20.
 */
public class NoticeListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private BaseActivity mContext;
    private List<PageNotice> pageNoticeList;

    public NoticeListAdapter(BaseActivity context, List<PageNotice> pageNoticeList) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.pageNoticeList = pageNoticeList;
    }

    @Override
    public int getCount() {
        return pageNoticeList.size();
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
        PageNotice pageNotice = pageNoticeList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_noticelist, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvNoticetitle.setText(pageNotice.getNoticeTitle());
        holder.tvRespondcount.setText(pageNotice.getRespondCount() + "人回复");
        holder.tvTime.setText(pageNotice.getIssuedTime());

        return convertView;
    }

    public void setNotifyChanged(List<PageNotice> pageNoticeList) {
        this.pageNoticeList = pageNoticeList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @Bind(R.id.tv_noticetitle)
        TextView tvNoticetitle;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_respondcount)
        TextView tvRespondcount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

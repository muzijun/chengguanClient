package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.model.Refuseparam;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 返工日志
 * Created by lijun on 2016/4/18.
 */
public class ReworkAdapter extends BaseAdapter {
    private Context mContext;
    private List<com.pactera.chengguan.municipal.model.Refuseparam> refuseList;

    public ReworkAdapter(Context context, List<Refuseparam> refuseList) {
        this.mContext = context;
        this.refuseList = refuseList;
    }

    @Override
    public int getCount() {
        return refuseList.size();
    }

    @Override
    public Object getItem(int position) {
        return refuseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Refuseparam refuse = refuseList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_rework, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(refuse.getRefuseOpinion());
        holder.tvDate.setText(refuse.getCreateTime());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

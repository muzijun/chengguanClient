package com.pactera.chengguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.model.municipal.CaseInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 流程日志适配器
 * Created by lijun on 2016/3/10.
 */
public class ProcessRecordAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private CaseInfo caseInfo;

    public ProcessRecordAdapter(Context context, CaseInfo caseInfo) {
        mContext = context;
        this.caseInfo = caseInfo;
        inflater = LayoutInflater.from(context);
    }

    public void setNotifyDataChanged(CaseInfo caseInfo){
        this.caseInfo = caseInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return caseInfo.getProcessLogList().size();
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
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.processrecord_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        CaseInfo.CaseProcessLog processLog = caseInfo.getProcessLogList().get(position);
        holder.node.setText(processLog.getLinkContent());
        holder.user.setText(processLog.getAttn());
        holder.date.setText(processLog.getProcessDate());
        holder.content.setText(processLog.getOpinion());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.node)
        TextView node;
        @Bind(R.id.user)
        TextView user;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

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
 * 延期记录适配器
 * Created by lijun on 2016/3/24.
 */
public class DelayRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private CaseInfo caseInfo;

    public DelayRecordAdapter(Context context, CaseInfo caseInfo) {
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
        return caseInfo.getExceedLogList().size();
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
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_delay_record, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CaseInfo.CaseExceedLog exceedLog = caseInfo.getExceedLogList().get(position);
        holder.txDay.setText(exceedLog.getExceedDays()+"天");
        holder.txDate.setText(exceedLog.getExceedDate());
        holder.txContent.setText(exceedLog.getReason());
        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.tx_day)
        TextView txDay;
        @Bind(R.id.tx_date)
        TextView txDate;
        @Bind(R.id.tx_content)
        TextView txContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.pactera.chengguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.chengguan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 延期记录适配器
 * Created by lijun on 2016/3/24.
 */
public class DelayRecordAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;

    public DelayRecordAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
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

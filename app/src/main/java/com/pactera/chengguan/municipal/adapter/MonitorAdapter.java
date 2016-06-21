package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.config.MonitorListener;
import com.pactera.chengguan.municipal.config.MunicipalContants;
import com.pactera.chengguan.municipal.model.municipal.PatrolRecord;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 巡查监控适配器
 * Created by lijun on 2016/4/12.
 */
public class MonitorAdapter extends BaseAdapter {
    private Context mContext;
    private MonitorListener monitorListener;
    private List<PatrolRecord> Datas;


    public MonitorAdapter(Context context, MonitorListener monitorListener, List<PatrolRecord> Data) {
        this.mContext = context;
        this.monitorListener = monitorListener;
        this.Datas = Data;

    }

    @Override
    public int getCount() {
        return Datas.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PatrolRecord patrolRecord = Datas.get(position);
        convertView = LayoutInflater.from(mContext).
                inflate(R.layout.item_monitor, null, false);
        ImageView image_type = ButterKnife.findById(convertView, R.id.image_type);
        ImageView image_state = ButterKnife.findById(convertView, R.id.image_state);
        ImageView image_points = ButterKnife.findById(convertView, R.id.image_points);
        TextView tv_points = ButterKnife.findById(convertView, R.id.tv_points);
        TextView tv_name = ButterKnife.findById(convertView, R.id.tv_name);
        TextView remarks = ButterKnife.findById(convertView, R.id.remarks);
        View item_monitor_content = ButterKnife.findById(convertView, R.id.item_monitor_content);
        item_monitor_content.setVisibility(View.GONE);
        if (patrolRecord.getRecordStatus() == PatrolRecord.STATUS_SUCCESS) {
            image_state.setImageResource(R.mipmap.icon_oval);
            image_points.setVisibility(View.INVISIBLE);
            tv_points.setVisibility(View.INVISIBLE);
        } else if (patrolRecord.getRecordStatus() == PatrolRecord.STATUS_FAIL) {
            image_state.setImageResource(R.mipmap.icon_fork);
            image_points.setVisibility(View.VISIBLE);
            image_points.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monitorListener.Clickpoints(patrolRecord);
                }
            });
            image_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monitorListener.Clickstate(patrolRecord, position);
                }
            });
            tv_points.setVisibility(View.INVISIBLE);
        } else if (patrolRecord.getRecordStatus() == PatrolRecord.STATUS_POINTS_DONE) {
            image_state.setImageResource(R.mipmap.icon_fork);
            image_points.setVisibility(View.INVISIBLE);
            tv_points.setVisibility(View.VISIBLE);
            tv_points.setText("" + patrolRecord.getPoints());
            image_points.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monitorListener.Clickstate(patrolRecord, position);
                }
            });
        } else if (patrolRecord.getRecordStatus() == PatrolRecord.STATUS_MODIFY_SUCCESS) {
            image_state.setImageResource(R.mipmap.icon_modify_success);
            item_monitor_content.setVisibility(View.VISIBLE);
            image_points.setVisibility(View.INVISIBLE);
            tv_points.setVisibility(View.INVISIBLE);
        }
        if (patrolRecord.getBaseType().equals(MunicipalContants.PATROL_TYPE_ROAD)) {
            image_type.setImageResource(R.mipmap.icon_road);
        } else if (patrolRecord.getBaseType().equals(MunicipalContants.PATROL_TYPE_BRIDGE)) {
            image_type.setImageResource(R.mipmap.icon_bridge);
        }
        tv_name.setText(patrolRecord.getFromName());
        remarks.setText("备注：" + patrolRecord.getComment());
        return convertView;
    }

    public void setNotifyChanged(List<PatrolRecord> patrolRecordList) {
        this.Datas = patrolRecordList;
        notifyDataSetChanged();
    }


}

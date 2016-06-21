package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.chengguan.R;

/**
 * Created by lijun on 2015/12/15.
 */
public class  SpinnerAdapter extends BaseAdapter {
    private String[] mList;
    private Context mContext;
    public SpinnerAdapter(Context pContext, String[] pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_item, null);
        TextView textView=(TextView)convertView.findViewById(R.id.text);
        textView.setText(mList[position]);
        return convertView;
    }
}

package com.pactera.chengguan.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pactera.chengguan.R;

/**
 * Created by lijun on 2015/12/3.
 */
public class ApprovalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    public ApprovalAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
         convertView = inflater
                .inflate(R.layout.approval_item, null, false);
        return convertView;
    }
}




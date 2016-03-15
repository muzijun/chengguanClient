package com.pactera.chengguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pactera.chengguan.R;

/**搜索适配器
 * Created by lijun on 2016/3/15.
 */
public class SearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;

    public SearchAdapter(Context context) {
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
        return inflater
                .inflate(R.layout.item_search, null, false);
    }
}

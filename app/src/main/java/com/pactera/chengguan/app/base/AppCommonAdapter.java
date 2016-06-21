package com.pactera.chengguan.app.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pactera.chengguan.app.view.AppViewHolder;

import java.util.List;

/**
 * @param <T>
 */
public abstract class AppCommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;
    protected int mLayoutId;

    public AppCommonAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppViewHolder holder = AppViewHolder.getHolder(mContext, convertView, mLayoutId, parent, position);
        convert(holder, position);
        return holder.getConvertView();
    }

    /**
     * get holder convert
     */
    public abstract void convert(AppViewHolder holder, int position);
}

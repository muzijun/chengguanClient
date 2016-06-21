package com.pactera.chengguan.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yetwish on 2015-05-11
 */

public class AppViewHolder {

    protected SparseArray<View> mViews;
    protected Context mContext;
    protected View mConvertView;
    protected int mPosition;

    /**
     * init holder
     */
    public AppViewHolder(Context context, int layoutId, ViewGroup parent, int position) {
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mViews = new SparseArray<View>();
        mPosition = position;
        mConvertView.setTag(this);
    }

    /**
     * @param context
     * @param convertView
     * @param layoutId
     * @param parent
     * @param position
     * @return
     */
    public static AppViewHolder getHolder(Context context, View convertView,
                                          int layoutId, ViewGroup parent, int position) {
        if (convertView == null) {
            return new AppViewHolder(context, layoutId, parent, position);
        } else {
            AppViewHolder holder = (AppViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * get view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * set text
     */
    public AppViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * set image res
     */
    public AppViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * set image bitmap
     */
    public AppViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
}

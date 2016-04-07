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
 * 公用长按list弹出框的适配器
 * Created by lijun on 2016/4/6.
 */
public class ListDialogAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private Context mContext;
    private String mContent[];

    public ListDialogAdapter(Context context, String content[]) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.mContent = content;
    }

    @Override
    public int getCount() {
        return mContent.length;
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
                    .inflate(R.layout.item_dialog, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(mContent[position]);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

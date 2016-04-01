package com.pactera.chengguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片对比适配器
 * Created by lijun on 2016/3/29.
 */
public class PictureCompareAdapter extends BaseAdapter {

    private Context mContext;

    public PictureCompareAdapter(Context context) {
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_picture_compare, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load("http://imga1.pic21.com/bizhi/140226/07916/s04.jpg").centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(holder.imageGrid);
        holder.txState.setText("处理前");
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.image_grid)
        ImageView imageGrid;
        @Bind(R.id.tx_state)
        TextView txState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


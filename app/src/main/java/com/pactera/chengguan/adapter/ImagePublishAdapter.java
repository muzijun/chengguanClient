package com.pactera.chengguan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.config.Contants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 添加图片
 */
public class ImagePublishAdapter extends BaseAdapter {
    private List<String> mDataList = new ArrayList<String>();
    private Context mContext;

    public ImagePublishAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    public int getCount() {
        // 多返回一个用于展示添加图标
        if (mDataList == null) {
            return 1;
        } else if (mDataList.size() == Contants.MAX_IMAGE_SIZE) {
            return Contants.MAX_IMAGE_SIZE;
        } else {
            return mDataList.size() + 1;
        }
    }

    public Object getItem(int position) {
        if (mDataList != null
                && mDataList.size() == Contants.MAX_IMAGE_SIZE) {
            return mDataList.get(position);
        } else if (mDataList == null || position - 1 < 0
                || position > mDataList.size()) {
            return null;
        } else {
            return mDataList.get(position - 1);
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        //所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(mContext,R.layout.item_publish, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isShowAddItem(position)) {
            holder.itemGridImage.setImageResource(R.drawable.btn_add_pic);
            holder.itemGridImage.setBackgroundResource(R.color.bg_gray);
        } else {
            final String path = mDataList.get(position);
            Glide.with(mContext).load(path).centerCrop().into(holder.itemGridImage);
        }

        return convertView;
    }

    private boolean isShowAddItem(int position) {
        int size = mDataList == null ? 0 : mDataList.size();
        return position == size;
    }

    public List<String> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<String> mDataList) {
        this.mDataList = mDataList;
    }

    static class ViewHolder {
        @Bind(R.id.item_grid_image)
        ImageView itemGridImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

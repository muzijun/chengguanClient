package com.pactera.chengguan.municipal.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.util.FileDownloadUtils;
import com.pactera.chengguan.municipal.view.ImageItemCycle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片对比适配器
 * Created by lijun on 2016/3/29.
 */
public class PictureCompareAdapter extends BaseAdapter {

    private BaseActivity mContext;
    private List<PicData> picDatas;
    private boolean before;

    public PictureCompareAdapter(BaseActivity context, List<PicData> picDatas, boolean state) {
        this.mContext = context;
        this.picDatas = picDatas;
        before = state;

    }

    @Override
    public int getCount() {
        return picDatas.size();
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
        PicData picData=picDatas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_picture_compare, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FileDownloadUtils.downloadLauncher(mContext, mCycleViewListener, holder.imageGrid, ChengApplication.instance.municipalApplication.access_token, picData);
        if (before) {
            holder.txState.setText("处理前");
        } else {
            holder.txState.setText("处理后");
        }
        return convertView;
    }
    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(int postion, View imageView) {

        }

        @Override
        public void displayImage( String imageURL,  ImageView imageView) {
            Glide.with(mContext.getApplicationContext()).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };
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


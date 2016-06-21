package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.chengguan.BuildConfig;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.municipal.model.FileDown;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**文件下载列表
 * Created by lijun on 2016/5/9.
 */
public class FileDownAdapter extends BaseAdapter {
    private List<FileDown> fileList;
    private Context mContext;

    public FileDownAdapter(List<FileDown> fileList,Context mcontext) {
        this.fileList = fileList;
        this.mContext=mcontext;

    }

    @Override
    public int getCount() {
        return fileList.size();
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
        final FileDown fileDown = fileList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(mContext).inflate(R.layout.item_file_down, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvFile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        holder.tvFile.getPaint().setAntiAlias(true);//抗锯齿
        holder.tvFile.setText(fileDown.getPhotoName());
        holder.tvFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String path= BuildConfig.BASE_URL + fileDown.getPhotoPath() + "&access_token=" + ChengApplication.instance.municipalApplication.access_token;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_file)
        TextView tvFile;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

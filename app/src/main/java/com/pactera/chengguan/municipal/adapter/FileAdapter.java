package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.util.FileUtil;


import java.util.ArrayList;

/**文件上传适配器
 * Created by lijun on 2015/12/29.
 */
public class FileAdapter extends BaseAdapter {
    private ArrayList<String> mfiles;
    private Context mContext;

    public FileAdapter(Context context, ArrayList<String> files) {
        this.mfiles = files;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mfiles.size();
    }

    @Override
    public Object getItem(int position) {
        return mfiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = FileUtil.getName(mfiles.get(position));
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_file, null, false);
            holder.file_name = (TextView) convertView.findViewById(R.id.file_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.file_name.setText(name);
        holder.file_name.setSelected(true);
        return convertView;
    }

    class ViewHolder {
        TextView file_name;// 时间
    }
    public ArrayList<String> getMfiles() {
        return mfiles;
    }

    public void setMfiles(ArrayList<String> mfiles) {
        this.mfiles = mfiles;
    }

    /**
     * 增加文件
     *
     * @param path 文件路径
     */
    public void addfile(String path) {
        mfiles.add(path);
        notifyDataSetChanged();
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     */
    public void deletefile(String path) {
        mfiles.remove(path);
        notifyDataSetChanged();
    }
}

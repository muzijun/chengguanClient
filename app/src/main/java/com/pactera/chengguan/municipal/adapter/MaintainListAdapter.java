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
import com.pactera.chengguan.municipal.model.municipal.PageIssue;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.view.ImageItemCycle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**养护列表适配器
 * Created by lijun on 2016/4/18.
 */
public class MaintainListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private BaseActivity mContext;
    private List<PageIssue> pageIssueList;

    public MaintainListAdapter(BaseActivity context, List<PageIssue> pageIssueList) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.pageIssueList = pageIssueList;
    }

    public void setNotifyChanged(List<PageIssue> pageIssueList){
        this.pageIssueList = pageIssueList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pageIssueList.size();
    }

    @Override
    public Object getItem(int position) {
        return pageIssueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_maintainlist, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        PageIssue pageIssue = pageIssueList.get(position);
        ArrayList<PicData> data = new ArrayList<PicData>();
        List<PicData> beforeList = pageIssue.getBeforeTreatmentS();
        if (beforeList != null && beforeList.size() > 0) {
            data.addAll(beforeList);
        }
        List<PicData> afterList = pageIssue.getAfterTreatmentS();
        if (afterList != null && afterList.size() > 0) {
            data.addAll(afterList);
        }
        if (data.size() > 0) {
            holder.imgItemCycle.setImageResources(mContext, data, mCycleViewListener
                    , ChengApplication.instance.municipalApplication.access_token, false);
        }
        holder.tvDate.setText(pageIssue.getCreateTime());
        holder.tvDesciption.setText(pageIssue.getIssueDescribe());
        holder.tvAddress.setText(pageIssue.getLocation());
        return convertView;
    }

    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(int postion, View imageView) {
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext.getApplicationContext()).load(imageURL).centerCrop().into(imageView);
        }
    };

    class ViewHolder {
        @Bind(R.id.imagecycle)
        ImageItemCycle imgItemCycle;
        @Bind(R.id.date)
        TextView tvDate;
        @Bind(R.id.desciption)
        TextView tvDesciption;
        @Bind(R.id.address)
        TextView tvAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

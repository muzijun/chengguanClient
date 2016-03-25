package com.pactera.chengguan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.ImagePagerActivity;
import com.pactera.chengguan.model.municipal.CaseInfo;
import com.pactera.chengguan.view.ImageItemCycle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 案件详情适配器
 * Created by lijun on 2016/3/9.
 */
public class CaseListAdapter extends BaseAdapter {
    private ArrayList<ArrayList<String>> infos = new ArrayList<ArrayList<String>>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    private LayoutInflater inflater;
    private Context mContext;
    private List<CaseInfo> caseInfoList;

    public CaseListAdapter(Context context, List<CaseInfo> caseInfoList) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.caseInfoList = caseInfoList;
        for (int i = 0; i < 10; i++) {
            ArrayList<String> info = new ArrayList<String>();
            for (int j = 0; j < imageUrls.length; j++) {
                info.add(imageUrls[j]);
            }
            infos.add(info);
        }
    }

    public void setNotifyChanged(List<CaseInfo> caseInfoList){
        this.caseInfoList = caseInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return caseInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return caseInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArrayList<String> data = infos.get(position);
        CaseInfo caseInfo = caseInfoList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_caselist, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imagecycle.setImageResources(data, mCycleViewListener);
        holder.date.setText(caseInfo.getDate());
        holder.termTime.setText(caseInfo.getTermTime()+"天");
        holder.description.setText(caseInfo.getDescription());
        holder.address.setText(caseInfo.getLocation());
        switch(caseInfo.getCaseStatus()){
            case CaseInfo.CASE_NEW:
                holder.caseStatus.setImageResource(R.mipmap.state_new);
                break;
            case CaseInfo.CASE_PROCESS:
                holder.caseStatus.setImageResource(R.mipmap.icon_progress);
                break;
            case CaseInfo.CASE_CHECK:
                holder.caseStatus.setImageResource(R.mipmap.icon_checked);
                break;
            case CaseInfo.CASE_FINISH:
                holder.caseStatus.setImageResource(R.mipmap.icon_end);
                break;
        }
        return convertView;
    }


    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(String info, int postion, View imageView) {
            Intent intent = new Intent(mContext, ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageUrls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, postion);
            mContext.startActivity(intent);
            Toast.makeText(mContext, "content->" + postion, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };

    static class ViewHolder {
        @Bind(R.id.imagecycle)
        ImageItemCycle imagecycle;
        @Bind(R.id.pic_status)
        TextView picStatus;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.case_status)
        ImageView caseStatus;
        @Bind(R.id.termtime)
        TextView termTime;
        @Bind(R.id.desciption)
        TextView description;
        @Bind(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}





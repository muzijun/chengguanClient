package com.pactera.chengguan.municipal.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChengApplication;
import com.pactera.chengguan.municipal.activity.MapActivity;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.municipal.CaseInfo;
import com.pactera.chengguan.municipal.model.municipal.PicData;
import com.pactera.chengguan.municipal.view.ImageItemCycle;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 案件详情适配器
 * Created by lijun on 2016/3/9.
 */
public class CaseListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private BaseActivity mContext;
    private List<CaseInfo> caseInfoList;

    public CaseListAdapter(BaseActivity context, List<CaseInfo> caseInfoList) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.caseInfoList = caseInfoList;
    }

    public void setNotifyChanged(List<CaseInfo> caseInfoList) {
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
        final CaseInfo caseInfo = caseInfoList.get(position);
        ArrayList<PicData> data = new ArrayList<PicData>();
        List<PicData> beforeList = caseInfo.getBeforeSmallPic();
        if (beforeList != null && beforeList.size() > 0) {
            data.addAll(beforeList);
        }
        List<PicData> afterList = caseInfo.getAfterSmallPic();
        if (afterList != null && afterList.size() > 0) {
            data.addAll(afterList);
        }
        convertView = inflater
                .inflate(R.layout.item_caselist, null, false);
        ImageItemCycle imagecycle = ButterKnife.findById(convertView, R.id.imagecycle);
        TextView date = ButterKnife.findById(convertView, R.id.date);
        ImageView caseStatus = ButterKnife.findById(convertView, R.id.case_status);
        TextView description = ButterKnife.findById(convertView, R.id.desciption);
        TextView address = ButterKnife.findById(convertView, R.id.address);

        if (data.size() > 0) {
            imagecycle.setImageResources(mContext, data, mCycleViewListener
                    , ChengApplication.instance.municipalApplication.access_token, false);
        }
        date.setText(caseInfo.getDate());
        description.setText(caseInfo.getDescription());
        address.setText(caseInfo.getLocation());
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MapActivity.class);
                intent.putExtra("case", caseInfo);
                mContext.startActivity(intent);
            }
        });
        switch (caseInfo.getCaseStatus()) {
            case CaseInfo.CASE_NEW:
                caseStatus.setImageResource(R.mipmap.state_new);
                break;
            case CaseInfo.CASE_PROCESS:
                caseStatus.setImageResource(R.mipmap.icon_progress);
                break;
            case CaseInfo.CASE_CHECK:
                caseStatus.setImageResource(R.mipmap.icon_checked);
                break;
            case CaseInfo.CASE_FINISH:
                caseStatus.setImageResource(R.mipmap.icon_end);
                break;
        }
        return convertView;
    }


    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(int postion, View imageView) {
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext.getApplicationContext()).load(imageURL).into(imageView);
        }
    };



}





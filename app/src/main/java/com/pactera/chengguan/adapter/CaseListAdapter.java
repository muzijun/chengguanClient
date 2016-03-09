package com.pactera.chengguan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.ImagePagerActivity;
import com.pactera.chengguan.view.ImageItemCycle;
import java.util.ArrayList;


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
    public CaseListAdapter(Context context) {
        mContext=context;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 10; i++) {
            ArrayList<String> info = new ArrayList<String>();
            for (int j = 0; j< imageUrls.length; j++) {
                info.add(imageUrls[j]);
            }
            infos.add(info);
        }
    }

    @Override
    public int getCount() {
        return infos.size();
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
        ArrayList<String> data = infos.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater
                    .inflate(R.layout.caselist_item, null, false);
            holder.imageItemCycle = (ImageItemCycle) convertView.findViewById(R.id.imagecycle);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageItemCycle.setImageResources(data,mCycleViewListener);
        return convertView;
    }


    private ImageItemCycle.ImageCycleViewListener mCycleViewListener = new ImageItemCycle.ImageCycleViewListener() {

        @Override
        public void onImageClick(String info, int postion, View imageView) {
            Intent intent=new Intent(mContext,ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,imageUrls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, postion);
            mContext.startActivity(intent);
            Toast.makeText(mContext, "content->" + postion, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };
}



class ViewHolder {
    ImageItemCycle imageItemCycle;
}



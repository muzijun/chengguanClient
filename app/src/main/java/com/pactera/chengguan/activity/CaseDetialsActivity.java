package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.ADInfo;
import com.pactera.chengguan.view.ImageCycleView;
import com.pactera.chengguan.view.PopMenu;

import java.util.ArrayList;

public class CaseDetialsActivity extends BaseActivity implements PopMenu.OnItemClickListener, View.OnClickListener {
    private PopMenu popMenu;
    private AQuery mAq;
    private ImageCycleView imageCycleView;
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detials);
        init();
    }

    protected void init() {
        LinearLayout menu = (LinearLayout) findViewById(R.id.lin);               // 初始化弹出菜单
        popMenu = new PopMenu(this);
        popMenu.addItems(new String[]{"菜单一", "菜单二", "菜单三", "菜单四"});
        popMenu.setOnItemClickListener(this);
        addView(menu);
        mAq = new AQuery(CaseDetialsActivity.this);
        mAq.id(R.id.title).text("考核案件");
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }
        imageCycleView = (ImageCycleView) findViewById(R.id.imagecycle);
        imageCycleView.setImageResources(infos, mCycleViewListener);
    }


    private void addView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    private ImageCycleView.ImageCycleViewListener mCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Intent intent = new Intent(CaseDetialsActivity.this, ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageUrls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
            startActivity(intent);
            Toast.makeText(CaseDetialsActivity.this, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(CaseDetialsActivity.this).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };


    public void check(View view) {
        startActivity(new Intent(CaseDetialsActivity.this, CheckActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:

                popMenu.showAsDropDown(v);
                break;
        }

    }

    @Override
    public void onItemClick(int index) {

    }
}

package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.ADInfo;
import com.pactera.chengguan.view.ImageCycleView;
import com.pactera.chengguan.view.PopMenu;

import java.util.ArrayList;

/**
 * 办结or处理中or审核
 * Created by lijun on 2016/3/9.
 */
public class CaseFinishActivity extends BaseActivity implements PopMenu.OnItemClickListener, View.OnClickListener {
    private PopMenu popMenu;
    private AQuery mAq;
    //作业前照片
    private ImageCycleView imageCycleView_begin;
    //作业后照片
    private ImageCycleView imageCycleView_end;
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    public static final String STATE = "State";
    //状态1为办结，2为处理中，3为审核
    private int state;
    //办结
    private String[] state_one = {"流程日志", "延期记录"};
    //处理中
    private String[] state_two = {"延期", "结案", "延期记录"};
    //审核
    private String[] state_three = {"考核", "延期", "结案", "不通过", "流程日志", "延期记录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_finish);
        init();
    }

    protected void init() {
        LinearLayout menu = (LinearLayout) findViewById(R.id.lin);               // 初始化弹出菜单
        popMenu = new PopMenu(this);
        addView();
        popMenu.setOnItemClickListener(this);
        addTitleView(menu);
        mAq = new AQuery(mContext);
        mAq.id(R.id.title).text("考核案件");
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }
        imageCycleView_begin = (ImageCycleView) findViewById(R.id.imagecycle);
        imageCycleView_end = (ImageCycleView) findViewById(R.id.imagecycle_end);
        imageCycleView_begin.setImageResources(infos, mCycleViewListener);
        imageCycleView_end.setImageResources(infos, mCycleViewListener);

    }

    /**
     * 根据传入的参数，加载不同的界面
     */
    private void addView() {
        state = getIntent().getIntExtra(STATE, 0);
        if (state == 1) {
            popMenu.addItems(state_one);
        } else if (state == 2) {
            popMenu.addItems(state_two);
        } else if (state == 3) {
            popMenu.addItems(state_three);
        }
    }


    private void addTitleView(LinearLayout linearLayout) {
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
            Intent intent = new Intent(mContext, ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageUrls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
            startActivity(intent);
            Toast.makeText(mContext, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(mContext).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };


    public void check(View view) {
        startActivity(new Intent(mContext, CheckActivity.class));
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
        if (state == 1) {
            switch (index) {
                case 0:
                    Intent intent = new Intent(mContext, ProcessRecordActivity.class);
                    startActivity(intent);
                    break;
            }
        } else if (state == 2) {
            switch (index) {

            }

        }  else if (state == 3) {
            switch (index) {

            }

        }



    }
}

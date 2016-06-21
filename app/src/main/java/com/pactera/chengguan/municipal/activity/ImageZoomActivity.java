package com.pactera.chengguan.municipal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.model.PhotoEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ImageZoomActivity extends Activity {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.photo_bt_exit)
    Button photoBtExit;
    @Bind(R.id.photo_bt_del)
    Button photoBtDel;
    @Bind(R.id.photo_relativeLayout)
    RelativeLayout photoRelativeLayout;
    private MyPageAdapter adapter;
    private int currentPosition;
    private ArrayList<String> mDataList = new ArrayList<String>();

    private RelativeLayout photo_relativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_zoom);
        ButterKnife.bind(this);
        photoRelativeLayout.setBackgroundColor(0x70000000);
        initData();
        photoBtExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        photoBtDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mDataList.size() == 1) {
                    removeImgs();
                    finish();
                } else {
                    removeImg(currentPosition);
                    viewpager.removeAllViews();
                    adapter.removeView(currentPosition);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        viewpager.setOnPageChangeListener(pageChangeListener);

        adapter = new MyPageAdapter(mDataList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(currentPosition);
    }

    private void initData() {
        currentPosition = getIntent().getIntExtra(
                Contants.EXTRA_CURRENT_IMG_POSITION, 0);
        mDataList = (ArrayList<String>) getIntent().getSerializableExtra(Contants.EXTRA_IMAGE_LIST);
    }

    private void removeImgs() {
        mDataList.clear();
        EventBus.getDefault().post(new PhotoEvent(mDataList));
    }

    private void removeImg(int location) {
        if (location + 1 <= mDataList.size()) {
            mDataList.remove(location);
        }
        EventBus.getDefault().post(new PhotoEvent(mDataList));
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            currentPosition = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    class MyPageAdapter extends PagerAdapter {
        private List<String> dataList = new ArrayList<String>();
        private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

        public MyPageAdapter(List<String> dataList) {
            this.dataList = dataList;
            int size = dataList.size();
            for (int i = 0; i != size; i++) {
                ImageView iv = new ImageView(ImageZoomActivity.this);
                Glide.with(ImageZoomActivity.this).load(dataList.get(i)).centerCrop().into(iv);
                iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                mViews.add(iv);
            }
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Object instantiateItem(View arg0, int arg1) {
            ImageView iv = mViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            if (mViews.size() >= arg1 + 1) {
                ((ViewPager) arg0).removeView(mViews.get(arg1));
            }
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        public void removeView(int position) {
            if (position + 1 <= mViews.size()) {
                mViews.remove(position);
            }
        }

    }
}
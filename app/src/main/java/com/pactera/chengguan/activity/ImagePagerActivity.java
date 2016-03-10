package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.base.BaseFragment;
import com.pactera.chengguan.fragment.ImageDetailFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lijun on 2016/3/4.
 */
public class ImagePagerActivity extends BaseActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.indicator)
    TextView indicator;
    private int pagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);
        ButterKnife.bind(this);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        String[] urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        pager.setAdapter(mAdapter);
        pager.setPageTransformer(true, new DepthPageTransformer());
        indicator = (TextView) findViewById(R.id.indicator);
        CharSequence text = getString(R.string.viewpager_indicator, 1, pager
                .getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, pager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        pager.setCurrentItem(pagerPosition);
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public String[] fileList;

        public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.length;
        }

        @Override
        public BaseFragment getItem(int position) {
            String url = fileList[position];
            return ImageDetailFragment.newInstance(url);
        }

    }

}

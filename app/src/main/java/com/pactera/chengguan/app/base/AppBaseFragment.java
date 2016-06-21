package com.pactera.chengguan.app.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lijun on 2015/12/2.
 */
public abstract class AppBaseFragment extends Fragment {
    protected View rootView;// 缓存Fragment view
    protected com.pactera.chengguan.municipal.base.BaseActivity mContext;
    protected Resources mRes;
    private boolean isPrepared;

    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    @Override
    public void onAttach(Context context) {
        mRes = getResources();
        super.onAttach(context);
    }

    /**
     * 设置控件
     *
     * @param view
     */
    public abstract void initContentView(View view);

    protected View initView(int layout, LayoutInflater inflater) {
        if (rootView == null) {
            rootView = inflater.inflate(layout, null);
            initContentView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        if (!isPrepared) {
            isPrepared = true;
            lazyLoad();
        }
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }

    /**
     * 得到根Fragment
     *
     * @return
     */
    protected Fragment getRootFragment() {
        Fragment fragment = getParentFragment();
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ChenguanOkHttpManager.cancel(this);
    }
}

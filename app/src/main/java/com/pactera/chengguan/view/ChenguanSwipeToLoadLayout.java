package com.pactera.chengguan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by lijun on 2015/12/15.
 */
public class ChenguanSwipeToLoadLayout extends SwipeToLoadLayout {
    public ChenguanSwipeToLoadLayout(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ChenguanSwipeToLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isLoadingMore() || isRefreshing()) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }
}

package com.pactera.chengguan.municipal.base;
import android.content.Context;
import com.pactera.chengguan.app.base.AppCommonAdapter;
import com.pactera.chengguan.app.view.AppViewHolder;
import com.pactera.chengguan.municipal.view.ViewHolder;

import java.util.List;

/**
 * @param <T>
 */
public abstract class CommonAdapter<T> extends AppCommonAdapter {


    public CommonAdapter(Context context, List<T> data, int layoutId) {
        super(context, data, layoutId);
    }
}

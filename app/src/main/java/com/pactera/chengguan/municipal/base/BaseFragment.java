package com.pactera.chengguan.municipal.base;
import android.content.Context;

import com.pactera.chengguan.app.base.AppBaseFragment;

/**市政
 * Created by lijun on 2015/12/2.
 */
public abstract class BaseFragment extends AppBaseFragment {
    protected BaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
    }
}

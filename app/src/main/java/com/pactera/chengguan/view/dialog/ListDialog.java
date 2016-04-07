package com.pactera.chengguan.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.adapter.ListDialogAdapter;
import com.pactera.chengguan.util.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * list对话框
 * Created by lijun on 2016/4/6.
 */
public class ListDialog extends Dialog {
    @Bind(R.id.dialog_list)
    ListView dialogList;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Context mContext;

    public ListDialog(Context context, AdapterView.OnItemClickListener onItemClickListener) {
        super(context);
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    public ListDialog(Context context, int themeResId,
                      AdapterView.OnItemClickListener onItemClickListener) {
        super(context, themeResId);
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.8);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        dialogList.setOnItemClickListener(onItemClickListener);
    }

    public void show(ListDialogAdapter listDialogAdapter) {
        show();
        dialogList.setAdapter(listDialogAdapter);
    }


}

package com.pactera.chengguan.municipal.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.adapter.ListDialogAdapter;
import com.pactera.chengguan.municipal.util.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * list对话框
 * Created by lijun on 2016/4/6.
 */
public class ListDialog extends Dialog {
    @Bind(R.id.dialog_list)
    ListView dialogList;
    private OnClickDialogListener mDialogListener;
    private Context mContext;
    // 定义dialog中点击回调接口
    public interface OnClickDialogListener {
        public void onClickItem(AdapterView<?> parent, View view, int position, long id);
    }
    public ListDialog(Context context, OnClickDialogListener mDialogListener) {
        super(context);
        this.mContext = context;
        this.mDialogListener = mDialogListener;
    }

    public ListDialog(Context context, int themeResId,
                      OnClickDialogListener mDialogListener) {
        super(context, themeResId);
        this.mContext = context;
        this.mDialogListener = mDialogListener;
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
        dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                mDialogListener.onClickItem(parent,view,position,id);
            }
        });

    }

    public void show(ListDialogAdapter listDialogAdapter) {
        show();
        dialogList.setAdapter(listDialogAdapter);
    }

}

package com.pactera.chengguan.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.util.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础通用Dialog类
 *
 * @author lj
 */
public class CommonDialog extends Dialog implements
        View.OnClickListener {


    @Bind(R.id.dialog_common_title)
    TextView dialogCommonTitle;
    @Bind(R.id.dialog_common_content_tv)
    TextView dialogCommonContentTv;
    @Bind(R.id.dialog_common_cancel_btn)
    Button dialogCommonCancelBtn;
    @Bind(R.id.dialog_line_view)
    View dialogLineView;
    @Bind(R.id.dialog_common_ok_btn)
    Button dialogCommonOkBtn;

    // 定义dialog中的按钮点击回调接口
    public interface OnClickDialogListener {
        public void onClickOkBtn();

        public void onClickCancelBtn();
    }

    private OnClickDialogListener mDialogListener;
    private Context mContext;


    public CommonDialog(Context context, OnClickDialogListener dialogListener) {
        super(context);
        this.mContext = context;
        this.mDialogListener = dialogListener;
    }

    public CommonDialog(Context context, int themeResId,
                        OnClickDialogListener onClickDialogListener) {
        super(context, themeResId);
        this.mContext = context;
        this.mDialogListener = onClickDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        ButterKnife.bind(this);
        dialogCommonOkBtn.setOnClickListener(this);
        dialogCommonCancelBtn.setOnClickListener(this);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.8);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_common_ok_btn:
                mDialogListener.onClickOkBtn();
                this.dismiss();
                break;
            case R.id.dialog_common_cancel_btn:
                mDialogListener.onClickCancelBtn();
                this.dismiss();
                break;
        }
    }

    /**
     * 设置dialog标题
     *
     * @param title
     */
    public void setDialogTitle(String title) {
        dialogCommonTitle.setText(title);
    }

    /**
     * 设置dialog显示内容
     *
     * @param content
     */
    public void setDialogContent(String content) {
        dialogCommonContentTv.setText(content);
    }

    /**
     * 隐藏取消按钮
     */
    public void hiddenCancelBtn() {
        dialogCommonCancelBtn.setVisibility(View.GONE);
        dialogLineView.setVisibility(View.GONE);
    }

}

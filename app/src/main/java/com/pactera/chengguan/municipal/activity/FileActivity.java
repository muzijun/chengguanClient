package com.pactera.chengguan.municipal.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.config.Contants;
import com.pactera.chengguan.municipal.adapter.FileAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.util.FileUtil;
import com.pactera.chengguan.municipal.view.NoScrollListView;
import com.pactera.chengguan.municipal.view.dialog.CommonDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择文件
 * Created by lijun on 2016/4/22.
 */
public class FileActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.mlist)
    NoScrollListView mlist;
    private FileAdapter fileAdapter;
    public static final String EXTRA_RESULT = "select_result";
    private static final int REQUEST_CODE = 6384; // onActivityResult request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        fileAdapter = new FileAdapter(this, new ArrayList<String>());
        mlist.setAdapter(fileAdapter);
        mlist.setOnItemLongClickListener(this);
        LinearLayout lin = (LinearLayout) findViewById(R.id.lin);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("完成");
        textView.setTextColor(getResources().getColor(R.color.colorBule));
        textView.setId(R.id.top);
        textView.setOnClickListener(this);
        lin.addView(textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                Intent data = new Intent();
                data.putStringArrayListExtra(EXTRA_RESULT, fileAdapter.getMfiles());
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    public void file(View view) {
        showChooser();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        CommonDialog dialog = new CommonDialog(mContext, R.style.dialog_dimenable, new CommonDialog.OnClickDialogListener() {
            @Override
            public void onClickOkBtn() {
                fileAdapter.deletefile((String) fileAdapter.getItem(position));
            }

            @Override
            public void onClickCancelBtn() {
            }
        });
        dialog.show();
        dialog.setDialogTitle("提示");
        dialog.setDialogContent("确认移除");
        return true;
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            totalsize(path);
                        } catch (Exception e) {
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void totalsize(String path) {
        long total = 0;
        if (fileAdapter.getMfiles().contains(path)) {
            Toast.makeText(this, "已经存在该文件了", Toast.LENGTH_SHORT).show();
            return;
        }
        if (FileUtil.getSize(path) > Contants.File_total_size) {
            Toast.makeText(this, "文件太大", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ArrayList<String> mlist = fileAdapter.getMfiles();
            for (String mpath : mlist) {
                total = FileUtil.getSize(mpath) + total;
            }
            total = total + FileUtil.getSize(path);
            if (total > Contants.File_total_size) {
                Toast.makeText(this, "总文件已超出不能添加", Toast.LENGTH_SHORT).show();
                return;
            } else {
                fileAdapter.addfile(path);
            }
        }


    }
}

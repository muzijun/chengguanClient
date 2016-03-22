package com.pactera.chengguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.ADInfo;
import com.pactera.chengguan.model.SelectEvent;
import com.pactera.chengguan.view.ImageCycleView;
import com.pactera.chengguan.view.PopMenu;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class CaseDetialsActivity extends BaseActivity implements PopMenu.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.imagecycle)
    ImageCycleView imagecycle;
    @Bind(R.id.tx_address)
    TextView txAddress;
    @Bind(R.id.tx_type)
    TextView txType;
    @Bind(R.id.tx_unit)
    TextView txUnit;
    @Bind(R.id.tx_describe)
    TextView txDescribe;
    @Bind(R.id.tx_date_edit)
    TextView txDateEdit;
    @Bind(R.id.tx_deduct)
    TextView txDeduct;
    private PopMenu popMenu;
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    //作业单位集合
    private ArrayList<String> mSelectData_unit = new ArrayList<String>();
    //考核类型集合
    private ArrayList<String> mSelectData_type = new ArrayList<String>();
    //作业单位
    private String[] unit_data = {"无锡市政府", "无锡城管局"};
    //考核类型
    private String[] type_data = {"日常", "月度", "季度", "年度"};
    //事业单位
    private String STATE_UNIT = "STATE_UNIT";
    //考核类型
    private String STATE_TYPE = "STATE_TYPE";
    //描述
    private String DESCRIPTION = "DESCRIPTION";
    //地址
    private String ADDRESS = "ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detials);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        addData();
        // 初始化弹出菜单
        popMenu = new PopMenu(mContext);
        popMenu.addItems(new String[]{"下派", "保存"});
        popMenu.setOnItemClickListener(this);
        addView(lin);
        title.setText("考核案件");
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }
        imagecycle.setImageResources(infos, mCycleViewListener);
        txUnit.setOnClickListener(this);
        txType.setOnClickListener(this);
        txAddress.setOnClickListener(this);
        txDescribe.setOnClickListener(this);
        txDateEdit.setOnClickListener(this);
        txDeduct.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }


    private void addView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    /**
     * 填充数据
     */
    private void addData() {
        for (int i = 0; i < unit_data.length; i++) {
            String unit = new String(unit_data[i]);
            mSelectData_unit.add(unit);
        }
        for (int i = 0; i < type_data.length; i++) {
            String type = new String(type_data[i]);
            mSelectData_type.add(type);
        }
    }

    private ImageCycleView.ImageCycleViewListener mCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            Intent intent = new Intent(CaseDetialsActivity.this, ImagePagerActivity.class);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, imageUrls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
            startActivity(intent);
            Toast.makeText(CaseDetialsActivity.this, "content->" + info.getContent(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            Glide.with(CaseDetialsActivity.this).load(imageURL).centerCrop().placeholder(R.mipmap.icon_stub).error(R.mipmap.icon_error).into(imageView);
        }
    };


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.tx_describe:
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", DESCRIPTION);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("content", txDescribe.getText().toString());
                intent.putExtra("title", "描述");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_address:
                intent = new Intent(mContext, InputActivity.class);
                intent.putExtra("type", ADDRESS);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "地址");
                intent.putExtra("content", txAddress.getText().toString());
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_type:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_TYPE);
                intent.putStringArrayListExtra("data", mSelectData_type);
                intent.putExtra("title", "考核类型");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_unit:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", mSelectData_unit);
                intent.putExtra("title", "作业单位");
                intent.putExtra("address", this.getClass().getName());
                startActivity(intent);
                break;
            case R.id.tx_deduct:
                intent=new Intent(mContext,PointsActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_date_edit:
                intent=new Intent(mContext,PostPoneActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(int index) {

    }

    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            //考核类型
            if (event.getType().equals(STATE_TYPE)) {
                txType.setText(event.getmMsg());
            }
            //作业单位
            else if (event.getType().equals(STATE_UNIT)) {
                txUnit.setText(event.getmMsg());
            } else if (event.getType().equals(DESCRIPTION)) {
                txDescribe.setText(event.getmMsg());
            } else if (event.getType().equals((ADDRESS))) {
                txAddress.setText(event.getmMsg());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

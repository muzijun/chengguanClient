package com.pactera.chengguan.municipal.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.activity.BdataSearchActivity;
import com.pactera.chengguan.municipal.base.BaseFragment;
import com.pactera.chengguan.municipal.view.PopMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基础数据
 * Created by lijun on 2015/12/2.
 */
public class BasicDataFragment extends BaseFragment implements PopMenu.OnItemClickListener, View.OnClickListener {
    @Bind(R.id.left_lin)
    LinearLayout leftLin;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    private PopMenu popMenu;
    private String[] option = {"桥梁", "道路", "泵站", "广场", "雨水管道", "河道栏杆", "街道设施"};

    /**
     * 用于桥梁的Fragment
     */
    private DataBridgeFragment dataBridgeFragment;
    /**
     * 用于道路的Fragment
     */
    private DataRoadFragment dataRoadFragment;

    /**
     * 用于泵站的Fragment
     */
    private DataPumpFragment dataPumpFragment;

    /**
     * 用于广场的Fragment
     */
    private DataSquareFragment dataSquareFragment;
    /**
     * 用于雨水管道的Fragment
     */
    private DataChannelFragment dataChannelFragment;
    /**
     * 用于河道栏杆的Fragment
     */
    private DataRiverFragment dataRiverFragment;

    /**
     * 用于街道设施的Fragment
     */
    private DataStreetFragment dataStreetFragment;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_basic_data, inflater);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        title.setText("桥梁");
        leftLin.setVisibility(View.GONE);
        popMenu = new PopMenu(mContext);
        popMenu.addItems(option);
        popMenu.setOnItemClickListener(this);
        addMenuView(lin);
        fragmentManager = getChildFragmentManager();
        setSelection(0);


    }


    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                //桥梁
                if (dataBridgeFragment == null) {
                    dataBridgeFragment = new DataBridgeFragment();
                    transaction.add(R.id.content, dataBridgeFragment);
                } else {
                    transaction.show(dataBridgeFragment);
                }
                break;
            case 1:
                //道路
                if (dataRoadFragment == null) {
                    dataRoadFragment = new DataRoadFragment();
                    transaction.add(R.id.content, dataRoadFragment);
                } else {
                    transaction.show(dataRoadFragment);
                }
                break;
            case 2:
                //泵站
                if (dataPumpFragment == null) {
                    dataPumpFragment = new DataPumpFragment();
                    transaction.add(R.id.content, dataPumpFragment);
                } else {
                    transaction.show(dataPumpFragment);
                }
                break;

            case 3:
                //广场
                if (dataSquareFragment == null) {
                    dataSquareFragment = new DataSquareFragment();
                    transaction.add(R.id.content, dataSquareFragment);
                } else {
                    transaction.show(dataSquareFragment);
                }
                break;
            case 4:
                //雨水管道
                if (dataChannelFragment == null) {
                    dataChannelFragment = new DataChannelFragment();
                    transaction.add(R.id.content, dataChannelFragment);
                } else {
                    transaction.show(dataChannelFragment);
                }
                break;
            case 5:
                //河道栏杆
                if (dataRiverFragment == null) {
                    dataRiverFragment = new DataRiverFragment();
                    transaction.add(R.id.content, dataRiverFragment);
                } else {
                    transaction.show(dataRiverFragment);
                }
                break;
            case 6:
                //街道设施
                if (dataStreetFragment == null) {
                    dataStreetFragment = new DataStreetFragment();
                    transaction.add(R.id.content, dataStreetFragment);
                } else {
                    transaction.show(dataStreetFragment);
                }
                break;

        }

        transaction.commit();
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (dataBridgeFragment != null) {
            transaction.hide(dataBridgeFragment);
        }
        if (dataRoadFragment != null) {
            transaction.hide(dataRoadFragment);
        }
        if (dataPumpFragment != null) {
            transaction.hide(dataPumpFragment);
        }
        if (dataSquareFragment != null) {
            transaction.hide(dataSquareFragment);
        }
        if (dataChannelFragment != null) {
            transaction.hide(dataChannelFragment);
        }
        if (dataRiverFragment != null) {
            transaction.hide(dataRiverFragment);
        }
        if (dataStreetFragment != null) {
            transaction.hide(dataStreetFragment);
        }

    }

    /**
     * 添加选项
     *
     * @param linearLayout
     */
    private void addMenuView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.icon_menu);
        imageView.setOnClickListener(this);
        linearLayout.addView(imageView);

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                popMenu.showAsDropDown(v);
                break;
            case R.id.content:
                Intent intent = new Intent(mContext, BdataSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //popmenu点击事件
    @Override
    public void onItemClick(int index) {
        switch (index) {
            case 0:
                title.setText(popMenu.getItemList().get(0));
                setSelection(0);
                break;
            case 1:
                title.setText(popMenu.getItemList().get(1));
                setSelection(1);
                break;
            case 2:
                title.setText(popMenu.getItemList().get(2));
                setSelection(2);
                break;
            case 3:
                title.setText(popMenu.getItemList().get(3));
                setSelection(3);
                break;
            case 4:
                title.setText(popMenu.getItemList().get(4));
                setSelection(4);
                break;
            case 5:
                title.setText(popMenu.getItemList().get(5));
                setSelection(5);
                break;
            case 6:
                title.setText(popMenu.getItemList().get(6));
                setSelection(6);
                break;

        }

    }


}

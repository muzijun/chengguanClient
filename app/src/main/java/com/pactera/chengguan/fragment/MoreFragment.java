package com.pactera.chengguan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.activity.ContactsActivity;
import com.pactera.chengguan.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 更多
 * Created by lijun on 2015/12/2.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.back_img)
    ImageView backImg;
    @Bind(R.id.tx_contacts)
    TextView txContacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(R.layout.fragment_more, inflater);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initContentView(View view) {
        ButterKnife.bind(this, view);
        title.setText(mRes.getString(R.string.title_more));
        backImg.setVisibility(View.GONE);
        txContacts.setOnClickListener(this);
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
        Intent intent;
        switch (v.getId()) {
            case R.id.tx_contacts:
                intent = new Intent(mContext, ContactsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

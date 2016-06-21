package com.pactera.chengguan.municipal.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.app.base.ChenguanOkHttpManager;
import com.pactera.chengguan.municipal.adapter.ContactsAdapter;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.bean.municipal.PageAddressbookBean;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.municipal.Addressbook;
import com.pactera.chengguan.municipal.util.DensityUtils;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.pactera.chengguan.municipal.view.ChenguanSwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录
 * Created by lijun on 2016/3/22.
 */
public class ContactsActivity extends BaseActivity implements View.OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lin)
    LinearLayout lin;
    @Bind(R.id.swipe_target)
    ListView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    ChenguanSwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.edit_searchText)
    EditText editSearchText;
    private List<Addressbook> addressbookList = new ArrayList<Addressbook>();        //数据集合
    private ContactsAdapter contactsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("通讯录");
        addView(lin);
        contactsAdapter=new ContactsAdapter(mContext,addressbookList);
        swipeTarget.setAdapter(contactsAdapter);
        swipeToLoadLayout.setRefreshEnabled(false);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        editSearchText.addTextChangedListener(new EditChangedListener());
        editSearchText.setText("");
    }

    private void addView(LinearLayout linearLayout) {
        ImageView imageView = new ImageView(mContext);
        imageView.setId(R.id.top);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.mipmap.icon_add);
        imageView.setOnClickListener(this);
        layoutParams.setMargins(0, 0, DensityUtils.dp2px(mContext, 10), 0);
        linearLayout.addView(imageView);

    }

    /**
     * 通讯录列表请求
     */
    private void requestAddressbook(String name) {
        ChenguanOkHttpManager.cancel(this);
        MunicipalRequest.requestPageAddressbook(this, this, name);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top:
                intent = new Intent(mContext, AddContactActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void success(String reqUrl, Object result) {
        PageAddressbookBean pageAddressbookBean = (PageAddressbookBean) result;
        pageAddressbookBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(mContext, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            PageAddressbookBean pageAddressbookBean = (PageAddressbookBean) baseBean;
            List<Addressbook> list = pageAddressbookBean.transformToAddressList();
            addressbookList = list;
            contactsAdapter.setNotifyChanged(addressbookList);
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "获取列表数据失败:" + message, Toast.LENGTH_LONG).show();
        }
    };

    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            requestAddressbook(s.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        editSearchText.setText("");
    }
}

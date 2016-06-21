package com.pactera.chengguan.municipal.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.bean.BaseBean;
import com.pactera.chengguan.municipal.bean.BaseHandler;
import com.pactera.chengguan.municipal.config.MunicipalCache;
import com.pactera.chengguan.municipal.config.RequestListener;
import com.pactera.chengguan.municipal.model.SelectEvent;
import com.pactera.chengguan.municipal.util.MunicipalRequest;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 添加联系人
 * Created by lijun on 2016/3/23.
 */
public class AddContactActivity extends BaseActivity implements View.OnClickListener, RequestListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.edit_email)
    EditText editEmail;
    @Bind(R.id.edit_phone)
    EditText editPhone;
    @Bind(R.id.commit)
    Button commit;
    @Bind(R.id.tv_unit)
    TextView tvUnit;
    private ArrayList<String> units = new ArrayList<String>();
    //单位
    private String STATE_UNIT = "STATE_UNIT";
    private int selectSectionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText("添加");
        commit.setOnClickListener(this);
        for (MunicipalCache.DataObject dataObject : MunicipalCache.organizationList) {
            units.add(dataObject.getName());
        }
        tvUnit.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }


    public void onEventMainThread(SelectEvent event) {
        if (event.getAddress().equals(this.getClass().getName())) {
            //月份
            if (event.getType().equals(STATE_UNIT)) {
                String msg = event.getmMsg();
                tvUnit.setText(msg);
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.commit:
                if (isempty()) {
                    String name = editName.getEditableText().toString();
                    String email = editEmail.getEditableText().toString();
                    String phone = editPhone.getEditableText().toString();
                    String unit = tvUnit.getText().toString();
                    if (!isEmail(email)) {
                        Toast.makeText(mContext, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    } else if (isPhoneToLong(phone)) {
                        Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    } else {
                        MunicipalRequest.requestAddAddressbook(mContext, this, name, phone, email, getIndexFrom(unit), unit);
                    }
                } else {
                    Toast.makeText(mContext, "请补全数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_unit:
                intent = new Intent(mContext, SelectActivity.class);
                intent.putExtra("type", STATE_UNIT);
                intent.putStringArrayListExtra("data", units);
                intent.putExtra("title", "单位");
                intent.putExtra("address", this.getClass().getName());
                intent.putExtra("checkItemContent", tvUnit.getText().toString());
                startActivity(intent);
        }
    }


    /**
     * 判断是否全部填入数据
     *
     * @return false是没有，true是通过
     */
    private boolean isempty() {
        if (editName.getText().toString().isEmpty()) {
            return false;
        }
        if (tvUnit.getText().toString().isEmpty()) {
            return false;
        }
        if (editPhone.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 判断邮箱是否合法
     * 邮箱不是必输项，可以为空
     *
     * @param email
     * @return
     */
    public boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return true;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号长度是否超出11位
     *
     * @param phoneNumber
     * @return
     */
    private boolean isPhoneToLong(String phoneNumber) {
        return phoneNumber.length() > 11;
    }

    private int getIndexFrom(String msg) {
        for (int i = 0; i < MunicipalCache.organizationList.size(); i++) {
            MunicipalCache.DataObject dataObject = MunicipalCache.organizationList.get(i);
            if (dataObject.getName().equals(msg)) {
                return dataObject.getId();
            }
        }
        return 0;
    }

    @Override
    public void success(String reqUrl, Object result) {
        BaseBean baseBean = (BaseBean) result;
        baseBean.checkResult(this, Handler);
    }

    @Override
    public void fail() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
    }

    private BaseHandler Handler = new BaseHandler() {
        @Override
        public void doSuccess(BaseBean baseBean, String message) {
            Toast.makeText(mContext, "提交成功!", Toast.LENGTH_LONG).show();
            setResult(1);
            finish();
        }

        @Override
        public void doError(int result, String message) {
            Toast.makeText(mContext, "提交失败:" + result + " | msg:" + message
                    , Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}

package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 多选单位
 * Created by lijun on 2016/6/13.
 */
public class SelectNoticeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.lin)
    LinearLayout lin;
    private ArrayList<String> mData;
    private String checkItemContent = "";
    private String type;
    private String address;
    private List<com.pactera.chengguan.municipal.model.MultiSelectNotice> choicelists = new ArrayList<com.pactera.chengguan.municipal.model.MultiSelectNotice>();
    private SelectNoticeAdapter selectNoticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        addTitleView(lin);
        title.setText(getIntent().getStringExtra("title"));
        type = getIntent().getStringExtra("type");
        checkItemContent = getIntent().getStringExtra("checkItemContent");
        mData = getIntent().getStringArrayListExtra("data");
        address = getIntent().getStringExtra("address");
        List<String> stringList = com.pactera.chengguan.municipal.util.AppUtils.convertStrToArray(checkItemContent);
        for (int i = 0; i < mData.size(); i++) {
            com.pactera.chengguan.municipal.model.MultiSelectNotice multiSelectNotice = new com.pactera.chengguan.municipal.model.MultiSelectNotice();
            multiSelectNotice.setName(mData.get(i));
            multiSelectNotice.setCheckItem(false);
            if (stringList.contains(mData.get(i))) {
                multiSelectNotice.setCheckItem(true);
            }
            choicelists.add(multiSelectNotice);
        }
        selectNoticeAdapter = new SelectNoticeAdapter(choicelists);
        listView.setAdapter(selectNoticeAdapter);
        listView.setOnItemClickListener(this);

    }

    private void addTitleView(LinearLayout linearLayout) {
        TextView textView = new TextView(mContext);
        textView.setId(R.id.top);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("提交");
        textView.setTextColor(getResources().getColor(R.color.colorBule));
        textView.setOnClickListener(this);
        linearLayout.addView(textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                com.pactera.chengguan.municipal.model.SelectEvent selectEvent = new com.pactera.chengguan.municipal.model.SelectEvent();
                String msg = "";
                for (int i = 0; i < choicelists.size(); i++) {
                    if (choicelists.get(i).isCheckItem()) {
                        msg = msg + choicelists.get(i).getName();
                        msg = msg + ",";
                    }
                }
                if (msg.length() != 0) {
                    msg = msg.substring(0, msg.length() - 1);
                }
                selectEvent.setmMsg(msg);
                selectEvent.setType(type);
                selectEvent.setAddress(address);
                EventBus.getDefault().post(selectEvent);
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        com.pactera.chengguan.municipal.model.MultiSelectNotice multiSelectNotice = choicelists.get(position);
        if (multiSelectNotice.isCheckItem()) {
            multiSelectNotice.setCheckItem(false);
        } else {
            multiSelectNotice.setCheckItem(true);
        }
        selectNoticeAdapter.setCheckItem(multiSelectNotice, position);
    }

    class SelectNoticeAdapter extends BaseAdapter {
        private List<com.pactera.chengguan.municipal.model.MultiSelectNotice> mDataList = new ArrayList<com.pactera.chengguan.municipal.model.MultiSelectNotice>();

        public void setCheckItem(com.pactera.chengguan.municipal.model.MultiSelectNotice multiSelectNotice, int position) {
            mDataList.set(position, multiSelectNotice);
            notifyDataSetChanged();
        }

        public SelectNoticeAdapter(List<com.pactera.chengguan.municipal.model.MultiSelectNotice> dataList) {
            mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            com.pactera.chengguan.municipal.model.MultiSelectNotice multiSelectNotice = mDataList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            fillValue(position, multiSelectNotice, holder);
            return convertView;
        }


        private void fillValue(int position, com.pactera.chengguan.municipal.model.MultiSelectNotice multiSelectNotice, ViewHolder viewHolder) {
            viewHolder.name.setText(mData.get(position));
            if (multiSelectNotice.isCheckItem()) {
                viewHolder.name.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.drop_down_checked), null);
            } else {
                viewHolder.name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    class ViewHolder {
        @Bind(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}



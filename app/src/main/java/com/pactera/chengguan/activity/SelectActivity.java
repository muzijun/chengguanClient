package com.pactera.chengguan.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.chengguan.R;
import com.pactera.chengguan.base.BaseActivity;
import com.pactera.chengguan.model.PhotoEvent;
import com.pactera.chengguan.model.SelectEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 选择界面
 * Created by lijun on 2016/3/16.
 */
public class SelectActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listView)
    ListView listView;
    //事业单位
    public static final String STATE_UNIT = "STATE_UNIT";
    //考核类型
    public static final String STATE_TYPE = "STATE_TYPE";
    //月份
    public static final String STATE_MONTH = "STATE_MONTH";
    private ArrayList<String> mData;
    private String type;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        title.setText(getIntent().getStringExtra("title"));
        type = getIntent().getStringExtra("type");
        mData = getIntent().getStringArrayListExtra("data");
        address=getIntent().getStringExtra("address");
        listView.setAdapter(new SelectAdapter(mData));
        listView.setOnItemClickListener(this);

    }


    class SelectAdapter extends BaseAdapter {
        private ArrayList<String> mDataList = new ArrayList<String>();

        public SelectAdapter(ArrayList<String> dataList) {
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
            String data = mDataList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(data);
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.name)
            TextView name;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SelectEvent selectEvent = new SelectEvent();
        selectEvent.setmMsg(mData.get(position));
        selectEvent.setType(type);
        selectEvent.setAddress(address);
        EventBus.getDefault().post(selectEvent);
        finish();
    }
}

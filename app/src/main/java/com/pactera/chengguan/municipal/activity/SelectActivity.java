package com.pactera.chengguan.municipal.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.base.BaseActivity;
import com.pactera.chengguan.municipal.model.SelectEvent;

import java.util.ArrayList;

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
    private ArrayList<String> mData;
    private String checkItemContent = "";
    private String type;
    private String address;
    private SelectAdapter selectAdapter;

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
        checkItemContent = getIntent().getStringExtra("checkItemContent");
        mData = getIntent().getStringArrayListExtra("data");
        address = getIntent().getStringExtra("address");
        selectAdapter = new SelectAdapter(mData);
        listView.setAdapter(selectAdapter);
        if (mData.contains(checkItemContent)) {
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).equals(checkItemContent)) {
                    selectAdapter.setCheckItem(i);
                    break;
                }
            }
        }
        listView.setOnItemClickListener(this);

    }


    class SelectAdapter extends BaseAdapter {
        private ArrayList<String> mDataList = new ArrayList<String>();
        private int checkItemPosition = -1;

        public void setCheckItem(int position) {
            checkItemPosition = position;
            notifyDataSetChanged();
        }

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
            fillValue(position, holder);
            return convertView;
        }


        private void fillValue(int position, ViewHolder viewHolder) {
            viewHolder.name.setText(mData.get(position));
            if (checkItemPosition != -1) {
                if (checkItemPosition == position) {
                    viewHolder.name.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.drop_down_checked), null);
                } else {
                    viewHolder.name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            } else {
                viewHolder.name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
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

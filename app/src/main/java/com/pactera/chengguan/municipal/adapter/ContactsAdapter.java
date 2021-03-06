package com.pactera.chengguan.municipal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pactera.chengguan.R;
import com.pactera.chengguan.municipal.model.municipal.Addressbook;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录适配器
 * Created by lijun on 2016/3/22.
 */
public class ContactsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<Addressbook> addressbookList;

    public void setNotifyChanged(List<Addressbook> addressbookList) {
        this.addressbookList = addressbookList;
        notifyDataSetChanged();
    }

    public ContactsAdapter(Context context,List<Addressbook> addressbookList) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.addressbookList = addressbookList;
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
        Addressbook addressbook=addressbookList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_contacts, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.telephone.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        holder.telephone.getPaint().setAntiAlias(true);//抗锯齿
        holder.name.setText(addressbook.getName());
        holder.telephone.setText(addressbook.getTelephone());
        holder.job.setText(addressbook.getOrganizationName());
        holder.telephone.setOnClickListener(new Click(addressbook.getTelephone()));
        return convertView;
    }

    @Override
    public int getCount() {
        return addressbookList.size();
    }

    class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.job)
        TextView job;
        @Bind(R.id.telephone)
        TextView telephone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class Click implements View.OnClickListener {

        private String mphone;
        public Click(String phone) {
            mphone=phone;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.telephone:
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mphone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }
}

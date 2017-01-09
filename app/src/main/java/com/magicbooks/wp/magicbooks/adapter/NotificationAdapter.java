package com.magicbooks.wp.magicbooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicbooks.wp.magicbooks.R;
import com.magicbooks.wp.magicbooks.models.Notification;
import com.magicbooks.wp.magicbooks.models.json.NotificationJson;
import com.magicbooks.wp.magicbooks.models.json.NotificationListJson;
import com.magicbooks.wp.magicbooks.views.ViewHolder;

/**
 * Created by wp on 2017/1/9.
 */
public class NotificationAdapter extends TAdapter<Notification> {

    public NotificationAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list, null);
        }

        TextView title = ViewHolder.get(convertView, R.id.title);
        TextView body = ViewHolder.get(convertView, R.id.body);
        TextView id = ViewHolder.get(convertView, R.id.id);
        Notification bean = mList.get(position);

        title.setText(bean.getTitle());
        body.setText(bean.getBody());
        id.setText(bean.getId());

        return convertView;
    }
}

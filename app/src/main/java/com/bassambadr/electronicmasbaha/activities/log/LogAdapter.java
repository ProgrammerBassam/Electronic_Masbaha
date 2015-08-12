package com.bassambadr.electronicmasbaha.activities.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bassambadr.electronicmasbaha.R;

import java.util.List;

/**
 * Created by Bassam on 8/12/2015.
 */
public class LogAdapter extends ArrayAdapter<LogItem> {

    public LogAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LogAdapter(Context context, int resource, List<LogItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.log_item, null);
        }

        LogItem p = getItem(position);

        TextView mLogNameTV = (TextView) v.findViewById(R.id.log_name_tv);
        mLogNameTV.setText(p.getLogName());

        TextView mLogCountTV = (TextView) v.findViewById(R.id.log_count_tv);
        mLogCountTV.setText(p.getLogCount());

        return v;
    }

}

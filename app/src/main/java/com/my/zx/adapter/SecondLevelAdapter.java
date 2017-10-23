package com.my.zx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.model.SecondLevelMo;

import java.util.List;

public class SecondLevelAdapter extends BaseAdapter {
    private Context context;
    private List<SecondLevelMo> HomeItemList;

    public SecondLevelAdapter(Context context, List<SecondLevelMo> list) {
        this.context = context;
        this.HomeItemList = list;
    }


    @Override
    public int getCount() {
        return HomeItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return HomeItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        SecondLevelMo item = HomeItemList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_title.setText(item.getTitle());
        viewHolder.tv_content.setText(item.getType());
        viewHolder.tv_time.setText(item.getTime());
        if (item.isTitleRed()) {
            viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.main_red_color));
//			viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.main_red_color));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
    }
}

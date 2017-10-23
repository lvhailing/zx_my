package com.my.zx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.zx.R;

public class HomeAdapter extends BaseAdapter {

	private Context context;

	public HomeAdapter(Context context) {
		this.context = context;
	}

	private String[] names = { "通讯录","代办任务","未读消息","通知公告","资产动态","领导动态","修改密码","工资查询","邮件"};

	private int[] icons = { R.drawable.home_phone_normal,R.drawable.home_daiban_normal,R.drawable.home_daiyue_normal
			,R.drawable.home_info_normal,R.drawable.home_zichan_normal,R.drawable.home_boss_normal
			,R.drawable.home_change_normal,R.drawable.home_salary_normal,R.drawable.home_email_normal};

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.grid_home_item, null);
		ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_home_item_icon);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_home_item_name);
		tv_name.setText(names[position]);
		iv_icon.setImageResource(icons[position]);
		return view;
	}
}

package com.my.zx.ac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.R;
import com.my.zx.adapter.SecondLevelAdapter;
import com.my.zx.model.HomeMo;
import com.my.zx.model.SecondLevelMo;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.StringUtil;
import com.my.zx.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class SecondLevelActivity extends BaseActivity {

	private HomeMo item;
	private TextView tv_title,tv_no_info;
	private ListView lv;
	private List<SecondLevelMo> homeItemList = new ArrayList<SecondLevelMo>();
	protected boolean isloading = false;
	protected int page = 1;
	private SecondLevelAdapter hia;
	private String mUrl;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			ResultObject ro = (ResultObject) msg.obj;
			if (ro.getCode() == 100) {
				//session过期，则吐司返回
				ToastUtil.showToast(ro.getMessage());
				return;
			}
			switch (msg.what) {
			case MesType.GETHOME_ITEMLIST:
				isloading = false;
				closeWaitProgress();
				if (ro.getSuccess()) {
					if (page == 1)
						homeItemList.clear();

					List<SecondLevelMo> homeItemListEvery = (List<SecondLevelMo>) ro.getObject();
					if (homeItemListEvery == null || homeItemListEvery.size() <= 0) {
//						if(page != 1)
//						Toast.makeText(getApplicationContext(), "没有最新数据了", Toast.LENGTH_SHORT).show();
						tv_no_info.setVisibility(View.VISIBLE);
						lv.setVisibility(View.INVISIBLE);
						return;
					}
					homeItemList.addAll(homeItemListEvery);

					setHomeItemListData();
				} else {
//					Toast.makeText(getApplicationContext(), "获取列表信息失败", Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "没有最新数据了", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		if (getIntent() != null) {
			item = (HomeMo) getIntent().getSerializableExtra("item");
		}

		initView();
		initData();
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.lv);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_no_info = (TextView) findViewById(R.id.tv_no_info);

		// 滑到底部加载
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
					break;
				case OnScrollListener.SCROLL_STATE_IDLE:
					int position = lv.getLastVisiblePosition();
					int total = homeItemList.size();
					if (isloading)
						return;
					if (position == (total - 1)) {
						page++;
						String newUrl = changeUrlPage(mUrl);
						isloading = true;
						DataService.getSecondLevelList(handler, newUrl);
					}
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
	}

	private void initData() {
		if (item != null) {
			tv_title.setText(item.getItemName());

			mUrl = item.getHref();
			isloading = true;
			showWaitProgress();
			DataService.getSecondLevelList(handler, mUrl);
		}
	}

	protected void setHomeItemListData() {

		if (page == 1) {
			hia = new SecondLevelAdapter(this, homeItemList);
			lv.setAdapter(hia);
		} else {
			hia.notifyDataSetChanged();
		}

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startWebViewActivity(homeItemList.get(position));
			}
		});
	}

	private void startWebViewActivity(SecondLevelMo item) {
		Intent intent = new Intent(this, WebviewActivity.class);
		Bundle bundle = new Bundle();
		intent.putExtra("mName", item.getType());
		intent.putExtra("mUrlOrHref", item.getUrl());
		intent.putExtras(bundle);
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		homeItemList.clear();
		initData();
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void goLback(View v) {
		finish();
	}

	protected String changeUrlPage(String url) {
		if (StringUtil.isNull(url))
			return "";
		// produce/mobilemng.nsf/agtGetViewData?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=15&oviewcategory=xw&type=info
		String[] urlArr = url.split("&");
		StringBuffer sb = new StringBuffer();
		for (String str : urlArr) {
			if (str.startsWith("page")) {
				sb.append("page=" + page + "&");
				continue;
			}
			sb.append(str + "&");
		}
		int total = sb.toString().length();
		return sb.deleteCharAt(total - 1).toString();
	}
	

//	@Override
//	protected void onResume() {
//		super.onResume();
//		homeItemList.clear();
//		initData();
//	}

}

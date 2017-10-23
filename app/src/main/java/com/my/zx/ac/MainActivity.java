package com.my.zx.ac;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.R;
import com.my.zx.fragments.HomeFragment;
import com.my.zx.fragments.TabEx1Fragment;
import com.my.zx.fragments.TabEx2Fragment;
import com.my.zx.fragments.TabEx3Fragment;

public class MainActivity extends Activity  implements OnClickListener {

	public static MainActivity instance=null;
	private HomeFragment homeFragment;
	private TabEx1Fragment lookFragment;
	private TabEx2Fragment planFragment;
	private TabEx3Fragment userFragment;
	private TextView[] tvs;
	private ImageView[] ivs;
	private int[] normals;
	private int[] presseds;
	private TextView tv_1, tv_2, tv_3, tv_4 ;
	private ImageView iv_1, iv_2, iv_3, iv_4 ;
	private RelativeLayout rl_1, rl_2, rl_3, rl_4;

	private FragmentManager fragmentManager;
//	private TextView main_yykf_tv_dai_ban, main_kfrc_tv_dai_ban, main_mine_tv_dai_ban;
	private long firstTime;
	


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentManager = getFragmentManager();
		instance=this;
		
		initView();
		initData();
	}
	
	private void initData() {
		setTabSelection(1);
	}

	private void initView() {
//		main_yykf_tv_dai_ban = (TextView) findViewById(R.id.main_yykf_tv_dai_ban);
//		main_kfrc_tv_dai_ban = (TextView) findViewById(R.id.main_kfrc_tv_dai_ban);
//		main_mine_tv_dai_ban = (TextView) findViewById(R.id.main_mine_tv_dai_ban);
		iv_1 = (ImageView) findViewById(R.id.iv_1);
		iv_2 = (ImageView) findViewById(R.id.iv_2);
		iv_3 = (ImageView) findViewById(R.id.iv_3);
		iv_4 = (ImageView) findViewById(R.id.iv_4);
		tv_1 = (TextView) findViewById(R.id.tv_1);
		tv_2 = (TextView) findViewById(R.id.tv_2);
		tv_3 = (TextView) findViewById(R.id.tv_3);
		tv_4 = (TextView) findViewById(R.id.tv_4);
		rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
		rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
		rl_3 = (RelativeLayout) findViewById(R.id.rl_3);
		rl_4 = (RelativeLayout) findViewById(R.id.rl_4);

		rl_1.setOnClickListener(this);
		rl_2.setOnClickListener(this);
		rl_3.setOnClickListener(this);
		rl_4.setOnClickListener(this);

		tvs = new TextView[] { tv_1, tv_2,tv_3, tv_4};
		ivs = new ImageView[] { iv_1, iv_2, iv_3, iv_4};
		normals = new int[] { R.drawable.icon1_normal, R.drawable.icon2_normal, R.drawable.icon3_normal, R.drawable.icon4_normal};
		presseds = new int[] { R.drawable.icon1_pressed, R.drawable.icon2_pressed, R.drawable.icon3_pressed, R.drawable.icon4_pressed};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_1:
			setTabSelection(1);
			break;
		case R.id.rl_2:
			setTabSelection(2);
			break;
		case R.id.rl_3:
			setTabSelection(3);
			break;
		case R.id.rl_4:
			setTabSelection(4);
			break;
		default:
			break;
		}
	}


	@SuppressLint({ "NewApi", "ResourceAsColor" })
	public void setTabSelection(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 1:
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
				transaction.add(R.id.main_content, homeFragment);
			} else {
				transaction.show(homeFragment);
			}
			setTextColor(1);
			break;
		case 2:
			if (lookFragment == null) {
				lookFragment = new TabEx1Fragment();
				transaction.add(R.id.main_content, lookFragment);
			} else {
				transaction.show(lookFragment);
			}
			setTextColor(2);
			break;
		case 3:
			if (planFragment == null) {
				planFragment = new TabEx2Fragment();
				transaction.add(R.id.main_content, planFragment);
			} else {
				transaction.show(planFragment);
			}
			setTextColor(3);
			break;
		case 4:
			if (userFragment == null) {
				userFragment = new TabEx3Fragment();
				transaction.add(R.id.main_content, userFragment);
			} else {
				transaction.show(userFragment);
			}
			setTextColor(4);
			break;
		}
		transaction.commit();
	}


	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null)
			transaction.hide(homeFragment);
		if (lookFragment != null)
			transaction.hide(lookFragment);
		if (planFragment != null)
			transaction.hide(planFragment);
		if (userFragment != null)
			transaction.hide(userFragment);
	}

	/**
	 * 退出程序
	 * 
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
				Toast.makeText(MainActivity.this, "再按一次可退出程序", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else {
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	private void setTextColor(int num) {
		for (int i = 1; i < 5; i++) {
			if (num == i) {
				tvs[i - 1].setTextColor(getResources().getColor(R.color.main_red_color));
				ivs[i - 1].setImageDrawable(getResources().getDrawable(presseds[i - 1]));
			} else {
				tvs[i - 1].setTextColor(getResources().getColor(R.color.main_black_color));
				ivs[i - 1].setImageDrawable(getResources().getDrawable(normals[i - 1]));
			}
		}
	}

	public void finishMe() {
		finish();
	}
	
	
}
package com.my.zx.ac;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.my.zx.R;
import com.my.zx.MyApplication;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.StringUtil;
import com.my.zx.widegt.CustomerDialog;


/**
 *   登录前设置IP、端口页
 */
public class LoginSettingActivity extends Activity implements View.OnClickListener, OnFocusChangeListener  {
	private LinearLayout ll_ip,ll_port,ll_http;
	private EditText et_ip1,et_ip2,et_ip3,et_ip4,et_port,et_http;
	private Button btn_cancel,btn_sure2;
	private String port;
	private String http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_setting);
		
		initView();
		initData();
		
	}

	private void initView() {
		ll_ip = (LinearLayout) findViewById(R.id.ll_ip);
		ll_port = (LinearLayout) findViewById(R.id.ll_port);
		ll_http = (LinearLayout) findViewById(R.id.ll_http);
		
		et_ip1 = (EditText) findViewById(R.id.et_ip1);
		et_ip2 = (EditText) findViewById(R.id.et_ip2);
		et_ip3 = (EditText) findViewById(R.id.et_ip3);
		et_ip4 = (EditText) findViewById(R.id.et_ip4);
		et_port = (EditText) findViewById(R.id.et_port);
		et_http = (EditText) findViewById(R.id.et_http);
		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_sure2 = (Button) findViewById(R.id.btn_sure2);
		
		et_ip1.setOnFocusChangeListener(this);
		et_ip2.setOnFocusChangeListener(this);
		et_ip3.setOnFocusChangeListener(this);
		et_ip4.setOnFocusChangeListener(this);
		et_port.setOnFocusChangeListener(this);
		et_http.setOnFocusChangeListener(this);
		btn_sure2.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		et_http.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				http=et_http.getText().toString().trim();
			}
		});
	}
	
	private void initData() {
		String ip= PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYIP);
		port = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYPORT);
		http = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYHTTP);
		if(!StringUtil.isNull(ip)){
			String[] ips=ip.split("\\.");
			et_ip1.setText(ips[0]);
			et_ip2.setText(ips[1]);
			et_ip3.setText(ips[2]);
			et_ip4.setText(ips[3]);
		}
		if(!StringUtil.isNull(port)){
			et_port.setText(port);
		}
		if(!StringUtil.isNull(http)){
			et_http.setText(http);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure2:
			doSet();
			break;
		case R.id.btn_cancel:
			showDailog();
			break;
		default:
			break;
		}
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.et_ip1:
			if(hasFocus)
				ipFocous();
			break;
		case R.id.et_ip2:
			if(hasFocus)
				ipFocous();
			break;
		case R.id.et_ip3:
			if(hasFocus)
				ipFocous();
			break;
		case R.id.et_ip4:
			if(hasFocus)
				ipFocous();
			break;
		case R.id.et_port:
			if(hasFocus)
				portFoucs();
			break;
		case R.id.et_http:
			if(hasFocus)
				httpFoucs();
			break;
		}
	}
	
	private void ipFocous() {
//		ll_ip.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_red));
//		ll_port.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
//		ll_http.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
	}
	
	private void portFoucs() {
//		ll_ip.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
//		ll_port.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_red));
//		ll_http.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
		
		if(!StringUtil.isNull(port)){
			et_port.setText(port);
			et_port.setSelection(port.length());
		}else{
			String defaultPort="80";
			et_port.setText(defaultPort);
			et_port.setSelection(defaultPort.length());
		}
	}
	
	private void httpFoucs() {
//		ll_ip.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
//		ll_port.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_gray));
//		ll_http.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_ac_weituo_rect_red));
		
		if(!StringUtil.isNull(http)){
			et_http.setText(http);
			et_http.setSelection(http.length());
		}else{
			String defaultHttp="http://";
			et_http.setText(defaultHttp);
			et_http.setSelection(defaultHttp.length());
		}
	}

	private void doSet() {
		String ip1= et_ip1.getText().toString().trim();
		String ip2= et_ip2.getText().toString().trim();
		String ip3= et_ip3.getText().toString().trim();
		String ip4= et_ip4.getText().toString().trim();
		String port= et_port.getText().toString().trim();
		
		boolean isIpNull= StringUtil.isNull(ip1)|| StringUtil.isNull(ip2)|| StringUtil.isNull(ip3)|| StringUtil.isNull(ip4);
		boolean isHttpNull= StringUtil.isNull(http);
		boolean isPortNull= StringUtil.isNull(port);
		
		//ip地址、域名不能同时为空
		if(isIpNull && isHttpNull){
			Toast.makeText(getApplicationContext(), "ip地址、域名不能同时为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		//ip有一项为空 ，就不记录本次数据
		if(!isIpNull){
			PreferenceUtil.putString(getApplicationContext(), PreferenceUtil.MYIP, ip1+"."+ip2+"."+ip3+"."+ip4);
		}
		
		//端口为空 ，则不记录本次数据
		if(isPortNull){
			PreferenceUtil.putString(getApplicationContext(), PreferenceUtil.MYPORT, port);
		}
		
		//域名 ，是否为空，需要按业务判断
		if(!isHttpNull){
			PreferenceUtil.showDiaDlg(this, -1, "域名有值 ， 则用域名登录 ， 确定域名无误吗？", "", "确定", "取消", new CustomerDialog.ClickCallBack() {
				@Override
				public void onOk(CustomerDialog dlg) {
					dlg.dismissDlg();
					PreferenceUtil.putString(getApplicationContext(), PreferenceUtil.MYHTTP, http);
					finish();
				}
				@Override
				public void onCancel(CustomerDialog dlg) {
					dlg.dismissDlg();
				}
			}, 1);
		}else{
			PreferenceUtil.putString(getApplicationContext(), PreferenceUtil.MYHTTP, "");
			finish();
		}
	}

	private void showDailog() {
		PreferenceUtil.showDiaDlg(this, -1, "确定不设置了吗？", "", "确定", "取消", new CustomerDialog.ClickCallBack() {

			@Override
			public void onOk(CustomerDialog dlg) {
				dlg.dismissDlg();
				finish();
			}

			@Override
			public void onCancel(CustomerDialog dlg) {
				dlg.dismissDlg();
			}
		}, 1);
	}

	public void goSetback(View v) {
		showDailog();
	}
	
}

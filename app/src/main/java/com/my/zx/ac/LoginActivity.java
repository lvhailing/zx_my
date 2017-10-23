package com.my.zx.ac;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.MyApplication;
import com.my.zx.jpush.JpushHelper;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.StringUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.Util;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener/*, View.OnFocusChangeListener*/ {
    private ImageView iv_back;
    private ImageView iv_password;
    private Button btn_sure;
    //    private TextView setting;
    private CheckBox cb;
    private EditText et_name, et_password;
    private String inputName = "", inputPas = "";
    private String userLoginName;
    private String userLoginPassword;

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
                case MesType.LOGIN:
                    closeWaitProgress();
                    if (ro.getSuccess()) {
                        finish();
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

                        //放入sp 已登录的标志
                        PreferenceUtil.putBoolean(LoginActivity.this, "isLogin", true);

                        //向极光后台注册别名
                        JpushHelper.registeJpush(LoginActivity.this.getApplicationContext(), userLoginName);

                        //发送登录广播
                        Util.sendMyBroadcast(LoginActivity.this, Constant.BROADCAST_LOGIN_IN);

                        //保存到sp
                        saveInfo();
                    } else {
                        Toast.makeText(getApplicationContext(), "登录不成功，请检查", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void goToSettingAc() {
        String ip = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYIP);
        if (ip.equals("")) {
            openSettingActivity();
        }
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_password = (ImageView) findViewById(R.id.iv_password);
        btn_sure = (Button) findViewById(R.id.btn_sure);
//        setting = (TextView) findViewById(R.id.setting);
        cb = (CheckBox) findViewById(R.id.cb);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);

//        et_name.setOnFocusChangeListener(this);
//        et_password.setOnFocusChangeListener(this);

        iv_back.setOnClickListener(this);
        iv_password.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
//        setting.setOnClickListener(this);
        cb.setOnClickListener(this);
    }

    private void initData() {
        final String name = PreferenceUtil.getString(getApplicationContext(), "userLoginName");
        final String password = PreferenceUtil.getString(getApplicationContext(), "userLoginPassword");

        final boolean isNameNull = StringUtil.isNull(name);
        final boolean isPasswordNull = StringUtil.isNull(password);

        if (!isNameNull) et_name.setText(name);
        if (!isPasswordNull) et_password.setText(password);
        if (!isNameNull && !isPasswordNull) {
            setSureBtnTrue();
        } else {
            setSureBtnFalse();
        }
        setSureBtnTrue();

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputName = et_name.getText().toString().trim();

               /* if (!inputName.equals("")) {
                    iv_cancel.setVisibility(View.VISIBLE);
                } else {
                    iv_cancel.setVisibility(View.INVISIBLE);
                }*/
                if ((!inputName.equals("") || !isNameNull) && (!inputPas.equals("")) || !isPasswordNull) {
                    setSureBtnTrue();
                } else {
                    setSureBtnFalse();
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputPas = et_password.getText().toString().trim();
                if ((!inputName.equals("") || !isNameNull) && (!inputPas.equals("")) || !isPasswordNull) {
                    setSureBtnTrue();
                } else {
                    setSureBtnFalse();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                sure();
                break;
            case R.id.iv_password:
                if (129 == et_password.getInputType()) {
                    //原先是隐藏密码，隐藏密码的type号是129
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT);    //显示密码
                } else {
                    //原先是显示密码
                    et_password.setInputType(129);  //隐藏密码
                }
                et_password.setSelection(et_password.length());     //设置光标位置到末尾
                break;
            /*case R.id.setting:
                openSettingActivity();
                break;
            case R.id.cb:
                boolean isChecked = cb.isChecked();
                cb.setChecked(isChecked);
                break;
            case R.id.iv_cancel:
                et_name.setText("");
                iv_cancel.setVisibility(View.INVISIBLE);
                break;*/
            default:
                break;
        }
    }


//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        switch (v.getId()) {
//            case R.id.et_name:
//                if (hasFocus) {
//                    iv_phone.setBackgroundResource(R.drawable.login_phone_select);
//                } else {
//                    iv_phone.setBackgroundResource(R.drawable.login_phone);
//                }
//                break;
//            case R.id.et_password:
//                if (hasFocus) {
//                    iv_code.setBackgroundResource(R.drawable.login_lock_select);
//                } else {
//                    iv_code.setBackgroundResource(R.drawable.login_lock);
//                }
//                break;
//        }
//    }

    private void sure() {
        if (!checkNet(this)) {
            Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_SHORT).show();
            return;
        }

//        userLoginName = "testwd";
//        userLoginPassword = "citicamc";
        userLoginName = et_name.getText().toString().trim();
        userLoginPassword = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(userLoginName)) {
            Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userLoginPassword)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        showWaitProgress();
        DataService.login(handler, userLoginName, userLoginPassword);
    }

    private void saveInfo() {
        PreferenceUtil.putString(getApplicationContext(), "userLoginName", userLoginName);
        if (cb.isChecked()) {
            PreferenceUtil.putString(getApplicationContext(), "userLoginPassword", userLoginPassword);
        } else {
            PreferenceUtil.putString(getApplicationContext(), "userLoginPassword", "");
        }

        //记录最新时间
        PreferenceUtil.putLong(getApplicationContext(), "session_time", System.currentTimeMillis());
    }

    private void openSettingActivity() {
        Intent intent = new Intent(this, LoginSettingActivity.class);
        startActivity(intent);
    }

    private void setSureBtnFalse() {
        btn_sure.setBackgroundResource(R.drawable.login_circle_gray);
        btn_sure.setClickable(false);
        btn_sure.setEnabled(false);
    }

    private void setSureBtnTrue() {
        btn_sure.setBackgroundResource(R.drawable.login_circle_red);
        btn_sure.setClickable(true);
        btn_sure.setEnabled(true);
    }

    public static boolean checkNet(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null) {
            NetworkInfo info = conn.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}

package com.my.zx.ac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.model.VersionMo;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.UpdateUtil;

import java.util.Map;


/**
 * 设置页面
 */
public class MySettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton ib_setting_push; // 推送开关
    //    private ImageButton ib_setting_remember_pwd; // 记录密码
    //    private RelativeLayout rl_setting_remember_pwd; // 记录密码
    private RelativeLayout rl_setting_ip; // IP地址/端口/域名
    private RelativeLayout rl_setting_change_password; // 修改登录密码
    private RelativeLayout rl_setting_check_update; // 检查更新
    //    private Button btn_setting_logout;  // 退出登录
    private TextView tv_setting_version_code; //  版本号
    private ImageView iv_back;
    private Intent intent;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ResultObject ro = (ResultObject) msg.obj;
            switch (msg.what) {
                case MesType.VERSION:
                    if (ro.getSuccess()) {
                        VersionMo v = (VersionMo) ro.getObject();
                        if (v != null) {
                            new UpdateUtil().checkVersion(MySettingActivity.this, v);
                        } else if (!TextUtils.isEmpty(ro.getMessage())) {
                            ToastUtil.showToast(ro.getMessage());
                        } else {
                            ToastUtil.showToast("已是最新版本啦");
                        }
                    } else if (ro.getCode() == 100) {
                        //session过期，则吐司返回
                        ToastUtil.showToast(ro.getMessage() == null ? "已是最新版本" : ro.getMessage());
                        return;
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        initView();
        initData();
    }

    public void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);

        ib_setting_push = (ImageButton) findViewById(R.id.ib_setting_push);
//        ib_setting_remember_pwd = (ImageButton) findViewById(R.id.ib_setting_remember_pwd);
//        rl_setting_remember_pwd = (RelativeLayout) findViewById(R.id.rl_setting_remember_pwd);
        rl_setting_ip = (RelativeLayout) findViewById(R.id.rl_setting_ip);
        rl_setting_change_password = (RelativeLayout) findViewById(R.id.rl_setting_change_password);
        rl_setting_check_update = (RelativeLayout) findViewById(R.id.rl_setting_check_update);
        tv_setting_version_code = (TextView) findViewById(R.id.tv_setting_version_code);
//        btn_setting_logout = (Button) findViewById(R.id.btn_setting_logout);

        iv_back.setOnClickListener(this);
        ib_setting_push.setOnClickListener(this);
//        ib_setting_remember_pwd.setOnClickListener(this);
        rl_setting_ip.setOnClickListener(this);
        rl_setting_change_password.setOnClickListener(this);
        rl_setting_check_update.setOnClickListener(this);
//        btn_setting_logout.setOnClickListener(this);

        //4.0需求 暂时不要
        rl_setting_ip.setVisibility(View.GONE);

        //推送开关
        if (PreferenceUtil.isOpenPush(this)) {
            ib_setting_push.setImageResource(R.drawable.img_set_on);
        } else {
            ib_setting_push.setImageResource(R.drawable.img_set_off);
        }
//
//        if (PreferenceUtil.isRememberPassword(this)) {
//            ib_setting_remember_pwd.setImageResource(R.drawable.img_set_on);
//        } else {
//            ib_setting_remember_pwd.setImageResource(R.drawable.img_set_off);
//        }
    }

    public void initData() {
        Map<String, Object> map = DeviceUtil.getVersionCodeAndName(this);
        if (map == null || map.size() == 0) {
            return;
        }
        String vname = (String) map.get("vname");
        tv_setting_version_code.setText(vname);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: // 返回
                finish();
                break;

            case R.id.ib_setting_push: // 推送开启
                if (PreferenceUtil.isOpenPush(this)) {
                    ib_setting_push.setImageResource(R.drawable.img_set_off);
                    PreferenceUtil.setOpenPush(this, false);
                } else {
                    ib_setting_push.setImageResource(R.drawable.img_set_on);
                    PreferenceUtil.setOpenPush(this, true);
                }
                break;

//            case R.id.ib_setting_remember_pwd: //  记住密码
//                if (PreferenceUtil.isRememberPassword(this)) {
//                    ib_setting_remember_pwd.setImageResource(R.drawable.img_set_off);
//                    PreferenceUtil.setOpenPush(this, false);
//                } else {
//                    ib_setting_remember_pwd.setImageResource(R.drawable.img_set_on);
//                    PreferenceUtil.setRememberPassword(this, true);
//                }
//                break;
            case R.id.rl_setting_ip: //  IP相关信息
                intent = new Intent(MySettingActivity.this, LoginSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_change_password: // 修改登录密码
                Intent intent = new Intent(this, WebviewActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("mName", "修改密码");
                intent.putExtra("mUrlOrHref", "/produce/webframe.nsf/modifyPasswordForm?openform");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.rl_setting_check_update: //  检查更新
                checkUpdate();
                break;
            /*case R.id.btn_setting_logout: // 退出登录
                showDialog();
                break;*/
        }
    }

    private void checkUpdate() {
        Map<String, Object> map = DeviceUtil.getVersionCodeAndName(this);
        if (map == null || map.size() == 0) return;
        String vname = (String) map.get("vname");
        int vcode = (Integer) map.get("vcode");
        DataService.checkVersion(handler, vname, vcode);
    }

//    private void showDialog() {
//        PreferenceUtil.showDiaDlg(this, -1, "确定要退出吗？", "", "确定", "取消", new CustomerDialog.ClickCallBack() {
//
//            @Override
//            public void onOk(CustomerDialog dlg) {
//                //情况用户信息
//                PreferenceUtil.putBoolean(MySettingActivity.this, "isLogin", false);
//                PreferenceUtil.putString(MySettingActivity.this, "userName", "");
//                PreferenceUtil.putString(MySettingActivity.this, "userId", "");
//                PreferenceUtil.putString(MySettingActivity.this, "my_cookie_string", "");
//                PreferenceUtil.putString(MySettingActivity.this, "my_cookie_domain_value", "");
//
//                //发送退出登录广播
//                Util.sendMyBroadcast(MySettingActivity.this, Constant.BROADCAST_LOGIN_OUT);
//
//                finish();
//            }
//
//            @Override
//            public void onCancel(CustomerDialog dlg) {
//                dlg.dismissDlg();
//            }
//        }, 1);
//    }
}

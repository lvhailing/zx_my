package com.my.zx.ac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.fragments.HomeFourFragment;
import com.my.zx.fragments.MenuFragment;
import com.my.zx.jpush.JpushHelper;
import com.my.zx.model.VersionMo;
import com.my.zx.net.DataService;
import com.my.zx.net.MesType;
import com.my.zx.net.ResultObject;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.UpdateUtil;
import com.my.zx.utils.Util;

import java.util.Map;

public class MainFourActivity extends FragmentActivity {
    private DrawerLayout drawer_layout;
    private HomeFourFragment homeFourFragment;
    private MenuFragment menuFragment;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ResultObject ro = (ResultObject) msg.obj;
            switch (msg.what) {
                case MesType.VERSION:
                    if (ro != null && ro.getSuccess()) {
                        VersionMo v = (VersionMo) ro.getObject();
                        if (v != null) {
                            new UpdateUtil().checkVersion(MainFourActivity.this, v);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_main);

        initView();
        initSettings();
        initBroadcast();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //刷新数据
        homeFourFragment.initHomeData();
    }

    private void initSettings() {
        if (!Util.checkLogin()) {
            return;
        }

        //设置极光推送别名
        String userId = PreferenceUtil.getString(this, "userId");
        JpushHelper.registeJpush(this, userId);
    }

    private void initView() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        homeFourFragment = new HomeFourFragment();
        menuFragment = new MenuFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, homeFourFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu, menuFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //检查更新
        checkUpdate();

    }

    //检查更新
    private void checkUpdate() {
        Map<String, Object> map = DeviceUtil.getVersionCodeAndName(this);
        if (map == null || map.size() == 0) return;
        String vname = (String) map.get("vname");
        int vcode = (Integer) map.get("vcode");
        DataService.checkVersion(handler, vname, vcode);
    }

    public void toggle() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawers();
        } else {
            drawer_layout.openDrawer(Gravity.LEFT);
        }
    }


    //监听登录和退出登录的广播
    private void initBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        //登陆广播
        intentFilter.addAction(Constant.BROADCAST_LOGIN_IN);
        //退出登陆广播
        intentFilter.addAction(Constant.BROADCAST_LOGIN_OUT);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constant.BROADCAST_LOGIN_IN)) {
                    //登陆广播
                    //设置主页的小红点
                    homeFourFragment.initHomeData();
                    //刷新更多按钮状态
                    homeFourFragment.refreshMoreBtn();
                    //刷新退出登录按钮状态
                    menuFragment.refreshLogoutBtn(true);
                } else if (intent.getAction().equals(Constant.BROADCAST_LOGIN_OUT)) {
                    //退出登陆广播
                    //需要去掉主页的小红点
                    homeFourFragment.refreshGridView_WhenLoginOut();
                    //刷新更多按钮状态
                    homeFourFragment.refreshMoreBtn();
                    //刷新退出登录按钮状态
                    menuFragment.refreshLogoutBtn(false);
                    //修改侧滑的名字为 "登录"
                    setMenuText("登录");
                }
            }
        }, intentFilter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setMenuText(String name) {
        menuFragment.setUserName(name);
    }

    public void refreshLogoutBtn(boolean isLogin) {
        menuFragment.refreshLogoutBtn(isLogin);
    }

    //获取更多按钮文案，防止编辑未完成时，即去登录提示
    public String getMoreBtnText() {
        return homeFourFragment.getMoreBtnText();
    }
}

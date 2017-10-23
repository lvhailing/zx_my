package com.my.zx.utils;

import android.content.Context;
import android.content.Intent;

import com.my.zx.MyApplication;
import com.my.zx.ac.LoginActivity;

/**
 * Created by lvhailing on 2017/9/28.
 * 类简单说明:
 */

public class Util {

    public static boolean isLogin() {
        return PreferenceUtil.getBoolean(MyApplication.instance, "isLogin", false);
    }

    public static boolean checkLogin() {
        return PreferenceUtil.getBoolean(MyApplication.instance, "isLogin", false);
    }

    public static void sendMyBroadcast(Context context, String type) {
        Intent intent = new Intent();
        intent.setAction(type);
        //发送无序广播
        context.sendBroadcast(intent);
    }

    public static void goToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    //4.0版本 因为将登陆后置了 所以cookie需要保存到sp，而不是之前的TheApplication
    public static void putCookieString(String cookieString) {
        PreferenceUtil.putString(MyApplication.instance, "my_cookie_string", cookieString);
    }

    public static void putCookie_DomainValue(String domainValue) {
        PreferenceUtil.putString(MyApplication.instance, "my_cookie_domain_value", domainValue);
    }

    public static String getCookieString() {
        return PreferenceUtil.getString(MyApplication.instance, "my_cookie_string");
    }

    public static String getDomainValue() {
        return PreferenceUtil.getString(MyApplication.instance, "my_cookie_domain_value");
    }

    public static String getIsLoginSuffix() {
        String suffix;
        if (PreferenceUtil.getBoolean(MyApplication.instance, "isLogin", false)) {
            suffix = "&isLogin=true";
        } else {
            suffix = "&isLogin=false";
        }
        return suffix;
    }
}

package com.my.zx;

import android.app.Application;
import android.webkit.CookieSyncManager;

import com.my.zx.utils.fresco.ImageLoader;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    public static MyApplication instance = null;
    public static String userName = null;
    private CookieStore cookies;
    private Cookie cookie;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CookieSyncManager.createInstance(this);

        //fresco初始化
        ImageLoader.getInstance().initImageLoader(getResources(), 1);

        // 初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public CookieStore getCookieS() {
        return cookies;
    }

    public void setCookieS(CookieStore cks) {
        cookies = cks;
    }


    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie ck) {
        cookie = ck;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
    }


}

package com.my.zx.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.my.zx.MyApplication;
import com.my.zx.R;
import com.my.zx.ac.WebviewActivity;
import com.my.zx.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class JpushReceiver extends BroadcastReceiver {
    private PendingIntent m_PendingIntent;
    private Notification m_Notification;
    private NotificationManager m_NotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        //如果推送被关闭
        if (!PreferenceUtil.isOpenPush(MyApplication.instance)) {
            Log.i("aaaa", "推送消息被关闭。消息类型： " + intent.getAction());
            return;
        }

        //开始处理推送
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //处理自定义消息
            Log.i("aaaa", "我的自定义消息");
            sendNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //用户点击通知栏通知的调用
            Log.i("aaaa", "我的自定义消息点击");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //极光发送的普通消息，在通知栏弹出
            Log.i("aaaa", "我的极光消息");
        }
    }

    public void sendNotification(Context context, Bundle bundle) {
        //发通知, 并设置点击发广播
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);//自定义内容
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

        //弹出通知
        m_NotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.launcher_icon); //设置图标
        builder.setTicker(TextUtils.isEmpty(title) ? "中信" : title);
        builder.setContentTitle(TextUtils.isEmpty(title) ? "中信消息" : title); //设置标题
        builder.setContentText(TextUtils.isEmpty(msg) ? "消息内容为空" : msg); //消息内容
        builder.setWhen(System.currentTimeMillis()); //发送时间
        builder.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder.setAutoCancel(true);//打开程序后图标消失
//        m_Intent = new Intent(JPushInterface.ACTION_NOTIFICATION_OPENED);

        //点击后去详情页面 带着bundle参数去
        Intent intent = new Intent(context, WebviewActivity.class);
        try {
            JSONObject extraJson = new JSONObject(extra);
            intent.putExtra("mName", extraJson.getString("itemName"));
            intent.putExtra("mUrlOrHref", extraJson.getString("href"));

            Log.i("aaaa", "extra--- " + extra);
            Log.i("aaaa", "maiVisitId--- " + extraJson.getString("maiVisitId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //通知ID,唯一标示。防止通知栏多个通知覆盖
        int notifyId = (int) System.currentTimeMillis();

        //请求码,唯一标示。防止多个intent覆盖，值多少都行 不要重复就可以
        int requestCode = notifyId + 1;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();

        m_NotificationManager.notify(Math.abs(notifyId), notification); // 通过通知管理器发送通知

        wakeUpScreenThreeSec(context);
    }

    //点亮屏幕3秒钟
    private void wakeUpScreenThreeSec(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "");
        wakeLock.acquire(1000 * 3);
        wakeLock.release();
    }

}

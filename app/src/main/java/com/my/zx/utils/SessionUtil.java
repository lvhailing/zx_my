package com.my.zx.utils;

import android.content.Context;
import android.widget.Toast;

import com.my.zx.Constant;

public class SessionUtil {
    public static void checkSession(Context context) {
        //取上次的最新访问时间
        long session_time = PreferenceUtil.getLong(context, "session_time");
        if (System.currentTimeMillis() - session_time > Constant.SESSION_TIME_DIFF) {
            Toast.makeText(context, Constant.NO_SESSION, Toast.LENGTH_SHORT).show();
//            Intent it = new Intent(context, MainFourActivity.class);
//            context.startActivity(it);
            return;
        }

        //记录最新时间
        PreferenceUtil.putLong(context, "session_time", System.currentTimeMillis());
    }
}

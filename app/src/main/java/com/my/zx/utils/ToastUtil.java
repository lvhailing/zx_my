package com.my.zx.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.MyApplication;
import com.my.zx.R;

/**
 * Created by lvhailing on 2017/9/27.
 * 类简单说明:
 */

public class ToastUtil {

    public static void showToast(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(MyApplication.instance, content, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToastPersonality(Context context,String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast toast = new Toast(context);
            TextView view = new TextView(context);
            view.setBackgroundResource(R.drawable.bg_toast);
            view.setTextColor(Color.WHITE);
            view.setText(content);
            view.setPadding(25, 25, 25, 25);
//            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setView(view);
            toast.show();
        }
    }

    public static void showToastPersonality2(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast toast = Toast.makeText(MyApplication.instance, content, Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(android.R.color.holo_green_light);
            view.setPadding(50, 50, 50, 50);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setView(view);
            toast.show();
        }
    }
}

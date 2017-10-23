package com.my.zx.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.my.zx.MyApplication;

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
}

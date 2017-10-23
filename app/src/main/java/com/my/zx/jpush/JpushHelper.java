package com.my.zx.jpush;

import android.content.Context;
import android.util.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lvhailing on 2017/5/3.
 * 类简单说明:
 */

public class JpushHelper {

    public static void registeJpush(Context context, String account) {

        JPushInterface.setAliasAndTags(context, account, null, new TagAliasCallback() {
            @Override
            public void gotResult(int code, String s, Set<String> set) {
                String logs;
                switch (code) {
                    case 0:
                        logs = "Set tag and alias success";
                        Log.i("aaaa", logs);
                        break;

                    case 6002:
                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                        Log.i("aaaa", logs);
                        break;

                    default:
                        logs = "Failed with errorCode = " + code;
                        Log.e("aaaa", logs);
                }

            }
        });
    }
}

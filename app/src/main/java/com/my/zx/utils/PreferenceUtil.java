package com.my.zx.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.my.zx.R;
import com.my.zx.widegt.CustomerDialog;
import com.my.zx.widegt.CustomerDialog.CustomerViewInterface;

public class PreferenceUtil {

    private static SharedPreferences sp = null;
    private static final String SP_USER = "sp_user"; // sp文件名

    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String MYIP = "myip";
    public static final String MYPORT = "myport";
    public static final String MYHTTP = "myhttp";

    private static SharedPreferences getSp(Context context) {
        if (null == sp) {
            sp = context.getSharedPreferences(SP_USER, Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE | Context.MODE_MULTI_PROCESS);
        }
        return sp;
    }

    public static boolean getBoolean(Context context, String key, boolean defVal) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defVal);
    }

    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, "");
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, 0);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, 0);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置是否开启推送
     *
     * @param is
     */
    public static void setOpenPush(Context context, boolean is) {
        getSp(context).edit().putBoolean("openPush", is).commit();
    }

    /**
     * 是否开启推送
     *
     * @return
     */
    public static boolean isOpenPush(Context context) {
        return getSp(context).getBoolean("openPush", true);
    }

    /**
     * 设置是否开启记住密码
     *
     * @param is
     */
    public static void setRememberPassword(Context context, boolean is) {
        getSp(context).edit().putBoolean("rememberPassword", is).commit();
    }

    /**
     * 是否记住密码
     *
     * @return
     */
    public static boolean isRememberPassword(Context context) {
        return getSp(context).getBoolean("rememberPassword", true);
    }

    /**
     * 显示天下贷风格的提示框,(带两个按钮)
     *
     * @param context    要显示的activity
     * @param iconRes    左上角图标，为-1则为默认图标
     * @param title      上方标题，为空则为默认标题
     * @param msg        提示消息，为空则为默认消息
     * @param okText     左边的按钮文字，为空则为默认文字
     * @param cancelText 右边的按钮文字，为空则为默认文字
     * @param callBack   回调方法，为空则点击按钮无效
     * @param type       对话框类型，1为普通双按钮框,2为带标题的双选按钮
     * @return 自定义的dialog
     */
    public static CustomerDialog showDiaDlg(Activity context, final int iconRes, final String msg, final String title, final String okText, final String cancelText, final CustomerDialog.ClickCallBack callBack, final int type) {
        final CustomerDialog cdlg;
        switch (type) {
            case 1:
                cdlg = new CustomerDialog(context, R.layout.dialog_background);
                break;
            case 2:
                cdlg = new CustomerDialog(context, R.layout.title_dialog_background);
                break;
            default:
                cdlg = null;
                break;
        }
//		DisplayMetrics dm = new DisplayMetrics();
//		WindowManager wm = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
//		wm.getDefaultDisplay().getMetrics(dm);
        cdlg.setDlgIfClick(true);// 默认是可以点击外界消失的
        cdlg.setOnCustomerViewCreated(new CustomerViewInterface() {

            @Override
            public void getCustomerView(Window window, AlertDialog dlg) {
                TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
                TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
                Button btn_left = (Button) window.findViewById(R.id.right_button);
                Button btn_right = (Button) window.findViewById(R.id.left_button);
                if (!TextUtils.isEmpty(msg)) {
                    tv_message.setText(StringUtil.ToDBC(msg));
                } else {
                    tv_message.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(title)) {
                    tv_title.setText(StringUtil.ToDBC(title));
                } else {
                    tv_title.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(okText)) btn_left.setText(okText);

                btn_left.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        callBack.onOk(cdlg);
                    }
                });

                if (!TextUtils.isEmpty(cancelText)) btn_right.setText(cancelText);
                btn_right.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        callBack.onCancel(cdlg);
                    }
                });
            }
        });
        cdlg.showDlg();
        return cdlg;
    }
}

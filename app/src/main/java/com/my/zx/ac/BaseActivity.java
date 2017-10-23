package com.my.zx.ac;

import android.app.Activity;
import android.app.ProgressDialog;

public class BaseActivity extends Activity {

    private ProgressDialog progress;

    public void showWaitProgress() {
        if (progress == null) progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(false);
        progress.setCancelable(true);
        progress.setMessage("加载中...");
        progress.show();
    }

    public void closeWaitProgress() {
        if (null != progress && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }
    }


}

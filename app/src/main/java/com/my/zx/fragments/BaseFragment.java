package com.my.zx.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private ProgressDialog progress;

    public void showWaitProgress(Context context) {
        if (progress == null) progress = new ProgressDialog(context);
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

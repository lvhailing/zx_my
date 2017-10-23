package com.my.zx.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.my.zx.R;
import com.my.zx.model.VersionMo;
import com.my.zx.widegt.CustomerDialog;
import com.my.zx.widegt.CustomerDialog.ClickCallBack;
import com.my.zx.widegt.DownloadFileDialog;

public class UpdateUtil {

    private Context context;

    public void checkVersion(Context context, VersionMo version) {
        if (version.getUpgradeContent() == null || version.getUpgradeUrl() == null) {
            return;
        }

        //更新的地址是相对地址。需要拼接 3/31号修改。再次确认又不要了，恢复成原先的就好
//        String absoluteUrl = JsonRequest.baseUrl + version.getUpgradeUrl();
//        version.setUpgradeUrl(absoluteUrl);
        this.context = context;
        String versionDesc = version.getUpgradeContent().replaceAll("\\|", "\n");
        if (versionDesc == null || "".equals(versionDesc.trim())) {
            versionDesc = "有新版本，请马上更新。";
        }
        if (version.isMust() != 0) {
            showForceDialog(version, versionDesc);
        } else {
            showNoForceDialog(version, versionDesc);
        }
    }


    private void showForceDialog(final VersionMo version, final String versionDesc) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("版本更新").setMessage(versionDesc).setIcon(R.drawable.ic_launcher).setPositiveButton("更新", new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = version.getUpgradeUrl();
                if (url != null && url.trim().length() > 0) {
                    startDownload(url, true);
                }
            }

        }).setCancelable(false).create();
        alertDialog.show();
    }

    private void showNoForceDialog(final VersionMo version, final String versionDesc) {
        PreferenceUtil.showDiaDlg((Activity) context, 0, versionDesc, "版本更新", "更新", "取消", new ClickCallBack() {
            @Override
            public void onOk(CustomerDialog dlg) {
                String url = version.getUpgradeUrl();
                if (url != null && url.trim().length() > 0) {
                    startDownload(url, false);
                }
            }

            @Override
            public void onCancel(CustomerDialog dlg) {
                dlg.dismissDlg();
            }
        }, 2);

        // AlertDialog alertDialog = new
        // AlertDialog.Builder(context).setTitle("版本更新").setMessage(versionDesc).setIcon(R.drawable.ic_launcher).setNegativeButton("取消",
        // new Dialog.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // dialog.dismiss();
        // }
        // }).setPositiveButton("更新", new Dialog.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // if (version != null) {
        // String url = version.getUpgradeUrl();
        // if (url != null && url.trim().length() > 0) {
        // startDownload(url, false);
        // }
        // }
        // }
        // }).create();
        // alertDialog.show();
    }

    private void startDownload(String url, final boolean force) {
        new DownloadFileDialog(context, url, 1, new DownloadFileDialog.OnAbortListener() {

            @Override
            public void abort() {
                if (force) {
                    Toast.makeText(context, "您取消了下载", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }
}

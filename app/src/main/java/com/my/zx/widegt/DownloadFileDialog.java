package com.my.zx.widegt;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.R;
import com.my.zx.utils.DeviceUtil;
import com.my.zx.utils.FileUtil;
import com.my.zx.utils.StringUtil;

import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFileDialog extends Dialog implements View.OnClickListener {
	private String mDownloadUrl;
	private TextView tvProgress;
	private ProgressBar progressDownload;
	private Context context;
	private int fileLength;
	private int flag;
	private DownloadTask mTask;
	private int currentLength = 0;
	private int currentProgress;
	private OnAbortListener mAbortListener;

	public DownloadFileDialog(Context context, String downloadUrl, int flag, OnAbortListener listener) {
		super(context);
		this.context=context;
		setCancelable(false);
		this.mDownloadUrl = downloadUrl;
		this.flag=flag;
		this.mAbortListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("文件下载");
		setContentView(R.layout.dialog_download_apk);
		setCancelable(false);
		
		initView();
	}
	
	private void initView() {
	 	Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        
        int[] infos = DeviceUtil.getDeviceWH(context);
        int screenWidth = 300;
        if(infos != null) {
        	screenWidth = infos[0];
        }
        
        lp.width = screenWidth; // 宽度

        dialogWindow.setAttributes(lp);
		
		tvProgress = (TextView) findViewById(R.id.tvProgress);
		progressDownload = (ProgressBar) findViewById(R.id.progressDownload);
		findViewById(R.id.btnCancel).setOnClickListener(this);
		mTask = new DownloadTask();
		mTask.execute();
	}
	
	private class DownloadTask extends AsyncTask<Void, Integer, File> {
		@Override
		protected File doInBackground(Void... arg0) {
			if(StringUtil.isNull(mDownloadUrl)) {
				return null;
			}
			File file = null;
			String fileName = mDownloadUrl.substring(mDownloadUrl.lastIndexOf("/") + 1);
			String pathName = "defalt";
			if(flag==1){
				pathName = FileUtil.getSecondDir(getContext(), "apk") + File.separator + fileName;
			}else if(flag==2){
				pathName = FileUtil.getSecondDir(getContext(), "contract") + File.separator + fileName;
			}
			try {
	            URL url = new URL(mDownloadUrl);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setConnectTimeout(10 * 1000);
	            connection.connect();
	            if (connection.getResponseCode() == HttpStatus.SC_OK) {
	            	fileLength = connection.getContentLength();
	            	if(fileLength <= 0) {
	            		return null;
	            	}
	                progressDownload.setMax(fileLength);
	                file = new File(pathName);
	                if (!file.exists()) {
	                	file.createNewFile();
	        		}
	                InputStream inputStream = connection.getInputStream();
	                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
	                byte[] buffer = new byte[2048];
	                while (true) {
	                    int len = inputStream.read(buffer);
	                    currentLength += len;
	                    currentProgress = currentLength * 100 / fileLength;
//	                    System.out.println("--------------"+currentLength+"----------"+fileLength+"----------"+currentProgress+"%");
//	                    Log.d("aaaaa", "--------------"+currentLength+"----------"+fileLength);
	                    publishProgress(len);
	                    if (len == -1) {
	                        break;
	                    }
	                    arrayOutputStream.write(buffer, 0, len);
//	                    try {
//							Thread.sleep(10);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
	                }
	                arrayOutputStream.close();
	                inputStream.close();
	                byte[] data = arrayOutputStream.toByteArray();
	                FileOutputStream fileOutputStream = new FileOutputStream(file);
	                fileOutputStream.write(data);
	                fileOutputStream.close();
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return file;
		}
		@Override
		protected void onPostExecute(File result) {
			if(result == null || !result.exists()) {
				Toast.makeText(getContext(), "文件下载失败！", Toast.LENGTH_LONG).show();
				dismiss();
				if(null != mAbortListener) {
					mAbortListener.abort();
				}
				return;
			}
			if(flag==1){
				installApk(result);
			}else if(flag==2){
				openPDF(result);
			}
			dismiss();
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			tvProgress.setText(getContext().getString(R.string.alert_current_progress,
					(double) currentLength / 1024, (double) fileLength / 1024, currentProgress));
//            System.out.println("----------"+currentProgress+"%%%");

			progressDownload.setProgress(currentLength);
		}
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
	
	protected void installApk(File file) {
		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		getContext().startActivity(intent);
	}

	public void openPDF(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(uri, "application/pdf");
        try {
            getContext().startActivity(intent);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(context, "您尚未安装pdf阅读器", Toast.LENGTH_SHORT).show();
        }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			if(null != mTask) {
				mTask.cancel(true);
				if(null != mAbortListener) {
					mAbortListener.abort();
				}
			}
			dismiss();
			break;
		default:
			break;
		}
	}

	public static interface OnAbortListener {
		void abort();
	}
}

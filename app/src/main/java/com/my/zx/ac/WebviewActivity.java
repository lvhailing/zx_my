package com.my.zx.ac;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zx.Constant;
import com.my.zx.R;
import com.my.zx.MyApplication;
import com.my.zx.net.JsonRequest;
import com.my.zx.utils.FileUtil;
import com.my.zx.utils.ToastUtil;
import com.my.zx.utils.Util;

import org.apache.http.HttpStatus;
import org.apache.http.cookie.Cookie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WebviewActivity extends BaseActivity {
    private WebView wv;
    private ProgressBar pb;
    //	private HomeMo item;
    private TextView tv_title;
    private String mName, mUrlOrHref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        if (getIntent() != null) {
//			item=(HomeMo) getIntent().getSerializableExtra("item");
            mName = (String) getIntent().getExtras().get("mName");
            mUrlOrHref = (String) getIntent().getExtras().get("mUrlOrHref");
        }
        initView();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        wv = (WebView) findViewById(R.id.webBulletin);
        pb = (ProgressBar) findViewById(R.id.progressBulletin);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new DemoJavaScriptInterface(), "jsObj");
        wv.setWebChromeClient(chromeClient);
        wv.setWebViewClient(webClient);
        wv.setDownloadListener(new MyWebViewDownLoadListener());
    }

//		wv.setDownloadListener(new MyWebViewDownLoadListener2());


    private void initData() {
        tv_title.setText(mName);

        //有且只有中信的系统，邮件是单独服务器的，只能用绝对路径
        String mUrl;
        if (mUrlOrHref.startsWith("http")) {
            mUrl = mUrlOrHref;
        } else {
            mUrl = JsonRequest.baseUrl + mUrlOrHref.substring(1, mUrlOrHref.length());
        }
        String deviceInfo = new Build().MODEL;
        String deviceInfoDec = "";
        try {
            deviceInfoDec = URLEncoder.encode(deviceInfo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mUrl += "&deviceInfo=" + deviceInfoDec;
        setCookies();
        wv.loadUrl(mUrl);
    }

    public static void setCookies() {
        Cookie ck = MyApplication.instance.getCookie();

//		CookieSyncManager.createInstance(MyApplication.instance);//改在theapplication中创建一次
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//		cookieManager.removeSessionCookie();//对三星手机不适配 移除该行  

        //4.0版本 因为将登陆后置了 所以cookie需要保存到sp，而不是之前的TheApplication
//		String cookieString = ck.getName() + "=" + ck.getValue() + "; domain=" + ck.getDomain();
//		cookieManager.setCookie(ck.getDomain(), cookieString);

        String domainValue = Util.getDomainValue();
        String cookieString = Util.getCookieString();
        String cookieResult = cookieString + "; domain=" + domainValue;
        cookieManager.setCookie(domainValue, cookieResult);
        CookieSyncManager.getInstance().sync();
    }

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);
        }
    };

    private WebViewClient webClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            pb.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pb.setVisibility(View.VISIBLE);
        }
    };

//	@Override
//	protected void onResume() {
//		super.onResume();
//		CookieSyncManager.getInstance().startSync();
//	}
//	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		CookieSyncManager.getInstance().stopSync();
//	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goback(View v) {
        finish();
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        //window.jsObj.callZXApp();
        public void callZXApp() {
            //正常保存，提交  js调用
            finish();
        }

        //window.jsObj.callZXAppForSessionInvalid();
        public void callZXAppForSessionInvalid() {
            //session过期或者未登录 js调用
            ToastUtil.showToast(Constant.NO_LOGIN_OR_SESSION);
            finish();
        }

        //window.jsObj.callZXAppForNum("18001122787");
        public void callZXAppForNum(final String agentPhone) {
//			Toast.makeText(WebviewActivity.this, "phone : " + agentPhone, Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("tel:" + agentPhone);
            Intent it = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(it);
        }
    }

    ;

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast t = Toast.makeText(getApplicationContext(), "需要SD卡。", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }
            DownloaderTask task = new DownloaderTask();
            task.execute(url);
        }
    }

    private class DownloaderTask extends AsyncTask<String, Void, File> {
        public DownloaderTask() {
        }

        @Override
        protected File doInBackground(String... params) {
            String url1 = params[0];
            if (url1 == null || url1.equals("")) {
                return null;
            }
            String fileName = System.currentTimeMillis() + url1.substring(url1.lastIndexOf("."));
            File file = null;
            String pathName = FileUtil.getSecondDir(WebviewActivity.this, "contract") + File.separator + fileName;
            try {
                URL url = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10 * 1000);

                connection.setRequestProperty("Cookie", Util.getCookieString());

                connection.connect();
                if (connection.getResponseCode() == HttpStatus.SC_OK) {
                    int fileLength = connection.getContentLength();
                    if (fileLength <= 0) {
                        return null;
                    }
                    file = new File(pathName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[2048];
                    while (true) {
                        int len = inputStream.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        arrayOutputStream.write(buffer, 0, len);
                    }
                    arrayOutputStream.close();
                    inputStream.close();
                    byte[] data = arrayOutputStream.toByteArray();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(data);
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return file;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(File result) {
            super.onPostExecute(result);
            closeProgressDialog();
            if (result == null) {
                Toast t = Toast.makeText(getApplicationContext(), "下载出错！", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }

            Toast t = Toast.makeText(getApplicationContext(), "正在打开", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();

            Intent intent = getFileIntent(result);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "您尚未安装" + end + "阅读器 ， 建议您到市场下载", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private ProgressDialog mDialog;
    private String end = "";

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog.setMessage("正在加载 ，请等待...");
            mDialog.setIndeterminate(false);//设置进度条是否为不明确
            mDialog.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDialog = null;
                }
            });
            mDialog.show();

        }
    }

    private void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public Intent getFileIntent(File file) {
//			 Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        long a = file.length();
        System.out.println(a);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public void writeToSDCard(String fileName, InputStream input) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[2048];
                int j = 0;
                while ((j = input.read(b)) != -1) {
                    fos.write(b, 0, j);
                }
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

	      /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
//	      else if(end.equals("pptx")||end.equals("ppt")){
//	    	  type = "application/vnd.ms-powerpoint"; 
//	      }else if(end.equals("docx")||end.equals("doc")){
//	    	  type = "application/vnd.ms-word";
//	      }else if(end.equals("xlsx")||end.equals("xls")){
//	    	  type = "application/vnd.ms-excel";
//	      }
        else {
//	    	  /*如果无法直接打开，就跳出软件列表给用户选择 */  
            type = "*/*";
        }
        return type;
    }


//	打开附件 方式二  用第三方浏览器打开 效果不好
//	private class MyWebViewDownLoadListener2 implements DownloadListener{
//
//        @Override
//        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
//                                    long contentLength) {        	
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);        	 
//        }
//    }
}

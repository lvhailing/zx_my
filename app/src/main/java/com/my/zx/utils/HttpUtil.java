package com.my.zx.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 联网
 */
public class HttpUtil {

    public static String httpGet_NoCookie(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            // 创建请求
            HttpGet get = new HttpGet(new URI(url));
            // 发get请求   
            HttpResponse httpResponse = httpClient.execute(get);
            // 如果服务成功返回响应
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 获取服务器响应的json字符
                    String json = EntityUtils.toString(entity);
                    Log.i("aaa", "result: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpPost_NoCookie(String url, Map<String, Object> params) {
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            HttpPost post = new HttpPost(new URI(url));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
            post.setEntity(entity);

            // 发post请求
            HttpResponse httpResponse = httpClient.execute(post);
            // 如果服务成功返回响应
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (entity != null) {
                    // 获取服务器响应的json字符
                    String json = EntityUtils.toString(httpEntity);
                    Log.i("aaa", "result: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String login_SaveCookie(String url, Map<String, Object> params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        if (params != null && !params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            HttpPost post = new HttpPost(new URI(url));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
            post.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                CookieStore mCookieStore = httpClient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie ck = cookies.get(i);
                    if (ck != null) {
//                        MyApplication.instance.setCookie(ck);
                        //4.0版本 因为将登陆后置了 所以cookie需要保存到sp，而不是之前的TheApplication
                        String cookieString = ck.getName() + "=" + ck.getValue();
                        String domainValue = ck.getDomain();
                        Util.putCookieString(cookieString);
                        Util.putCookie_DomainValue(domainValue);
                        return ck.getValue();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpPost_ByCookie(String url, Map<String, Object> params) {
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        if (params != null && !params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            HttpPost post = new HttpPost(new URI(url));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
            post.setEntity(entity);

            post.setHeader("Cookie", Util.getCookieString());

            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity);
                Log.i("aaa", "result: " + json);
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpGet_ByCookie(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet(new URI(url));

            get.setHeader("Cookie", Util.getCookieString());

            HttpResponse httpResponse = httpClient.execute(get);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    String json = EntityUtils.toString(entity);
                    Log.i("aaa", "result: " + json);
                    return json;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

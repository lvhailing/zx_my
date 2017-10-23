package com.my.zx.net;

import com.my.zx.MyApplication;
import com.my.zx.model.AdPhotoMo;
import com.my.zx.model.HomeMo;
import com.my.zx.model.VersionMo;
import com.my.zx.utils.HttpUtil;
import com.my.zx.utils.PreferenceUtil;
import com.my.zx.utils.StringUtil;
import com.my.zx.utils.Util;

import java.util.HashMap;
import java.util.Map;

public class JsonRequest {

    //原先的内网地址
//    public static String baseUrl = "http://oa.amc.intra.citic.com/";

    //2017年3月30日添加，需求是改成了外网访问
    public static String baseUrl = "http://47.93.47.17/";

    private String loginPath = "names.nsf?login";
    private String homeListPath = "produce/mobileMng.nsf/MobileHomeData?readform";
    private String checkVersionPath = "produce/mobilemng.nsf/getappupversion?readform";
    private String homeAdPhotosPath = "produce/mobilemng.nsf/agtGetViewDataPic?open&&odbpath=application/infopublish.nsf&oviewname=vwinfoforshow&page=1&rows=6&oviewcategory=tpxw&type=pic";
    private String feedBackPath = "application/feedbackproblem.nsf/postproblem?openagent";

    public JsonRequest() {
        String ip = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYIP);
        String port = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYPORT);
        String mHttp = PreferenceUtil.getString(MyApplication.instance, PreferenceUtil.MYHTTP);
        if (!StringUtil.isNull(mHttp)) {
            baseUrl = mHttp + "/";
        } else if (!StringUtil.isNull(ip)) {
            if (!StringUtil.isNull(port)) {
                baseUrl = "http://" + ip + ":" + port + "/";
            } else {
                baseUrl = "http://" + ip + "/";
            }
        }
    }

    // 用户登录
    public ResultObject login(String userName, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", userName);
        map.put("password", password);
        MyApplication.userName = userName;
        try {
            String response = HttpUtil.login_SaveCookie(baseUrl + loginPath, map);
            ResultObject resultObject = JsonParse.parseLogin(response);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    // 获取首页小红点等信息
    public ResultObject getHomeList() {
        try {
            String response = HttpUtil.httpPost_ByCookie(baseUrl + homeListPath + Util.getIsLoginSuffix(), null);
            ResultObject resultObject = JsonParse.parseListHome(response, "list", HomeMo.class);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    //首页轮播图
    public ResultObject getHomeAdPhotos() {
        try {
            String response = HttpUtil.httpGet_NoCookie(baseUrl + homeAdPhotosPath);
            ResultObject resultObject = JsonParse.parseList(response, "list", AdPhotoMo.class);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    //用户点击首页进入到子列表页
    public ResultObject getSecondLevelList(String mUrl) {
        try {
            String response = HttpUtil.httpGet_ByCookie(baseUrl + mUrl + Util.getIsLoginSuffix());
            ResultObject resultObject = JsonParse.parseListSecondLevel(response);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    //提交反馈
    public ResultObject feedBack(String content) {
        try {
            String response = HttpUtil.httpGet_NoCookie(baseUrl + feedBackPath + "&content=" + content + Util.getIsLoginSuffix());
            ResultObject resultObject = JsonParse.parseNoData(response);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    //版本检查
    public ResultObject checkVersion(String vname, int vcode) {
        try {
            String response = HttpUtil.httpGet_NoCookie(baseUrl + checkVersionPath + "&appVersioin=" + vname + "&appVersionInt=" + vcode);
            ResultObject resultObject = JsonParse.parseObject(response, VersionMo.class);
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            return catchAndDo(e);
        }
    }

    private ResultObject catchAndDo(Exception e) {
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(-1);
        resultObject.setSuccess(false);
        resultObject.setMessage("连接出现问题，请重试");
        return resultObject;
    }
}

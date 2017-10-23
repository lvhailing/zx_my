package com.my.zx.net;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.my.zx.Constant;
import com.my.zx.model.SecondLevelMo;
import com.my.zx.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {

    //只关心接口调成功还是失败 不要里面的数据
    public static ResultObject parseNoData(String response) {
        if (response == null) {
            return new ResultObject(Constant.NO_SERVER_DATA, -1);
        }
        Map<String, Object> map = JSON.parseObject(response);
        String message = (String) map.get("message");
        String codeStr = (String) map.get("code");
        int code = Integer.valueOf(codeStr);

        ResultObject resultObject = new ResultObject();
        resultObject.setMessage(message);
        resultObject.setSuccess(code == 0);
        resultObject.setCode(code);
        return resultObject;
    }

    public static ResultObject parseObject(String response, Class<?> clz) {
        if (response == null) {
            return new ResultObject(Constant.NO_SERVER_DATA, -1);
        }
        ResultObject resultObject = new ResultObject();
        Map<String, Object> map = JSON.parseObject(response);
        String message = (String) map.get("message");
        String codeStr = (String) map.get("code");
        int code = Integer.valueOf(codeStr);
        Map<String, Object> data = (Map<String, Object>) map.get("data");

        Object object = JSON.parseObject(data.toString(), clz);
        resultObject.setMessage(message);
        resultObject.setSuccess(code == 0);
        resultObject.setObject(object);
        resultObject.setCode(code);
        return resultObject;
    }

    public static ResultObject parseList(String response, String key, Class<?> clz) {
        if (response == null) {
            return new ResultObject(Constant.NO_SERVER_DATA, false);
        }
        ResultObject resultObject = new ResultObject();

        Map<String, Object> map = JSON.parseObject(response);
        String message = (String) map.get("message");
        String codeStr = (String) map.get("code");
        int code = Integer.valueOf(codeStr);
        Map<String, Object> data = (Map<String, Object>) map.get("data");

        String value = data.get(key).toString();
        if (value != null) {
            List<?> list = JSON.parseArray(value, clz);
            resultObject.setSuccess(true);
            resultObject.setObject(list);
        } else {
            resultObject.setSuccess(false);
        }
        resultObject.setMessage(message);
        resultObject.setCode(code);
        return resultObject;
    }

    public static ResultObject parseListHome(String response, String key, Class<?> clz) {
        if (response == null) {
            return new ResultObject(Constant.NO_SERVER_DATA, false);
        }
        ResultObject resultObject = new ResultObject();

        Map<String, Object> map = JSON.parseObject(response);
        String message = (String) map.get("message");
        String codeStr = (String) map.get("code");
        int code = Integer.valueOf(codeStr);
        Map<String, Object> data = (Map<String, Object>) map.get("data");

        String userName = (String) data.get("username");
        String userId = (String) data.get("userId");

        String listStr = data.get(key).toString();
        if (listStr != null) {
            List<?> list = JSON.parseArray(listStr, clz);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userName", userName);
            resultMap.put("userId", userId);
            resultMap.put("list", list);

            resultObject.setSuccess(true);
            resultObject.setObject(resultMap);
        } else {
            resultObject.setSuccess(false);
        }
        resultObject.setMessage(message);
        resultObject.setCode(code);
        return resultObject;
    }

    public static ResultObject parseListSecondLevel(String response) {
        if (response == null) {
            return new ResultObject(Constant.NO_SERVER_DATA, false);
        }
        ResultObject resultObject = new ResultObject();
        Map<String, Object> map = JSON.parseObject(response);
        String codeStr = (String) map.get("code");
        int code = Integer.valueOf(codeStr);
        String message = (String) map.get("message");

        if (map.get("data") == null) {
            return resultObject;
        }

        Map<String, Object> data = (Map<String, Object>) map.get("data");

        List<SecondLevelMo> trzList = new ArrayList<SecondLevelMo>();
        JSONArray dataJsonArrayArea = (JSONArray) data.get("list");
        if (dataJsonArrayArea != null) {
            for (Object jsonObject : dataJsonArrayArea) {
                Map<String, Object> jsonMap = (Map<String, Object>) jsonObject;
                SecondLevelMo rent = new SecondLevelMo();

                rent.setTitle((String) jsonMap.get("title"));
                rent.setType((String) jsonMap.get("type"));
                rent.setTime((String) jsonMap.get("time"));
                rent.setUrl((String) jsonMap.get("url"));
                rent.setTitleRed((Boolean) jsonMap.get("isTitleRed"));
                trzList.add(rent);
            }
            resultObject.setSuccess(true);
            resultObject.setObject(trzList);
        } else {
            resultObject.setSuccess(false);
        }
        resultObject.setMessage(message);
        resultObject.setCode(code);
        return resultObject;
    }

    public static ResultObject parseLogin(String session) {
        if (StringUtil.isNull(session)) {
            return new ResultObject(Constant.NO_SERVER_DATA, false);
        }
        return new ResultObject(session, true);
    }
}

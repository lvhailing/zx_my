package com.my.zx.net;


import com.my.zx.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonBuild {

    protected static String buildParas() {
    	Map<String, Object> map = new HashMap<String, Object>();
        String result ="";
        map.put("appType", 1);
        map.put("source", 1);
        for (String entry : map.keySet()) {
            if(!StringUtil.isNull(result)) {
                result = result + "&";
            }
            result = result + "" + entry +"=" + map.get(entry);
        }
        return result;
    }
    
    protected static String buildParas(Map<String ,Object> map) {
        String result ="";
        map.put("appType", 1);
        map.put("source", 1);
        for (String entry : map.keySet()) {

            if(!StringUtil.isNull(result)) {
                result = result + "&";
            }
            result = result + "" + entry +"=" + map.get(entry);
        }
        return result;
    }
    

    public static Map<String ,Object> buildParas2Map(Map<String ,Object> map) {
      map.put("appType", 1);
      map.put("source", 1);
      return map;
    }
    
}

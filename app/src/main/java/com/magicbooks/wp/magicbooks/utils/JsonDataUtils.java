package com.magicbooks.wp.magicbooks.utils;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;

public class JsonDataUtils {
    /**
     * ali de fastJson
     *
     * @param response json
     * @param clazz
     * @return
     */
    public final static <T> T jsonToObject(String response, Class<T> clazz) {
        if (StringUtils.strIsEmpty(response)) {
            return null;
        }
        try {
            return JSON.parseObject(response, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static int jsonGetKey(String response, String key) {
        if (StringUtils.strIsEmpty(response)) {
            return -1;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param originDetailJson
     * @return
     */
    public static HashMap<String, String> paseJsonValueToHashMap(String originDetailJson) {
        HashMap<String, String> valueHashMap = new HashMap<String, String>();
        try {
            JSONObject obj = new JSONObject(originDetailJson);
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                String value = obj.getString(key).toString();
                if (key.equals("user")) {
                    try {
                        String user = obj.getString("user");
                        if (!StringUtils.strIsEmpty(user)) {
                            JSONObject userObj = new JSONObject(user);
                            Iterator userIterator = userObj.keys();
                            while (userIterator.hasNext()) {
                                String userKey = userIterator.next().toString();
                                String userValue = userObj.getString(userKey).toString();
                                valueHashMap.put("user." + userKey, userValue);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueHashMap;
    }
}

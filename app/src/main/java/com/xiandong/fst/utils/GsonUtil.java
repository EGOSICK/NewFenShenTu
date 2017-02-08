package com.xiandong.fst.utils;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Gson辅助类
 */
public class GsonUtil {
    /**
     * 字符串转化为实体
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (null == json || json.length() == 0) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 实体转化字符串
     */
    public static String toJson(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

}

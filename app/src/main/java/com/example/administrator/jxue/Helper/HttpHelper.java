package com.example.administrator.jxue.Helper;

import com.lidroid.xutils.HttpUtils;

/**
 * Created by Administrator on 2015-5-1.
 */
public class HttpHelper {
    private static HttpUtils utils;

    public static void init() {
        utils = new HttpUtils();
    }

    public static HttpUtils getUtils() {
        return utils;
    }
}

package com.magicbooks.wp.magicbooks.http_utils;

/**
 * Created by wp on 2016/12/30.
 */
import android.content.Context;

import com.lidroid.xutils.http.RequestParams;
import com.magicbooks.wp.magicbooks.AppConfig;
import com.magicbooks.wp.magicbooks.utils.PackageUtils;
import com.magicbooks.wp.magicbooks.utils.StringUtils;

import java.net.URI;

public class Urls {
    private static URI uri;

    public static String html5Type = "/hybird/";
    public static String device = "android";
    public static String versionCode = "";
    public static String host = "http://magicbooks.carkoubei.com/api/v1/";
    public static String fayeUrl = "http://carkoubei.com:9595";

    /**
     * GET方式请求基本的参数
     *
     * @param mContext
     * @return
     */
    public static String getUrlParam(Context mContext) {
        return "?user_token=" + AppConfig.getUserToken(mContext)
                + "&device=" + device
                + "&client_id=" + AppConfig.getNoticeClientId(mContext)
                + "&version_code=" + PackageUtils.getVersionName(mContext).replace("v", "").replace("test", "");
    }

    /**
     * PUT POST DELETE 等上送的公共参数
     *
     * @param mContext
     * @return
     */
    public static RequestParams getCommentParam(Context mContext) {
        RequestParams params = new RequestParams();
        if (!StringUtils.strIsEmpty(AppConfig.getUserToken(mContext))) {
            params.addBodyParameter("user_token", AppConfig.getUserToken(mContext));
        }
        params.addBodyParameter("device", "android");
        params.addBodyParameter("version_code", PackageUtils.getVersionName(mContext).replace("v", "").replace("test", "") + "");
        return params;
    }

    //登录模块
    public static String getLoginUrl() {
        return host + "auth/login";
    }
    public static String getNotificationUrl() {
        return host + "notifications";
    }
}

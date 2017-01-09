package com.magicbooks.wp.magicbooks.http_utils;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;

/**
 * auther：huwei on 15/12/16 15:33
 * mail：huweijava@163.com
 */
public class XutilsHttpClient {
    private static HttpUtils client;

    /**
     * 单例模式获取实例对象
     * @return HttpUtils对象实例
     */
    public synchronized static HttpUtils getInstence(Context context) {
        if (client == null) {
            // 设置请求超时时间为10秒
            client = new HttpUtils(10000);
            client.configSoTimeout(10000);
            client.configResponseTextCharset("UTF-8");
            client.configDefaultHttpCacheExpiry(1000);
            /*// 保存服务器端(Session)的Cookie
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
            cookieStore.clear(); // 清除原来的cookie
            client.configCookieStore(cookieStore);*/
        }
        return client;
    }
}

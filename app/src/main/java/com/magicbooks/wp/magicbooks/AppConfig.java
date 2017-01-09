package com.magicbooks.wp.magicbooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;


import com.magicbooks.wp.magicbooks.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 常用信息的存储
 */
public class AppConfig {
    private static String userToken = null;
    private static String userAccount;
    private static int userId = -1;
    private static String faye_channel = "";

    /**

     *  常用的变量存储
     */
    public static String USER_ID = "user_id";
    public static String USER_TOKEN_KEY = "user_token";
    public static String USER_ACCOUNT = "user_account";
    public static String API_HOST = "api_host";
    public static String HYBIRD_HOST = "hybird_host";
    public static String ABOUT_HOST = "about_host";
    public static String ACC_TOKEN_KEY_D = "access_token_d";
    public static String API_HOST_D = "api_host_d";
    public static String HYBIRD_HOST_D = "hybird_host_d";
    public static String ABOUT_HOST_D = "about_host_d";

    public static String APP_VERSION_CODE = "versionCode";//app版本号升级后用到
    public static final String PATH;

    public static boolean isShowDialog = false;

    static {
        String root = Environment.getExternalStorageDirectory().getParent();
        PATH = (root == null ? "" : root) + Environment.getExternalStorageDirectory();
    }
    /**
     * 用户的token
     *
     * @param context
     * @return
     */
    public static String getUserToken(Context context) {
        if (userToken == null) {
            userToken = PreferenceUtils.getPrefString(context, USER_TOKEN_KEY, null);
        }
        return userToken;
    }

    public static void setUserToken(Context context, String userToken) {
        AppConfig.userToken = userToken;
        PreferenceUtils.setPrefString(context, USER_TOKEN_KEY, userToken);
    }

    public static int getUserId(Context context) {
        if (userId == -1) {
            userId = Integer.parseInt(PreferenceUtils.getPrefString(context, USER_ID, null));
        }
        return userId;
    }

    public static void setUserId(Context context, int userId) {
        AppConfig.userId = userId;
        PreferenceUtils.setPrefString(context, USER_ID, String.valueOf(userId));
    }
    /**
     * 用户的帐号
     *
     * @param context
     * @return
     */
    public static void setUserAccount(Context context, String account) {
        PreferenceUtils.setPrefString(context, USER_ACCOUNT, account);
        AppConfig.userAccount = account;
    }

    /***
     * 当前登录账户
     * @param context
     * @return
     */
    public static String getUserAccount(Context context) {
        if (TextUtils.isEmpty(userAccount)) {
            userAccount = PreferenceUtils.getPrefString(context, USER_ACCOUNT, "");
        }
        return userAccount;
    }

    /**
     * 设置推送的clientid
     */
    private static String CLIENT_ID = "client_id";

    public static void setNoticeClientId(Context context, String clientid) {
        PreferenceUtils.setPrefString(context, CLIENT_ID, clientid);
    }

    public static String getNoticeClientId(Context context) {
        return PreferenceUtils.getPrefString(context, CLIENT_ID, "");
    }

    public static String getFaye_channel(Context context) {
        Log.i("22222222", getUserToken(context) + getUserId(context));
        return getUserToken(context) + getUserId(context);
    }

    public static void clearCache(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        PreferenceUtils.clearPreference(context, settings);
    }

}
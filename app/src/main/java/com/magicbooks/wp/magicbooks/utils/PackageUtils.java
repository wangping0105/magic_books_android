package com.magicbooks.wp.magicbooks.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtils {

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {

            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {

            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return versionName;
    }

    /***
     * 比较版本号 用的是versionName 进行对比 需要进行数组拆分比较
     * @param context
     * @param newAppCode
     * @return
     */
    public static boolean isUploadNewApp(Context context, String newAppCode){
        boolean isUploadNewApp = false;
        String locationApp = getVersionName(context).replace("v", "").replace("test", "");
//        Logger.d("dive", "server app version is=" + newAppCode + "=location version is=" + PackageUtils.getVersionName(context).replace("v", "").replace("test", ""));
        if(!StringUtils.strIsEmpty(newAppCode)){
            String[] newAppVCode = newAppCode.split("\\.");
            String[] locationAppVCode = locationApp.split("\\.");
            if(!StringUtils.strIsEmpty(newAppVCode[0]) && !StringUtils.strIsEmpty(locationAppVCode[0])){

                if((Integer.parseInt(newAppVCode[0]) - Integer.parseInt(locationAppVCode[0])) > 0){
                    isUploadNewApp = true;
                } else if((Integer.parseInt(newAppVCode[0]) - Integer.parseInt(locationAppVCode[0])) == 0){
                    if(!StringUtils.strIsEmpty(newAppVCode[1]) && !StringUtils.strIsEmpty(locationAppVCode[1])){

                        if((Integer.parseInt(newAppVCode[1]) - Integer.parseInt(locationAppVCode[1])) > 0){
                            isUploadNewApp = true;
                        } else if((Integer.parseInt(newAppVCode[1]) - Integer.parseInt(locationAppVCode[1])) == 0){
                            if(!StringUtils.strIsEmpty(newAppVCode[2]) && !StringUtils.strIsEmpty(locationAppVCode[2])){

                                if((Integer.parseInt(newAppVCode[2]) - Integer.parseInt(locationAppVCode[2])) > 0){
                                    isUploadNewApp = true;
                                } else {
                                    isUploadNewApp = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return isUploadNewApp;
    }
}

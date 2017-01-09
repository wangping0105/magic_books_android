package com.magicbooks.wp.magicbooks.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

/**
 * Created by dive on 2015/7/8.
 */
public class StringUtils {
    public static Boolean strIsEmpty(String orgStr){
        if(orgStr == null || orgStr.equals("null") || orgStr == "null" || orgStr.length() <= 0){
            return  true;
        }
        return false;
    }


    public static String toUpperCase(String str){
        String first = str.substring(0, 1).toUpperCase();
        String rest = str.substring(1, str.length());
        return new StringBuffer(first).append(rest).toString();
    }

    public static String getRemoveLast(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.substring(str.indexOf("-")+1);
            return str;
        }else {
            return str;
        }

    }
}

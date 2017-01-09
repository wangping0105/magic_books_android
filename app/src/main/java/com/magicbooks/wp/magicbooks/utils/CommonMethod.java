package com.magicbooks.wp.magicbooks.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by wp on 2017/1/6.
 */
public class CommonMethod {

    /**
     * 检查是否能连接网络
     */
    public static boolean check_connect_net(Context con) {
        ConnectivityManager cwjManager = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (cwjManager.getActiveNetworkInfo() != null
                    && cwjManager.getActiveNetworkInfo().isAvailable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //后台任务
    private class MyTask extends AsyncTask<String, Integer, String> {
        String TAG = "MyTask";
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground(Params... params) called");
            try {
//               validateUser();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
        }
    }
}

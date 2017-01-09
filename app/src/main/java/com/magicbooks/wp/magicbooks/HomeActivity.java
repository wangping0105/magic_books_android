package com.magicbooks.wp.magicbooks;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.magicbooks.wp.magicbooks.adapter.NotificationAdapter;
import com.magicbooks.wp.magicbooks.http_utils.Urls;
import com.magicbooks.wp.magicbooks.http_utils.XutilsHttpClient;
import com.magicbooks.wp.magicbooks.models.Notification;
import com.magicbooks.wp.magicbooks.models.json.NotificationListJson;
import com.magicbooks.wp.magicbooks.utils.CommonMethod;
import com.magicbooks.wp.magicbooks.utils.CustomToast;
import com.magicbooks.wp.magicbooks.utils.JsonDataUtils;
import com.saulpower.fayeclient.FayeClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity  {
    private Handler handler;
    protected Context context = HomeActivity.this;
    NotificationAdapter notificationAdapter;
    protected HttpUtils httpUtils = new XutilsHttpClient().getInstence(context);

    private int newNotificationCount = 0;
    @ViewInject(R.id.username)
    TextView userNameView;

    @ViewInject(R.id.title)
    TextView titleView;

    @ViewInject(R.id.new_notificatoin)
    TextView newNotificatoinView;

//    @ViewInject(R.id.notification_list)
    ListView notificationListView;
    private PullToRefreshListView mPullToRefreshListView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewUtils.inject(this);
        userNameView.setText(AppConfig.getUserAccount(context));
        titleView.setText("消息");
        handler.postDelayed(notificatiosRunnable, 100);

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.notification_list);
        mPullToRefreshListView.setEmptyView(findViewById(R.id.empty));
        notificationListView = mPullToRefreshListView.getRefreshableView();
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetNotificationsTask().execute();
            }
        });

        notificationAdapter = new NotificationAdapter(context);

        fayeNotification();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // 刚刚开始进来的时候执行的请求第一页 notifications
    AlertViewRunnable notificatiosRunnable = new AlertViewRunnable();
    class AlertViewRunnable implements Runnable {
        @Override
        public void run() {
            fetchNotificatios(1);
        }
    }

    // 执行上拉刷新任务
    private class GetNotificationsTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call onRefreshComplete when the list has been refreshed.
            fetchNotificatios(1);
            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    newNotificationCount ++;
                    newNotificatoinView.setText("最新通知("+ msg.obj +"),共有"+ newNotificationCount +"条未读通知，请上拉刷新！");
                    break;
            }
        }
    };

    // faye
    private void fayeNotification() {
        URI uri = URI.create(String.format("ws://%s:9595/faye", "carkoubei.com"));
        String channel = String.format("/notifications/%s", AppConfig.getFaye_channel(context));
        Log.i("=============", channel);
        FayeClient fayeClient = new FayeClient(handler, uri, channel);
        fayeClient.setFayeListener(new FayeClient.FayeListener() {
            @Override
            public void connectedToServer() {
            }

            @Override
            public void disconnectedFromServer() {
            }

            @Override
            public void subscribedToChannel(String subscription) {
            }

            @Override
            public void subscriptionFailedWithError(String error) {
            }

            @Override
            public void messageReceived(JSONObject json) {
                Log.i("messageReceived", json.toString());
                try {
                    Log.i("messageReceived", json.getJSONObject("notification").getString("title"));

                    Message message = Message.obtain();
//                    message.arg1 = 1;
//                    message.arg2 = 2;
                    message.obj = json.getJSONObject("notification").getString("title");
                    message.what = 1;

                    mHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        fayeClient.connectToServer(null);
    }

    /**
     * 通知消息列表
     */
    private void fetchNotificatios(int page) {
        if (!CommonMethod.check_connect_net(context)) {
            CustomToast.showMsg(context, "请检查您的网络连接!");
            return;
        }

        RequestParams params = Urls.getCommentParam(context);
        params.addBodyParameter("page", String.valueOf(page));
        final HttpHandler<String> send = httpUtils.send(HttpRequest.HttpMethod.GET, Urls.getNotificationUrl(), params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                Log.i("11111111dive", "url is=" + getRequestUrl());
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("jack return....", "返回的信息" + responseInfo.result);
                NotificationListJson notificationListJson = JsonDataUtils.jsonToObject(responseInfo.result, NotificationListJson.class);
                if (notificationListJson != null) {
                    if (notificationListJson.getCode() == 0) {
                        NotificationListJson.DataInfo dataInfo = notificationListJson.getData();

                        List<Notification> list = dataInfo.getNotifications();
                        notificationAdapter.setList(list);
                        notificationAdapter.setListView(notificationListView);
                        notificationListView.setAdapter(notificationAdapter);

//                        ArrayList<Map<String,String>> notificationArrayList = new ArrayList();
//                        Map<String,String> map=null;
//                        for (Notification notification : list) {
//                            map = new HashMap<String,String>();       //为避免产生空指针异常，有几列就创建几个map对象
//                            map.put("id", notification.getId());
//                            map.put("title", notification.getTitle());
//                            map.put("body", notification.getBody());
//                            notificationArrayList.add(map);
//                            Log.d("notification", notification.getTitle());
//
//                        }
//                        Log.d("notification size", notificationArrayList.size()+"");
//
//                        SimpleAdapter adapter = new SimpleAdapter(HomeActivity.this, notificationArrayList, R.layout.list,
//                                new String[]{"id", "title", "body"},
//                                new int[]{R.id.id, R.id.title, R.id.body});
//                        lv.setAdapter(adapter);
                    } else {
                        CustomToast.showMsg(context, notificationListJson.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                CustomToast.showMsg(context, e.getExceptionCode() + "，请求出错！");
            }

        });
    }
}

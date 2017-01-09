package com.magicbooks.wp.magicbooks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.magicbooks.wp.magicbooks.http_utils.Urls;
import com.magicbooks.wp.magicbooks.http_utils.XutilsHttpClient;
import com.magicbooks.wp.magicbooks.models.json.LoginJson;
import com.magicbooks.wp.magicbooks.utils.CommonMethod;
import com.magicbooks.wp.magicbooks.utils.JsonDataUtils;
import com.magicbooks.wp.magicbooks.utils.CustomToast;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginError, mRegister, ONLYTEST;
    int selectIndex=1;
    int tempSelect=selectIndex;
    boolean isReLogin=false;
    private int SERVER_FLAG=0;
    private Handler handler;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    protected Context mContext = MainActivity.this;
    protected HttpUtils httpUtils = new XutilsHttpClient().getInstence(mContext);//在onstart里赋值的话有时候会在子activity里创建不了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  //不显示系统的标题栏
        //  getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //    WindowManager.LayoutParams.FLAG_FULLSCREEN );
        handler = new Handler();
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);
        mLoginError  = (Button) findViewById(R.id.login_error);
        mRegister    = (Button) findViewById(R.id.register);
        ONLYTEST     = (Button) findViewById(R.id.registfer);
        ONLYTEST.setOnClickListener(this);
        //        ONLYTEST.setOnLongClickListener((OnLongClickListener) this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        httpUtils = new XutilsHttpClient().getInstence(mContext);
        ViewUtils.inject(this); // 必须要有的 @action
        //  countryselect=(RelativeLayout) findViewById(R.id.countryselect_layout);
        //  countryselect.setOnClickListener(this);
        //  coutry_phone_sn=(TextView) findViewById(R.id.contry_sn);
        //  coutryName=(TextView) findViewById(R.id.country_name);

        //  coutryName.setText(coutry_name_array[selectIndex]);    //默认为1
        //  coutry_phone_sn.setText("+"+coutry_phone_sn_array[selectIndex]);
    }

    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if(s.toString().length()>0){
                    bt_username_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login_error: //无法登陆(忘记密码了吧)
                CustomToast.showMsg(mContext, "无法登陆(忘记密码了吧)!");
                break;
            case R.id.register:    //注册新的用户
                CustomToast.showMsg(mContext, "注册新的用户!");
                break;

            case R.id.registfer:
                if(SERVER_FLAG>10){
                    Toast.makeText(this, "[内部测试--谨慎操作]", Toast.LENGTH_SHORT).show();
                }
                SERVER_FLAG++;
                break;
            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;
            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
            case R.id.bt_pwd_eye:
                if(et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
                //  bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_s);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else{
                //  bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_n);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }
    @OnClick(R.id.login)
    public void loginClick(View v) {
        closeKeyBroad();
        validateUser();
    }

    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.registfer:
                if(SERVER_FLAG>9){
                }
                //   SERVER_FLAG++;
                break;
        }
        return true;
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(isReLogin){
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                MainActivity.this.startActivity(mHomeIntent);
            }else{
                MainActivity.this.finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.baidu.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }

    private void get() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, "http://www.baidu.com",
            new RequestCallBack<String>() {
                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    Log.i("222222", current + "/" + total);
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.i("222222", responseInfo.result);
                }

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    Log.i("2233333333333333",error.toString());
                }
            });
    }
    /**
     * 登陆
     */
    private void validateUser() {
        if(!CommonMethod.check_connect_net(mContext)){
            CustomToast.showMsg(mContext, "请检查您的网络连接!");
            return;
        }

        boolean flag = true;
        final String username = et_name.getText().toString();
        if (TextUtils.isEmpty(username)) {
            CustomToast.makeText(mContext, "请填写手机", 500).show();
            return;
        }

        String password = et_pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            CustomToast.showMsg(mContext, "请填写密码");
            return;
        }

        et_pass.setEnabled(false);
        et_name.setEnabled(false);
        et_name.setCursorVisible(true);
        et_pass.setCursorVisible(true);

        final RequestParams params = Urls.getCommentParam(mContext);
        params.addBodyParameter("phone", username);
        params.addBodyParameter("password", password);
        final HttpHandler<String> send = httpUtils.send(HttpRequest.HttpMethod.POST, Urls.getLoginUrl(), params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
//                startLoadingProgress();
                Log.i("11111111dive", "url is=" + getRequestUrl() + "," + params);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d("dive", "登陆返回的信息" + responseInfo.result);
//                dismissLoadingprogress();
                LoginJson loginJson = JsonDataUtils.jsonToObject(responseInfo.result, LoginJson.class);
                if (loginJson != null) {
                    if (loginJson.getCode() == 0) {
                        LoginJson.DataInfo dataInfo = loginJson.getData();
                        if (dataInfo != null) {

                            try {
                                if (!AppConfig.getUserAccount(mContext).equals(username)) {//上次登录和本次登录不是同一个帐号清空本地所有缓存
                                    AppConfig.clearCache(mContext);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            AppConfig.setUserAccount(mContext, dataInfo.getName());
                            AppConfig.setUserToken(mContext, dataInfo.getUser_token());
                            AppConfig.setUserId(mContext, dataInfo.getId().intValue());
                            AppConfig.getUserId(mContext);

                            login();
                            setFocusable();
                        } else {
                            CustomToast.makeText(mContext, "用户名或密码错误", 500).show();
                            setFocusable();
                        }
                    } else {
                        CustomToast.makeText(mContext, loginJson.getMessage(), 500).show();
                        setFocusable();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                CustomToast.showMsg(mContext, e.getExceptionCode() + "，请求出错！");
                setFocusable();
            }

        });
    }
    // end validateUser

    /**
     * 登陆 原因是android 4.0中不允许app在主进程中访问网络。需要使用到 AsyncTask 类，在后台访问网络
     */
    private void login() {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }

    private void closeKeyBroad() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setFocusable() {

        et_pass.setFocusable(true);
        et_pass.setFocusableInTouchMode(true);
        et_pass.requestFocus();

        et_name.setFocusable(true);
        et_name.setFocusableInTouchMode(true);
        et_name.requestFocus();

        et_pass.setEnabled(true);
        et_name.setEnabled(true);
    }
}

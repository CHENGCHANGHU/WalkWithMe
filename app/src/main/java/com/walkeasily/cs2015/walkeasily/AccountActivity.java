package com.walkeasily.cs2015.walkeasily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.walkeasily.cs2015.walkeasily.server.ServiceRulesException;
import com.walkeasily.cs2015.walkeasily.server.UserService;
import com.walkeasily.cs2015.walkeasily.server.UserServiceImpl;

import java.lang.ref.WeakReference;
import java.nio.channels.Channel;
import java.util.WeakHashMap;

/**
 * Created by Administrator on 2016/3/17.
 */
public class AccountActivity extends Activity {

    private ImageView headPic;
    private EditText userName;
    private EditText password;
    private Button btn_login;
    private TextView btn_cannotLogin;
    private TextView btn_newUser;

    private UserService userService;

    private static final int FLAG_LOGIN_SUCCESS = 1;

    private static final String MSG_LOGIN_ERROR = "登录错误";
    private static final String MSG_LOGIN_SUCCESS = "登录成功";
    public static final String MSG_LOGIN_FAILED = "登录名或密码出错";
    public static final String MSG_SERVER_ERROR = "请求服务器错误";
    public static final String MSG_RESPONSE_TIMEOUT = "服务器响应超时";
    public static final String MSG_REQUEST_TIMEOUT = "服务器请求超时";

    private CHandler handler = new CHandler(this);

    private static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //初始化界面控件
        initView();

        //登录按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginName = userName.getText().toString();
                final String loginPassword = password.getText().toString();

                if (dialog == null) {
                    dialog = new ProgressDialog(AccountActivity.this);
                }
                dialog.setTitle("请等待");
                dialog.setMessage("登录中...");
                dialog.setCancelable(false);
                dialog.show();

                //副线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            userService.userLogin(loginName, loginPassword);
                            handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
                        } catch (ServiceRulesException e) {
                            e.printStackTrace();
                            Message message = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("ErrorMsg", e.getMessage());
                            message.setData(data);
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message message = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("ErrorMsg", MSG_LOGIN_ERROR);
                            message.setData(data);
                            handler.sendMessage(message);
                        }
                    }
                }).start();

                btn_login.setBackgroundColor(Color.parseColor("#ff6a5cff"));
            }
        });

        //无法登录按钮
        btn_cannotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(AccountActivity.this)
//                        .setTitle("提示")
//                        .setMessage("您的问题已经发送")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                startActivity();
            }
        });

        //新用户按钮
        btn_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, SignUpActivity.class));
            }
        });

    }

    private void initView() {
        headPic = (ImageView) findViewById(R.id.iv_headPic);
        userName = (EditText) findViewById(R.id.et_userName);
        password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.tv_loginButton);
        btn_cannotLogin = (TextView) findViewById(R.id.tv_cannotLogin);
        btn_newUser = (TextView) findViewById(R.id.tv_newUser);

        userService = new UserServiceImpl();
    }

    private static class CHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        private CHandler(AccountActivity activity) {
            this.mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (dialog != null) {
                dialog.dismiss();
            }

            int flag = msg.what;
            switch (flag) {
                case 0:
                    String errormsg = (String) msg.getData().getSerializable("ErrorMsg");
                    ((AccountActivity) mActivity.get()).showTip(errormsg);
                    break;
                case FLAG_LOGIN_SUCCESS:
                    ((AccountActivity) mActivity.get()).showTip(MSG_LOGIN_SUCCESS);
                    break;
                default:
                    break;
            }
        }
    }


    private void showTip(String str) {
        Toast.makeText(AccountActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}

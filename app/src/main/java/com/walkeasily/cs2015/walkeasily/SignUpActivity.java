package com.walkeasily.cs2015.walkeasily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.walkeasily.cs2015.walkeasily.bean.User;
import com.walkeasily.cs2015.walkeasily.customView.SignUpView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SignUpActivity extends Activity {

    private ImageView mTabLine;
    private int mScreenWidth;

    private SignUpView mSignUpView;

    //person sign up
    private Button mBtnPerson;
    private EditText mPUserName;
    private EditText mPUserPass;
    private EditText mPUserPass_;
    private EditText mPUserId;
    private EditText mPUserAge;
    private EditText mPUserMajor;
    private EditText mPUserEmail;
    private EditText mPUserPhoneNum;

    //social sign up
    private Button mBtnSocial;
    private EditText mSUserName;
    private EditText mSUserPass;
    private EditText mSUserPass_;
    private EditText mSUserId;
    private EditText mSUserAge;
    private EditText mSUserMajor;
    private EditText mSUserEmail;
    private EditText mSUserPhoneNum;
    private EditText mSUserLinderName;
    private EditText mSUserLinderPhoneNum;

    private String strUserName;
    private String strUserPass;
    private String strUserPass_;
    private String strUserId;
    private String strUserAge;
    private String strUserMajor;
    private String strUserEmail;
    private String strUserPhoneNum;
    private String strUserLinderName;
    private String strUserLinderPhoneNum;

    private AlertDialog mDialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_sign_up);

        Bmob.initialize(this, "1d3081d12cfcfcacf3452e41c2ef2e82");

        init();

    }

    private void init() {
        mTabLine = (ImageView) findViewById(R.id.tabLine);
        mScreenWidth = Util.getScreenWidth(SignUpActivity.this);
        mTabLine.getLayoutParams().width = mScreenWidth / 2;

        mSignUpView = (SignUpView) findViewById(R.id.sign_up_view);

        mBtnPerson = (Button) findViewById(R.id.id_btn_personSignUp);
        mPUserName = (EditText) findViewById(R.id.id_user_name_p);
        mPUserPass = (EditText) findViewById(R.id.id_user_password_p);
        mPUserPass_ = (EditText) findViewById(R.id.id_user_password__p);
        mPUserId = (EditText) findViewById(R.id.id_user_id_p);
        mPUserAge = (EditText) findViewById(R.id.id_user_age_p);
        mPUserMajor = (EditText) findViewById(R.id.id_user_major_p);
        mPUserEmail = (EditText) findViewById(R.id.id_user_email_p);
        mPUserPhoneNum = (EditText) findViewById(R.id.id_user_phoneNum_p);

        mBtnSocial = (Button) findViewById(R.id.id_btn_socialSignUp);
        mSUserName = (EditText) findViewById(R.id.id_user_name_s);
        mSUserPass = (EditText) findViewById(R.id.id_user_password_s);
        mSUserPass_ = (EditText) findViewById(R.id.id_user_password__s);
        mSUserId = (EditText) findViewById(R.id.id_user_id_s);
        mSUserAge = (EditText) findViewById(R.id.id_user_age_s);
        mSUserMajor = (EditText) findViewById(R.id.id_user_major_s);
        mSUserEmail = (EditText) findViewById(R.id.id_user_email_s);
        mSUserPhoneNum = (EditText) findViewById(R.id.id_user_phoneNum_s);
        mSUserLinderName = (EditText) findViewById(R.id.id_user_linderName_s);
        mSUserLinderPhoneNum = (EditText) findViewById(R.id.id_user_linderPhoneNum_s);


        clickAction();

    }

    private void clickAction() {
        mSignUpView.setOnCustomScrollListener(new SignUpView.OnCustomScrollListener() {
            @Override
            public void customScrollListener(int scrollX) {
                Log.i("cch", "" + scrollX);
                mTabLine.setX(scrollX / 2);
            }
        });

        mBtnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = mPUserName.getText().toString();
                strUserPass = mPUserPass.getText().toString();
                strUserPass_ = mPUserPass_.getText().toString();
                strUserId = mPUserId.getText().toString();
                strUserAge = mPUserAge.getText().toString();
                strUserMajor = mPUserMajor.getText().toString();
                strUserEmail = mPUserEmail.getText().toString();
                strUserPhoneNum = mPUserPhoneNum.getText().toString();

                confirePassword(strUserPass, strUserPass_);
                Toast.makeText(SignUpActivity.this, strUserPass + ":" + strUserPass_, Toast.LENGTH_SHORT).show();
                checkNull(strUserName, strUserPass, strUserId, strUserMajor, strUserEmail, strUserPhoneNum);

                sendInfo(strUserName, strUserPass, strUserId, strUserAge, strUserMajor, strUserEmail, strUserPhoneNum, "", "");

            }
        });

        mBtnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = mSUserName.getText().toString();
                strUserPass = mSUserPass.getText().toString();
                strUserPass_ = mSUserPass_.getText().toString();
                strUserId = mSUserId.getText().toString();
                strUserAge = mSUserAge.getText().toString();
                strUserMajor = mSUserMajor.getText().toString();
                strUserEmail = mSUserEmail.getText().toString();
                strUserPhoneNum = mSUserPhoneNum.getText().toString();
                strUserLinderName = mSUserLinderName.getText().toString();
                strUserLinderPhoneNum = mSUserLinderPhoneNum.getText().toString();

                confirePassword(strUserPass, strUserPass_);
                checkNull(strUserName, strUserPass, strUserId, strUserMajor, strUserEmail, strUserPhoneNum);
                sendInfo(strUserName, strUserPass, strUserId, strUserAge, strUserMajor, strUserEmail, strUserPhoneNum, strUserLinderName, strUserLinderPhoneNum);

            }
        });
    }

    private void sendInfo(String strUserName, String strUserPass, String strUserId, String strUserAge, String strUserMajor, String strUserEmail, String strUserPhoneNum, String s, String s1) {
        User newUser = new User();
        newUser.setName(strUserName);
        newUser.setPassword(strUserPass);
        newUser.setStudentId(strUserId);
        newUser.setAge(strUserAge);
        newUser.setMajorAndClass(strUserMajor);
        newUser.setMail(strUserEmail);
        newUser.setPhoneNum(strUserPhoneNum);
        newUser.setLinderName(s);
        newUser.setLinderPhoneNum(s1);

        newUser.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("提示")
                        .setMessage("注册成功")
                        .show();
            }

            @Override
            public void onFailure(int i, String s) {
                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle("提示")
                        .setMessage("注册失败")
                        .show();
            }
        });
    }

    private void checkNull(String strUserName, String strUserPass, String strUserId, String strUserMajor, String strUserEmail, String strUserPhoneNum) {
        if (strUserName.equals("") || strUserPass.equals("") || strUserId.equals("") || strUserMajor.equals("") || strUserEmail.equals("") || strUserPhoneNum.equals("")) {
            mDialog = new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("错误提示")
                    .setMessage("有必要内容为空，请输入！")
                    .show();
        }
    }


    private void confirePassword(String s1, String s2) {
        if (s1.equals(s2)) {
            mDialog = new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle("错误提示")
                    .setMessage("密码不一样，请重新输入！")
                    .show();
        }
    }


}

package com.walkeasily.cs2015.walkeasily;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.walkeasily.cs2015.walkeasily.customView.SignUpView;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SignUpActivity extends Activity {

    private ImageView mTabLine;
    private int mScreenWidth;

    private SignUpView mSignUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_sign_up);

        init();

    }

    private void init() {
        mTabLine = (ImageView) findViewById(R.id.tabLine);
        mScreenWidth = Util.getScreenWidth(SignUpActivity.this);
        mTabLine.getLayoutParams().width = mScreenWidth / 2;

        mSignUpView = (SignUpView) findViewById(R.id.sign_up_view);
        mSignUpView.setOnCustomScrollListener(new SignUpView.OnCustomScrollListener() {
            @Override
            public void customScrollListener(int scrollX) {
                Log.i("cch", "" + scrollX);
//                mTabLine.setLeft(scrollX);
                mTabLine.setX(scrollX / 2);
            }
        });
    }


}

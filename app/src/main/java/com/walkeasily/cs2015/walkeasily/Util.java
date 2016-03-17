package com.walkeasily.cs2015.walkeasily;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.baidu.mapapi.map.InfoWindow;

/**
 * Created by admin on 2016/1/5.
 */
public class Util {
    /**
     * 判断网络是否连通
     * @param context
     * @return
     */
    public static void isNetworkConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info==null) {
                Dialog alertDialog = new AlertDialog.Builder(context).
                        setTitle("无网络连接").
                        setMessage("请检查网络是否连接").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alertDialog.show();
        }

    }


}

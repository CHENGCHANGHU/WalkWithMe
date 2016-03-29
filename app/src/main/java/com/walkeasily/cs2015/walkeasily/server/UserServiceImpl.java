package com.walkeasily.cs2015.walkeasily.server;

import com.walkeasily.cs2015.walkeasily.AccountActivity;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/19.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {
        /**
         * 查看IP地址
         * cmd->ipconfig -all
         */

        URL url = null;
        InputStream in = null;
        OutputStream out = null;
        byte[] data = null;

        HttpURLConnection urlConnection = null;

        try {
//            Map<String, String> params = new HashMap<>();
//            params.put("loginName", loginName);
//            params.put("loginPassword", loginPassword);
//            data = setPostPassParams(params).toString().getBytes();
//
//            url = new URL("http://0000000000");
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setUseCaches(false);
//            urlConnection.connect();
//            in = urlConnection.getInputStream();
//            out = urlConnection.getOutputStream();
//
//            out.write(data);
//            out.flush();
//
//            int responseCode = urlConnection.getResponseCode();
//            if (responseCode != HttpURLConnection.HTTP_OK) {
//                throw new ServiceRulesException("请求服务器出错");
//            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private static StringBuffer setPostPassParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();

        /**
         * k1=v1&k2=v2&......
         */
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                    .append("&");
        }

        //把最后一个&去掉
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer;
    }
}

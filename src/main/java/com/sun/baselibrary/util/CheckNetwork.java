package com.sun.baselibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Sun
 * @created: 2019/6/4 22:12
 * @description:
 */
public class CheckNetwork {
    /**
     * 判断网络是否连通
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            if(context!=null){
                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnected();
            }else{
                /**如果context为空，就返回false，表示网络未连接*/
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}

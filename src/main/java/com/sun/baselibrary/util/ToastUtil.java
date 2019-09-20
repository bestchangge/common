package com.sun.baselibrary.util;

import android.widget.Toast;

import com.sun.baselibrary.IApp;

/**
 * @author Sun
 * @created: 2019/6/13 16:16
 * @description:
 */
public class ToastUtil {
    public static void showToast(String s){
        if(!StringUtil.isNullOrEmpty(s)){
            Toast.makeText(IApp.getInstance(),s,Toast.LENGTH_SHORT).show();
        }
    }
}

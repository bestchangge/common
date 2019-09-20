package com.sun.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Sun
 * @created: 2019/7/31 21:09
 * @description: 关闭软键盘
 */
public class SoftInputUtil {
    public static void closeSoftInput(Activity context){
        if (context.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
            }
        }

    }
}

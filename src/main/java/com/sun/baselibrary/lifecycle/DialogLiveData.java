package com.sun.baselibrary.lifecycle;

import android.arch.lifecycle.MutableLiveData;

import com.sun.baselibrary.bean.DialogBean;

/**
 * @author Sun
 * @created: 2019/9/24 10:23
 * @description: DialogLiveData
 */
public class DialogLiveData<T> extends MutableLiveData<T> {

    private DialogBean dialogBean=new DialogBean();

    public void setValue(boolean isShow) {
        dialogBean.setShow(isShow);
        dialogBean.setMsg("");
        setValue((T) dialogBean);
    }
    public void  setValue(boolean isShow, String msg) {
        dialogBean.setShow(isShow);
        dialogBean.setMsg(msg);
        setValue((T) dialogBean);
    }
}

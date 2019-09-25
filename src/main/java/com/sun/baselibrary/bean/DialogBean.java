package com.sun.baselibrary.bean;

/**
 * @author Sun
 * @created: 2019/9/24 10:26
 * @description: 封装的加载框实体类
 */
public class DialogBean {


    private boolean isShow;
    private String msg;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

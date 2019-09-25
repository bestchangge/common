package com.sun.baselibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.sun.baselibrary.R;
import com.sun.baselibrary.databinding.DialogLoadingBinding;
import com.sun.baselibrary.util.DensityUtil;
import com.sun.baselibrary.util.StringUtil;

/**
 * @author Sun
 * @created: 2019/9/24 10:56
 * @description: 等待对话框
 */
public class LoadingDialog extends Dialog{

    private DialogLoadingBinding binding;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);
        setCanceledOnTouchOutside(false);
        binding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_loading,null,false);
        setContentView(binding.getRoot());
        Window window=getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width= DensityUtil.dp2px(context,150);
        lp.height= DensityUtil.dp2px(context,110);
        lp.gravity= Gravity.CENTER;
        window.setAttributes(lp);
    }

    /**
     * 设置等待提示信息
     */
    public void setLoadingMsg(String msg) {
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }
        binding.tvMsg.setText(msg);
    }
}

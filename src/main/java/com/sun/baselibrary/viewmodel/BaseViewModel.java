package com.sun.baselibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.sun.baselibrary.bean.DialogBean;

/**
 * @author Sun
 * @created: 2019/9/24 10:36
 * @description:
 */
public abstract class BaseViewModel extends AndroidViewModel {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * 用来通知 Activiy/Fragment是否显示等待Dialog
     */
    protected DialogLiveData<DialogBean> showProgress=new DialogLiveData<>();

    public void showProgress(LifecycleOwner owner, Observer<DialogBean> observer){
        showProgress.observe(owner,observer);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        showProgress=null;
    }
}

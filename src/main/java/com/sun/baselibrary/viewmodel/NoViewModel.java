package com.sun.baselibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.sun.baselibrary.lifecycle.BaseViewModel;

/**
 * @author Sun
 * @created: 2019/5/13 17:51
 * @description: 空NoViewModel
 */
public class NoViewModel extends BaseViewModel {
    public NoViewModel(@NonNull Application application) {
        super(application);
    }

}

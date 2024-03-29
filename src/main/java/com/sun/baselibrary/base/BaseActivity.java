package com.sun.baselibrary.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sun.baselibrary.viewmodel.BaseViewModel;
import com.sun.baselibrary.util.ClassUtil;
import com.sun.baselibrary.util.SoftInputUtil;
import com.sun.baselibrary.view.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Sun
 * @created: 2019/5/9
 * @description: 基类
 */
public abstract class BaseActivity<VM extends BaseViewModel, D extends ViewDataBinding> extends AppCompatActivity {

    protected D binding;
    // ViewModel
    protected VM mViewModel;

    private LoadingDialog loadingDialog;

    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        getIntentData(getIntent());
        initViewModel();
        initObserve();
        showNavBack();
        initData();
    }

    /**
     * 显示返回键
     */
    protected void showNavBack() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 隐藏返回键
     */
    protected void hiddenNavBack() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * 隐藏标题栏
     */
    protected void hiddenActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * 页面跳转
     */
    protected void readyGo(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void getIntentData(Intent intent) {
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.mViewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    /**
     * 监听当前ViewModel中 showProgress
     */
    private void initObserve() {
        if (mViewModel == null) return;
        mViewModel.showProgress(this, bean -> {
            if (bean.isShow()) {
                showProgress(bean.getMsg());
            } else {
                dismissProgress();
            }
        });
    }

    /**
     * 显示用户等待框
     * @param msg 提示信息
     */
    protected void showProgress(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMsg(msg);
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setLoadingMsg(msg);
            loadingDialog.show();
        }
    }

    /**
     * 隐藏等待框
     */
    protected void dismissProgress() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeDisposable();//及时取消订阅避免内存泄漏
    }

    public void removeDisposable() {
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            // clear 和 dispose的区别是：  disposed = true;
            this.mCompositeDisposable.clear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SoftInputUtil.closeSoftInput(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 禁止改变字体大小
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}

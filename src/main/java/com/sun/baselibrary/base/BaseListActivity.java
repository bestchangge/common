package com.sun.baselibrary.base;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sun.baselibrary.R;
import com.sun.baselibrary.http.HttpConfig;
import com.sun.baselibrary.util.ClassUtil;

import java.util.List;

import me.solidev.statusviewlayout.StatusViewLayout;

/**
 * @author Sun
 * @created: 2019/7/30 12:36
 * @description: 带下拉刷新/上拉加载通用列表的页面
 */
public abstract class BaseListActivity<VM extends AndroidViewModel, T> extends BaseActivity {

    protected VM mViewModel;
    private StatusViewLayout mStatusViewLayout;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private int mPageSize;
    private int mCurrentPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    protected void initData() {
        initCustomData();
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.mViewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
        mPageSize = getPageSize();
        mCurrentPage = getInitPage();
        mStatusViewLayout = binding.getRoot().findViewById(R.id.statusViewLayout);
        mSwipeRefreshLayout = binding.getRoot().findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = binding.getRoot().findViewById(R.id.recyclerView);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        boolean mIsRefreshAndLoadMoreEnabled = isRefreshAndLoadMoreEnabled();
        mSwipeRefreshLayout.setEnabled(mIsRefreshAndLoadMoreEnabled);
        mAdapter = createAdapter();
        if (mAdapter == null) {
            throw new IllegalAccessError("请设置适配器");
        }
        mRecyclerView.setAdapter(mAdapter);
        mStatusViewLayout.setOnRetryListener(v -> {
            mStatusViewLayout.showLoading();
            loadData(getInitPage());
        });
        mSwipeRefreshLayout.setOnRefreshListener(mIsRefreshAndLoadMoreEnabled ? (SwipeRefreshLayout.OnRefreshListener) () -> {
            mCurrentPage = getInitPage();
            //防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
            loadData(mCurrentPage);
        } : null);
        mAdapter.setOnLoadMoreListener(mIsRefreshAndLoadMoreEnabled ? (BaseQuickAdapter.RequestLoadMoreListener) () -> {
            loadData(mCurrentPage);
        } : null, mRecyclerView);

        mStatusViewLayout.showLoading();
        mCurrentPage = getInitPage();
        mAdapter.setEnableLoadMore(false);
        loadData(mCurrentPage);
    }

    /**
     * 初始化用户其它数据
     */
    public void initCustomData() {

    }


    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }


    /**
     * 加载数据
     *
     * @param pageNo 页码
     */
    protected abstract void loadData(int pageNo);

    /**
     * 提供适配器
     *
     * @return 多条目类型适配器
     */
    protected abstract BaseQuickAdapter<T, BaseViewHolder> createAdapter();

    /**
     * 加载列表数据成功
     *
     * @param isRefresh 是否是刷新
     * @param data      列表数据
     */
    protected void onLoadDataSuccess(boolean isRefresh, List<T> data) {
        mStatusViewLayout.showContent();
        int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size == 0) {
                mStatusViewLayout.showEmpty("暂无数据");
            } else {
                setData(true, data);
            }
            mAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            setData(false, data);
        }
    }

    public boolean isRefresh() {
        return getCurrentPage() == getInitPage();
    }

    /**
     * 加载错误
     *
     * @param isRefresh
     * @param err
     */
    protected void onLoadDataErr(boolean isRefresh, String err) {
        if (isRefresh) {
            mStatusViewLayout.showError(err);
            mAdapter.setEnableLoadMore(true);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mAdapter.loadMoreFail();
        }
    }

    private void setData(boolean isRefresh, List<T> data) {
        mCurrentPage++;
        int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < mPageSize) {
            //第一页如果不够一夜就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    protected int getPageSize() {
        return HttpConfig.PAGE_SIZE;
    }

    protected int getInitPage() {
        return 1;
    }

    /**
     * 是否启用下拉刷新和上拉加载
     */
    protected abstract boolean isRefreshAndLoadMoreEnabled();
}

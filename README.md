#common

1.BaseListActivity带下拉刷新/上拉加载通用列表的页面

    public class TestActivity extends BaseListActivity<TestViewModel,TestJavaBean> {
    
        @Override
        protected void loadData(int pageNo) {
            getDataList(pageNo);
        }
    
        @Override
        public void initCustomData() {
            setTitle("Test");
        }
    
        private void getDataList(int pageNo) {
            mViewModel.getDataList(pageNo).observe(this,data -> {
                if(data!=null){
                    onLoadDataSuccess(getCurrentPage()==getInitPage(),data.getDataList());
                }else{
                    onLoadDataErr(getCurrentPage()==getInitPage(),"加载失败");
                }
            });
        }
    
        @Override
        protected BaseQuickAdapter<TestJavaBean.Data, BaseViewHolder> createAdapter() {
            return new TestAdapter();
        }
    
        @Override
        protected boolean isRefreshAndLoadMoreEnabled() {
            return true;
        }
    }




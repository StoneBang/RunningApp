package com.itheima72.googleplay.ui.fragment;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.ToastUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

/**
 * 带有下拉刷新listView界面的基类
 * @author Administrator
 *
 */
public abstract class BaseRefreshListFragment extends BaseFragment{
	protected PullToRefreshListView refreshListView;
	protected ListView listView;
	
	/**
	 * 初始化RefreshListView
	 */
	protected void initRefreshListView(){
		refreshListView = (PullToRefreshListView) View.inflate(getActivity(), R.layout.ptr_listview, null);
		refreshListView.setMode(Mode.BOTH);//设置既可以上拉也可以下拉
		
		//1.得到普通的listview对象，给普通listview对象设置adapter
		listView = refreshListView.getRefreshableView();
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));//隐藏listview的selector
//		listView.setSelector(android.R.color.transparent);//用这个也行
		
		//2.给refreshListView设置刷新监听器
		refreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			/**
			 * 上拉和下拉都会执行该方法
			 * @param refreshView
			 */
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if(refreshListView.getCurrentMode()==Mode.PULL_FROM_START){
					CommonUtil.runOnUIThread(new Runnable() {
						@Override
						public void run() {
							//如果当前是下拉刷新，
							ToastUtil.showToast("下拉刷新,哒哒哒...");
							//结束刷新
							refreshListView.onRefreshComplete();
						}
					},1500);
				}else {
					//如果当前是上拉加载更多，
					contentPage.loadDataAndRefreshPage();
				}
			}
		});
	}
}

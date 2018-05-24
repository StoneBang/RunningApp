package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.adapter.AppAdapter;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;
import com.itheima72.googleplay.util.ToastUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class GameFragment extends BaseRefreshListFragment{
	private AppAdapter appAdapter;
	private ArrayList<AppInfo> list = new ArrayList<AppInfo>();
	@Override
	protected View getSuccessView() {
		initRefreshListView();
		
		//设置adapter
		appAdapter = new AppAdapter(list);
		listView.setAdapter(appAdapter);
		return refreshListView;
	}

	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Game+list.size());
		final ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) JsonUtil.parseJsonToList(result, new TypeToken<List<AppInfo>>(){}.getType());
		if(appInfos!=null){
			CommonUtil.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					list.addAll(appInfos);
					appAdapter.notifyDataSetChanged();
					//结束刷新
					refreshListView.onRefreshComplete();
				}
			});
			
		}
		
		return appInfos;
	}
}

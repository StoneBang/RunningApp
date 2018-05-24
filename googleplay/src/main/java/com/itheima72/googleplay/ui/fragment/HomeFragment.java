package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.bean.Home;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.activity.AppDetailActivity;
import com.itheima72.googleplay.ui.adapter.HomeAdapter;
import com.itheima72.googleplay.ui.adapter.HomeHeaderAdapter;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;
import com.itheima72.googleplay.util.LogUtil;
import com.itheima72.googleplay.util.ToastUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends BaseRefreshListFragment{
	private HomeAdapter homeAdapter;
	private ArrayList<AppInfo> list = new ArrayList<AppInfo>();
	private ViewPager viewPager;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			handler.sendEmptyMessageDelayed(0, 2500);
		};
	};
	@Override
	protected View getSuccessView() {
		initRefreshListView();
		
		initHomeHeader();
		//设置adapter
		homeAdapter = new HomeAdapter(list);
		listView.setAdapter(homeAdapter);
		
		//设置item点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),AppDetailActivity.class);
//				LogUtil.e(this, "position： "+position);
				intent.putExtra("packageName",list.get(position-2).getPackageName());
				startActivity(intent);
			}
		});
		
		return refreshListView;
	}
	/**
	 * 初始化Home的headerView
	 */
	private void initHomeHeader(){
		View headerView = View.inflate(getActivity(), R.layout.layout_home_header, null);
		viewPager = (ViewPager) headerView.findViewById(R.id.viewPager);
		//对ViewPager的高度进行动态设置，根据它的宽高比;
		//1.首先，获取viewpager的宽度，就是屏幕的宽度
		int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		//2.根据图片的宽高比，计算对应的高度,宽高比是2.65f
		float height = width/2.65f;
		//3.重新给viewPager设置高度,
		LayoutParams params = viewPager.getLayoutParams();
		params.height = (int) height;
		viewPager.setLayoutParams(params);
		
		listView.addHeaderView(headerView);//该方法必须在setAdapter之前调用
	}
	
	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Home+list.size());
		final Home home = JsonUtil.parseJsonToBean(result, Home.class);
		
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				if(home!=null){
					if(home.getPicture()!=null && home.getPicture().size()>0){
						//说明是第一页请求的数据，服务器返回的大图的url数据
						viewPager.setAdapter(new HomeHeaderAdapter(home.getPicture()));
						
						//一开始让viewPager默认选中一个比较大的值
						viewPager.setCurrentItem(home.getPicture().size()*100000);
					}
					
//					list = home.getList();//注意：不能这样写
					//更新数据
					list.addAll(home.getList());
					//更新adapter
					homeAdapter.notifyDataSetChanged();
					//结束刷新
					refreshListView.onRefreshComplete();
				}
			}
		});
		
		return home;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//延时发送消息
		handler.sendEmptyMessageDelayed(0, 2500);
	}
	@Override
	public void onStop() {
		super.onStart();
		//移除消息
		handler.removeMessages(0);
	}
	
	/**
	 * 使用JSONObject方式解析
	 * @return
	 */
	public Home parseJson(String json){
		if(TextUtils.isEmpty(json))return null;
		
		Home home = new Home();
		try {
			JSONObject jsonObject = new JSONObject(json);
			//1.解析picture字段
			JSONArray pictureArray = jsonObject.getJSONArray("picture");
			ArrayList<String> picture = new ArrayList<String>();
			for (int i = 0; i < pictureArray.length(); i++) {
				picture.add(pictureArray.getString(i));
			}
			home.setPicture(picture);
			
			//2.解析list字段
			JSONArray listArray = jsonObject.getJSONArray("list");
			ArrayList<AppInfo> list = new ArrayList<AppInfo>();
			for (int i = 0; i < listArray.length(); i++) {
				JSONObject appInfoObj = listArray.getJSONObject(i);
				AppInfo appInfo = new AppInfo();
				appInfo.setDes(appInfoObj.getString("des"));
				appInfo.setDownloadUrl(appInfoObj.getString("downloadUrl"));
				appInfo.setIconUrl(appInfoObj.getString("iconUrl"));
				appInfo.setId(appInfoObj.getInt("id"));
				appInfo.setName(appInfoObj.getString("name"));
				appInfo.setPackageName(appInfoObj.getString("packageName"));
				appInfo.setSize(appInfoObj.getLong("size"));
				appInfo.setStars((float) appInfoObj.getDouble("stars"));
				
				list.add(appInfo);
			}
			home.setList(list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return home;
	}
}

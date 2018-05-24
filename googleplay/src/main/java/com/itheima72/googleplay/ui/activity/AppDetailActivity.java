package com.itheima72.googleplay.ui.activity;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.manager.DownloadInfo;
import com.itheima72.googleplay.manager.DownloadManager;
import com.itheima72.googleplay.manager.DownloadManager.DownloadObserver;
import com.itheima72.googleplay.ui.fragment.ContentPage;
import com.itheima72.googleplay.ui.model.AppDesModel;
import com.itheima72.googleplay.ui.model.AppInfoModel;
import com.itheima72.googleplay.ui.model.AppSafeModel;
import com.itheima72.googleplay.ui.model.AppScreenModel;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class AppDetailActivity extends AppCompatActivity implements OnClickListener,DownloadObserver{
	private String packageName;
	private ContentPage contentPage;
	private AppInfo appInfo;
	private LinearLayout model_container;

	private AppInfoModel appInfoModel;
	private AppSafeModel appSafeModel;
	private AppScreenModel appScreenModel;
	private AppDesModel appDesModel;
	private ScrollView scrollview;
	private ProgressBar pb_progress;
	private Button btn_download;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		packageName = getIntent().getStringExtra("packageName");
		setActionBar();
		
		//创建ContentPage，并且将它作为Activity的contentView
		contentPage = new ContentPage(this) {
			@Override
			protected Object loadData() {
				return requestData();
			}
			@Override
			protected View createSuccessView() {
				View view = View.inflate(AppDetailActivity.this, R.layout.activity_app_detail, null);
				scrollview = (ScrollView) view.findViewById(R.id.scrollview);
				model_container = (LinearLayout) view.findViewById(R.id.model_container);
				pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
				btn_download = (Button) view.findViewById(R.id.btn_download);
				//1.初始化app info模块
				appInfoModel = new AppInfoModel();
				model_container.addView(appInfoModel.getModelView());
				//2.初始化app safe模块
				appSafeModel = new AppSafeModel();
				model_container.addView(appSafeModel.getModelView());
				//3.初始化app screen模块
				appScreenModel = new AppScreenModel(AppDetailActivity.this);
				model_container.addView(appScreenModel.getModelView());
				//3.初始化app des模块
				appDesModel = new AppDesModel(scrollview);
				model_container.addView(appDesModel.getModelView());
				
				btn_download.setOnClickListener(AppDetailActivity.this);
				return view;
			}
		};
		setContentView(contentPage);
		
		//注册下载监听器
		DownloadManager.getInstance().registerDownloadObserver(this);
	}
	/**
	 * 请求数据的方法
	 * @return
	 */
	public Object requestData(){
		String url = String.format(Url.Detail,packageName);
		String result = HttpHelper.get(url);
		appInfo = JsonUtil.parseJsonToBean(result, AppInfo.class);
		if(appInfo!=null){
			CommonUtil.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					//更新UI的操作
					refreshUI();
				}
			});
		}
		return appInfo;
	}
	/**
	 * 更新UI的操作
	 */
	private void refreshUI(){
		//1.显示app info模块的数据
		appInfoModel.setData(appInfo);
		//2.显示app safe模块的数据
		appSafeModel.setData(appInfo);
		//3.显示app screen模块的数据
		appScreenModel.setData(appInfo);
		//4.显示app des模块的数据
		appDesModel.setData(appInfo);
		
		//5.刷新下载状态
		DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(appInfo);
		if(downloadInfo!=null){
			refreshDownloadState(downloadInfo);
		}
	}
	
	/**
	 * 设置ActionBar
	 */
	private void setActionBar(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(getString(R.string.app_detail));
		
		//1.显示Home按钮，并设置可以被点击
		actionBar.setDisplayHomeAsUpEnabled(true);//显示Home按钮
		actionBar.setDisplayShowHomeEnabled(true);//设置可以被点击
	}
	/**
	 * 点击ActionBar的home按钮会执行该方法
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_download:
			DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(appInfo);
			if(downloadInfo==null){
				//说明从来木有下载过,需要进行下载
				DownloadManager.getInstance().download(appInfo);
			}else {
				if(downloadInfo.getState()==DownloadManager.STATE_DOWNLOADING
					|| downloadInfo.getState()==DownloadManager.STATE_WAITING){
					//需要暂停操作
					DownloadManager.getInstance().pause(appInfo);
				}else if (downloadInfo.getState()==DownloadManager.STATE_PAUSE
					|| downloadInfo.getState()==DownloadManager.STATE_ERROR) {
					//需要下载操作
					DownloadManager.getInstance().download(appInfo);
				}else if (downloadInfo.getState()==DownloadManager.STATE_FINISH) {
					//需要安装操作
					DownloadManager.getInstance().installApk(appInfo);
				}
			}
			break;
		}
	}
	@Override
	public void onDownloadStateChange(DownloadInfo downloadInfo) {
		refreshDownloadState(downloadInfo);
	}
	/**
	 * 根据state设置不同的文本
	 * @param downloadInfo
	 */
	private void refreshDownloadState(DownloadInfo downloadInfo) {
		if(appInfo==null || appInfo.getId()!=downloadInfo.getId()){
			//如果当前的app和正在下载的不是同一个，那么要return
			return;
		}
		switch (downloadInfo.getState()) {
		case DownloadManager.STATE_FINISH:
			btn_download.setText("安装");
			break;
		case DownloadManager.STATE_ERROR:
			btn_download.setText("失败，重下");
			break;
		case DownloadManager.STATE_PAUSE:
			btn_download.setText("继续下载");
			btn_download.setBackgroundResource(0);//移除背景
			//设置进度
			float fraction = downloadInfo.getCurrentLength()*100f/downloadInfo.getSize();//12.31232121
			pb_progress.setProgress((int) fraction);
			break;
		case DownloadManager.STATE_WAITING:
			btn_download.setText("等待中");
			break;
		}
	}
	//获取下载进度，更新ProgressBar
	@Override
	public void onDownloadProgressChange(DownloadInfo downloadInfo) {
		if(appInfo==null || appInfo.getId()!=downloadInfo.getId()){
			//如果当前的app和正在下载的不是同一个，那么要return
			return;
		}
		float fraction = downloadInfo.getCurrentLength()*100f/downloadInfo.getSize();//12.31232121
		pb_progress.setProgress((int) fraction);
		//移除btn_download的背景
		btn_download.setBackgroundResource(0);
		btn_download.setText((int)fraction+"%");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		DownloadManager.getInstance().unregisterDownloadObserver(this);
	}
}

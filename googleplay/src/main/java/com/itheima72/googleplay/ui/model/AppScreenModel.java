package com.itheima72.googleplay.ui.model;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.activity.AppDetailActivity;
import com.itheima72.googleplay.ui.activity.ImageScaleActivity;
import com.itheima72.googleplay.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AppScreenModel extends BaseModel<AppInfo>{
	private View view;
	private LinearLayout ll_screen;
	private int appScreenWidth,appScreenHeight;
	private int app_screen_margin;
	private AppDetailActivity activity;
	public AppScreenModel(AppDetailActivity activity){
		this.activity = activity;
		
		view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_screen, null);
		ll_screen = (LinearLayout) view.findViewById(R.id.ll_screen);
		
		appScreenWidth = (int) CommonUtil.getDimens(R.dimen.app_screen_width);
		appScreenHeight = (int) CommonUtil.getDimens(R.dimen.app_screen_height);
		app_screen_margin = (int) CommonUtil.getDimens(R.dimen.app_screen_margin);
	}
	@Override
	public View getModelView() {
		return view;
	}

	@Override
	public void setData(AppInfo appInfo) {
		final ArrayList<String> screen = appInfo.getScreen();
		for (int i = 0; i < screen.size(); i++) {
			ImageView imageView = new ImageView(GooglePlayApplication.getContext());
			LayoutParams params = new LayoutParams(appScreenWidth,appScreenHeight);
			if(i>0)params.leftMargin = app_screen_margin;
			imageView.setLayoutParams(params);
			
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+screen.get(i),imageView,ImageLoaderOptions.pager_options);
			
			ll_screen.addView(imageView);
			
			final int temp = i;//定义临时变量接收i的值
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(GooglePlayApplication.getContext(),ImageScaleActivity.class);
//					//告诉系统要创新出给我重新创建任务栈来放俺的目标Activity
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					GooglePlayApplication.getContext().startActivity(intent);
					
					//但是其实我们并不需要这样重新开启任务栈,优秀的做法是这样：
					Intent intent = new Intent(activity,ImageScaleActivity.class);
					intent.putStringArrayListExtra("urlList", screen);
					intent.putExtra("currentItem", temp);
					activity.startActivity(intent);
				}
			});
		}
		
	}

}

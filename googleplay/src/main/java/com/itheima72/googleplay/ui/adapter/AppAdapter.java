package com.itheima72.googleplay.ui.adapter;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AppAdapter extends BasicAdapter<AppInfo>{

	public AppAdapter(ArrayList<AppInfo> list) {
		super(list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_home, null);
		}
		HomeHolder holder = HomeHolder.getHolder(convertView);
		
		//获取AppInfo，设置数据
		AppInfo appInfo = list.get(position);
		holder.tv_name.setText(appInfo.getName());
		holder.rb_star.setRating(appInfo.getStars());//设置星级
		holder.tv_size.setText(Formatter.formatFileSize(GooglePlayApplication.getContext(),appInfo.getSize()));
		holder.tv_des.setText(appInfo.getDes());
		
		//加载图片
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+appInfo.getIconUrl(),holder.iv_icon, ImageLoaderOptions.options
				,new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				FadeInBitmapDisplayer.animate(view, 800);//渐渐显示的效果
			}
		});
		
		return convertView;
	}

	static class HomeHolder{
		ImageView iv_icon;
		TextView tv_name,tv_size,tv_des;
		RatingBar rb_star;
		
		public HomeHolder(View convertView){
			iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			rb_star = (RatingBar) convertView.findViewById(R.id.rb_star);
			tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			tv_des = (TextView) convertView.findViewById(R.id.tv_des);
		}
		
		public static HomeHolder getHolder(View convertView){
			HomeHolder holder = (HomeHolder) convertView.getTag();
			if(holder==null){
				holder = new HomeHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}

}

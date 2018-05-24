package com.itheima72.googleplay.ui.adapter;

import java.util.ArrayList;

import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HomeHeaderAdapter extends BasePagerAdapter<String>{

	public HomeHeaderAdapter(ArrayList<String> list) {
		super(list);
	}
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(GooglePlayApplication.getContext());
		imageView.setScaleType(ScaleType.FIT_XY);
		
		//加载图片
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+list.get(position%list.size()),imageView,ImageLoaderOptions.pager_options);
		
		container.addView(imageView);
		return imageView;
	}

}

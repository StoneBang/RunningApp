package com.itheima72.googleplay.ui.adapter;

import java.util.ArrayList;

import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.lib.photoview.PhotoViewAttacher;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageScaleAdapter extends BasePagerAdapter<String>{

	public ImageScaleAdapter(ArrayList<String> list) {
		super(list);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(GooglePlayApplication.getContext());
		
		final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
		
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+list.get(position), imageView, ImageLoaderOptions.pager_options
				,new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				//更新ImageVIew的宽高
				attacher.update();
			}
		});
		
		container.addView(imageView);
		return imageView;
	}
}

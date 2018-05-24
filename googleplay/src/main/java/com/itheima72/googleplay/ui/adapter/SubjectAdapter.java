package com.itheima72.googleplay.ui.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.Subject;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.view.RatioImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class SubjectAdapter extends BasicAdapter<Subject>{

	public SubjectAdapter(ArrayList<Subject> list) {
		super(list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_subject, null);
		}
		SubjectHolder holder = SubjectHolder.getHolder(convertView);
		
		Subject subject = list.get(position);
		holder.setData(subject);
		
		return convertView;
	}

	static class SubjectHolder{
		RatioImageView iv_image;
		TextView tv_des;
		
		public SubjectHolder(View convertView){
			iv_image = (RatioImageView) convertView.findViewById(R.id.iv_image);
			tv_des = (TextView) convertView.findViewById(R.id.tv_des);
		}
		public static SubjectHolder getHolder(View convertView){
			SubjectHolder holder = (SubjectHolder) convertView.getTag();
			if(holder==null){
				holder = new SubjectHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
		/**
		 * 设置数据
		 * @param subject
		 */
		public void setData(Subject subject){
			tv_des.setText(subject.getDes());
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+subject.getUrl(), iv_image, ImageLoaderOptions.pager_options);
		}
	}
}

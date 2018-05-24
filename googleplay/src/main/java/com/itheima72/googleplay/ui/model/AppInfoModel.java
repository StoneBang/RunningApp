package com.itheima72.googleplay.ui.model;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 负责初始化详情界面app info模块的view和显示数据的逻辑
 * 
 * @author Administrator
 * 
 */
public class AppInfoModel extends BaseModel<AppInfo>{
	// app info的控件
	private ImageView iv_icon;
	private TextView tv_name, tv_download_num, tv_version, tv_date, tv_size;
	private RatingBar rb_star;
	private View view;
	public AppInfoModel() {
		view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_info, null);
		iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
		tv_version = (TextView) view.findViewById(R.id.tv_version);
		tv_date = (TextView) view.findViewById(R.id.tv_date);
		tv_size = (TextView) view.findViewById(R.id.tv_size);
		rb_star = (RatingBar) view.findViewById(R.id.rb_star);
	}

	/**
	 * 获取当前app info模块的View对象
	 * 
	 * @return
	 */
	public View getModelView() {
		return view;
	}

	/**
	 * 显示数据的逻辑的方法
	 * 
	 * @param appInfo
	 */
	public void setData(AppInfo appInfo) {
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+appInfo.getIconUrl(),iv_icon,ImageLoaderOptions.options);
		tv_name.setText(appInfo.getName());
		rb_star.setRating(appInfo.getStars());
		tv_download_num.setText("下载："+appInfo.getDownloadNum());
		tv_version.setText("版本："+appInfo.getVersion());
		tv_date.setText("日期："+appInfo.getDate());
		tv_size.setText("大小："+Formatter.formatFileSize(GooglePlayApplication.getContext(), appInfo.getSize()));
	}
}

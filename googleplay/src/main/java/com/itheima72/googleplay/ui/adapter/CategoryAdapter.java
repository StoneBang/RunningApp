package com.itheima72.googleplay.ui.adapter;

import java.util.ArrayList;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.CategoryInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryAdapter extends BasicAdapter<Object>{

	public CategoryAdapter(ArrayList<Object> list) {
		super(list);
	}
	//定义item类型的常量
	private final int ITEM_TITLE = 0;//title类型的item
	private final int ITEM_INFO = 1;//info类型的item
	
	/**
	 * 返回有多少种item类型
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	/**
	 * 返回当前position的item是什么类型的
	 */
	@Override
	public int getItemViewType(int position) {
		//获取当前position的数据，判断其类型，
		Object object = list.get(position);
		if(object instanceof String){
			//属于title类型的item
			return ITEM_TITLE;
		}else {
			//属于info类型的item
			return ITEM_INFO;
		}
//		return super.getItemViewType(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//根据当前position的item类型，去加载对应的View
		int itemViewType = getItemViewType(position);
		switch (itemViewType) {
		case ITEM_TITLE://如果是title类型，则加载title的布局
			if(convertView==null){
				convertView = View.inflate(GooglePlayApplication.getContext(),R.layout.adapter_category_title, null);
			}
			TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			String title = (String) list.get(position);
			tv_title.setText(title);
			break;
		case ITEM_INFO://如果是info类型，则加载info的布局
			if(convertView==null){
				convertView = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_category_info, null);
			}
			CategoryInfoHolder holder = CategoryInfoHolder.getHolder(convertView);
			CategoryInfo info = (CategoryInfo) list.get(position);
			
			holder.tv_name1.setText(info.getName1());
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info.getUrl1(),holder.iv_icon1,ImageLoaderOptions.options);
			
			//由于第2个和第3个可能木有，那么我们在显示的时候需要判断了
			if(!TextUtils.isEmpty(info.getUrl2())){
				//需要重新设置为可见，原因是布局是复用的
				holder.ll_info2.setVisibility(View.VISIBLE);
				
				holder.tv_name2.setText(info.getName2());
				ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info.getUrl2(),holder.iv_icon2,ImageLoaderOptions.options);
			}else {
				//说明当前木有第2个，那么需要隐藏第2个的区域
				holder.ll_info2.setVisibility(View.INVISIBLE);
			}
			
			if(!TextUtils.isEmpty(info.getUrl3())){
				//需要重新设置为可见，原因是布局是复用的
				holder.ll_info3.setVisibility(View.VISIBLE);
				
				holder.tv_name3.setText(info.getName3());
				ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info.getUrl3(),holder.iv_icon3,ImageLoaderOptions.options);
			}else {
				//说明当前木有第3个，那么需要隐藏第3个的区域
				holder.ll_info3.setVisibility(View.INVISIBLE);
			}
			break;
		}
		return convertView;
	}

	static class CategoryInfoHolder{
		ImageView iv_icon1,iv_icon2,iv_icon3;
		TextView tv_name1,tv_name2,tv_name3;
		LinearLayout ll_info2,ll_info3;
		public CategoryInfoHolder(View convertView){
			iv_icon1 = (ImageView) convertView.findViewById(R.id.iv_icon1);
			iv_icon2 = (ImageView) convertView.findViewById(R.id.iv_icon2);
			iv_icon3 = (ImageView) convertView.findViewById(R.id.iv_icon3);
			tv_name1 = (TextView) convertView.findViewById(R.id.tv_name1);
			tv_name2 = (TextView) convertView.findViewById(R.id.tv_name2);
			tv_name3 = (TextView) convertView.findViewById(R.id.tv_name3);
			ll_info2 = (LinearLayout) convertView.findViewById(R.id.ll_info2);
			ll_info3 = (LinearLayout) convertView.findViewById(R.id.ll_info3);
		}
		public static CategoryInfoHolder getHolder(View convertView){
			CategoryInfoHolder holder = (CategoryInfoHolder) convertView.getTag();
			if(holder==null){
				holder = new CategoryInfoHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}
}

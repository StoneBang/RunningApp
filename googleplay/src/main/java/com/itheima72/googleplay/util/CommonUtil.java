package com.itheima72.googleplay.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.R.id;
import com.itheima72.googleplay.global.GooglePlayApplication;

public class CommonUtil {
	/**
	 * 强大的方法：能够在主线程执行任务
	 */
	public static void runOnUIThread(Runnable r){
		GooglePlayApplication.getMainHandler().post(r);
	}
	/**
	 * 强大的方法：能够在主线程延时执行任务
	 */
	public static void runOnUIThread(Runnable r,long delayMillis){
		GooglePlayApplication.getMainHandler().postDelayed(r, delayMillis);
	}
	/**
	 * 将子View从它父View中移除
	 * @param child
	 */
	public static void removeSelfFromParent(View child){
		if(child!=null){
			ViewParent parent = child.getParent();
			if(parent!=null && parent instanceof ViewGroup){
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//将子VIew从父View中移除
			}
		}
	}
	
	/**
	 * 获取Resources对象
	 * @return
	 */
	public static Resources getResources(){
		return GooglePlayApplication.getContext().getResources();
	}
	/**
	 * 获取字符串资源
	 * @param id
	 * @return
	 */
	public static String getString(int id){
		return getResources().getString(id);
	}
	/**
	 * 获取字符串数组资源
	 * @param id
	 * @return
	 */
	public static String[] getStringArray(int id){
		return getResources().getStringArray(id);
	}
	
	/**
	 * 获取color资源
	 * @param id
	 * @return
	 */
	public static int getColor(int id){
		return getResources().getColor(id);
	}
	/**
	 * 获取drawable资源
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(int id){
		return getResources().getDrawable(id);
	}
	/**
	 * 获取dp资源
	 * @param id
	 * @return
	 */
	public static float getDimens(int id){
		return getResources().getDimension(id);
	}
	
}

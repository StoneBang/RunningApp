package com.itheima72.googleplay.util;

import com.itheima72.googleplay.global.GooglePlayApplication;

import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	public static void showToast(String text){
		if(toast==null){
			toast = Toast.makeText(GooglePlayApplication.getContext(),text,0);
		}
		toast.setText(text);
		toast.show();
	}
}

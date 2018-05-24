package com.itheima72.googleplay.ui.activity;

import java.util.ArrayList;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.lib.photoview.HackyViewPager;
import com.itheima72.googleplay.ui.adapter.ImageScaleAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ImageScaleActivity extends Activity{
	private HackyViewPager viewPager;//使用HackyViewPager，能够避免IllegalArgumentException: pointerIndex out of range
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_scale);
		viewPager = (HackyViewPager) findViewById(R.id.viewPager);
		
		ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
		int currentItem = getIntent().getIntExtra("currentItem", 0);
		
		ImageScaleAdapter adapter = new ImageScaleAdapter(urlList);
		viewPager.setAdapter(adapter);
		
		//默认选中点击的位置
		viewPager.setCurrentItem(currentItem);
	}
}

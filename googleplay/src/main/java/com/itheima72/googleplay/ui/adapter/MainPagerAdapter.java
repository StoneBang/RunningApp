package com.itheima72.googleplay.ui.adapter;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.ui.fragment.FragmentFactory;
import com.itheima72.googleplay.util.CommonUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter{
	private String[] tabs;
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		tabs = CommonUtil.getStringArray(R.array.tab_names);
	}
	/**
	 * 返回每个位置对应的fragment对象
	 */
	@Override
	public Fragment getItem(int position) {
		return FragmentFactory.create(position);
	}
	@Override
	public int getCount() {
		return tabs.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return tabs[position];
	}

}

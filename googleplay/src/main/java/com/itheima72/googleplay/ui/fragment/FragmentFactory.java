package com.itheima72.googleplay.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * 生成fragment的工厂类
 * @author Administrator
 *
 */
public class FragmentFactory {
	/**
	 * 根据不同的position，生产对应的fragment对象
	 * @param position
	 * @return
	 */
	public static Fragment create(int position){
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new AppFragment();
			break;
		case 2:
			fragment = new GameFragment();
			break;
		case 3:
			fragment = new SubjectFragment();
			break;
		case 4:
			fragment = new RecommendFragment();
			break;
		case 5:
			fragment = new CategoryFragment();
			break;
		case 6:
			fragment = new HotFragment();
			break;
		}
		return fragment;
	}
}

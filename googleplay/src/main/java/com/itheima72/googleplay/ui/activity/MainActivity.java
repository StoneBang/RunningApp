package com.itheima72.googleplay.ui.activity;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.lib.PagerSlidingTab;
import com.itheima72.googleplay.ui.adapter.MainPagerAdapter;
import com.itheima72.googleplay.util.LogUtil;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private PagerSlidingTab pagerIndicator;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		setActionBar();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		pagerIndicator = (PagerSlidingTab) findViewById(R.id.pagerIndicator);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		//1.填充ViewPager
		viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
		//2.绑定pagerIndicator和viewPager
		pagerIndicator.setViewPager(viewPager);
	}

	/**
	 * 设置A
	 */
	private void setActionBar() {
		// 1.获取v7包中的ActionBar对象
		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.ic_launcher);// 设置ActionBAR的图标
		actionBar.setTitle(getString(R.string.app_name));// 设置ActionBAR的标题

		// 2.显示ActionBar的home按钮
		actionBar.setDisplayHomeAsUpEnabled(true);// 显示home按钮
		actionBar.setDisplayShowHomeEnabled(true);// 设置home按钮可以被点击，其实默认就可以被点击

		// 3.给home按钮设置3条线的图标
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer_am, 0, 0);
		drawerToggle.syncState();// 同步ActionBar和DrawerLayout的状态

		// 4.给3条线增加进出的动画
		drawerLayout.setDrawerListener(new DrawerListener() {
			@Override
			public void onDrawerStateChanged(int newState) {
				// 将drawerLayout的滑动状态告诉drawerToggle
				drawerToggle.onDrawerStateChanged(newState);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// 将drawerLayout的滑动的百分比告诉drawerToggle
				drawerToggle.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// 将drawerLayout的打开的状态告诉drawerToggle
				drawerToggle.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				// 将drawerLayout的关闭的状态告诉drawerToggle
				drawerToggle.onDrawerClosed(drawerView);
			}
		});
	}

	/**
	 * 当点击ActionBar的home按钮的时候会执行该方法
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		drawerToggle.onOptionsItemSelected(item);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

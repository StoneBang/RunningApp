package com.itheima72.googleplay.ui.fragment;

import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.LogUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
	protected ContentPage contentPage;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(contentPage==null){
			contentPage = new ContentPage(getActivity()) {
				@Override
				protected Object loadData() {
					//自己调自己：报异常:stack overflow
					return requestData();
				}
				@Override
				protected View createSuccessView() {
					return getSuccessView();
				}
			};
		}else {
//			LogUtil.e(this, "使用已经创建的ContentPage - ："+contentPage.getParent().getClass().getSimpleName());
			//此时的ContentPage已经有父View：NoSaveStateFrameLayout
			//做法:应该讲COntentPage从父VIew中移除
			CommonUtil.removeSelfFromParent(contentPage);
		}
		return contentPage;
	}
	
	/**
	 * 获取成功的View，每个界面自己去实现
	 * @return
	 */
	protected abstract View getSuccessView();
	/**
	 * 获取请求的数据，每个界面自己去实现，已经在子线程调用了
	 * @return
	 */
	protected abstract Object requestData();
}

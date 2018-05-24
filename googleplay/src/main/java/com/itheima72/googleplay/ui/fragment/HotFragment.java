package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.view.FlowLayout;
import com.itheima72.googleplay.util.ColorUtil;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.DrawableUtil;
import com.itheima72.googleplay.util.JsonUtil;
import com.itheima72.googleplay.util.ToastUtil;

public class HotFragment extends BaseFragment{
	private ScrollView scrollView;
	private FlowLayout flowLayout;
	private int textHPadding,textVPadding;
	private float hot_radius;
	@Override
	protected View getSuccessView() {
		scrollView = new ScrollView(getActivity());
		flowLayout = new FlowLayout(getActivity());
		
		textHPadding = (int) CommonUtil.getDimens(R.dimen.hot_text_hpading);
		textVPadding = (int) CommonUtil.getDimens(R.dimen.hot_text_vpading);
		hot_radius = CommonUtil.getDimens(R.dimen.hot_radius);
		
		//1.设置四周的padding值
		int padding = (int) CommonUtil.getDimens(R.dimen.flowlayout_padding);
		flowLayout.setPadding(padding,padding,padding,padding);
		//2.设置flowLayout的水平和垂直间距
		int spacing = (int) CommonUtil.getDimens(R.dimen.flowlayout_spacing);
		flowLayout.setHorizontalSpacing(spacing);
		flowLayout.setVerticalSpacing(spacing);
		
		
		scrollView.addView(flowLayout);
		return scrollView;
	}

	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Hot);
		final ArrayList<String> list = (ArrayList<String>) JsonUtil.parseJsonToList(result,new TypeToken<List<String>>(){}.getType());
		
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				//往FlowLayout中添加45个TextView
				if(list!=null){
					for (int i = 0; i < list.size(); i++) {
						final TextView textView = new TextView(getActivity());
						textView.setText(list.get(i));
						textView.setTextColor(Color.WHITE);
						textView.setGravity(Gravity.CENTER);
						textView.setTextSize(16);
						textView.setPadding(textHPadding, textVPadding, textHPadding, textVPadding);
//						textView.setBackgroundColor(ColorUtil.randomColor());
						Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(),hot_radius);
						Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(),hot_radius);
						textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));
						
						flowLayout.addView(textView);
						
						textView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								ToastUtil.showToast(textView.getText().toString());
							}
						});
					}
				}
				
			}
		});
		return list;
	}
}

package com.itheima72.googleplay.ui.view;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.util.CommonUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * 显示小字体的一些样式
 * @author Administrator
 *
 */
public class SmallTextView extends TextView{

	public SmallTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SmallTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SmallTextView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		//设置共同属性或者样式
		setSingleLine();
		setTextSize(16);
		setTextColor(CommonUtil.getColor(R.color.txt_small));
	}
}

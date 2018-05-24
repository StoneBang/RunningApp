package com.itheima72.googleplay.ui.model;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class AppDesModel extends BaseModel<AppInfo> implements OnClickListener{
	private View view;
	private TextView tv_des,tv_author;
	private ImageView iv_des_arrow;
	int minHeight;//tv_des控件5行时候的高度
	int maxHeight;//tv_des控件全部的高度
	private ScrollView scrollView;
	public AppDesModel(ScrollView scrollView){
		this.scrollView = scrollView;
		
		view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_des,null);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		tv_author = (TextView) view.findViewById(R.id.tv_author);
		iv_des_arrow = (ImageView) view.findViewById(R.id.iv_des_arrow);
		
		view.setOnClickListener(this);
	}
	@Override
	public View getModelView() {
		return view;
	}

	@Override
	public void setData(AppInfo appInfo) {
		tv_des.setText(appInfo.getDes());
		tv_author.setText(appInfo.getAuthor());
		
		//1.一开始需要显示5行的高度
		tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				//用完要立即移除监听器,因为只要当前view的宽高改变，都会重新layout，那么会导致onGlobalLayout重复回调
				tv_des.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				
				maxHeight = tv_des.getHeight();//获取全部的高度
				//2.获取5行时候的高度
				tv_des.setMaxLines(5);//设置5行后，会引起重新layout
				tv_des.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						//用完要立即移除监听器,因为只要当前view的宽高改变，都会重新layout，那么会导致onGlobalLayout重复回调
						tv_des.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						
						minHeight = tv_des.getHeight();//获取5行的高度
						//3.取消5行的限制，将tv_des的height设置为5行的高度
						tv_des.setMaxLines(Integer.MAX_VALUE);
						tv_des.getLayoutParams().height = minHeight;
						tv_des.requestLayout();
					}
				});
			}
		});
		
	}
	private boolean isExtend = false;//是否是展开的
	@Override
	public void onClick(View v) {
		ValueAnimator animator;
		if(isExtend){
			animator = ValueAnimator.ofInt(maxHeight,minHeight);
		}else {
			animator = ValueAnimator.ofInt(minHeight,maxHeight);
		}
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				//获取动画当前的值，设置给view的高度
				int animatedValue = (Integer) animator.getAnimatedValue();
				tv_des.getLayoutParams().height = animatedValue;
				tv_des.requestLayout();
				
				//如果是展开，则需要scrollView向上滚动
				if(isExtend){
//					scrollView.scrollBy(0, scrollView.getMaxScrollAmount());
					scrollView.scrollBy(0, maxHeight-minHeight);
				}
			}
		});
		animator.setDuration(350);
		animator.start();
		
		isExtend = !isExtend;
		//旋转箭头
		ViewPropertyAnimator.animate(iv_des_arrow).rotationBy(180).setDuration(350).start();
	}
	
}

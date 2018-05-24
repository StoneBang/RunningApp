package com.itheima72.googleplay.ui.model;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.bean.SafeInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.global.ImageLoaderOptions;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.util.LogUtil;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AppSafeModel extends BaseModel<AppInfo> implements OnClickListener{
	private View view;
	private ImageView iv_image1,iv_image2,iv_image3,iv_safe_arrow;
	private ImageView iv_des_image1,iv_des_image2,iv_des_image3;
	private TextView tv_safe_des1,tv_safe_des2,tv_safe_des3;
	private LinearLayout ll_des2,ll_des3;
	private LinearLayout ll_des_container;
	private RelativeLayout rl_safe_top;
	private int height;
	public AppSafeModel(){
		view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_safe, null);
		iv_image1 = (ImageView) view.findViewById(R.id.iv_image1);
		iv_image2 = (ImageView) view.findViewById(R.id.iv_image2);
		iv_image3 = (ImageView) view.findViewById(R.id.iv_image3);
		iv_safe_arrow = (ImageView) view.findViewById(R.id.iv_safe_arrow);
		
		iv_des_image1 = (ImageView) view.findViewById(R.id.iv_des_image1);
		iv_des_image2 = (ImageView) view.findViewById(R.id.iv_des_image2);
		iv_des_image3 = (ImageView) view.findViewById(R.id.iv_des_image3);
		
		tv_safe_des1 = (TextView) view.findViewById(R.id.tv_safe_des1);
		tv_safe_des2 = (TextView) view.findViewById(R.id.tv_safe_des2);
		tv_safe_des3 = (TextView) view.findViewById(R.id.tv_safe_des3);
		
		ll_des2 = (LinearLayout) view.findViewById(R.id.ll_des2);
		ll_des3 = (LinearLayout) view.findViewById(R.id.ll_des3);
		ll_des_container = (LinearLayout) view.findViewById(R.id.ll_des_container);
		rl_safe_top = (RelativeLayout) view.findViewById(R.id.rl_safe_top);
		
		rl_safe_top.setOnClickListener(this);
	}

	@Override
	public View getModelView() {
		return view;
	}

	@Override
	public void setData(AppInfo appInfo) {
		ArrayList<SafeInfo> safe = appInfo.getSafe();
		SafeInfo info1 = safe.get(0);
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info1.getSafeUrl(),iv_image1, ImageLoaderOptions.pager_options);
		ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info1.getSafeDesUrl(),iv_des_image1, ImageLoaderOptions.pager_options);
		tv_safe_des1.setText(info1.getSafeDes());
		
		//由于第2个和第3个可能木有，那么久需要判断
		if(safe.size()>1){
			//说明有第2个
			SafeInfo info2 = safe.get(1);
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info2.getSafeUrl(),iv_image2, ImageLoaderOptions.pager_options);
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info2.getSafeDesUrl(),iv_des_image2, ImageLoaderOptions.pager_options);
			tv_safe_des2.setText(info2.getSafeDes());
		}else {
			//隐藏第2个
			ll_des2.setVisibility(View.GONE);
		}
		
		if(safe.size()>2){
			//说明有第3个
			SafeInfo info3 = safe.get(2);
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info3.getSafeUrl(),iv_image3, ImageLoaderOptions.pager_options);
			ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+info3.getSafeDesUrl(),iv_des_image3, ImageLoaderOptions.pager_options);
			tv_safe_des3.setText(info3.getSafeDes());
		}else {
			//隐藏第3个
			ll_des3.setVisibility(View.GONE);
		}
		//1.先获取ll_des_container的高度
		ll_des_container.measure(0, 0);//保证能够获取到值
		height = ll_des_container.getMeasuredHeight();
		
		//2.通过将高度设置为0隐藏ll_des_container的区域
		ll_des_container.getLayoutParams().height = 0;
		ll_des_container.requestLayout();//重新让布局参数生效
		
		//3.增加右移的动画
		ViewHelper.setTranslationX(view, -view.getWidth());
		ViewPropertyAnimator.animate(view).translationXBy(view.getWidth())
		.setInterpolator(new OvershootInterpolator(4))
		.setDuration(450)
		.setStartDelay(600)
		.start();
	}
	
	private boolean isExtend = false;//是否是展开的
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_safe_top:
			ValueAnimator animator;
			if(isExtend){
				//需要执行收缩动画
				animator = ValueAnimator.ofInt(height,0);
			}else {
				//需要展开动画
				animator = ValueAnimator.ofInt(0,height);
			}
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					//获取动画的值，设置为ll_des_container的高度
					int animatedValue = (Integer) animator.getAnimatedValue();
					ll_des_container.getLayoutParams().height =animatedValue;
					ll_des_container.requestLayout();//重新让布局参数生效
				}
			});
			animator.setDuration(350);
			animator.start();
			
			//更改isExtend的值
			isExtend = !isExtend;
			//让箭头转圈
			ViewPropertyAnimator.animate(iv_safe_arrow).rotationBy(180).setDuration(350).start();
//			ViewPropertyAnimator.animate(iv_image2).translationXBy(40)
//			.setInterpolator(new OvershootInterpolator(4))//超过一点再回来
//			.setInterpolator(new CycleInterpolator(4))//左右晃动
//			.setInterpolator(new BounceInterpolator())//像球落地一样的感觉
//			.setDuration(350).start();
//			ViewPropertyAnimator.animate(iv_image2).scaleXBy(0.2f).setDuration(350).start();
//			ViewPropertyAnimator.animate(iv_image2).scaleYBy(0.2f).setDuration(350).start();
			break;
		}
	}

}

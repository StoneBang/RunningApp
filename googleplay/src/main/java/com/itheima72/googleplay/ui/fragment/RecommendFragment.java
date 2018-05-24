package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.lib.randomlayout.StellarMap;
import com.itheima72.googleplay.lib.randomlayout.StellarMap.Adapter;
import com.itheima72.googleplay.util.ColorUtil;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;
import com.itheima72.googleplay.util.ToastUtil;

public class RecommendFragment extends BaseFragment{

	private StellarMap stellarMap;
	private ArrayList<String> list;

	@Override
	protected View getSuccessView() {
		stellarMap = new StellarMap(getActivity());
		//1.设置内部的子View距四边的内边距
		int innerPadding = (int) CommonUtil.getDimens(R.dimen.stellarmap_padding);
		stellarMap.setInnerPadding(innerPadding,innerPadding,innerPadding,innerPadding);
		
		return stellarMap;
	}

	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Recommend);
		list = (ArrayList<String>) JsonUtil.parseJsonToList(result,new TypeToken<List<String>>(){}.getType());
		
		if(list!=null){
			CommonUtil.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					//设置数据
					stellarMap.setAdapter(new StellarMapAdapter());
					//设置最初显示第几组的数据
					stellarMap.setGroup(0, true);
					//设置x和y方向的子View的分布密度
					stellarMap.setRegularity(15, 15);//不要设置过大，也不要过小，差不多意思就行了,跟每页的count相当就行
				}
			});
		}
		
		return list;
	}
	
	class StellarMapAdapter implements Adapter{
		/**
		 * 返回多少组数据
		 */
		@Override
		public int getGroupCount() {
			return 3;
		}
		/**
		 * 返回指定的group有多少个数据
		 */
		@Override
		public int getCount(int group) {
			return 11;
		}
		/**
		 * group： 当前是第几组
		 * position:在当前组中的位置
		 */
		@Override
		public View getView(int group, int position, View convertView) {
			final TextView textView = new TextView(getActivity());
			//1.设置文本数据
			int listPosition = group*getCount(group) + position;
			textView.setText(list.get(listPosition));
			//2.设置随机的字体
			Random random = new Random();
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,random.nextInt(8)+14);//14-21
			//3.上色，设置随机字体颜色
			textView.setTextColor(ColorUtil.randomColor());
			//4.设置点击事件
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtil.showToast(textView.getText().toString());
				}
			});
			
			return textView;
		}
		/**
		 * 然而并没有什么用
		 */
		@Override
		public int getNextGroupOnPan(int group, float degree) {
			return 0;
		}
		/**
		 * 当前组缩放完毕之后，下一组加载哪一组的数据
		 */
		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			//0->1->2->0
			return (group+1)%3;
		}
		
	}
}

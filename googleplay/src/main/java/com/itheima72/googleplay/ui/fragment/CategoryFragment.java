package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.itheima72.googleplay.R;
import com.itheima72.googleplay.bean.Category;
import com.itheima72.googleplay.bean.CategoryInfo;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.adapter.CategoryAdapter;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;

public class CategoryFragment extends BaseFragment{

	private ListView listView;
	private ArrayList<Object> list = new ArrayList<Object>();
	private CategoryAdapter categoryAdapter;
	@Override
	protected View getSuccessView() {
		listView = (ListView) View.inflate(getActivity(), R.layout.listview, null);
		
		categoryAdapter = new CategoryAdapter(list);
		listView.setAdapter(categoryAdapter);
		
		return listView;
	}

	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Category);
		final ArrayList<Category> categories = (ArrayList<Category>) JsonUtil.parseJsonToList(result,new TypeToken<List<Category>>(){}.getType());
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				if(categories!=null){ 
					//将categories中的每个Category的title和infos数据放入同一个list中
					for(Category category : categories){
						//1.将title放入list中
						String title = category.getTitle();
						list.add(title);
						//2.将infos中所有的对象放入list中
						ArrayList<CategoryInfo> infos = category.getInfos();
						list.addAll(infos);
//						list.add(infos);//note:千万不要这样写，是不对滴!
					}
					categoryAdapter.notifyDataSetChanged();
				}				
			}
		});
		
		return categories;
	}
}

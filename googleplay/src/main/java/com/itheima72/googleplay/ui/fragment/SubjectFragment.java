package com.itheima72.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import com.google.gson.reflect.TypeToken;
import com.itheima72.googleplay.bean.Subject;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.ui.adapter.SubjectAdapter;
import com.itheima72.googleplay.util.CommonUtil;
import com.itheima72.googleplay.util.JsonUtil;

public class SubjectFragment extends BaseRefreshListFragment{
	private SubjectAdapter subjectAdapter;
	private ArrayList<Subject> list = new ArrayList<Subject>();
	@Override
	protected View getSuccessView() {
		initRefreshListView();
		
		subjectAdapter = new SubjectAdapter(list);
		listView.setAdapter(subjectAdapter);
		
		return refreshListView;
	}

	@Override
	protected Object requestData() {
		String result = HttpHelper.get(Url.Subject+0);
		final ArrayList<Subject> subjects = (ArrayList<Subject>) JsonUtil.parseJsonToList(result,  new TypeToken<List<Subject>>(){}.getType());
		
		if(subjects!=null){
			CommonUtil.runOnUIThread(new Runnable() {
				@Override
				public void run() {
					list.addAll(subjects);
					subjectAdapter.notifyDataSetChanged();
					refreshListView.onRefreshComplete();
				}
			});
		}
		
		return subjects;
	}
}

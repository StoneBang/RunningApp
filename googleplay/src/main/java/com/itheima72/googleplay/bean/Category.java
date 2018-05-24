package com.itheima72.googleplay.bean;

import java.util.ArrayList;

public class Category {
	private String title;//分类的名称
	private ArrayList<CategoryInfo> infos;//大分类下的小分类
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<CategoryInfo> getInfos() {
		return infos;
	}
	public void setInfos(ArrayList<CategoryInfo> infos) {
		this.infos = infos;
	}
	
	
}

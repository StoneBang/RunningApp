package com.itheima72.googleplay.ui.model;

import android.view.View;

public abstract class BaseModel<T> {
	/**
	 * 获取模块对应的VIew对象
	 * @return
	 */
	public abstract View getModelView();
	/**
	 * 处理数据的业务逻辑方法
	 * @param t
	 */
	public abstract void setData(T t);
}

package com.itheima72.googleplay.manager;

import java.io.File;

import com.itheima72.googleplay.bean.AppInfo;

/**
 * 用于封装下载相关的数据信息
 * @author Administrator
 *
 */
public class DownloadInfo {
	private int id;//下载任务的唯一标识
	private int state;//下载的状态
	private String downloadUrl;//下载地址url后缀
	private long currentLength;//当前已经下载的长度
	private long size;//总的大小
	private String path;//apk文件的保存的绝对路径
	
	/**
	 * 从appInfo从初始化相关的数据
	 * @param appInfo
	 * @return
	 */
	public static DownloadInfo create(AppInfo appInfo){
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setId(appInfo.getId());//设置DownloadInfo的唯一标识
		downloadInfo.setDownloadUrl(appInfo.getDownloadUrl());//设置下载地址url后缀
		downloadInfo.setSize(appInfo.getSize());//设置总大小
		downloadInfo.setCurrentLength(0);
		downloadInfo.setState(DownloadManager.STATE_NONE);//设置未下载的初始状态
		
		//绝对路径： /mnt/sdcard/包名/download/有缘网.apk
		downloadInfo.setPath(DownloadManager.DOWNLOAD_DIR+File.separator
				+ appInfo.getName() +".apk");//设置apk文件的保存路径
		
		return downloadInfo;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public long getCurrentLength() {
		return currentLength;
	}
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}

package com.itheima72.googleplay.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;

import com.itheima72.googleplay.bean.AppInfo;
import com.itheima72.googleplay.global.GooglePlayApplication;
import com.itheima72.googleplay.http.HttpHelper;
import com.itheima72.googleplay.http.HttpHelper.HttpResult;
import com.itheima72.googleplay.http.Url;
import com.itheima72.googleplay.util.CommonUtil;

public class DownloadManager {
	//定义下载目录  ：  /mnt/sdcard/包名/download
	public static String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()+"/"
			+GooglePlayApplication.getContext().getPackageName()+"/download";
	
	//定义6种下载状态常量
	public static final int STATE_NONE = 0;//未下载的状态
	public static final int STATE_DOWNLOADING = 1;//下载中的状态
	public static final int STATE_PAUSE = 2;//暂停的状态
	public static final int STATE_FINISH = 3;//下载完成的状态
	public static final int STATE_ERROR = 4;//下载出错失败的状态
	public static final int STATE_WAITING = 5;//等待中的状态,下载任务已经创建，但是run方法木有执行
	
	private static DownloadManager mInstance = new DownloadManager(); 
	public static DownloadManager getInstance(){
		return mInstance;
	}
	/**
	 * 用于存放所有的下载监听器对象
	 */
	private ArrayList<DownloadObserver> downloadObserverList = new ArrayList<DownloadManager.DownloadObserver>();
	/**
	 * 用于存放所有任务的下载信息,注意：下载的数据是缓存在内存中，并木有进行持久化保存
	 */
	//当你的map的key是integer的时候，建议使用sparseArray来获取更高的效率
	private SparseArray<DownloadInfo> downloadInfoMap = new SparseArray<DownloadInfo>();
	/**
	 * 用于存放DownloadTask对象，为了暂停的时候取出对应的task，然后从线程池中移除，及时为缓冲队列中的任务腾出系统资源
	 */
	private SparseArray<DownloadTask> downloadTaskMap = new SparseArray<DownloadTask>();
	
	private DownloadManager(){
		//初始化下载目录
		File file = new File(DOWNLOAD_DIR);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public DownloadInfo getDownloadInfo(AppInfo appInfo){
		return downloadInfoMap.get(appInfo.getId());
	}
	
	/**
	 * 下载方法
	 */
	public void download(AppInfo appInfo){
		//1.获取DownloadInfo对象，因为后续的操作都需要根据它的state来处理
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo==null){
			downloadInfo = DownloadInfo.create(appInfo);
			downloadInfoMap.put(downloadInfo.getId(), downloadInfo);//将downloadInfo存放起来
		}
		//2.根据state判断是否能够进行下载，只有3种状态才能下载：none,pause,error
		if(downloadInfo.getState()==STATE_NONE || downloadInfo.getState()==STATE_PAUSE
				|| downloadInfo.getState()==STATE_ERROR){
			//可以进行下载操作了
			DownloadTask downloadTask = new DownloadTask(downloadInfo);
			//将downloadTask存放起来，便于暂停的时候取出
			downloadTaskMap.put(downloadInfo.getId(), downloadTask);
			
			//更改状态为waiting状态
			downloadInfo.setState(STATE_WAITING);
			//通知监听器状态改变
			notifyDownloadStateChange(downloadInfo);
			
			//将downloadTask交给线程池来管理
			ThreadPoolManager.getInstance().execute(downloadTask);
		}
	}
	
	/**
	 * 下载任务
	 * @author Administrator
	 *
	 */
	class DownloadTask implements Runnable{
		private DownloadInfo downloadInfo;
		public DownloadTask(DownloadInfo downloadInfo) {
			super();
			this.downloadInfo = downloadInfo;
		}
		@Override
		public void run() {
			//3.一旦run方法执行，说明已经开始下载了
			//更改状态为downloading状态
			downloadInfo.setState(STATE_DOWNLOADING);
			notifyDownloadStateChange(downloadInfo);//通知监听器状态改变
			
			//4.开始下载操作,分为2种情况:从头下载和断点下载
			HttpResult httpResult;
			File file = new File(downloadInfo.getPath());
			if(!file.exists() || file.length()!=downloadInfo.getCurrentLength()){
				//属于从头下载的情况
				file.delete();//删除无效文件
				downloadInfo.setCurrentLength(0);//重置currentLength
				
				String url = String.format(Url.Download, downloadInfo.getDownloadUrl());
				httpResult = HttpHelper.download(url);
			}else {
				//属于断点下载的情况
				String url = String.format(Url.Break_Download,downloadInfo.getDownloadUrl(),downloadInfo.getCurrentLength());
				httpResult = HttpHelper.download(url);
			}
			
			//5.读取流对象，写入文件
			if(httpResult!=null && httpResult.getInputStream()!=null){
				//说明请求文件数据成功
				InputStream is = httpResult.getInputStream();
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file,true);//往后追加
					byte[] buffer = new byte[1024*8];//8k的缓冲区
					int len = -1;//用来记录每次读取的长度
					while((len=is.read(buffer))!=-1 && downloadInfo.getState()==STATE_DOWNLOADING){
						fos.write(buffer, 0, len);
						//更新currentLength
						downloadInfo.setCurrentLength(downloadInfo.getCurrentLength()+len);
						//通知监听器下载进度改变
						notifyDownloadProgressChange(downloadInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					processErrorState(file,downloadInfo);
				}finally{
					//关闭流和链接
					httpResult.close();
					try {
						if (fos != null)fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//6.for循环结束走到这里，有几种情况:a.下载成功   b.暂停   c.下载失败
				if(file.length()==downloadInfo.getSize() && downloadInfo.getState()==STATE_DOWNLOADING){
					//下载完成
					downloadInfo.setState(STATE_FINISH);
					notifyDownloadStateChange(downloadInfo);
				}else if (downloadInfo.getState()==STATE_PAUSE) {
					notifyDownloadStateChange(downloadInfo);
				}else if (file.length()!=downloadInfo.getCurrentLength()) {
					//下载失败的情况
					processErrorState(file, downloadInfo);
				}
				
			}else {
				//请求文件数据失败
				//更改状态为error
				processErrorState(file,downloadInfo);
			}
			
			//7.run方法将要结束了，需要将downloadTask从downloadTaskMap中移除
			downloadTaskMap.remove(downloadInfo.getId());
		}
		
	}
	/**
	 * 处理下载失败的情况
	 * @param file
	 * @param downloadInfo
	 */
	private void processErrorState(File file,DownloadInfo downloadInfo) {
		file.delete();//删除文件
		downloadInfo.setCurrentLength(0);//重置currentLength
		downloadInfo.setState(STATE_ERROR);
		notifyDownloadStateChange(downloadInfo);//通知监听器状态改变
	}
	
	/**
	 * 通知下载监听器状态
	 * @param downloadInfo
	 */
	private void notifyDownloadStateChange(final DownloadInfo downloadInfo) {
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				for (DownloadObserver observer : downloadObserverList) {
					observer.onDownloadStateChange(downloadInfo);
				}
			}
		});
	}
	/**
	 * 通知下载监听器进度改变
	 * @param downloadInfo
	 */
	private void notifyDownloadProgressChange(final DownloadInfo downloadInfo){
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				for (DownloadObserver observer : downloadObserverList) {
					observer.onDownloadProgressChange(downloadInfo);
				}
			}
		});
	}
	
	/**
	 * 暂停方法
	 */
	public void pause(AppInfo appInfo){
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo!=null){
			//将state更改为pause
			downloadInfo.setState(STATE_PAUSE);
			notifyDownloadStateChange(downloadInfo);
			
			//获取downloadTask对象，将其及时从线程池中移除,为缓冲队列的任务腾出系统资源(很急)
			DownloadTask downloadTask = downloadTaskMap.get(downloadInfo.getId());
			ThreadPoolManager.getInstance().remove(downloadTask);
		}
	}
	/**
	 * 安装apk方法
	 */
	public void installApk(AppInfo appInfo){
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo!=null){
			/* <action android:name="android.intent.action.VIEW" />
             <category android:name="android.intent.category.DEFAULT" />
             <data android:scheme="content" />
             <data android:scheme="file" />
             <data android:mimeType="application/vnd.android.package-archive" />*/
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重新创建任务栈
			intent.setDataAndType(Uri.parse("file://"+downloadInfo.getPath()),"application/vnd.android.package-archive");
			GooglePlayApplication.getContext().startActivity(intent);
		}
	}
	
	/**
	 * 注册下载监听器对象
	 * @param downloadObserver
	 */
	public void registerDownloadObserver(DownloadObserver downloadObserver){
		if(!downloadObserverList.contains(downloadObserver)){
			downloadObserverList.add(downloadObserver);
		}
	}
	
	/**
	 * 移除下载监听器对象
	 * @param downloadObserver
	 */
	public void unregisterDownloadObserver(DownloadObserver downloadObserver){
		if(downloadObserverList.contains(downloadObserver)){
			downloadObserverList.remove(downloadObserver);
		}
	}
	
	/**
	 * 下载状态和进度改变的监听器
	 * @author Administrator
	 *
	 */
	public interface DownloadObserver{
		/**
		 * 下载状态改变的回调
		 */
		void onDownloadStateChange(DownloadInfo downloadInfo);
		
		/**
		 * 下载进度改变的回调
		 */
		void onDownloadProgressChange(DownloadInfo downloadInfo);
	}
}

package com.hbb.coder.runningapp.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hbb.coder.runningapp.MainActivity;
import com.hbb.coder.runningapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @Description: 描述
 * @AUTHOR 刘楠  Create By 2016/10/27 0027 15:56
 */
public class FileUtils {


    private static NotificationManager mNotificationManager;
    private static NotificationCompat.Builder mBuilder;
    private static Handler sHandler;
    private static Runnable sRunnable;
    private  static long currentLengthF;
    //初始化通知
    private static void initNotification(Context mContext) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("正在下载") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher) //设置通知的小图标
                .setLargeIcon(BitmapFactory
                        .decodeResource(mContext.getResources(),
                                R.mipmap.ic_launcher)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false);//设置通知被点击一次是否自动取消
        sHandler = new Handler();

//要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
        sRunnable = new Runnable() {
            @Override
            public void run() {
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                sHandler.postDelayed(this, 500);
            }
        };
    }

    public static File createFile(Context context) {
        initNotification(context);
        File file = null;
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download" + "/test.apk");
        }

//        else {
//            file = new File(context.getCacheDir().getAbsolutePath()+"/test.apk");
//        }

        Log.d("vivi", "file " + file.getAbsolutePath());

        return file;

    }


    public static void writeFile2Disk(Response<ResponseBody> response, File file, HttpCallBack httpCallBack) {

        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.body().byteStream();
        final long totalLength = response.body().contentLength();
        sRunnable = new Runnable() {
            @Override
            public void run() {
                mBuilder.setProgress((int) totalLength, (int) currentLengthF, false);
                mNotificationManager.notify(0, mBuilder.build());
                sHandler.postDelayed(this, 500);
            }
        };
        sHandler.postDelayed(sRunnable, 500);
        try {

            os = new FileOutputStream(file);


            int len;

            byte[] buff = new byte[1024];

            while ((len = is.read(buff)) != -1) {

                os.write(buff, 0, len);
                currentLength += len;
                currentLengthF=currentLength;
                Log.d("vivi", "当前进度:" + currentLength);
                httpCallBack.onLoading(currentLength, totalLength);
                if(currentLength==totalLength){
                    mBuilder.setProgress((int) totalLength, (int) totalLength, false)
                    .setContentTitle("下载完成");
                    mNotificationManager.notify(0, mBuilder.build());
                    sHandler.removeCallbacksAndMessages(null);
                }
            }
            // httpCallBack.onLoading(currentLength,totalLength,true);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}

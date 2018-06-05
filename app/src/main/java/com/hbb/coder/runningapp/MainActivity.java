package com.hbb.coder.runningapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.hbb.coder.runningapp.utils.FileUtils;
import com.hbb.coder.runningapp.utils.HttpCallBack;
import com.hbb.coder.runningapp.utils.NetUtils;
import com.hbb.coder.runningapp.widget.NumberProgressBar;
import com.hbb.coder.runningapp.widget.OnProgressBarListener;
import com.hbb.network.interfaces.CommonServices;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private NumberProgressBar mMProgressBar;
    private File mApkFile;
    private LinearLayout mProgressContainer;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMProgressBar = findViewById(R.id.progreessbar);
        mProgressContainer = findViewById(R.id.update_progress);
        initNotification();

    }


    //初始化通知
    private void initNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setContentTitle("正在更新...") //设置通知标题
                .setSmallIcon(R.mipmap.ic_launcher) //设置通知的小图标
                .setLargeIcon(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.ic_launcher)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(false)//设置通知被点击一次是否自动取消
                .setContentText("下载进度:" + "0%");

    }


    /**
     * @param file
     * @return
     * @Description 安装apk
     */
        protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(MainActivity.this, "com.shawpoo.app.fileprovider", file);  //包名.fileprovider
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    public void testClick(View view) {
        if(NetUtils.isNetworkAvailable(MainActivity.this)){
            if(NetUtils.isWifi(MainActivity.this)){

                startDownload();
            }else{
                Toast.makeText(MainActivity.this, "当前非wifi网络,继续?", Toast.LENGTH_SHORT).show();
                return;
            }

        }else{
            Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
        }


    }


    private void startDownload() {

        //http://surl.qq.com/195D0D?qbsrc=51&asr=4286

        String downloadUrl = "android_release.apk";

//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apphelp.jswym.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CommonServices downloadService = retrofit.create(CommonServices.class);

        Call<ResponseBody> responseBodyCall = downloadService.downloadFile(downloadUrl);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                mProgressContainer.setVisibility(View.VISIBLE);

                Log.d("vivi", response.message() + "  length  " + response.body().contentLength() + "  type " + response.body().contentType());

                //建立一个文件
                mApkFile = FileUtils.createFile(MainActivity.this);

                //下载文件放在子线程
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存到本地
                        FileUtils.writeFile2Disk(response, mApkFile, new HttpCallBack() {
                            @Override
                            public void onLoading(final long current, final long total) {
                                /**
                                 * 更新进度条
                                 */
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("vivi", current + " to " + total);
                                        Log.d("vivi", " runOnUiThread  " + currentThread().getName());
                                        mMProgressBar.setMax((int) total);
                                        mMProgressBar.setProgress((int) current);
                                        if(current==total){
                                            Toast.makeText(getApplicationContext(),"开始安装", Toast.LENGTH_SHORT).show();
                                            installApk(mApkFile);
                                        }

                                    }
                                });
                            }
                        });

                    }
                }.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                Log.d("vivi", t.getMessage() + "  " + t.toString());
            }
        });

    }
}

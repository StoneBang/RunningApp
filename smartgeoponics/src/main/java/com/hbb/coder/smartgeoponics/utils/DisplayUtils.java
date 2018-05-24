package com.hbb.coder.smartgeoponics.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Thomsen on 2017/7/13.
 */

public class DisplayUtils {

    @NonNull
    private static DisplayMetrics getMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric;
    }


    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics metric = getMetrics(context);
        return metric.density;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = getMetrics(context);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = getMetrics(context);
        return metric.heightPixels;
    }

    /**
     * 根据密度获取大小
     * @param context
     * @param dp
     * @return
     */
    public static int getPixel(Context context, int dp) {
        return Math.round(dp * getDensity(context));
    }



    /**
     * 根据密度获取大小
     * @param context
     * @param dp
     * @return
     */
    public static int getPixel(Context context, float dp) {
        return Math.round(dp * getDensity(context));
    }

    /**
     * 获取像素
     * @param view
     * @param dp
     * @return
     */
    public static int getPixel(View view, int dp) {
        return getPixel(view.getContext(), dp);
    }


    /**
     * 根据传入的百分比乘以屏幕的宽度,计算出view的piex值
     * @param context
     * @param dimenRes
     * @return
     */
    public static  int getViewWidthPercent(Context context, float dimenRes){
        return (int) (getScreenWidth(context) * dimenRes);
    }

    /**
     * 根据传入的百分比乘以屏幕的高度,计算出view的piex值
     * @param context
     * @param dimenRes
     * @return
     */
    public static  int getViewHeightPercent(Context context, float dimenRes){
        return (int) (getScreenHeight(context) * dimenRes);

    }
}

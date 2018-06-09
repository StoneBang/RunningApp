package com.hbb.network.utils;

import android.content.res.Resources;

/**
 * Created by Administrator on 2018/5/31.
 */

public class ResourseUtils {
    /**
     * 状态栏高度
     */
    public static final String STATUS_BAR_HEIGHT = "status_bar_height";

    /**
     * 获取内部资源dimen，status_bar_height 状态栏
     * @param res
     * @param key
     * @return
     */
    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

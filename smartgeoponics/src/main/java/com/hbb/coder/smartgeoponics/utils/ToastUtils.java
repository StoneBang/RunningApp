package com.hbb.coder.smartgeoponics.utils;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hbb.coder.smartgeoponics.MainActivity;
import com.hbb.network.MyAppication;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/5/23.
 */

public class ToastUtils {

    public static Toast mToast = null;

    public static void showMessage(String vStr) {

        if (mToast == null) {

            mToast = Toast.makeText(MyAppication.sContext, vStr, Toast.LENGTH_SHORT);

        } else {

            mToast.setText(vStr);
        }
        mToast.show();
    }

}

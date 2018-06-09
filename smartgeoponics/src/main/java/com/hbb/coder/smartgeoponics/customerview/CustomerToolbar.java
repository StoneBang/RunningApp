package com.hbb.coder.smartgeoponics.customerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hbb.coder.smartgeoponics.R;

/**
 * Created by Administrator on 2018/5/31.
 */

public class CustomerToolbar extends Toolbar {
    public CustomerToolbar(Context context) {
        super(context);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


}

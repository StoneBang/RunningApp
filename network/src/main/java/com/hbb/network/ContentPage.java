package com.hbb.network;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 能够管理每个界面加载数据的功能：不同state下，显示不同的View
 *
 * @author Administrator
 */
public abstract class ContentPage extends FrameLayout {
    // 加载中的状态
    public static final int STATE_LOADING = 0;
    // 加载成功的状态
    public static final int STATE_SUCCESS = 1;
    // 加载失败的状态
    public static final int STATE_ERROR = 2;
    // 加载为空的状态
    public static final int STATE_EMPTY = 3;

    public ContentPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initContentPage();
    }

    public ContentPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContentPage();
    }

    public ContentPage(Context context) {
        super(context);
        initContentPage();
    }

    public void setState(int state) {
        mState = state;
    }

    private int mState = STATE_LOADING;//当前界面的state，默认是加载中的状态
    private View loadingView;// 加载中的VIew
    private View errorView;// 加载失败的VIew
    private View successView;// 加载数据成功的VIew
    /**
     * 初始化ContentPage
     */
    private void initContentPage() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // 1.天然地往ContentPage中添加3种状态对应的view对象
        // 添加loadingVIew
        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.page_loading,
                    null);
        }
        addView(loadingView, params);

        // 添加errorView
        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.page_error,
                    null);
            Button btn_reload = (Button) errorView.findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.先转圈
                    mState = STATE_LOADING;
                    showPage();
                    //2.重新加载数据
                    loadDataAndRefreshPage();
                }
            });
        }
        addView(errorView, params);

        //添加successView
        if (successView == null) {
            successView = createSuccessView();
        }
        if (successView != null) {
            addView(successView, params);
        } else {
            //说明createSuccessView方法没有实现，则要提示对方
            throw new IllegalArgumentException("The method createSuccessView() can not return null!");
        }

        //2.显示一下当前默认的VIew
        showPage();

        //3.去服务器请求数据，然后刷新page
        loadDataAndRefreshPage();
    }

    /**
     * 根据当前的state，控制view显示与隐藏
     */
    public void showPage() {
        //1.先隐藏所有的View，
        loadingView.setVisibility(View.INVISIBLE);
        successView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        //2.谁的state谁的VIew显示，其他则隐藏
        switch (mState) {
            case STATE_LOADING://显示loadingView，隐藏其他VIew
                loadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                errorView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS:
                successView.setVisibility(View.VISIBLE);
                break;
        }
    }
    /**
     * 请求服务器的数据，然后根据请求回来的数据刷新Page
     */
    public void loadDataAndRefreshPage() {
        new Thread() {
            @Override
            public void run() {
                loadData();
            }

            ;
        }.start();
    }

    /**
     * 根据请求回来的数据，判断对应的state
     *
     * @param data
     * @return
     */
    public void checkData(Object data) {
        if (data == null) {
            //说明服务器返回的数据为空，应该是error状态
            mState= STATE_ERROR;
        } else {
            //说明服务器返回的数据是成功的
            mState =STATE_SUCCESS;
        }

        showPage();

    }

    /**
     * 由于每个子界面的successView都不一样，所以应该由每个子界面自己去实现，我不用关心
     *
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * 由于每个子界面请求数据的过程不一样，而我们只需要关心请求回来的数据的对象
     *
     * @return
     */
    protected abstract void loadData();


}

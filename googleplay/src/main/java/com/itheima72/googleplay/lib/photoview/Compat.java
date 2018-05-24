package com.itheima72.googleplay.lib.photoview;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

public class Compat {
	
	private static final int SIXTY_FPS_INTERVAL = 1000 / 60;
	
	public static void postOnAnimation(View view, Runnable runnable) {
		if (VERSION.SDK_INT >= 16) {
//			SDK16.postOnAnimation(view, runnable);
			view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
		} else {
			view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
		}
	}

}

package com.hbb.coder.citychoose.location;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.hbb.coder.citychoose.CityPickDialogFragment;
import com.hbb.coder.citychoose.utils.BdLocationUtil;

/**
 * Created by Administrator on 2018/5/26.
 */

public class LocateCityService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public LocateCityService() {
        super("LocateCityService");
    }



    public static void startUploadImg(Context context,String action)
    {
        Intent intent = new Intent(context, LocateCityService.class);
        intent.setAction(action);

        context.startService(intent);
    }


        @Override
        protected void onHandleIntent(@Nullable final Intent intent) {

        if (intent != null) {



                BdLocationUtil.getInstance().requestLocation(new BdLocationUtil.MyLocationListener() {
                    @Override
                    public void myLocation(BDLocation location) {
                        if (location == null) {
                            return;
                        }
                        if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                            String mCounty = location.getCountry();        //获取国家
                            String mProvince = location.getProvince();     //获取省份
                            String mCity = location.getCity();             //获取城市
                            String mDistrict = location.getDistrict();     //获取区
                            Log.i("==requestLocation===", "myLocation: " + mCounty + "="
                                    + mProvince + "=" + mCity + "=" + mDistrict);
                            Intent intents = new Intent();
                            if (CityPickDialogFragment.CITY_REQUEST_ACTION == intent.getAction()) {
                                intents.setAction(CityPickDialogFragment.CITY_REQUEST_ACTION);
                            }else if(CityPickDialogFragment.MAIN_REQUEST_ACTION== intent.getAction()){
                                intents.setAction(CityPickDialogFragment.MAIN_REQUEST_ACTION);
                            }

                            intents.putExtra(Intent.EXTRA_TEXT, mCity);
                            sendBroadcast(intents);
                        }
                    }
                },getApplicationContext());

            }


    }
}

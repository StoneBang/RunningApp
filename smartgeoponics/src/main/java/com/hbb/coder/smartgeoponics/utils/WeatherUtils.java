package com.hbb.coder.smartgeoponics.utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/30.
 */

public class WeatherUtils {

    public static int [] all=new int[]{100,101,102,103,104,
            200,201,202,203,204,205,206,207,208,209,210,211,212,213,
            300,301,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,
            399,400,401,402,403,404,405,406,407,408,409,410,499,
            500,501,502,503,504,505,506,507,508,509,510,511,512,513,514,515,
            900,901,999};

    public static int [] sun=new int[]{100};
    public static int [] cloudy=new int[]{101,102,103,104,};
    public static int [] thunder=new int[]{300,301,302,303,304};
    public static int [] rain=new int[]{305,306,307,308,309,310,311,312,313,314,315,316,317,318, 399};
    public static int [] snow=new int[]{400,401,402,403,404,405,406,407,408,409,410,499};




    public  static  int weather_sun=1;
    public  static  int weather_cloudy=2;
    public  static  int weather_thunder=3;
    public  static  int weather_rain=4;
    public  static  int weather_snow=5;


    public  static ArrayList<String> getSun(){

        ArrayList<String> weatherCode = new ArrayList<>();

        for (int i=0;i<sun.length;i++) {

            weatherCode.add(sun[i] + "");

        }
       return weatherCode;
    }

    public  static ArrayList<String> getCloudy(){

        ArrayList<String> weatherCode = new ArrayList<>();

        for (int i=0;i<cloudy.length;i++) {

            weatherCode.add(cloudy[i] + "");

        }
        return weatherCode;
    }
    public  static ArrayList<String> getThunder(){

        ArrayList<String> weatherCode = new ArrayList<>();

        for (int i=0;i<thunder.length;i++) {

            weatherCode.add(thunder[i] + "");

        }
        return weatherCode;
    }
    public  static ArrayList<String> getRain(){

        ArrayList<String> weatherCode = new ArrayList<>();

        for (int i=0;i<rain.length;i++) {

            weatherCode.add(rain[i] + "");

        }
        return weatherCode;
    }
    public  static ArrayList<String> getSnow(){

        ArrayList<String> weatherCode = new ArrayList<>();

        for (int i=0;i<snow.length;i++) {

            weatherCode.add(snow[i] + "");

        }
        return weatherCode;
    }




    {



    }
    public static int getWeatherType(String weatherCode){
        ArrayList<String> sun = getSun();
        ArrayList<String> cloudy = getCloudy();
        ArrayList<String> thunder = getThunder();
        ArrayList<String> rain = getRain();
        ArrayList<String> snow = getSnow();
        if(sun.contains(weatherCode)){
            return weather_sun;
        }

        if(cloudy.contains(weatherCode)){
            return weather_cloudy;
        }

        if(thunder.contains(weatherCode)){
            return weather_thunder;
        }

        if(rain.contains(weatherCode)){
            return weather_rain;
        }

        if(snow.contains(weatherCode)){
            return weather_snow;
        }

        return -1;
    }
}

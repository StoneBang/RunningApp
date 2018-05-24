package com.hbb.coder.citychoose.database;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CityDbSchema {

    public static final class CityTable{

        public static final String  TABLENAME="cities";

        public static final  class Cols{
            public static final String ID="id";
            public static final String NAME="c_name";
            public static final String PINYIN="c_pinyin";
            public static final String CODE="c_code";
            public static final String PROVINCE="c_province";
        }

    }




}

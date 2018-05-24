package com.hbb.coder.citychoose.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CityBaseHelper extends SQLiteOpenHelper {


    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "china.db";

    public CityBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//
//        db.execSQL(" create TABLE " +
//                CityDbSchema.CrimeTable.NAME +"(" +
//                "_id  integer primary key  autoincrement ," +
//                CityDbSchema.CrimeTable.Cols.ID +","+
//                CityDbSchema.CrimeTable.Cols.NAME +","+
//                CityDbSchema.CrimeTable.Cols.PINYIN +
//                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

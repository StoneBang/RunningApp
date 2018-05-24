package com.hbb.coder.smartgeoponics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hongbang on 2018/3/18.
 */

public class CityLabel {

    private static CityLabel sCrimeLabel;
    private static final String DB_NAME = "china.db";
    private static final String ASSETS_NAME = "china.db";
    private static final int BUFFER_SIZE = 1024;
    private String DB_PATH;
    private Context mContext;

    private SQLiteDatabase mDatabase;

    public List<CityBean> getCityList() {

        List<CityBean> citys = new ArrayList<>();

        CityCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                citys.add(cursor.getCity());

                cursor.moveToNext();

            }

        } catch (Exception exception) {


        } finally {
            cursor.close();
            CityComparator comparator = new CityComparator();
            Collections.sort(citys, comparator);
        }

        return citys;
    }

    private CityLabel(Context context) {

        mContext = context.getApplicationContext();

        //mDatabase = new CityBaseHelper(mContext).getWritableDatabase();

        mDatabase = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);

        DB_PATH = File.separator
                + "data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator
                + context.getPackageName()
                + File.separator
                + "databases"
                + File.separator;

    }


    public void copyDBFile() {
        File dir = new File(DB_PATH);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(DB_PATH + DB_NAME);

        if (!dbFile.exists()) {

            InputStream is;OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();os.close();is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CityLabel getCrimeLabel(Context context) {
        if (sCrimeLabel == null) {

            sCrimeLabel = new CityLabel(context);
        }
        return sCrimeLabel;
    }


    public CityBean getCrime(String cityId) {

        CityCursorWrapper cursor = queryCrimes(
                CityDbSchema.CityTable.Cols.ID + " = ? ", new String[]{cityId});

        try {
            if (cursor.getCount() == 0) {

                return null;

            }
            cursor.moveToFirst();

            return cursor.getCity();

        } catch (Exception exception) {

        } finally {
            cursor.close();
        }

        return null;

    }


    public void addCrime(CityBean cityBean) {
        ContentValues contentValues = getContentValues(cityBean);
        mDatabase.insert(CityDbSchema.CityTable.NAME, null, contentValues);
    }


    public void updateCrime(CityBean cityBean) {

        String id = cityBean.getId();

        ContentValues contentValues = getContentValues(cityBean);

        mDatabase.update(CityDbSchema.CityTable.NAME,
                contentValues,
                CityDbSchema.CityTable.Cols.ID + "=?",
                new String[]{id});

    }


    public CityCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {


        Cursor query = mDatabase.query(CityDbSchema.CityTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CityCursorWrapper(query);

    }

    private static ContentValues getContentValues(CityBean crime) {

        ContentValues values = new ContentValues();
        values.put(CityDbSchema.CityTable.Cols.ID, crime.getId());
        values.put(CityDbSchema.CityTable.Cols.NAME, crime.getName());
        values.put(CityDbSchema.CityTable.Cols.PINYIN, crime.getPinyin());
        return values;

    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<CityBean> {
        @Override
        public int compare(CityBean lhs, CityBean rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }

}

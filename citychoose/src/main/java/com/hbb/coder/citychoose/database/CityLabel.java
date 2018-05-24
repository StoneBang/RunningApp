package com.hbb.coder.citychoose.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.hbb.coder.citychoose.bean.City;

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


    /**
     * 获得全部城市
     * @return
     */
    public List<City> getAllCityList() {

        List<City> citys = new ArrayList<>();

        CityCursorWrapper cursor = queryCrimes(null, null);

        extractDate(citys, cursor);

        return citys;
    }


    /**
     * 根据指定城市模糊查询城市
     * @param cityName
     * @return
     */
    public List<City> getSearchCityList(String cityName) {

        List<City> citys = new ArrayList<>();

        CityCursorWrapper cursor = searchCity(cityName);

        extractDate(citys, cursor);

        return citys;
    }


    /**
     * 从游标遍历获得城市(排序)
     * @param citys
     * @param cursor
     */
    private void extractDate(List<City> citys, CityCursorWrapper cursor) {
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
    }


    private CityLabel(Context context) {

        mContext = context.getApplicationContext();

        DB_PATH = File.separator
                + "data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator
                + context.getPackageName()
                + File.separator
                + "databases"
                + File.separator;

        copyDBFile();
        //mDatabase = new CityBaseHelper(mContext).getWritableDatabase();

        mDatabase = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);



    }


    /**
     * 拷贝db文件到手机存储(首次会)
     */
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


    /**
     * 根据城市id获取城市实例
     * @param cityId
     * @return
     */
    public City getCrime(String cityId) {

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


    /**
     * 增加实例
     * @param cityBean
     */
    public void addCrime(CityBean cityBean) {
        ContentValues contentValues = getContentValues(cityBean);
        mDatabase.insert(CityDbSchema.CityTable.TABLENAME, null, contentValues);
    }

    /**
     * 更新实例
     * @param cityBean
     */
    public void updateCrime(CityBean cityBean) {

        String id = cityBean.getId();

        ContentValues contentValues = getContentValues(cityBean);

        mDatabase.update(CityDbSchema.CityTable.TABLENAME,
                contentValues,
                CityDbSchema.CityTable.Cols.ID + "=?",
                new String[]{id});

    }


    /**
     * 查询指定城市
     * @param cityName
     * @return
     */
    public  CityCursorWrapper searchCity(String cityName){
        String sql = "select * from " + CityDbSchema.CityTable.TABLENAME + " where "
                + CityDbSchema.CityTable.Cols.NAME + " like ? " + "or "
                + CityDbSchema.CityTable.Cols.PINYIN + " like ? ";
        Cursor query = mDatabase.rawQuery(sql, new String[]{"%"+cityName+"%", cityName+"%"});
//        Cursor query = mDatabase.query(
//                CityDbSchema.CityTable.TABLENAME,
//                new String[]{CityDbSchema.CityTable.Cols.NAME},
//                CityDbSchema.CityTable.Cols.NAME + " LIKE ? ",
//                new String[]{"%"+cityName+"%"},
//                null, null, null
//        );

        return new CityCursorWrapper(query);

    }


    /**
     * 查询全部城市
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public CityCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor query = mDatabase.query(CityDbSchema.CityTable.TABLENAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CityCursorWrapper(query);

    }


    /**
     * 封装存储单元实例
     * @param crime
     * @return
     */
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
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }

}

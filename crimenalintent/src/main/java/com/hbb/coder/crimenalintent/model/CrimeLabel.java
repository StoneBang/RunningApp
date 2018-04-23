package com.hbb.coder.crimenalintent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hbb.coder.crimenalintent.database.CrimeBaseHelper;
import com.hbb.coder.crimenalintent.database.CrimeCursorWrapper;
import com.hbb.coder.crimenalintent.database.CrimeDbSchema.CrimeTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by hongbang on 2018/3/18.
 */

public class CrimeLabel {

    private static CrimeLabel sCrimeLabel;

//    private List<Crime> mCrimeList;

    private Context mContext;

    private SQLiteDatabase mDatabase;

    public List<Crime> getCrimeList() {

        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                crimes.add(cursor.getCrime());

                cursor.moveToNext();

            }

        } catch (Exception exception) {


        } finally {
            cursor.close();
        }

        return crimes;
    }
//
//    public void setCrimeList(List<Crime> crimeList) {
//        mCrimeList = crimeList;
//    }

    private CrimeLabel(Context context) {

        mContext = context.getApplicationContext();

        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();


//        mCrimeList = new ArrayList<>();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar instance = Calendar.getInstance();
//
//        instance.setTime(new Date());

//        for (int i=0;i<100;i++){
//            Crime crime = new Crime();
//            crime.setSolve(i%2==0);
//            crime.setTitle("Crime"+i);
//            instance.add(Calendar.DAY_OF_YEAR,i%12);
//            crime.setDate(instance.getTime());
//            mCrimeList.add(crime);
//        }


    }

    public static CrimeLabel getCrimeLabel(Context context) {
        if (sCrimeLabel == null) {

            sCrimeLabel = new CrimeLabel(context);
        }
        return sCrimeLabel;
    }

    public Crime getCrime(UUID uuid) {

//        for (int i = 0; i < mCrimeList.size(); i++) {
//
//            Crime crime = mCrimeList.get(i);
//
//            if (uuid.equals(crime.getUUID())) {
//
//                return crime;
//
//            }
//        }

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID+" = ? ", new String[]{uuid.toString()});

        try {

            if (cursor.getCount() == 0) {

                return null;

            }
            cursor.moveToFirst();

            return cursor.getCrime();

        } catch (Exception exception) {

        } finally {
            cursor.close();
        }

        return null;

    }

    public void addCrime(Crime crime) {
//        crime.setDate(new Date());
//        mCrimeList.add(crime);

        ContentValues contentValues = getContentValues(crime);

        mDatabase.insert(CrimeTable.NAME, null, contentValues);
    }


    public void updateCrime(Crime crime) {

        String uuidString = crime.getUUID().toString();

        ContentValues contentValues = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME,
                contentValues,
                CrimeTable.Cols.UUID + "=?",
                new String[]{uuidString});

    }


    public CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {


        Cursor query = mDatabase.query(CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeCursorWrapper(query);

    }

    private static ContentValues getContentValues(Crime crime) {

        ContentValues values = new ContentValues();


        values.put(CrimeTable.Cols.UUID, crime.getUUID().toString());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolve() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;

    }

}

package com.hbb.coder.crimenalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.media.MediaDataSource;

import com.hbb.coder.crimenalintent.model.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CrimeCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Crime getCrime(){

        String uuidStr = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String titleStr = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        String suspectStr = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT));
        long dateStr = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
        int solvedStr = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidStr));

        crime.setTitle(titleStr);

        crime.setDate(new Date(dateStr));

        crime.setSolve(solvedStr!=0);

        crime.setSuspect(suspectStr);

        return crime;
    }




}

package com.hbb.coder.smartgeoponics.database;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Administrator on 2018/3/23.
 */

public class CityCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CityCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public CityBean getCity() {

        String id = getString(getColumnIndex(CityDbSchema.CityTable.Cols.ID));
        String name = getString(getColumnIndex(CityDbSchema.CityTable.Cols.NAME));
        String pinyin = getString(getColumnIndex(CityDbSchema.CityTable.Cols.PINYIN));

        CityBean city = new CityBean();
        city.setId(id);
        city.setName(name);
        city.setPinyin(pinyin);


        return city;
    }


}

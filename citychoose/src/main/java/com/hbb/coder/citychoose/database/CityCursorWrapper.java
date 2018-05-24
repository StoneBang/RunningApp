package com.hbb.coder.citychoose.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hbb.coder.citychoose.bean.City;

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


    public City getCity() {

        String id = getString(getColumnIndex(CityDbSchema.CityTable.Cols.ID));
        String name = getString(getColumnIndex(CityDbSchema.CityTable.Cols.NAME));
        String pinyin = getString(getColumnIndex(CityDbSchema.CityTable.Cols.PINYIN));
        String code = getString(getColumnIndex(CityDbSchema.CityTable.Cols.CODE));
        String province = getString(getColumnIndex(CityDbSchema.CityTable.Cols.PROVINCE));

        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setPinyin(pinyin);
        city.setProvince(province);
        city.setCode(code);

        return city;
    }


}

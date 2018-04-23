package com.hbb.coder.crimenalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by hongbang on 2018/3/18.
 */

public class Crime {

    private UUID mUUID;
    private String mTitle;
    private String mSuspect;

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    private Date mDate;
    private boolean mSolve;

    public Crime() {
        mUUID=UUID.randomUUID();
        mDate=new Date();
    }

    public Crime(UUID uuid) {
        mUUID=uuid;
        mDate=new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolve() {
        return mSolve;
    }

    public void setSolve(boolean solve) {
        mSolve = solve;
    }
}

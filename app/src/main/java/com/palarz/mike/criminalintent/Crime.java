package com.palarz.mike.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by QNP684 on 9/2/2016.
 */
public class Crime {

    private UUID mID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        //Generate unique ID
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(Date date) {
        mDate = date;
    }


    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}

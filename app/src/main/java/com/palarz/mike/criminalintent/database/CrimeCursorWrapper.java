package com.palarz.mike.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.palarz.mike.criminalintent.Crime;
import com.palarz.mike.criminalintent.database.CrimeDBSchema.CrimeTable;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by QNP684 on 9/28/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper (Cursor cursor){
        super(cursor);
    }

    public Crime getCrime(){
        String UUIDString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(UUIDString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;
    }
}

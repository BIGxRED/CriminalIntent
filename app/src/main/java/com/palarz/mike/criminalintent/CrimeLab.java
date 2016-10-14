package com.palarz.mike.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.palarz.mike.criminalintent.database.CrimeBaseHelper;
import com.palarz.mike.criminalintent.database.CrimeCursorWrapper;
import com.palarz.mike.criminalintent.database.CrimeDBSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by QNP684 on 9/3/2016.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID ID){
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String [] {ID.toString()}
        );

        try{
            if (cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Crime crime){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFilesDir == null)
            return null;

        return new File(externalFilesDir, crime.getPhotoFileName());
    }

    public void updateCrime(Crime crime){
        String UUIDString = crime.getID().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String [] {UUIDString});
    }

    public boolean deleteCrime(UUID ID){
//        CrimeCursorWrapper cursor = queryCrimes(
//                CrimeTable.Cols.UUID + " = ?",
//                new String [] {ID.toString()}
//        );
//
//        mDatabase.delete(CrimeTable.NAME, null, null);
//        cursor.close();
        mDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?",
                new String [] {ID.toString()});
        return false;
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getID().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String [] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,   //Columns input - null value selects all columns
                whereClause,
                whereArgs,
                null,   //groupBy input
                null,   //having input
                null    //orderBy input
        );
        return new CrimeCursorWrapper(cursor);
    }
}

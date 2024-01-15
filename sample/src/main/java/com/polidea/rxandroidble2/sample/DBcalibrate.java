package com.polidea.rxandroidble2.sample;

import android.content.ContentValues;
import android.content.Context;
import android.content.Entity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DBcalibrate extends  SQLiteOpenHelper {
    public static final String DBcali = "calibrating.db";
    public static final String Calibratio_Table_name = "calibrationtable";

    public DBcalibrate(Context context) {
        super(context, DBcali, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + Calibratio_Table_name + "(id INTEGER PRIMARY KEY,calibration DECIMAL(4,2))");
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Calibratio_Table_name);
        onCreate(db);
    }


    public boolean insert(int s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", s);
        contentValues.put("calibration", s1);
        //db.replace(CONTACTS_TABLE_NAME, null, contentValues);
        db.insert(Calibratio_Table_name, null, contentValues);
        return true;
    }


    public ArrayList getAllCotacts1() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        ArrayList<String> array_list1 = new ArrayList<String>();
        Cursor res = db1.rawQuery("select ( calibration ) AS cali from " + Calibratio_Table_name, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
//========================================================

            if (res.getColumnIndex("cali") != -1) {
                array_list1.add(res.getString(res.getColumnIndex("cali")));
            }

//=========================================================
            res.moveToNext();
        }
        return array_list1;
    }


    /*public ArrayList getdata(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance(); // This will give you the current time.
        // Removing the timestamp from current time to point to todays date
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -1); // Will subtract 3 days from today.
        Date adaybefore = calendar.getTime();
        calendar.add(Calendar.DATE, 0); // Will be your 3 days after today
        Date today = calendar.getTime();

        db.query("fullname", null, "YOUR_DATE_COLUMN >= ? AND YOUR_DATE_COLUMN <= ?", new String[] { adaybefore.getTime() + "", today.getTime() + "" }, null, null, null);
    }*/
    public boolean update(String s, String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+Calibratio_Table_name+" SET name = "+"'"+s+"', "+ "salary = "+"'"+s1+"'");
        return true;
    }
    public boolean delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+Calibratio_Table_name);
        return true;
    }
}
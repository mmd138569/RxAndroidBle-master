package com.polidea.rxandroidble2.sample.example4_characteristic;


import android.content.ContentValues;
import android.content.Context;
import android.content.Entity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final String CONTACTS_TABLE_NAME = "SalaryDetails";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE "+ CONTACTS_TABLE_NAME +"(id INTEGER PRIMARY KEY,salary DECIMAL(4,2),datetime string )");
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
        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_TABLE_NAME);
        onCreate(db);
    }
    public boolean insert(float s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", s);
        contentValues.put("salary", s1);

        OffsetDateTime offsetDT = OffsetDateTime.now();
        String s=offsetDT.toLocalDate()+" "+ String.valueOf(offsetDT.getHour())+":"+String.valueOf(offsetDT.getMinute())+":"+String.valueOf(offsetDT.getSecond());

        contentValues.put("datetime",s);
        //db.replace(CONTACTS_TABLE_NAME, null, contentValues);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }
    public ArrayList getAllCotacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "select (id ||' : ' || salary || ' : '|| datetime) AS fullname from "+CONTACTS_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
//========================================================

            if(res.getColumnIndex("fullname")!=-1) {
                array_list.add(res.getString(res.getColumnIndex("fullname")));
            }

//=========================================================
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList getAllCotacts1() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        ArrayList<String> array_list1 = new ArrayList<String>();
        Cursor res = db1.rawQuery( "select ( salary ) AS fullname1 from "+CONTACTS_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
//========================================================

            if(res.getColumnIndex("fullname1")!=-1) {
                array_list1.add(res.getString(res.getColumnIndex("fullname1")));
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
        db.execSQL("UPDATE "+CONTACTS_TABLE_NAME+" SET name = "+"'"+s+"', "+ "salary = "+"'"+s1+"'");
        return true;
    }
    public boolean delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+CONTACTS_TABLE_NAME);
        return true;
    }
}
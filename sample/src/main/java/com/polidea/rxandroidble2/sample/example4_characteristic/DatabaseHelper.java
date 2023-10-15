package com.polidea.rxandroidble2.sample.example4_characteristic;


import android.content.ContentValues;
import android.content.Context;
import android.content.Entity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final String CONTACTS_TABLE_NAME = "SalaryDetails";
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE "+ CONTACTS_TABLE_NAME +"(id INTEGER PRIMARY KEY, name text,salary DECIMAL(4,2),datetime default current_timestamp )");
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
    public boolean insert(/*String s,*/ String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("name", s);
        contentValues.put("salary", s1);
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
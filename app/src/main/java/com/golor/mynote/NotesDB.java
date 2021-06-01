package com.golor.mynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "notes";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    //    public static final String PATH = "path";
//    public static final String VIDEO = "video";
    public static final String CREATE_TIME = "createTime";

    public NotesDB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // callback function will be called when create database at first times;
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TITLE + " TEXT ,"
                + CONTENT + " TEXT NOT NULL ,"
                + CREATE_TIME + " TEXT NOT NULL "
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // callback function will be called when upgrade database;

    }
}

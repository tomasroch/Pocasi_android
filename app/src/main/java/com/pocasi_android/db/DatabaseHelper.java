package com.pocasi_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CITIES";

    // Table columns
    public static final String ID = "_id";
    public static final String CITY = "city";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String LAST_TEMP = "last_temp";
    public static final String LAST_UPDATED = "last_updated";


    // Database Information
    static final String DB_NAME = "POCASI.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CITY + " TEXT NOT NULL, " + LONGITUDE + " REAL NOT NULL, " + LATITUDE + " REAL NOT NULL, "
            + LAST_TEMP + " TEXT, " + LAST_UPDATED + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
package com.pocasi_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String cityName, Double longitude, Double latitude) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.CITY, cityName);
        contentValue.put(DatabaseHelper.LONGITUDE, longitude);
        contentValue.put(DatabaseHelper.LATITUDE, latitude);
        contentValue.put(DatabaseHelper.LAST_TEMP, "°");
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.ID, DatabaseHelper.CITY, DatabaseHelper.LONGITUDE, DatabaseHelper.LATITUDE, DatabaseHelper.LAST_TEMP, DatabaseHelper.LAST_UPDATED };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String temperature, String lastUpdated) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LAST_TEMP, temperature);
        contentValues.put(DatabaseHelper.LAST_UPDATED, lastUpdated);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + id, null);
        return i;
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + id, null);
    }

}

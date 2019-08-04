package com.example.androidassignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccountDatabase extends SQLiteOpenHelper {
    public AccountDatabase(@Nullable Context context) {
        super(context, DatabaseUtil.databaseName, null, DatabaseUtil.databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseUtil.AccountTable.tableName +
                " (" + DatabaseUtil.AccountTable.accountNum + " INTEGER PRIMARY KEY," +
                DatabaseUtil.AccountTable.nameColumn + " VARCHAR(255)," +
                DatabaseUtil.AccountTable.genderColumn + " VARCHAR(255)," +
                DatabaseUtil.AccountTable.birthdateColumn + " VARCHAR(255)," +
                DatabaseUtil.AccountTable.ageColumn + " INTEGER," +
                DatabaseUtil.AccountTable.countryColumn + " VARCHAR(255)," +
                DatabaseUtil.AccountTable.addressColumn + " VARCHAR(255)," +
                DatabaseUtil.AccountTable.photouriColumn + " VARCHAR(255))" );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseUtil.EmailTable.tableName +
                " (" + DatabaseUtil.EmailTable._ID + " INTEGER PRIMARY KEY," +
                DatabaseUtil.EmailTable.emailColumn + " VARCHAR(255))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

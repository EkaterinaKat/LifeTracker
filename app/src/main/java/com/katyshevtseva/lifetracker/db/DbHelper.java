package com.katyshevtseva.lifetracker.db;

import static com.katyshevtseva.lifetracker.db.DbConstants.ACTIVITY_ID;
import static com.katyshevtseva.lifetracker.db.DbConstants.BEGIN;
import static com.katyshevtseva.lifetracker.db.DbConstants.ID;
import static com.katyshevtseva.lifetracker.db.DbConstants.TITLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "lt.db";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + ActivityDao.NAME + "(" +
                ID + " INTEGER primary key autoincrement, " +
                TITLE + " TEXT )");

        database.execSQL("create table " + EntryDao.NAME + "(" +
                ID + " INTEGER primary key autoincrement, " +
                BEGIN + " TEXT, " +
                ACTIVITY_ID + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}


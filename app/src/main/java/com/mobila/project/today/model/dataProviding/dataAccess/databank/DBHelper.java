package com.mobila.project.today.model.dataProviding.dataAccess.databank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_FILE_NAME = "today.db";
    public static final int DB_VERSION = 1;
    private final String table;

    public DBHelper(@Nullable Context context, String table) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
        this.table = table;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SemesterTable.SQL_DELETE);
        onCreate(db);
    }
}

package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

public class ParentDataAccessImpl implements ParentDataAccess {
    private DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    public void openDbConnection(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.database = this.dbHelper.getWritableDatabase();
    }
}

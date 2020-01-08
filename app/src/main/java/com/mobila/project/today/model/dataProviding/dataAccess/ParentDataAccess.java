package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.sqlite.SQLiteDatabase;

interface ParentDataAccess {

    void openDbConnection(SQLiteDatabase database);
}

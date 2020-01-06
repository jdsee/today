package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.sqlite.SQLiteDatabase;

public interface ParentDataAccess {

    void openDbConnection(SQLiteDatabase database);
}

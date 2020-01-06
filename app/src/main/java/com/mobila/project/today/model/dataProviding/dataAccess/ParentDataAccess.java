package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

public interface ParentDataAccess {

    void openDbConnection(DBHelper dbHelper);

    void closeDbConnection();
}

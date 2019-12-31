package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.TaskTable;

import java.util.Date;

class TaskDataAccessImpl implements TaskDataAccess {
    private static TaskDataAccess instance;

    private SQLiteDatabase database;
    private final DBHelper dbHelper;

    private TaskDataAccessImpl() {
        this.dbHelper = new DBHelper(null);
    }

    TaskDataAccess getInstance(){
        if (instance == null)
            instance = new TaskDataAccessImpl();
        return instance;
    }

    //TODO possibility to change the related course?

    @Override
    public void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_DEADLINE, date.getTime());
        this.updateTaskInDB(task, values);
    }

    @Override
    public void setContent(Identifiable task, String content) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_CONTENT, content);
        this.updateTaskInDB(task, values);
    }

    private void updateTaskInDB(Identifiable task, ContentValues values){
        this.database.update(TaskTable.TABLE_NAME, values,
                TaskTable.COLUMN_ID + "=?s", new String[]{task.getID()});
    }
}

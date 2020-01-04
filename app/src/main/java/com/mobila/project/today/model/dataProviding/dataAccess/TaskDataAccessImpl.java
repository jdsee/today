package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import java.util.Date;

import android.content.ContentValues;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.TaskTable;

class TaskDataAccessImpl implements TaskDataAccess {
    private static TaskDataAccess instance;

    public static final String TAG = TaskDataAccessImpl.class.getName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private TaskDataAccessImpl() {
        this.dbHelper = new DBHelper(null);
    }

    static TaskDataAccess getInstance() {
        if (instance == null)
            instance = new TaskDataAccessImpl();
        return instance;
    }

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

    @Override
    public void open(Context context) {
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        this.database.close();
    }

    private void updateTaskInDB(Identifiable task, ContentValues values) {
        this.database.update(TaskTable.TABLE_NAME, values,
                TaskTable.COLUMN_ID + "=?", new String[]{task.getID()});
    }
}
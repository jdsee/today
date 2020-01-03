package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import java.util.Date;

public class TaskDataAccessImpl implements TaskDataAccess {
    public static final String TAG = TaskDataAccessImpl.class.getName();

    private static TaskDataAccess instance;

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    static TaskDataAccess getInstance() {
        if (instance == null) {
            instance = new TaskDataAccessImpl();
        }
        return instance;
    }

    @Override
    public Course getCourse(Identifiable task) throws DataKeyNotFoundException {
        Log.d(TAG, "Requesting Course of Task from DB");
        Cursor cursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                CourseTable.COLUMN_ID + " = '" + task.getID() + "'",
                null, null, null, null);
        Course course = new Course(cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_TITLE)));
        cursor.close();
        return course;
    }

    @Override
    public Date getDeadline(Identifiable task) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException {

    }

    @Override
    public String getContent(Identifiable task) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setContent(Identifiable task, String content) throws DataKeyNotFoundException {

    }

    @Override
    public void open(Context context) {
        this.dbHelper=new DBHelper(context);
        this.database=this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {

    }
}

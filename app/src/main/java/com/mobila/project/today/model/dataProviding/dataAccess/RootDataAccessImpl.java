package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.SampleDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class RootDataAccessImpl implements RootDataAccess {
    public static RootDataAccess instance;
    public static final String TAG = RootDataAccessImpl.class.getName();

    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private List<Semester> semesters;

    static RootDataAccess getInstance() {
        if (instance == null)
            instance = new RootDataAccessImpl();
        return instance;
    }

    public RootDataAccessImpl() {
        this(null);
    }

    RootDataAccessImpl(SQLiteDatabase database) {
        this.database = database;
        this.dbHelper = new DBHelper(null);
        this.semesters = null;
    }

    @Override
    public void open() {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(this.database, SemesterTable.TABLE_NAME);
    }

    @Override
    public List<Semester> getAllSemesters() {
        return SampleDataProvider.getExampleSemesters();
        /*if (this.semesters == null) {
            this.semesters = this.getAllSemestersFromDB();
        }
        return this.semesters;*/
    }

    private List<Semester> getAllSemestersFromDB() {
        Cursor cursor = this.database.query(SemesterTable.TABLE_NAME, SemesterTable.ALL_COLUMNS,
                null, null, null, null, SemesterTable.COLUMN_NR);
        List<Semester> semesters = new LinkedList<>();
        while (cursor.moveToNext()) {
            Semester semester = new Semester(
                    cursor.getString(cursor.getColumnIndex(SemesterTable.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SemesterTable.COLUMN_NR))
            );
            semesters.add(semester);
        }
        cursor.close();
        return semesters;
    }

    @Override
    public void addSemester(Semester semester) {
        ContentValues values = semester.toValues();
        this.database.insert(SemesterTable.TABLE_NAME, null, values);
        if (this.semesters != null)
            this.semesters.add(semester);
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public void removeEntityInstance(Identifiable instance) throws DataKeyNotFoundException {

    }
}

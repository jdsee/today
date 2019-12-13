package com.mobila.project.today.model.dataProviding.dataAccess.databank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobila.project.today.model.Semester;

import java.util.ArrayList;
import java.util.List;

public class SemesterDataSource {
    public static final String TAG = SemesterDataSource.class.getName();

    private Context context;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    public SemesterDataSource(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public void open() {
        this.database = this.dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Semester createSemester(Semester semester) {
        ContentValues values = semester.toValues();
        this.database.insert(SemesterTable.TABLE_NAME, null, values);
        return semester;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(this.database, SemesterTable.TABLE_NAME);
    }

    public List<Semester> getAllSemesters() {
        List<Semester> semesters = new ArrayList<>();
        Cursor cursor = this.database.query(SemesterTable.TABLE_NAME, SemesterTable.ALL_COLUMNS,
                null, null, null, null, SemesterTable.TABLE_NAME);
        while (cursor.moveToNext()) {
            Semester semester = new Semester(
                    cursor.getInt(cursor.getColumnIndex(SemesterTable.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SemesterTable.COLUMN_NR))
            );
            semesters.add(semester);
        }
        cursor.close();
        return semesters;
    }

    public void seedDB(List<Semester> semesters) {
        long numSemesters = this.getDataItemsCount();
        if (numSemesters != semesters.size()) {
            try {
                for (Semester semester : semesters) {
                    this.createSemester(semester);
                    Log.d(TAG, "Semester added to db: " + semester + System.lineSeparator());
                }
                Log.d(TAG, "Data inserted to db");
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        } else
            Log.d(TAG, "Data already inserted to db");
    }
}

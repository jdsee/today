package com.mobila.project.today.model.dataProviding.dataAccess.databank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mobila.project.today.model.Section;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_FILE_NAME = "today.db";
    private static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SemesterTable.SQL_CREATE);
        db.execSQL(CourseTable.SQL_CREATE);
        db.execSQL(TaskTable.SQL_CREATE);
        db.execSQL(SectionTable.SQL_CREATE);
        db.execSQL(LectureTable.SQL_CREATE);
        db.execSQL(NoteTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NoteTable.SQL_DELETE);
        db.execSQL(LectureTable.SQL_DELETE);
        db.execSQL(SectionTable.SQL_DELETE);
        db.execSQL(TaskTable.SQL_DELETE);
        db.execSQL(CourseTable.SQL_DELETE);
        db.execSQL(SemesterTable.SQL_DELETE);
        onCreate(db);
    }
}

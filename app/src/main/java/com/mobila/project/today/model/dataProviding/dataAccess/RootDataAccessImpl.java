package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.TaskTable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class RootDataAccessImpl extends ParentDataAccessImpl implements RootDataAccess {
    private static RootDataAccess instance;
    public static final String TAG = RootDataAccessImpl.class.getName();

    private List<Semester> semesters;

    static RootDataAccess getInstance() {
        if (instance == null)
            instance = new RootDataAccessImpl();
        return instance;
    }

    private RootDataAccessImpl() {
        this(null);
    }

    RootDataAccessImpl(SQLiteDatabase database) {
        this.database = database;
        this.semesters = null;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(this.database, SemesterTable.TABLE_NAME);
    }

    @Override
    public List<Semester> getAllSemesters() {
        if (this.semesters == null) {
            this.semesters = this.getAllSemestersFromDB();
        }
        return this.semesters;
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
    public void removeSemester(Semester semester) {
        this.semesters.remove(semester);
        this.database.delete(SemesterTable.TABLE_NAME,
                SemesterTable.COLUMN_ID + " = '" + semester.getID() + "'", null);
    }

    @Override
    public List<Task> getAllTasks() {
        Cursor cursor = this.database.query(TaskTable.TABLE_NAME, TaskTable.ALL_COLUMNS,
                null, null, null, null,
                TaskTable.COLUMN_RELATED_TO);
        List<Task> tasks = new LinkedList<>();
        while (cursor.moveToNext()) {
            Task task = new Task(
                    cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_CONTENT)),
                    new Date(cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_DEADLINE)))
            );
            tasks.add(task);
        }
        cursor.close();

        // for now the tasks won't be cached in an IdentityMapper
        // one solution to make that possible could be an added getCourse() method in Task

        return tasks;
    }
}
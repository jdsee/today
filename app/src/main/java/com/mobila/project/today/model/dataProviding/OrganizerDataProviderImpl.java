package com.mobila.project.today.model.dataProviding;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.dataProviding.dataAccess.AttachmentDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SectionDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

class OrganizerDataProviderImpl implements OrganizerDataProvider {
    private static OrganizerDataProviderImpl instance;

    private SQLiteDatabase database;

    static OrganizerDataProviderImpl getInstance() {
        if (instance == null)
            instance = new OrganizerDataProviderImpl();
        return instance;
    }

    private OrganizerDataProviderImpl() {
    }

    @Override
    public void openDbConnection(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    @Override
    public void closeDbConnection() {
        if (this.database != null)
            this.database.close();
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    @Override
    public RootDataAccess getRootDataAccess() {
        return RootDataAccess.getInstance();
    }

    @Override
    public SemesterDataAccess getSemesterDataAccess() {
        return SemesterDataAccess.getInstance();
    }

    @Override
    public CourseDataAccess getCourseDataAccess() {
        return CourseDataAccess.getInstance();
    }

    @Override
    public SectionDataAccess getSectionDataAccess() {
        return SectionDataAccess.getInstance();
    }

    @Override
    public LectureDataAccess getLectureDataAccess() {
        return LectureDataAccess.getInstance();
    }

    @Override
    public NoteDataAccess getNoteDataAccess() {
        return NoteDataAccess.getInstance();
    }

    @Override
    public AttachmentDataAccess getAttachmentDataAccess() {
        return AttachmentDataAccess.getInstance();
    }

    @Override
    public TaskDataAccess getTaskDataAccess() {
        return TaskDataAccess.getInstance();
    }
}

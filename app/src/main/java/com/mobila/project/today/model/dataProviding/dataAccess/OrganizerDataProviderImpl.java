package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

class OrganizerDataProviderImpl implements OrganizerDataProvider {
    private static OrganizerDataProviderImpl instance;
    private final RootDataAccess rootAccess;
    private final SemesterDataAccess semesterAccess;
    private final CourseDataAccess courseAccess;
    private final TaskDataAccess taskAccess;
    private final SectionDataAccess sectionAccess;
    private final NoteDataAccess noteAccess;

    private DBHelper dbHelper;

    private final LectureDataAccess lectureAccess;

    static OrganizerDataProviderImpl getInstance() {
        if (instance == null)
            instance = new OrganizerDataProviderImpl();
        return instance;
    }

    private OrganizerDataProviderImpl() {
        this.rootAccess = RootDataAccess.getInstance();
        this.semesterAccess = SemesterDataAccess.getInstance();
        this.courseAccess = CourseDataAccess.getInstance();
        this.taskAccess = TaskDataAccess.getInstance();
        this.sectionAccess = SectionDataAccess.getInstance();
        this.lectureAccess = LectureDataAccess.getInstance();
        this.noteAccess = NoteDataAccess.getInstance();
    }

    @Override
    public void openDbConnection(Context context) {
        this.dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        this.rootAccess.openDbConnection(database);
        this.semesterAccess.openDbConnection(database);
        this.courseAccess.openDbConnection(database);
        this.taskAccess.openDbConnection(database);
        this.sectionAccess.openDbConnection(database);
        this.lectureAccess.openDbConnection(database);
        this.noteAccess.openDbConnection(database);
    }

    @Override
    public void closeDbConnection() {
        if (this.dbHelper != null)
            this.dbHelper.close();
    }

    @Override
    public RootDataAccess getRootDataAccess() {
        return this.rootAccess;
    }

    @Override
    public SemesterDataAccess getSemesterDataAccess() {
        return this.semesterAccess;
    }

    @Override
    public CourseDataAccess getCourseDataAccess() {
        return this.courseAccess;
    }

    @Override
    public TaskDataAccess getTaskDataAccess() {
        return this.taskAccess;
    }

    @Override
    public SectionDataAccess getSectionDataAccess() {
        return this.sectionAccess;
    }

    @Override
    public LectureDataAccess getLectureDataAccess() {
        return this.lectureAccess;
    }

    @Override
    public NoteDataAccess getNoteDataAccess() {
        return this.noteAccess;
    }

    @Override
    public AttachmentDataAccess getAttachmentDataAccess() {
        return AttachmentDataAccess.getInstance();
    }
}

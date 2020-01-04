package com.mobila.project.today.model.dataProviding;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.activities.MainActivity;
import com.mobila.project.today.activities.TodayActivity;
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
    private static final OrganizerDataProviderImpl instance = new OrganizerDataProviderImpl();

    //TODO set access instances final
    private RootDataAccess rootAccess;
    private SemesterDataAccess semesterAccess;
    private CourseDataAccess courseAccess;
    private SectionDataAccess sectionAccess;
    private LectureDataAccess lectureAccess;
    private TaskDataAccess taskAccess;
    private NoteDataAccess noteAccess;
    private final AttachmentDataAccess attachmentAccess;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    static OrganizerDataProviderImpl getInstance() {
        return instance;
    }

    private OrganizerDataProviderImpl() {
        this.attachmentAccess = AttachmentDataAccess.getInstance();
        this.rootAccess = RootDataAccess.getInstance();
        this.semesterAccess = SemesterDataAccess.getInstance();
        this.courseAccess = CourseDataAccess.getInstance();
        this.sectionAccess = SectionDataAccess.getInstance();
    }

    @Override
    public void openDbConnection(Context context) {
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
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
        return this.attachmentAccess;
    }

    @Override
    public TaskDataAccess getTaskDataAccess() {
        return taskAccess;
    }
}

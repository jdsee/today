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

public interface OrganizerDataProvider {
    static OrganizerDataProvider getInstance() {
        return OrganizerDataProviderImpl.getInstance();
    }

    void openDbConnection(Context context);

    void closeDbConnection();

    SQLiteDatabase getDatabase();

    RootDataAccess getRootDataAccess();

    SemesterDataAccess getSemesterDataAccess();

    CourseDataAccess getCourseDataAccess();

    SectionDataAccess getSectionDataAccess();

    LectureDataAccess getLectureDataAccess();

    NoteDataAccess getNoteDataAccess();

    AttachmentDataAccess getAttachmentDataAccess();

    TaskDataAccess getTaskDataAccess();
}

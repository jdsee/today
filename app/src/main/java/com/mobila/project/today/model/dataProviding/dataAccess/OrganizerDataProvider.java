package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;

public interface OrganizerDataProvider {
    static OrganizerDataProvider getInstance() {
        return OrganizerDataProviderImpl.getInstance();
    }

    void openDbConnection(Context context);

    void closeDbConnection();

    RootDataAccess getRootDataAccess();

    SemesterDataAccess getSemesterDataAccess();

    CourseDataAccess getCourseDataAccess();

    SectionDataAccess getSectionDataAccess();

    LectureDataAccess getLectureDataAccess();

    NoteDataAccess getNoteDataAccess();

    AttachmentDataAccess getAttachmentDataAccess();

    TaskDataAccess getTaskDataAccess();
}

package com.mobila.project.today.dataProviding;

import com.mobila.project.today.dataProviding.dataAccess.AttachmentDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SectionDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;

public interface OrganizerDataProvider {
    static OrganizerDataProvider getInstance() {
        return OrganizerDataProviderImpl.getInstance();
    }

    RootDataAccess getRootDataAccess();

    SemesterDataAccess getSemesterDataAccess();

    CourseDataAccess getCourseDataAccess();

    SectionDataAccess getSectionDataAccess();

    LectureDataAccess getLectureDataAccess();

    NoteDataAccess getNoteDataAccess();

    AttachmentDataAccess getAttachmentDataAccess();

    TaskDataAccess getTaskDataAccess();
}

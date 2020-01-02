package today.model.dataProviding;

import today.model.dataProviding.dataAccess.AttachmentDataAccess;
import today.model.dataProviding.dataAccess.CourseDataAccess;
import today.model.dataProviding.dataAccess.LectureDataAccess;
import today.model.dataProviding.dataAccess.NoteDataAccess;
import today.model.dataProviding.dataAccess.RootDataAccess;
import today.model.dataProviding.dataAccess.SectionDataAccess;
import today.model.dataProviding.dataAccess.SemesterDataAccess;
import today.model.dataProviding.dataAccess.TaskDataAccess;

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

package com.mobila.project.today.dataProviding;

import com.mobila.project.today.dataProviding.dataAccess.AttachmentDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SectionDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Definable;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

class OrganizerDataProviderImpl implements OrganizerDataProvider {
    private static final OrganizerDataProviderImpl instance = new OrganizerDataProviderImpl();

    private RootDataAccess rootAccess;
    private SemesterDataAccess semesterAccess;
    private CourseDataAccess courseAccess;
    private SectionDataAccess sectionAccess;
    private LectureDataAccess lectureAccess;
    private TaskDataAccess taskAccess;
    private NoteDataAccess noteAccess;
    private AttachmentDataAccess attachmentAccess;

    static OrganizerDataProviderImpl getInstance() {
        return instance;
    }

    private OrganizerDataProviderImpl() {
    }

    @Override
    public List<Semester> getAllSemesters() {
        return this.rootAccess.getAllSemesters();
    }

    @Override
    public List<Task> getAllTasks() {
        return this.rootAccess.getAllTasks();
    }

    @Override
    public void updateEntityInstance(Identifiable instance) throws DataKeyNotFoundException {

    }

    @Override
    public void removeEntityInstance(Identifiable instance) throws DataKeyNotFoundException {
        this.rootAccess.removeEntityInstance(instance);
    }

    @Override
    public <T extends Definable, R extends Identifiable> R getParent(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException {
        switch (context.getTypeIdentifier()) {
            case Course.COURSE_TYPE_IDENTIFIER:
                return null;
        }
        return null;
    }

    @Override
    public <T extends Definable, R extends Identifiable> List<R> getChildren(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException {
            Identifiable contextID = (Identifiable) context;
            switch (context.getTypeIdentifier()) {
                case Semester.SEMESTER_TYPE_IDENTIFIER:
                    return (List<R>) semesterAccess.getCourses(contextID);
                case Course.COURSE_TYPE_IDENTIFIER:
                    return (List<R>) courseAccess.getSections(contextID);

            }
        return null;
    }

    @Override
    public <T extends Definable, R extends Identifiable> void addChild(T context, R child) {

    }

    @Override
    public <T extends Definable, R extends Identifiable> R getContent(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException {
        return null;
    }

    @Override
    public <T extends Definable, R extends Identifiable> R setContent(T context, R content)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException {
        return null;
    }
}

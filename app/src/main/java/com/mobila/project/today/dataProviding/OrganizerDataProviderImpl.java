package com.mobila.project.today.dataProviding;

import com.mobila.project.today.dataProviding.dataAccess.AttachmentDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SectionDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;

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

    static OrganizerDataProviderImpl getInstance() {
        return instance;
    }

    private OrganizerDataProviderImpl() {
        attachmentAccess = AttachmentDataAccess.createInstance();
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

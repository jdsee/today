package today.model.dataProviding;

import today.model.dataProviding.dataAccess.AttachmentDataAccess;
import today.model.dataProviding.dataAccess.CourseDataAccess;
import today.model.dataProviding.dataAccess.LectureDataAccess;
import today.model.dataProviding.dataAccess.NoteDataAccess;
import today.model.dataProviding.dataAccess.RootDataAccess;
import today.model.dataProviding.dataAccess.SectionDataAccess;
import today.model.dataProviding.dataAccess.SemesterDataAccess;
import today.model.dataProviding.dataAccess.TaskDataAccess;

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
        this.attachmentAccess = AttachmentDataAccess.getInstance();
        this.rootAccess = RootDataAccess.getInstance();

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

package com.mobila.project.today.dataProviding;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Definable;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface OrganizerDataProvider {
    static final String UNDEFINED_CONTEXT_ERROR = "the requested operation is not defined for this context";

    static OrganizerDataProvider getInstance() {
        return OrganizerDataProviderImpl.getInstance();
    }

    List<Semester> getAllSemesters();

    List<Task> getAllTasks();

    void removeEntityInstance(Identifiable instance) throws DataKeyNotFoundException;

    void updateEntityInstance(Identifiable instance) throws DataKeyNotFoundException;

    <T extends Definable, R extends Identifiable> R getParent(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException;

    <T extends Definable, R extends Identifiable> List<R> getChildren(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException;

    <T extends Definable, R extends Identifiable> void addChild(T context, R child);

    <T extends Definable, R extends Identifiable> R getContent(T context)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException;

    <T extends Definable, R extends Identifiable> R setContent(T context, R content)
            throws OperationNotSupportedInActualContextException, DataKeyNotFoundException;
    

    /*
    <T extends Definable> String getTitle(T context);

    <T extends Definable> void setTitle(T context, String title);

    <T extends Definable> Date getDate(T context);

    <T extends Definable> void setDate(T context, Date date);

    String getLectureRoom();

    void setLectureRoom(String room);

    String getLecturer();

    void setLecturer(String lecturer);

    List<NoteReference> getNoteReferences();

    void addNoteReference(Note ref, int row);

    //--------------

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    void addNote(Identifiable lecture, Note note) throws DataKeyNotFoundException;

    List<Attachment> getAttachments(Identifiable note) throws DataKeyNotFoundException;

    void addAttachment(Identifiable note, Attachment attachment) throws DataKeyNotFoundException;
     */
}

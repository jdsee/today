package com.mobila.project.today.dataAccess;

import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Definable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.NoteReference;
import com.mobila.project.today.model.Task;

import java.util.Date;
import java.util.List;

public interface OrganizerDataProvider {

    <T extends Definable, R extends Identifiable> List<R> getChildren(T context);

    //Semester getSemester(Identifiable course) throws DataKeyNotFoundException;
    //Course getCourse(Identifiable section) throws DataKeyNotFoundException;
    //Section getSection(Identifiable lecture) throws DataKeyNotFoundException;

    List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException;

    void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException;

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    void addNote(Identifiable lecture, Note note) throws DataKeyNotFoundException;

    List<Attachment> getAttachments(Identifiable note) throws DataKeyNotFoundException;

    void addAttachment(Identifiable note, Attachment attachment) throws DataKeyNotFoundException;

    List<Task> getAllTasks();


    void removeEntity(Identifiable entity) throws DataKeyNotFoundException;

    <T extends Identifiable, R extends Identifiable> R getParent(T context);

    <T extends Identifiable> String getTitle(T context);

    <T extends Identifiable> void setTitle(T context, String title);

    <T extends Identifiable, R> R getContent(T context);

    <T extends Identifiable> Date getDate(T context);

    <T extends Identifiable> void setDate(T context, Date date);

    String getLectureRoom();

    void setLectureRoom(String room);

    String getLecturer();

    void setLecturer(String lecturer);

    List<NoteReference> getNoteReferences();

    void addNoteReference(Note ref, int row);
}

package com.mobila.project.today.dataProviding;

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

    //<T extends Definable, R extends Identifiable> R getParent(T context);

    <T extends Definable, R extends Identifiable> List<R> getChildren(T context);

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    void addNote(Identifiable lecture, Note note) throws DataKeyNotFoundException;

    List<Attachment> getAttachments(Identifiable note) throws DataKeyNotFoundException;

    void addAttachment(Identifiable note, Attachment attachment) throws DataKeyNotFoundException;

    List<Task> getAllTasks();

    void removeEntityInstance(Identifiable instance) throws DataKeyNotFoundException;

    <T extends Definable> String getTitle(T context);

    <T extends Definable> void setTitle(T context, String title);

    <T extends Definable, R extends Identifiable> R getContent(T context);

    <T extends Definable> Date getDate(T context);

    <T extends Definable> void setDate(T context, Date date);

    String getLectureRoom();

    void setLectureRoom(String room);

    String getLecturer();

    void setLecturer(String lecturer);

    List<NoteReference> getNoteReferences();

    void addNoteReference(Note ref, int row);
}

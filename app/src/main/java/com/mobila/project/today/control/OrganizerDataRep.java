package com.mobila.project.today.control;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.NoteReference;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.modelMock.CourseMock;

import java.util.Date;
import java.util.List;

/**
 * Implement as singleton!
 */
public interface OrganizerDataRep {
    <T extends Identifiable> List<?> getChildren(T context);

    <T extends Identifiable, R extends Identifiable> void addChild(T context, R child);

    Task getTasks(CourseMock course);

    void addTask(CourseMock course, Task task);

    void removeEntity(Identifiable entity);

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
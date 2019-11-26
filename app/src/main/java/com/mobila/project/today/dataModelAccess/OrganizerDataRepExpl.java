package com.mobila.project.today.dataModelAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.NoteReference;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.Date;
import java.util.List;

public interface OrganizerDataRepExpl {
    /*
    static OrganizerDataRep getInstance() {
        return OrganizerDataRepMock.getInstance();
    }
    */

    List<Semester> getSemesters();

    List<Course> getCourses();

    void addCourse(Identifiable course);

    List<Section> getSections();

    void addSection(Identifiable section);

    List<Lecture> getLectures();

    void addLecture(Identifiable lecture);


    <T extends Identifiable, R extends Identifiable> void addChild(T context, R child);

    List<Task> getTasks(Course course);

    void addTask(Course course, Task task);

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

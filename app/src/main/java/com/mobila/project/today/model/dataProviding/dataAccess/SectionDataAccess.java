package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Identifiable;

import java.util.List;

public interface SectionDataAccess extends ParentDataAccess {
    static SectionDataAccess getInstance() {
        return SectionDataAccessImpl.getInstance();
    }

    Course getCourse(Identifiable section);

    List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException;

    void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException;

    void setTitle(Identifiable section, String title) throws DataKeyNotFoundException;

    void setLecturer(Identifiable section, String lecturer) throws DataKeyNotFoundException;

    void removeLecture(Identifiable section, Lecture lecture);
}

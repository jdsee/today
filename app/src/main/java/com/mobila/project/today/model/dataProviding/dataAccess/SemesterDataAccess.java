package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.List;

public interface SemesterDataAccess extends ParentDataAccess {
    static SemesterDataAccess getInstance(){return SemesterDataAccessImpl.getInstance();}

    List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException;

    void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException;

    void removeCourse(Identifiable semester, Course course);

    void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException;
}

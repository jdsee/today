package com.mobila.project.today.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;

import java.util.List;

public interface SemesterDataAccess {
    List<Semester> getAllSemesters();

    void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException;

    List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException;
}

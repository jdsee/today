package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.List;

class SemesterDataAccessImpl implements SemesterDataAccess {
    @Override
    public List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException {

    }

    @Override
    public int getNumber(Identifiable semester) throws DataKeyNotFoundException {
        return 0;
    }

    @Override
    public void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException {

    }
}

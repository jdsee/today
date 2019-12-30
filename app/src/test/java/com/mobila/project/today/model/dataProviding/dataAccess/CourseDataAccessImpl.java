package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;

import java.util.List;

public class CourseDataAccessImpl implements CourseDataAccess {

    @Override
    public Semester getSemester(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public List<Section> getSections(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addSection(Identifiable course, Section section) throws DataKeyNotFoundException {

    }

    @Override
    public List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addTask(Identifiable course, Task task) throws DataKeyNotFoundException {

    }

    @Override
    public String getTitle(Identifiable course) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setTitle(Identifiable course, String title) throws DataKeyNotFoundException {

    }

    @Override
    public void removeSection(Identifiable section) {

    }

    @Override
    public void removeTask(Identifiable task) {

    }
}

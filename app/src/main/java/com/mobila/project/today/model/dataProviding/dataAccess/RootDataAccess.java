package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface RootDataAccess {
    static RootDataAccess getInstance() {
        return RootDataAccessImpl.getInstance();
    }

    void open();

    void close();

    List<Semester> getAllSemesters();

    void addSemester(Semester semester);

    List<Task> getAllTasks();
}

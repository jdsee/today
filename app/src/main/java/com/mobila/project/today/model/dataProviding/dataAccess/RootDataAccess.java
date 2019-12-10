package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface RootDataAccess {

    List<Semester> getAllSemesters();

    List<Task> getAllTasks();

    void removeEntityInstance(Identifiable instance) throws DataKeyNotFoundException;
}

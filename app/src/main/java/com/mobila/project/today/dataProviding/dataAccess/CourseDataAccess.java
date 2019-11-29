package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Task;

import java.util.Date;
import java.util.List;

public interface CourseDataAccess {

    List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException;

    void addTask(Identifiable course, Task task) throws DataKeyNotFoundException;


    List<Section> getSections(Identifiable course) throws DataKeyNotFoundException;

    void addSection(Identifiable course, Section section) throws DataKeyNotFoundException;


    String getTitle(Identifiable course) throws DataKeyNotFoundException;

    void setTitle(Identifiable course, String title) throws DataKeyNotFoundException;
}

package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface CourseDataAccess {

    Semester getSemester(Identifiable course) throws DataKeyNotFoundException;

    List<Section> getSections(Identifiable course) throws DataKeyNotFoundException;

    void addSection(Identifiable course, Section section) throws DataKeyNotFoundException;

    List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException;

    void addTask(Identifiable course, Task task) throws DataKeyNotFoundException;

    String getTitle(Identifiable course) throws DataKeyNotFoundException;

    void setTitle(Identifiable course, String title) throws DataKeyNotFoundException;

    void removeSection(Identifiable section);

    void removeTask(Identifiable task);
}

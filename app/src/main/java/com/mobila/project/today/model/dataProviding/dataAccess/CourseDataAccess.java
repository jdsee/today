package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface CourseDataAccess {
    static CourseDataAccess getInstance() {
        return CourseDataAccessImpl.getInstance();
    }

    void open(Context context);

    void close();

    Semester getSemester(Identifiable course) throws DataKeyNotFoundException;

    List<Section> getSections(Identifiable course) throws DataKeyNotFoundException;

    void addSection(Identifiable course, Section section) throws DataKeyNotFoundException;

    List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException;

    void addTask(Identifiable course, Task task) throws DataKeyNotFoundException;

    void setTitle(Identifiable course, String title) throws DataKeyNotFoundException;

    void removeSection(Identifiable course, Section section);

    void removeTask(Identifiable course, Task task);
}

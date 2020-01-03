package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;

import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public interface RootDataAccess {
    static RootDataAccess getInstance() {
        return RootDataAccessImpl.getInstance();
    }

    void open(Context context);

    void close();

    List<Semester> getAllSemesters();

    void addSemester(Semester semester);

    void removeSemester(Semester semester);

    List<Task> getAllTasks();
}

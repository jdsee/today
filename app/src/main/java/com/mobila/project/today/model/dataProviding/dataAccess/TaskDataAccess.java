package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.Date;

public interface TaskDataAccess {


    static TaskDataAccess getInstance(){return TaskDataAccessImpl.getInstance();}

    Course getCourse(Identifiable section) throws DataKeyNotFoundException;

    Date getDeadline(Identifiable task) throws DataKeyNotFoundException;

    void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException;

    String getContent(Identifiable task) throws DataKeyNotFoundException;

    void setContent(Identifiable task, String content) throws DataKeyNotFoundException;

    void open(Context context);

    void close();
}

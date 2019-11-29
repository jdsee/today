package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.Date;

public interface TaskDataAccess {
    Course getCourse(Identifiable section) throws DataKeyNotFoundException;


    Date getDeadline(Identifiable task) throws DataKeyNotFoundException;

    void setDeatline(Identifiable task, Date date) throws DataKeyNotFoundException;


    String getContent(Identifiable task) throws DataKeyNotFoundException;

    void setContent(Identifiable task, String content) throws DataKeyNotFoundException;
}

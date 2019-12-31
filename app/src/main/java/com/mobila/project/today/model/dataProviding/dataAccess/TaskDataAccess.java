package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.Date;

public interface TaskDataAccess {

    void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException;

    void setContent(Identifiable task, String content) throws DataKeyNotFoundException;
}

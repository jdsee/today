package com.mobila.project.today.model.dataProviding.dataAccess;


import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;

import java.util.Date;

public interface TaskDataAccess extends ParentDataAccess {
    static TaskDataAccess getInstance() {
        return TaskDataAccessImpl.getInstance();
    }

    void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException;

    void setContent(Identifiable task, String content) throws DataKeyNotFoundException;
}

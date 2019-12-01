package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.Date;

public interface Task extends Identifiable {
    /**
     * Returns the course containing this task.
     *
     * @return the course containing this task
     */
    Course getCourse() throws DataKeyNotFoundException;

    Date getDeadline() throws DataKeyNotFoundException;

    void setDeadline(Date date) throws DataKeyNotFoundException;

    String getContent();

    void setContent(String content) throws DataKeyNotFoundException;
}

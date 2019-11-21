package com.mobila.project.today.model;

import java.util.Date;

public interface Task extends Identifiable {
    /**
     * Returns the course containing this task.
     *
     * @return the course containing this task
     */
    Course getCourse();

    Date getDeadline();

    void setDeadline(Date date);

    String getContent();

    void setContent(String content);
}

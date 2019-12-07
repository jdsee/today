package com.mobila.project.today.model;

import android.os.Parcelable;
import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.Date;

public interface Task extends Identifiable, Parcelable {
    String INTENT_EXTRA_CODE = "EXTRA_TASK";

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

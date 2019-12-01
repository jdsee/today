package com.mobila.project.today.model;

import android.os.Parcelable;

import java.util.Date;

public interface Task extends Identifiable, Parcelable {
    String INTENT_EXTRA_CODE = "EXTRA_TASK";

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

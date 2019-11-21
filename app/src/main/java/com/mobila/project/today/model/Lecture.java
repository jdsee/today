package com.mobila.project.today.model;

import java.util.Date;

public interface Lecture extends Identifiable {
    /**
     * Returns the section containing this lecture.
     *
     * @return the section containing this lecture
     */
    Section getSection();

    Note getNote();

    String getRoom();

    void setRoom(String room);

    Date getDate();

    void setDate(Date date);

    String getLecturer();

    void setLecturer(String lecturer);
}

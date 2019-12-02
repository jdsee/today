package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.Date;

public interface Lecture extends Identifiable {
    /**
     * Returns the section containing this lecture.
     *
     * @return the section containing this lecture
     */
    Section getSection() throws DataKeyNotFoundException;

    Note getNote() throws DataKeyNotFoundException;

    int getLectureNr() throws DataKeyNotFoundException;

    void setLectureNr(int number) throws DataKeyNotFoundException;

    int getLecturePosition() throws DataKeyNotFoundException;

    void setLecturePosition(int position) throws DataKeyNotFoundException;

    String getRoomNumber() throws DataKeyNotFoundException;

    void setRoomNumber(String room) throws DataKeyNotFoundException;

    Date getDate() throws DataKeyNotFoundException;

    void setDate(Date date) throws DataKeyNotFoundException;

    String getLecturer() throws DataKeyNotFoundException;

    void setLecturer(String lecturer) throws DataKeyNotFoundException;
}

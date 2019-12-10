package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.Date;
import java.util.List;
//TODO implement parcelable
public interface Lecture extends Identifiable {
    /**
     * Returns the section containing this lecture.
     *
     * @return the section containing this lecture
     */
    Section getSection() throws DataKeyNotFoundException;

    Note getNote() throws DataKeyNotFoundException;

    List<Attachment> getAttachments() throws DataKeyNotFoundException;

    void addAttachment(Attachment attachment) throws DataKeyNotFoundException;

    void removeAttachment(Identifiable attachment) throws DataKeyNotFoundException;

    int getLectureNr() throws DataKeyNotFoundException;

    void setLectureNr(int number) throws DataKeyNotFoundException;

    String getRoomNr() throws DataKeyNotFoundException;

    void setRoomNr(String room) throws DataKeyNotFoundException;

    Date getDate() throws DataKeyNotFoundException;

    void setDate(Date date) throws DataKeyNotFoundException;
}

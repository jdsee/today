package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;

import java.util.Date;
import java.util.List;

public interface LectureDataAccess {

    static LectureDataAccess getInstance() {
        return LectureDataAccessImpl.getInstance();
    }

    Section getSection(Identifiable lecture) throws DataKeyNotFoundException;

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    List<Attachment> getAttachments(Identifiable note) throws DataKeyNotFoundException;

    void addAttachment(Identifiable note, Identifiable attachment) throws DataKeyNotFoundException;

    int getLectureNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException;

    String getRoomNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setRoomNumber(Identifiable lecture, String roomNumber) throws DataKeyNotFoundException;

    Date getDate(Identifiable lecture) throws DataKeyNotFoundException;

    void setDate(Identifiable lecture, Date date) throws DataKeyNotFoundException;

    void removeAttachment(Identifiable attachment);
}

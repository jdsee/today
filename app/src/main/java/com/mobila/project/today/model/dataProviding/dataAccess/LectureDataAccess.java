package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import java.util.Date;
import java.util.List;

public interface LectureDataAccess extends ParentDataAccess {

    static LectureDataAccess createInstance() {
        return LectureDataAccessImpl.getInstance();
    }

    Section getSection(Identifiable lecture) throws DataKeyNotFoundException;

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    List<Attachment> getAttachments(Identifiable lecture) throws DataKeyNotFoundException;

    void addAttachment(Identifiable lecture, Attachment attachment) throws DataKeyNotFoundException;

    void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException;

    void setRoomNumber(Identifiable lecture, String roomNumber) throws DataKeyNotFoundException;

    void setDate(Identifiable lecture, Date date) throws DataKeyNotFoundException;

    void removeAttachment(Identifiable lecture, Attachment attachment);
}

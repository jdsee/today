package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;

import java.util.Date;
import java.util.List;

public class LectureDataAccessImpl implements LectureDataAccess {

    private static LectureDataAccessImpl instance;

    static LectureDataAccess getInstance(){
        if (instance==null)
            instance=new LectureDataAccessImpl();
        return instance;
    }

    @Override
    public Section getSection(Identifiable lecture) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public Note getNote(Identifiable lecture) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public List<Attachment> getAttachments(Identifiable note) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void addAttachment(Identifiable note, Identifiable attachment) throws DataKeyNotFoundException {

    }

    @Override
    public int getLectureNumber(Identifiable lecture) throws DataKeyNotFoundException {
        return 0;
    }

    @Override
    public void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException {

    }

    @Override
    public String getRoomNumber(Identifiable lecture) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setRoomNumber(Identifiable lecture, String roomNumber) throws DataKeyNotFoundException {

    }

    @Override
    public Date getDate(Identifiable lecture) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setDate(Identifiable lecture, Date date) throws DataKeyNotFoundException {

    }

    @Override
    public void removeAttachment(Identifiable attachment) {

    }
}

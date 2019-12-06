package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;

import java.util.Date;
import java.util.List;

class LectureImpl implements Lecture {
    private final RootDataAccess rootDataAccess;
    private final LectureDataAccess dataAccess;

    private final int ID;
    private int lectureNr;
    private Date date;
    private String roomNr;

    public LectureImpl(int ID, int lectureNr) {
        this.ID = ID;
        this.lectureNr = lectureNr;
        this.roomNr = roomNr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getLectureDataAccess();
    }

    public LectureImpl(int ID, int lectureNr, Date date, String roomNr) {
        this(ID, lectureNr);
        this.date = date;
        this.roomNr = roomNr;
    }


    @Override
    public Section getSection() throws DataKeyNotFoundException {
        return this.dataAccess.getSection(this);
    }

    @Override
    public Note getNote() throws DataKeyNotFoundException {
        return this.dataAccess.getNote(this);
    }

    @Override
    public List<Attachment> getAttachments() throws DataKeyNotFoundException {
        return this.dataAccess.getAttachments(this);
    }

    @Override
    public void addAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.addAttachment(this, attachment);
    }

    @Override
    public void removeAttachment(Identifiable attachment) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(attachment);
    }

    @Override
    public int getLectureNr() throws DataKeyNotFoundException {
        return this.dataAccess.getLectureNumber(this);
    }

    @Override
    public void setLectureNr(int number) throws DataKeyNotFoundException {
        this.lectureNr = number;
        this.dataAccess.setLectureNumber(this, number);
    }

    @Override
    public String getRoomNr() throws DataKeyNotFoundException {
        return this.dataAccess.getRoomNumber(this);
    }

    @Override
    public void setRoomNr(String roomNr) throws DataKeyNotFoundException {
        this.roomNr = roomNr;
        this.dataAccess.setRoomNumber(this, roomNr);
    }

    @Override
    public Date getDate() throws DataKeyNotFoundException {
        return this.dataAccess.getDate(this);
    }

    @Override
    public void setDate(Date date) throws DataKeyNotFoundException {
        this.date = date;
        this.dataAccess.setDate(this, date);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

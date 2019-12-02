package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;

import java.util.Date;

class LectureImpl implements Lecture {
    private final RootDataAccess rootDataAccess;
    private final LectureDataAccess dataAccess;

    private final int ID;
    private int lectureNr;
    private int position;
    private Date date;
    private String roomNr;
    private String lecturer;

    public LectureImpl(int ID, int lectureNr, int position) {
        this.ID = ID;
        this.lectureNr = lectureNr;
        this.position = position;
        this.roomNr = roomNr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getLectureDataAccess();
    }

    public LectureImpl(int ID, int lectureNr, int position, Date date, String roomNr, String lecturer) {
        this(ID, lectureNr, position);
        this.date = date;
        this.roomNr = roomNr;
        this.lecturer = lecturer;
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
    public int getLectureNr() throws DataKeyNotFoundException {
        return this.dataAccess.getLectureNumber(this);
    }

    @Override
    public void setLectureNr(int number) throws DataKeyNotFoundException {
        this.lectureNr = number;
        this.dataAccess.setLectureNumber(this, number);
    }

    @Override
    public int getLecturePosition() throws DataKeyNotFoundException {
        return this.dataAccess.getLecturePosition(this);
    }

    @Override
    public void setLecturePosition(int position) throws DataKeyNotFoundException {
        this.position = position;
        this.dataAccess.setLecturePosition(this, position);
    }

    @Override
    public String getRoomNumber() throws DataKeyNotFoundException {
        return this.dataAccess.getRoomNumber(this);
    }

    @Override
    public void setRoomNumber(String roomNr) throws DataKeyNotFoundException {
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
    public String getLecturer() throws DataKeyNotFoundException {
        return this.dataAccess.getLecturer(this);
    }

    @Override
    public void setLecturer(String lecturer) throws DataKeyNotFoundException {
        this.lecturer = lecturer;
        this.dataAccess.setLecturer(this, lecturer);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

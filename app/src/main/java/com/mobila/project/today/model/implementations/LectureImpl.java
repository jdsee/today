package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;

import java.util.Date;

class LectureImpl implements Lecture {
    private final RootDataAccess rootAccess;
    private final LectureDataAccess lectureAccess;

    private final int ID;
    private int nr;
    private int position;
    private String room;
    

    @Override
    public Section getSection() {
        return null;
    }

    @Override
    public Note getNote() {
        return null;
    }

    @Override
    public int getLectureNr() {
        return 0;
    }

    @Override
    public void setLetureNr() {

    }

    @Override
    public int getLecturePosition() {
        return 0;
    }

    @Override
    public void setLecturePosition() {

    }

    @Override
    public String getRoom() {
        return null;
    }

    @Override
    public void setRoom(String room) {

    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public void setDate(Date date) {

    }

    @Override
    public String getLecturer() {
        return null;
    }

    @Override
    public void setLecturer(String lecturer) {

    }

    @Override
    public int getID() {
        return 0;
    }
}

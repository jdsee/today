package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;

import java.util.Date;

public interface LectureDataAccess {

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    void removeNote(Identifiable lecture) throws DataKeyNotFoundException;


    int getLectureNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException;


    int getLecturePosition(Identifiable lecture) throws DataKeyNotFoundException;

    void setLecturePosition(Identifiable lecture, int position) throws DataKeyNotFoundException;


    String getRoomNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setRoomNumber(Identifiable lecture, String roomNumber) throws DataKeyNotFoundException;


    Date getDate(Identifiable lecture) throws DataKeyNotFoundException;

    void setDate(Identifiable lecture, Date date) throws DataKeyNotFoundException;


    String getLecturer(Identifiable lecture) throws DataKeyNotFoundException;

    void setLecturer(Identifiable lecture, String lecturer) throws DataKeyNotFoundException;

    Section getSection(Identifiable lecture) throws DataKeyNotFoundException;
}

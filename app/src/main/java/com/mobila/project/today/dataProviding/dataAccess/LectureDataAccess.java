package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;

public interface LectureDataAccess {

    Note getNote(Identifiable lecture) throws DataKeyNotFoundException;

    void removeNote(Identifiable lecture) throws DataKeyNotFoundException;


    int getLectureNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException;


    int getLecturePosition(Identifiable lecture) throws DataKeyNotFoundException;

    void setLecturePosition(Identifiable lecture, int position) throws DataKeyNotFoundException;


    int getRoomNumber(Identifiable lecture) throws DataKeyNotFoundException;

    void setRoomNumber(Identifiable lecture, int roomNumber) throws DataKeyNotFoundException;


    int getLectureDate(Identifiable lecture) throws DataKeyNotFoundException;

    void setLectureDate(Identifiable lecture, int date) throws DataKeyNotFoundException;


    String getLecturer(Identifiable lecture) throws DataKeyNotFoundException;

    void setLecturer(Identifiable lecture, String lecturer) throws DataKeyNotFoundException;
}

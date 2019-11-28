package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;

import java.util.List;

public interface SectionDataAccess {
    List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException;

    void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException;
}

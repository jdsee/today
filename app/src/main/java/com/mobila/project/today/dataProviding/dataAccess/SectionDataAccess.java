package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;

import java.util.List;

public interface SectionDataAccess {
    List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException;

    String getTitle(Identifiable section) throws DataKeyNotFoundException;

    String setTitle(Identifiable section, String title) throws DataKeyNotFoundException;

    void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException;

    Course getCourse(Identifiable section) throws DataKeyNotFoundException;
}

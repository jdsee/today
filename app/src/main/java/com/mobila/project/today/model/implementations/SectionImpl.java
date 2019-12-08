package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SectionDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Section;

import java.util.List;

public class SectionImpl implements Section {
    private final RootDataAccess rootDataAccess;
    private final SectionDataAccess dataAccess;

    private final int ID;
    private String title;
    private String lecturer;

    public SectionImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getSectionDataAccess();
    }

    @Override
    public Course getCourse() throws DataKeyNotFoundException {
        return this.dataAccess.getCourse(this);
    }

    @Override
    public String getTitle() throws DataKeyNotFoundException {
        return this.title;
    }


    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.dataAccess.setTitle(this, title);
    }

    @Override
    public List<Lecture> getLectures() throws DataKeyNotFoundException {
        return this.dataAccess.getLectures(this);
    }

    @Override
    public void addLecture(Lecture lecture) throws DataKeyNotFoundException {
        this.dataAccess.addLecture(this, lecture);
    }

    @Override
    public void removeLecture(Identifiable lecture) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(lecture);
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

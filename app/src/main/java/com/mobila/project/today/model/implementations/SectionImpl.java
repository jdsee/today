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

class SectionImpl implements Section {
    private final RootDataAccess rootAccess;
    private final SectionDataAccess sectionAccess;

    private final int ID;
    private String title;

    public SectionImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootAccess = dataProvider.getRootDataAccess();
        this.sectionAccess = dataProvider.getSectionDataAccess();
    }

    @Override
    public Course getCourse() throws DataKeyNotFoundException {
        return this.sectionAccess.getCourse(this);
    }

    @Override
    public String getTitle() throws DataKeyNotFoundException {
        return this.title;
    }


    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.sectionAccess.setTitle(this, title);
    }

    @Override
    public List<Lecture> getLectures() throws DataKeyNotFoundException {
        return this.sectionAccess.getLectures(this);
    }

    @Override
    public void addLecture(Lecture lecture) throws DataKeyNotFoundException {
        this.sectionAccess.addLecture(this, lecture);
    }

    @Override
    public void removeLecture(Identifiable lecture) throws DataKeyNotFoundException {
        this.rootAccess.removeEntityInstance(lecture);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

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
    private List<Lecture> lectures;

    public SectionImpl(int ID, String title, String lecturer) {
        this.ID = ID;
        this.title = title;
        this.lecturer = lecturer;
        this.lectures = null;

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
        this.dataAccess.setTitle(this, title);
        this.title = title;
    }

    private void initLectures() {
        this.lectures = this.dataAccess.getLectures(this);
    }

    @Override
    public List<Lecture> getLectures() throws DataKeyNotFoundException {
        if (this.lectures == null)
            this.initLectures();
        return this.lectures;
    }

    @Override
    public void addLecture(Lecture lecture) throws DataKeyNotFoundException {
        this.dataAccess.addLecture(this, lecture);
        if (this.lectures == null)
            this.initLectures();
        else this.lectures.add(lecture);
    }

    @Override
    public void removeLecture(Identifiable lecture) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(lecture);
        if (this.lectures != null)
            this.lectures.remove(lecture);
    }

    @Override
    public String getLecturer() throws DataKeyNotFoundException {
        return this.lecturer;
    }

    @Override
    public void setLecturer(String lecturer) throws DataKeyNotFoundException {
        this.dataAccess.setLecturer(this, lecturer);
        this.lecturer = lecturer;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

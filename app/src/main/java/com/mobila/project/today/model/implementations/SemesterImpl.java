package com.mobila.project.today.model.implementations;

import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;

import java.util.List;

public class SemesterImpl implements Semester {
    private final RootDataAccess rootDataAccess;
    private final SemesterDataAccess dataAccess;

    private final int ID;
    private int semesterNr;

    public SemesterImpl(int ID, int semesterNr) {
        this.ID = ID;
        this.semesterNr = semesterNr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getSemesterDataAccess();
    }

    @Override
    public List<Course> getCourses() throws UncheckedTodayException {
        return this.dataAccess.getCourses(this);
    }

    @Override
    public int getSemesterNr() throws UncheckedTodayException {
        return this.semesterNr;
    }

    @Override
    public void setSemesterNr(int number) throws UncheckedTodayException {
        this.semesterNr = number;
        this.dataAccess.setNumber(this, number);
    }

    @Override
    public void addCourse(Course course) throws UncheckedTodayException {
        this.dataAccess.addCourse(this, course);
    }

    @Override
    public void removeCourse(Course course) throws UncheckedTodayException {
        this.rootDataAccess.removeEntityInstance(course);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

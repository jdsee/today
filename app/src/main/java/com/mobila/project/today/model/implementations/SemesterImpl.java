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
    private List<Course> courses;

    public SemesterImpl(int ID, int semesterNr) {
        this.ID = ID;
        this.semesterNr = semesterNr;
        this.courses = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getSemesterDataAccess();
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

    private void initCourses() {
        this.courses = this.dataAccess.getCourses(this);
    }

    @Override
    public List<Course> getCourses() throws UncheckedTodayException {
        if (this.courses == null)
            this.initCourses();
        return this.courses;
    }

    @Override
    public void addCourse(Course course) throws UncheckedTodayException {
        this.dataAccess.addCourse(this, course);
        if (this.courses == null)
            this.initCourses();
        else this.courses.add(course);
    }

    @Override
    public void removeCourse(Course course) throws UncheckedTodayException {
        this.rootDataAccess.removeEntityInstance(course);
        if (this.courses!=null)
            this.courses.remove(course);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

package com.mobila.project.today.model.implementations;

import com.mobila.project.today.TodayException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;

import java.util.List;

class SemesterImpl implements Semester {
    private final RootDataAccess rootAccess;
    private final SemesterDataAccess semesterAccess;

    private final int ID;
    private int nr;

    public SemesterImpl(int ID, int nr) {
        this.ID = ID;
        this.nr = nr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootAccess = dataProvider.getRootDataAccess();
        this.semesterAccess = dataProvider.getSemesterDataAccess();
    }

    @Override
    public List<Course> getCourses() throws TodayException {
        return this.semesterAccess.getCourses(this);
    }

    @Override
    public int getNumber() throws TodayException {
        return this.nr;
    }

    @Override
    public void setNumber(int nr) throws TodayException {
        this.nr = nr;
        this.semesterAccess.setNumber(this, nr);
    }

    @Override
    public void addCourse(Course course) throws TodayException {
        this.semesterAccess.addCourse(this, course);
    }

    @Override
    public void removeCourse(Course course) throws TodayException {
        this.rootAccess.removeEntityInstance(course);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

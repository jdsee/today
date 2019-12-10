package com.mobila.project.today.model;

import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SemesterDataAccess;

import java.util.List;

/**
 * Allows access to all data of the "Semester"-entity.
 */
public class Semester implements Identifiable {
    private final RootDataAccess rootDataAccess;
    private final SemesterDataAccess semesterDataAccess;

    private final int ID;
    private int semesterNr;
    private List<Course> courses;

    public Semester(int ID, int semesterNr) {
        this.ID = ID;
        this.semesterNr = semesterNr;
        this.courses = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.semesterDataAccess = dataProvider.getSemesterDataAccess();
    }

    public int getSemesterNr() throws UncheckedTodayException {
        return this.semesterNr;
    }

    public void setSemesterNr(int number) throws UncheckedTodayException {
        this.semesterNr = number;
        this.semesterDataAccess.setNumber(this, number);
    }

    private void initCourses() {
        this.courses = this.semesterDataAccess.getCourses(this);
    }

    /**
     * Returns a list with all courses contained in this semester.
     *
     * @return a list with all courses contained in this semester
     */
    public List<Course> getCourses() throws UncheckedTodayException {
        if (this.courses == null)
            this.initCourses();
        return this.courses;
    }

    public void addCourse(Course course) throws UncheckedTodayException {
        this.semesterDataAccess.addCourse(this, course);
        if (this.courses == null)
            this.initCourses();
        else this.courses.add(course);
    }

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

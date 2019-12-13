package com.mobila.project.today.model;

import android.content.ContentValues;

import com.mobila.project.today.UncheckedTodayException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SemesterDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;

import java.util.List;
import java.util.UUID;

/**
 * Allows access to all data of the "Semester"-entity.
 */
public class Semester implements Identifiable {
    private final RootDataAccess rootDataAccess;
    private final SemesterDataAccess semesterDataAccess;

    private final String ID;
    private int semesterNr;
    private List<Course> courses;

    public Semester(String id, int semesterNr) {
        this.ID = id;
        this.semesterNr = semesterNr;
        this.courses = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.semesterDataAccess = dataProvider.getSemesterDataAccess();
    }

    /*public Semester(int semesterNr) {
        int id = UUID.randomUUID().toString();
        this(id, semesterNr);
    }*/

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
    public String getID() {
        return this.ID;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(SemesterTable.COLUMN_ID, this.ID);
        values.put(SemesterTable.COLUMN_NR, this.semesterNr);
        return values;
    }
}

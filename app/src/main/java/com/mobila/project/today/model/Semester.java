package com.mobila.project.today.model;

import com.mobila.project.today.UncheckedTodayException;

import java.util.List;

/**
 * Allows access to all data of the "Semester"-entity.
 */
public interface Semester extends Identifiable {
    /**
     * Returns a list with all courses contained in this semester.
     *
     * @return a list with all courses contained in this semester
     */
    List<Course> getCourses() throws UncheckedTodayException;

    int getSemesterNr() throws UncheckedTodayException;

    void setSemesterNr(int nr) throws UncheckedTodayException;

    void addCourse(Course course) throws UncheckedTodayException;

    void removeCourse(Course course) throws UncheckedTodayException;
}

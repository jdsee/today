package com.mobila.project.today.model;

import com.mobila.project.today.TodayException;

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
    List<Course> getCourses() throws TodayException;
}

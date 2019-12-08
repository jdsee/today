package com.mobila.project.today.model;

import android.os.Parcelable;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.implementations.CourseImpl;

import java.util.List;

/**
 * Allows access to all data contained in the "Course"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 * There are also course related notes stored in the @code{Course}.
 */
public interface Course extends Identifiable, Parcelable {
    String INTENT_EXTRA_CODE = "EXTRA_COURSE";

    static Course createCourse(int id, String title){
        return new CourseImpl(id, title);
    }
    /**
     * Returns the semester containing this course.
     *
     * @return the semester containing this course
     */
    Semester getSemester() throws DataKeyNotFoundException;

    /**
     * Returns the title of this course.
     *
     * @return the title of this course
     */
    String getTitle();

    /**
     * Sets the title for this course.
     *
     * @param title title of this course
     */
    void setTitle(String title) throws DataKeyNotFoundException;

    /**
     * Returns a list with all sections contained in this course.
     *
     * @return a list with all sections contained in this course
     */
    List<Section> getSections() throws DataKeyNotFoundException;

    /**
     * Adds a section to this course.
     *
     * @param section section to add
     */
    void addSection(Section section) throws DataKeyNotFoundException;

    /**
     * Removes a section of this course.
     */
    void removeSection(Identifiable section) throws DataKeyNotFoundException;

    /**
     * Returns a list with all tasks contained in this course.
     *
     * @return a list with all tasks contained in this course
     */
    List<Task> getTasks() throws DataKeyNotFoundException;

    /**
     * Adds a task to this course.
     */
    void addTask(Task task) throws DataKeyNotFoundException;

    /**
     * Removes a task contained in this course.
     */
    void removeTask(Identifiable task) throws DataKeyNotFoundException;
}

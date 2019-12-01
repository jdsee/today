package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OperationNotSupportedInActualContextException;

import java.util.List;

/**
 * Allows access to all data contained in the "Course"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 * There are also course related notes stored in the @code{Course}.
 */
public interface Course extends Identifiable, Definable {
    public static final String COURSE_TYPE_IDENTIFIER = "course";

    /**
     * Returns the semester containing this course.
     *
     * @return the semester containing this course
     */
    Semester getSemester() throws DataKeyNotFoundException, OperationNotSupportedInActualContextException;

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
    List<Section> getSections() throws DataKeyNotFoundException, OperationNotSupportedInActualContextException;

    /**
     * Adds a section to this course.
     *
     * @param section section to add
     */
    void addSection(Section section);

    /**
     * Removes a section of this course.
     */
    void removeSection(Identifiable section);

    /**
     * Returns a list with all tasks contained in this course.
     *
     * @return a list with all tasks contained in this course
     */
    List<Task> getTasks();

    /**
     * Adds a task to this course.
     */
    void addTask(Task task);

    /**
     * Removes a task contained in this course.
     */
    void removeTask(Identifiable task);
}

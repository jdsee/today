package com.mobila.project.today.model;

import java.util.List;

/**
 * Allows access to all data contained in the "Course"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 * There are also course related notes stored in the @code{Course}.
 */
public interface Course extends Identifiable {

    /**
     * Returns the semester containing this course.
     *
     * @return the semester containing this course
     */
    Semester getSemester();

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
    void setTitle(String title);

    /**
     * Returns a list with all sections contained in this course.
     *
     * @return a list with all sections contained in this course
     */
    List<Section> getSections();

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

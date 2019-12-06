package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.List;

/**
 * Allows access to all data contained in the "Section"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 */
public interface Section extends Identifiable {
    /**
     * Returns the course containing this section.
     *
     * @return the course containing this section
     */
    Course getCourse() throws DataKeyNotFoundException;

    /**
     * Returns the title of this section.
     *
     * @return the title of this section
     */
    String getTitle() throws DataKeyNotFoundException;

    /**
     * Sets the title for this section.
     *
     */
    void setTitle(String title) throws DataKeyNotFoundException;

    List<Lecture> getLectures() throws DataKeyNotFoundException;

    void addLecture(Lecture lecture) throws DataKeyNotFoundException;

    void removeLecture(Identifiable lecture) throws DataKeyNotFoundException;

    String getLecturer() throws DataKeyNotFoundException;

    void setLecturer(String lecturer) throws DataKeyNotFoundException;
}

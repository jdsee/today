package com.mobila.project.today.model;

import java.util.List;

/**
 * Allows access to all data contained in the "SectionMock"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 */
public interface Section extends Identifiable {
    /**
     * Returns the course containing this section.
     *
     * @return the course containing this section
     */
    Course getCourse();

    /**
     * Returns the title of this section.
     *
     * @return the title of this section
     */
    String getTitle();

    /**
     * Sets the title for this section.
     */
    void setTitle(String title);

    List<Lecture> getLectures();

    void addLecture(Lecture lecture);

    void removeLecture(Identifiable id);
}

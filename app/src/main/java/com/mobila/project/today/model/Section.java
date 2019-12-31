package com.mobila.project.today.model;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.SectionDataAccess;

import java.util.List;

/**
 * Allows access to all data contained in the "Section"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 */
public class Section implements Identifiable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_SECTION";
    private final RootDataAccess rootDataAccess;
    private final SectionDataAccess dataAccess;

    private final String ID;
    private String title;
    private String lecturer;
    private List<Lecture> lectures;

    public Section(String ID, String title, String lecturer) {
        this.ID = ID;
        this.title = title;
        this.lecturer = lecturer;
        this.lectures = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getSectionDataAccess();
    }

    /**
     * Returns the course containing this section.
     *
     * @return the course containing this section
     */
    public Course getCourse() throws DataKeyNotFoundException {
        //TODO this method is probably useless
     return null;
    }

    /**
     * Returns the title of this section.
     *
     * @return the title of this section
     */
    public String getTitle() throws DataKeyNotFoundException {
        return this.title;
    }

    /**
     * Sets the title for this section.
     */
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.dataAccess.setTitle(this, title);
        this.title = title;
    }

    private void initLectures() {
        this.lectures = this.dataAccess.getLectures(this);
    }

    public List<Lecture> getLectures() throws DataKeyNotFoundException {
        if (this.lectures == null)
            this.initLectures();
        return this.lectures;
    }


    public void addLecture(Lecture lecture) throws DataKeyNotFoundException {
        this.dataAccess.addLecture(this, lecture);
        if (this.lectures == null)
            this.initLectures();
        else this.lectures.add(lecture);
    }


    public void removeLecture(Lecture lecture) throws DataKeyNotFoundException {
        this.dataAccess.removeLecture(this, lecture);
        if (this.lectures != null)
            this.lectures.remove(lecture);
    }


    public String getLecturer() throws DataKeyNotFoundException {
        return this.lecturer;
    }


    public void setLecturer(String lecturer) throws DataKeyNotFoundException {
        this.dataAccess.setLecturer(this, lecturer);
        this.lecturer = lecturer;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
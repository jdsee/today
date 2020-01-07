package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.util.List;

/**
 * Allows access to all data contained in the "Course"-entity.
 * The main content are the sections, which store all relevant data for taking notes.
 * There are also course related notes stored in the @code{Course}.
 */
public class Course implements Identifiable, Parcelable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_COURSE";

    private final RootDataAccess rootDataAccess;
    private final CourseDataAccess dataAccess;

    private final String ID;
    private String title;
    private List<Task> tasks;
    private List<Section> sections;

    public Course(String ID, String title) {
        this.ID = ID;
        this.title = title;
        this.tasks = null;
        this.sections = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getCourseDataAccess();
    }

    public Course(String title) {
        this(
                KeyGenerator.getUniqueKey(),
                title
        );
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    private Course(Parcel in) {
        this(
                in.readString(),
                in.readString()
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.title);
    }

    /**
     * Returns the title of this course.
     *
     * @return the title of this course
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title for this course.
     *
     * @param title title of this course
     */
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.dataAccess.setTitle(this, title);
    }

    /**
     * Returns a list with all sections contained in this course.
     *
     * @return a list with all sections contained in this course
     */
    public List<Section> getSections() throws DataKeyNotFoundException {
        if (this.sections == null)
            this.sections = this.dataAccess.getSections(this);
        return this.sections;
    }

    /**
     * Adds a section to this course.
     *
     * @param section section to add
     */
    public void addSection(Section section) throws DataKeyNotFoundException {
        if (this.sections != null)
            this.sections.add(section);
        this.dataAccess.addSection(this, section);
    }

    /**
     * Removes a section of this course.
     */
    public void removeSection(Section section) throws DataKeyNotFoundException {
        if (this.sections != null)
            this.sections.remove(section);
        this.dataAccess.removeSection(this, section);
    }

    /**
     * Returns a list with all tasks contained in this course.
     *
     * @return a list with all tasks contained in this course
     */
    public List<Task> getTasks() throws DataKeyNotFoundException {
        if (this.tasks == null)
            this.tasks = this.dataAccess.getTasks(this);
        return this.tasks;
    }

    public void refresh(){
        this.tasks = this.dataAccess.getTasks(this);
    }

    /**
     * Adds a task to this course.
     */
    public void addTask(Task task) throws DataKeyNotFoundException {
        if (this.tasks != null)
            this.tasks.add(task);
        this.dataAccess.addTask(this, task);
    }

    /**
     * Removes a task contained in this course.
     */
    public void removeTask(Task task) throws DataKeyNotFoundException {
        if (this.tasks != null)
            this.tasks.remove(task);
        this.dataAccess.removeTask(this, task);
    }

    @Override
    public String getID() {
        return this.ID;
    }
}

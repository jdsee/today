package com.mobila.project.today.model.implementations;

import android.os.Parcel;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;

import java.util.List;

public class CourseImpl implements Course {
    private final RootDataAccess rootDataAccess;
    private final CourseDataAccess dataAccess;

    private final int ID;
    private String title;
    private List<Task> tasks;
    private List<Section> sections;

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new CourseImpl(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    private CourseImpl(Parcel in) {
        this(
                in.readInt(),
                in.readString()
        );
    }

    public CourseImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;
        this.tasks = null;
        this.sections = null;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getCourseDataAccess();
    }

    @Override
    public Semester getSemester() throws DataKeyNotFoundException {
        return this.dataAccess.getSemester(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.dataAccess.setTitle(this, title);
    }

    private void checkSectionsNotNull(){
        if (this.sections == null)
            this. sections = this.dataAccess.getSections(this);
    }

    @Override
    public List<Section> getSections() throws DataKeyNotFoundException {
        this.checkSectionsNotNull();
        return this.sections;
    }

    @Override
    public void addSection(Section section) throws DataKeyNotFoundException {
        this.checkSectionsNotNull();
        this.sections.add(section);
        this.dataAccess.addSection(this, section);
    }

    @Override
    public void removeSection(Identifiable section) throws DataKeyNotFoundException {
        this.checkSectionsNotNull();
        this.sections.remove(section);
        this.rootDataAccess.removeEntityInstance(section);
    }


    private void checkTasksNotNull() {
        if (this.tasks == null)
            this. tasks = dataAccess.getTasks(this);
    }

    @Override
    public List<Task> getTasks() throws DataKeyNotFoundException {
        this.checkTasksNotNull();
        this.dataAccess.getTasks(this);
        return this.tasks;
    }

    @Override
    public void addTask(Task task) throws DataKeyNotFoundException {
        this.checkTasksNotNull();
        this.tasks.add(task);
        this.dataAccess.addTask(this, task);
    }

    @Override
    public void removeTask(Identifiable task) throws DataKeyNotFoundException {
        this.checkTasksNotNull();
        this.tasks.remove(task);
        this.rootDataAccess.removeEntityInstance(task);
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.title);
    }
}

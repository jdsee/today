package com.mobila.project.today.model.implementations;

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

class CourseImpl implements Course {
    private final RootDataAccess rootAccess;
    private final CourseDataAccess courseAccess;

    private final int ID;
    private String title;

    public CourseImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootAccess = dataProvider.getRootDataAccess();
        this.courseAccess = dataProvider.getCourseDataAccess();
    }

    @Override
    public Semester getSemester() throws DataKeyNotFoundException {
        return this.courseAccess.getSemester(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.courseAccess.setTitle(this, title);
    }

    @Override
    public List<Section> getSections() throws DataKeyNotFoundException {
        return this.courseAccess.getSections(this);
    }

    @Override
    public void addSection(Section section) throws DataKeyNotFoundException {
        this.courseAccess.addSection(this, section);
    }

    @Override
    public void removeSection(Identifiable section) throws DataKeyNotFoundException {
        this.rootAccess.removeEntityInstance(section);
    }


    @Override
    public List<Task> getTasks() throws DataKeyNotFoundException {
        return this.courseAccess.getTasks(this);
    }

    @Override
    public void addTask(Task task) throws DataKeyNotFoundException {
        this.courseAccess.addTask(this, task);
    }

    @Override
    public void removeTask(Identifiable task) throws DataKeyNotFoundException {
        this.rootAccess.removeEntityInstance(task);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

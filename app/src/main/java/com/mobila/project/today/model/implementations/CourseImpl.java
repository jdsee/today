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
    private final RootDataAccess rootDataAccess;
    private final CourseDataAccess dataAccess;

    private final int ID;
    private String title;

    public CourseImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;

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

    @Override
    public List<Section> getSections() throws DataKeyNotFoundException {
        return this.dataAccess.getSections(this);
    }

    @Override
    public void addSection(Section section) throws DataKeyNotFoundException {
        this.dataAccess.addSection(this, section);
    }

    @Override
    public void removeSection(Identifiable section) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(section);
    }


    @Override
    public List<Task> getTasks() throws DataKeyNotFoundException {
        return this.dataAccess.getTasks(this);
    }

    @Override
    public void addTask(Task task) throws DataKeyNotFoundException {
        this.dataAccess.addTask(this, task);
    }

    @Override
    public void removeTask(Identifiable task) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(task);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

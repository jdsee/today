package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OperationNotSupportedInActualContextException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;

import java.util.List;

class CourseImpl implements Course {
    private static OrganizerDataProvider dataProvider;

    private final int ID;
    private String title;

    public CourseImpl(int ID) {
        this.ID = ID;
    }

    @Override
    public Semester getSemester()
            throws DataKeyNotFoundException, OperationNotSupportedInActualContextException {
        return dataProvider.getParent(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        dataProvider.updateEntityInstance(this);
    }

    @Override
    public List<Section> getSections()
            throws DataKeyNotFoundException, OperationNotSupportedInActualContextException {
        return dataProvider.getChildren(this);
    }

    @Override
    public void addSection(Section section) {
        dataProvider.addChild(this, section);
    }

    @Override
    public void removeSection(Identifiable section) {

    }

    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void removeTask(Identifiable task) {

    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getTypeIdentifier() {
        return null;
    }
}

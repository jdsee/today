/*
package com.mobila.project.today.model;

import java.util.List;

class CourseImpl implements Course {
    private static OrganizerDataRep dataRep;
    private final int id;

    static {
        //dataRep = OrganizerDataRep.getInstance();
    }

    public CourseImpl(int id) {
        this.id = id;
    }

    @Override
    public Semester getSemester() {
        return dataRep.getParent(this);
    }

    @Override
    public String getTitle() {
        return dataRep.getTitle(this);
    }

    @Override
    public void setTitle(String title) {
        dataRep.setTitle(this, title);
    }

    @Override
    public List<Section> getSections() {
        return dataRep.getChildren(this);
    }

    @Override
    public void addSection(Section section) {
        dataRep.addChild(this, section);
    }

    @Override
    public void removeSection(Identifiable section) {
        dataRep.removeEntityInstance(section);
    }

    @Override
    public List<Task> getTasks() {
        return dataRep.getTasks(this);
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void removeTask(Identifiable task) {

    }

    @Override
    public int getID() {
        return 0;
    }
}
*/

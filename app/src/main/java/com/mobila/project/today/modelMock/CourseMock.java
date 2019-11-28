package com.mobila.project.today.modelMock;

import java.util.List;

public class CourseMock {
    private int id;
    private String name;
    private String lecturer;
    private String room;
    private List<TaskMock> tasks;
    private List<SectionMock> sections;

    public CourseMock(int id, String name, String lecturer, List<TaskMock> tasks, List<SectionMock> sections) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.tasks = tasks;
        this.sections = sections;
    }

    public CourseMock(int id, String name, String lecturer, String room) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public List<TaskMock> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskMock> tasks) {
        this.tasks = tasks;
    }

    public List<SectionMock> getSections() {
        return sections;
    }

    public void setSections(List<SectionMock> sections) {
        this.sections = sections;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

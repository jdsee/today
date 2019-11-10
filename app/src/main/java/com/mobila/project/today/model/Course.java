package com.mobila.project.today.model;

import java.util.List;

public class Course {
    private int id;
    private String name;
    private String lecturer;
    private List<Task> tasks;
    private List<Section> sections;

    public Course(int id, String name, String lecturer, List<Task> tasks, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.tasks = tasks;
        this.sections = sections;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}

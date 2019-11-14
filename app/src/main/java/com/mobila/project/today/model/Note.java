package com.mobila.project.today.model;

public class Note {
    private long id;
    private String title;
    private String content;
    private long semester;
    private String course;
    private String category;
    private String event;
    private String date;

    public Note(long id, String title, String content, long semester, String course, String category, String event, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.semester = semester;
        this.course = course;
        this.category = category;
        this.event = event;
        this.date = date;
    }

    public long getSemester() {
        return semester;
    }

    public void setSemester(long semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Note(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

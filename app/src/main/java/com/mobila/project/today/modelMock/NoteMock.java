package com.mobila.project.today.modelMock;

import android.text.Spannable;
import android.text.SpannableString;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


public class NoteMock{
    private long id;
    private String title;
    private Spannable content;
    private long semester;
    private String course;
    private String category;
    private String event;
    private String date;
    private ArrayList<File> extensions;

    public NoteMock(long id, String title, SpannableString content, long semester, String course, String category, String event, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.semester = semester;
        this.course = course;
        this.category = category;
        this.event = event;
        this.date = date;
    }

    public NoteMock(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = new SpannableString(content);
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

    public Spannable  getContent() {
        return content;
    }

    public void setContent(Spannable content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Method for adding a extension
     * @param attachment The extension which should be stored
     */
    public void addAttachment(File attachment){
        if (this.extensions==null){
            this.extensions=new ArrayList<>();
        }
        this.extensions.add(attachment);
    }
}

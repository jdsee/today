package com.mobila.project.today.model;

import java.util.Date;

public class TaskImpl implements Task {
    @Override
    public Course getCourse() {
        return null;
    }

    @Override
    public Date getDeadline() {
        return new Date(12122019);
    }

    @Override
    public void setDeadline(Date date) {

    }

    @Override
    public String getContent() {
        return "example task";
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public int getID() {
        return 0;
    }
}

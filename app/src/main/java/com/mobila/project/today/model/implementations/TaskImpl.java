package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

import java.util.Date;

public class TaskImpl implements Task {
    private final TaskDataAccess taskAccess;

    private final int ID;
    private String content;
    private Date deadline;

    public TaskImpl(int ID, String content, Date deadline) {
        this.ID = ID;
        this.content = content;
        this.deadline = deadline;

        this.taskAccess = OrganizerDataProvider.getInstance().getTaskDataAccess();
    }

    @Override
    public Course getCourse() throws DataKeyNotFoundException {
        return this.taskAccess.getCourse(this);
    }

    @Override
    public Date getDeadline() throws DataKeyNotFoundException {
        return this.deadline;
    }

    @Override
    public void setDeadline(Date date) throws DataKeyNotFoundException {
        this.taskAccess.setDeadline(this, date);
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) throws DataKeyNotFoundException {
        this.taskAccess.setContent(this, content);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

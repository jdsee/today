package com.mobila.project.today.model.implementations;

import android.os.Parcel;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

import java.util.Date;
import java.util.Objects;

public class TaskImpl implements Task {
    private final TaskDataAccess dataAccess;

    private final int ID;
    private String content;
    private Date deadline;

    public TaskImpl(int id, String content, Date deadline, TaskDataAccess dataAccess) {
        this.ID = id;
        this.content = content;
        this.deadline = deadline;

        this.dataAccess = dataAccess;
    }

    public TaskImpl(int id, String content, Date deadline) {
        this(id, content, deadline, OrganizerDataProvider.getInstance().getTaskDataAccess());
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public TaskImpl createFromParcel(Parcel source) {
            return new TaskImpl(source);
        }

        @Override
        public TaskImpl[] newArray(int size) {
            return new TaskImpl[size];
        }
    };

    private TaskImpl(Parcel in) {
        this(
                in.readInt(),
                in.readString(),
                new Date(in.readLong())
        );
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.ID);
        out.writeString(this.content);
        out.writeLong(this.deadline.getTime());
    }

    @Override
    public Course getCourse() throws DataKeyNotFoundException {
        return this.dataAccess.getCourse(this);
    }

    @Override
    public Date getDeadline() {
        if (this.deadline == null)
            this.deadline = this.dataAccess.getDeadline(this);
        return this.deadline;
    }

    @Override
    public void setDeadline(Date date) throws DataKeyNotFoundException {
        this.dataAccess.setDeadline(this, date);
        this.deadline = date;
    }

    @Override
    public String getContent() {
        //TODO: should the db be accessed here in any situation? constructor with content ensures actuallity -> set will update any changes to member
        if (this.content == null)
            this.content = this.dataAccess.getContent(this);
        return this.content;
    }

    @Override
    public void setContent(String content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
        this.content = content;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskImpl task = (TaskImpl) o;
        return ID == task.ID &&
                Objects.equals(dataAccess, task.dataAccess) &&
                Objects.equals(content, task.content) &&
                Objects.equals(deadline, task.deadline);
        //TODO when DataAccess is implemented: change inspection so that dataAccess must be nonNull
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataAccess, ID, content, deadline);
    }
}

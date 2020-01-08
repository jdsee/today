package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobila.project.today.model.dataProviding.dataAccess.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.TaskDataAccess;

import java.util.Date;
import java.util.Objects;

public class Task implements Identifiable, Parcelable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_TASK";
    private final TaskDataAccess dataAccess;

    private final String ID;
    private String content;
    private Date deadline;
    private Course relatedCourse;

    //TODO considering a relatedTo-Member for easy access to the parent course
    // -> useful in rootDataAccess

    public Task(String content) {
        this(
                KeyGenerator.getUniqueKey(),
                content,
                new Date()
        );
    }

    public Task(String id, String content, Date deadline, TaskDataAccess dataAccess) {
        this.ID = id;
        this.content = content;
        this.deadline = deadline;

        this.dataAccess = dataAccess;
    }

    public Task(String id, String content, Date deadline) {
        this(
                id,
                content,
                deadline,
                OrganizerDataProvider.getInstance().getTaskDataAccess()
        );
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    private Task(Parcel in) {
        this(
                in.readString(),
                in.readString(),
                new Date(in.readLong()));

    }

    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.ID);
        out.writeString(this.content);
        out.writeLong(this.deadline.getTime());
    }

    /**
     * Returns the course containing this task.
     *
     * @return the course containing this task
     */
    public Course getCourse() throws DataKeyNotFoundException {
        //TODO add attribute to class and add the corresponding parameter in constructor
        return null;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date date) throws DataKeyNotFoundException {
        this.dataAccess.setDeadline(this, date);
        this.deadline = date;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return this.ID.equals(task.ID) &&
                Objects.equals(this.content, task.content) &&
                Objects.equals(this.deadline, task.deadline);
        //TODO when DataAccess is implemented: change inspection so that dataAccess must be nonNull
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataAccess, ID, content, deadline);
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
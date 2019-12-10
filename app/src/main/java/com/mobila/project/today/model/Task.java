package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.TaskDataAccess;

import java.util.Date;
import java.util.Objects;

public class Task implements Identifiable, Parcelable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_TASK";
    private final TaskDataAccess dataAccess;

    private final int ID;
    private String content;
    private Date deadline;

    public Task(int id, String content, Date deadline, TaskDataAccess dataAccess) {
        this.ID = id;
        this.content = content;
        this.deadline = deadline;

        this.dataAccess = dataAccess;
    }

    public Task(int id, String content, Date deadline) {
        this(id, content, deadline, OrganizerDataProvider.getInstance().getTaskDataAccess());
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
                in.readInt(),
                in.readString(),
                new Date(in.readLong())
        );
    }

    public int describeContents() {
        return hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.ID);
        out.writeString(this.content);
        out.writeLong(this.deadline.getTime());
    }

    /**
     * Returns the course containing this task.
     *
     * @return the course containing this task
     */
    public Course getCourse() throws DataKeyNotFoundException {
        return this.dataAccess.getCourse(this);
    }

    public Date getDeadline() {
        if (this.deadline == null)
            this.deadline = this.dataAccess.getDeadline(this);
        return this.deadline;
    }

    public void setDeadline(Date date) throws DataKeyNotFoundException {
        this.dataAccess.setDeadline(this, date);
        this.deadline = date;
    }

    public String getContent() {
        //TODO: should the db be accessed here in any situation? constructor with content ensures actuallity -> set will update any changes to member
        if (this.content == null)
            this.content = this.dataAccess.getContent(this);
        return this.content;
    }

    public void setContent(String content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
        this.content = content;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return ID == task.ID &&
                Objects.equals(dataAccess, task.dataAccess) &&
                Objects.equals(content, task.content) &&
                Objects.equals(deadline, task.deadline);
        //TODO when DataAccess is implemented: change inspection so that dataAccess must be nonNull
    }

    public int hashCode() {
        return Objects.hash(dataAccess, ID, content, deadline);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
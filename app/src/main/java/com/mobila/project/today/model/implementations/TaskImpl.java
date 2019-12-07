package com.mobila.project.today.model.implementations;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

import java.util.Date;

public class TaskImpl implements Task, Parcelable {
    private final TaskDataAccess dataAccess;

    private final int ID;
    private String content;
    private Date deadline;

    public static final Creator<TaskImpl> CREATOR = new Creator<TaskImpl>() {
        @Override
        public TaskImpl createFromParcel(Parcel in) {
            return new TaskImpl(in);
        }

        @Override
        public TaskImpl[] newArray(int size) {
            return new TaskImpl[size];
        }
    };

    public TaskImpl(int id, String content, Date deadline) {
        this.ID = id;
        this.content = content;
        this.deadline = deadline;

        this.dataAccess = OrganizerDataProvider.getInstance().getTaskDataAccess();
    }

    private TaskImpl(Parcel in) {
        this(
                in.readInt(),
                in.readString(),
                new Date(in.readLong())
        );
    }

    @Override
    public Course getCourse() throws DataKeyNotFoundException {
        return this.dataAccess.getCourse(this);
    }

    @Override
    public Date getDeadline() throws DataKeyNotFoundException {
        return this.deadline;
    }

    @Override
    public void setDeadline(Date date) throws DataKeyNotFoundException {
        this.dataAccess.setDeadline(this, date);
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
    }

    @Override
    public int getID() {
        return this.ID;
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
}

package com.mobila.project.today.model.implementations;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

import java.util.Date;

public class TaskImpl implements Task, Parcelable {
    public TaskImpl(){}

    private TaskImpl(Parcel in) {
    }

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

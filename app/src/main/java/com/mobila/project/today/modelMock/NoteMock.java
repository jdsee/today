package com.mobila.project.today.modelMock;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;

import java.io.File;
import java.util.ArrayList;


public class NoteMock implements Parcelable {
    private long id;
    private String title;
    private Spannable content;
    private long semester;
    private String course;
    private String category;
    private String event;
    private String date;
    private ArrayList<File> attachments = new ArrayList<>();

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

    private NoteMock(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.semester = in.readLong();
        this.course = in.readString();
        this.category = in.readString();
        this.event = in.readString();
        this.date = in.readString();
    }

    public static final Creator<NoteMock> CREATOR = new Creator<NoteMock>() {
        @Override
        public NoteMock createFromParcel(Parcel in) {
            return new NoteMock(in);
        }

        @Override
        public NoteMock[] newArray(int size) {
            return new NoteMock[size];
        }
    };

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
        if (this.attachments ==null){
            this.attachments =new ArrayList<>();
        }
        this.attachments.add(attachment);
    }

    public File getAttachment(int position){
        return attachments.get(position);
    }

    public int getAttachmentCount(){
        return attachments.size();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(semester);
        dest.writeString(course);
        dest.writeString(category);
        dest.writeString(event);
        dest.writeString(date);
    }
}

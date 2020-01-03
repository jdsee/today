package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.AttachmentDataAccess;

import java.io.File;
import java.util.UUID;

public class Attachment implements Identifiable, Parcelable {

    private final AttachmentDataAccess dataAccess;

    private final String ID;
    private File content;

    public Attachment(String ID, @NonNull File content) {
        this.ID = ID;
        this.content = content;

        this.dataAccess = OrganizerDataProvider.getInstance().getAttachmentDataAccess();
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel source) {
            return null;
            //TODO
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[0];
        }
    };

    public Attachment(File content) {
        this(
                UUID.randomUUID().toString(),
                content
        );
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    public Lecture getLecture() throws DataKeyNotFoundException {
        return this.dataAccess.getLecture(this);
    }

    public File getContent() throws DataKeyNotFoundException {
        if (this.content == null)
            this.content = this.dataAccess.getContent(this);
        return this.content;
    }

    public void setContent(File content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
        this.content = content;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}

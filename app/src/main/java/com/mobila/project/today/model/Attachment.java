package com.mobila.project.today.model;

import android.net.Uri;
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
    private String name;
    private final Uri content;

    public Attachment(String ID, String name, Uri content) {
        this.ID = ID;
        this.content = content;
        this.name = name;
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

    public Attachment(String name, Uri content) {
        this(
                UUID.randomUUID().toString(),
                name,
                content
        );
    }


    @Override
    public int describeContents() {
        //todo
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //todo
    }

    public Uri getContent() throws DataKeyNotFoundException {
        return this.content;
    }

    @Override
    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }
}

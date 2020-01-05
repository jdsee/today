package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.LectureDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Lecture implements Identifiable, Parcelable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_LECTURE";
    private final RootDataAccess rootDataAccess;
    private final LectureDataAccess dataAccess;

    private final String ID;
    private int lectureNr;
    private Date date;
    private String roomNr;

    public Lecture(String id, int lectureNr, Date date, String roomNr) {
        this.ID = id;
        this.lectureNr = lectureNr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getLectureDataAccess();
        this.date = date;
        this.roomNr = roomNr;
    }

    public Lecture(int lectureNr, Date date, String roomNr) {
        this(
                KeyGenerator.getUniqueKey(),
                lectureNr, date, roomNr
        );
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel source) {
            return new Lecture(source);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    private Lecture(Parcel in) {
        this(in.readString(), in.readInt(), new Date(in.readLong()), in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeInt(this.lectureNr);
        dest.writeLong(date.getTime());
        dest.writeString(this.roomNr);
    }

    /**
     * Returns the section containing this lecture.
     *
     * @return the section containing this lecture
     */
    public Section getSection() throws DataKeyNotFoundException {
        return this.dataAccess.getSection(this);
    }

    public Note getNote() throws DataKeyNotFoundException {
        return this.dataAccess.getNote(this);
    }

    public List<Attachment> getAttachments() throws DataKeyNotFoundException {
        return this.dataAccess.getAttachments(this);
    }

    public void addAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.addAttachment(this, attachment);
    }

    public void removeAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.removeAttachment(this, attachment);
    }

    public int getLectureNr() throws DataKeyNotFoundException {
        return this.lectureNr;
    }

    public void setLectureNr(int number) throws DataKeyNotFoundException {
        this.lectureNr = number;
        this.dataAccess.setLectureNumber(this, number);
    }

    public String getRoomNr() throws DataKeyNotFoundException {
        return this.roomNr;
    }

    public void setRoomNr(String roomNr) throws DataKeyNotFoundException {
        this.roomNr = roomNr;
        this.dataAccess.setRoomNumber(this, roomNr);
    }

    public Date getDate() throws DataKeyNotFoundException {
        return this.date;
    }

    public void setDate(Date date) throws DataKeyNotFoundException {
        this.date = date;
        this.dataAccess.setDate(this, date);
    }

    @Override
    public String getID() {
        return this.ID;
    }
}

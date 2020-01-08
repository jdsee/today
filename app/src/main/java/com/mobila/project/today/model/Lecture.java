package com.mobila.project.today.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mobila.project.today.model.dataProviding.dataAccess.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.LectureDataAccess;

import java.util.Date;
import java.util.List;

public class Lecture implements Identifiable, Parcelable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_LECTURE";
    private final LectureDataAccess dataAccess;

    private final String ID;
    private int lectureNr;
    private Date date;
    private String roomNr;
    private List<Attachment> attachments;

    public Lecture(String id, int lectureNr, Date date, String roomNr) {
        this.ID = id;
        this.lectureNr = lectureNr;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
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
        Note note = this.dataAccess.getNote(this);
        return note != null ? note : new Note();
    }

    public List<Attachment> getAttachments() throws DataKeyNotFoundException {
        if (this.attachments == null)
            this.attachments = this.dataAccess.getAttachments(this);
        return attachments;
    }

    public void addAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.addAttachment(this, attachment);
        this.attachments.add(attachment);
    }

    public void removeAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.removeAttachment(this, attachment);
        this.attachments.remove(attachment);
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

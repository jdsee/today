package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.AttachmentTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.LectureTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.NoteTable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LectureDataAccessImpl implements LectureDataAccess {

    private static LectureDataAccessImpl instance;

    private final SQLiteDatabase database;
    private Note note;
    private IdentityMapper<Attachment> attachmentCache;

    static LectureDataAccess getInstance() {
        if (instance == null)
            instance = new LectureDataAccessImpl();
        return instance;
    }

    private LectureDataAccessImpl() {
        this(
                new IdentityMapper<>(),
                OrganizerDataProvider.getInstance().getDatabase()
        );
    }

    private LectureDataAccessImpl(IdentityMapper<Attachment> attachmentCache, SQLiteDatabase database){
        this.attachmentCache = attachmentCache;
        this.database= database;
    }

    @Override
    public Section getSection(Identifiable lecture) throws DataKeyNotFoundException {
        //TODO
        return null;
    }

    @Override
    public Note getNote(Identifiable lecture) throws DataKeyNotFoundException {
        if(this.note==null) {
            Cursor cursor = this.database.query(NoteTable.TABLE_NAME, NoteTable.ALL_COLUMNS,
                    NoteTable.COLUMN_RELATED_TO + "=?", new String[]{lecture.getID()},
                    null, null, null);
            if(cursor.moveToNext()){
                this.note=new Note(
                        cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TITLE))
                );
            } this.note = new Note();
            cursor.close();
        }
        return note;
    }

    @Override
    public List<Attachment> getAttachments(Identifiable lecture) throws DataKeyNotFoundException {
        List<Attachment> attachments = this.attachmentCache.get(lecture);
        if (attachments == null){
            Cursor cursor = this.database.query(AttachmentTable.TABLE_NAME, AttachmentTable.ALL_COLUMNS,
                    AttachmentTable.COLUMN_RELATED_TO + "=?", new String[]{lecture.getID()},
                    null, null, AttachmentTable.COLUMN_NAME);
            attachments = new LinkedList<>();
            while (cursor.moveToNext()){
                Attachment attachment = new Attachment(
                        cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_NAME)),
                        Uri.parse(cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_URI)))
                );
                attachments.add(attachment);
            }
            cursor.close();
        }
        return attachments;
    }

    @Override
    public void addAttachment(Identifiable lecture, Attachment attachment) throws DataKeyNotFoundException {
        this.attachmentCache.addElement(note, attachment);
        ContentValues values = new ContentValues();
        values.put(AttachmentTable.COLUMN_ID, attachment.getID());
        values.put(AttachmentTable.COLUMN_NAME, attachment.getName());
        values.put(AttachmentTable.COLUMN_URI, attachment.getContent().toString());
        values.put(AttachmentTable.COLUMN_RELATED_TO, lecture.getID());
    }

    @Override
    public void removeAttachment(Identifiable lecture, Attachment attachment) {
        this.database.delete(AttachmentTable.TABLE_NAME,
                AttachmentTable.COLUMN_ID + "=?", new String[]{attachment.getID()});
        this.attachmentCache.removeElement(lecture, attachment);
    }

    @Override
    public void setLectureNumber(Identifiable lecture, int number) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(LectureTable.COLUMN_NR, number);
        this.database.update(LectureTable.TABLE_NAME, values,
                AttachmentTable.COLUMN_ID + "=?", new String[]{lecture.getID()});
    }


    @Override
    public void setRoomNumber(Identifiable lecture, String roomNumber) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(LectureTable.COLUMN_ROOM_NR, roomNumber);
        this.database.update(LectureTable.TABLE_NAME, values,
                AttachmentTable.COLUMN_ID + "=?", new String[]{lecture.getID()});
    }

    @Override
    public void setDate(Identifiable lecture, Date date) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(LectureTable.COLUMN_ROOM_NR, date.getTime());
        this.database.update(LectureTable.TABLE_NAME, values,
                AttachmentTable.COLUMN_ID + "=?", new String[]{lecture.getID()});
    }
}

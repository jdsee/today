package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.AttachmentTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.LectureTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.NoteTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SectionTable;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LectureDataAccessImpl extends ParentDataAccessImpl implements LectureDataAccess {

    private static LectureDataAccessImpl instance;

    private IdentityMapper<Attachment> attachmentCache;

    static LectureDataAccess getInstance() {
        if (instance == null)
            instance = new LectureDataAccessImpl();
        return instance;
    }

    private LectureDataAccessImpl() {
        this.attachmentCache = new IdentityMapper<>();
    }

    /**
     * SUPPOSED FOR TESTING REASONS ONLY!
     * do not use this for creating new objects!
     *
     * @param attachmentCache
     * @param database
     */
    LectureDataAccessImpl(IdentityMapper<Attachment> attachmentCache, SQLiteDatabase database) {
        this.attachmentCache = attachmentCache;
        this.database = database;
    }

    @Override
    public Section getSection(Identifiable lecture) throws DataKeyNotFoundException {
        //Getting parentID of lecture
        Cursor lectureCursor = this.database.query(LectureTable.TABLE_NAME, new String[]{LectureTable.COLUMN_RELATED_TO},
                LectureTable.COLUMN_ID + "=?", new String[]{lecture.getID()},
                null, null, null);
        if (!lectureCursor.moveToNext())
            throw new DataKeyNotFoundException("We have an Orphan!");
        String sectionID = lectureCursor.getString(lectureCursor.getColumnIndex(LectureTable.COLUMN_RELATED_TO));
        lectureCursor.close();
        //Getting parent of lecture
        Cursor sectionCursor = this.database.query(SectionTable.TABLE_NAME, SectionTable.ALL_COLUMNS,
                SectionTable.COLUMN_ID + "=?", new String[]{sectionID},
                null, null, null);
        sectionCursor.moveToFirst();
        Section section = new Section(
                sectionCursor.getString(sectionCursor.getColumnIndex(SectionTable.COLUMN_ID)),
                sectionCursor.getString(sectionCursor.getColumnIndex(SectionTable.COLUMN_TITLE)),
                sectionCursor.getString(sectionCursor.getColumnIndex(SectionTable.COLUMN_LECTURER))
        );
        sectionCursor.close();
        return section;
    }

    @Override
    public Note getNote(Identifiable lecture) throws DataKeyNotFoundException {
        Note note = null;
        Cursor cursor = this.database.query(NoteTable.TABLE_NAME, null,
                NoteTable.COLUMN_RELATED_TO + "=?", new String[]{lecture.getID()},
                null, null, null);
        if (cursor.moveToNext()) {
            note = new Note(
                    cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TITLE))
            );
        } else
            //TODO constructor for note
            note = this.addNoteToDB(lecture);

        cursor.close();
        return note;
    }

    private Note addNoteToDB(Identifiable lecture) {
        Note note = new Note();
        ContentValues values = new ContentValues();

        values.put(NoteTable.COLUMN_ID, note.getID());
        values.put(NoteTable.COLUMN_TITLE, note.getTitle());
        values.put(NoteTable.COLUMN_CONTENT, "");
        values.put(NoteTable.COLUMN_RELATED_TO, lecture.getID());

        this.database.insert(NoteTable.TABLE_NAME, null, values);
        return note;
    }

    @Override
    public List<Attachment> getAttachments(Identifiable lecture) throws DataKeyNotFoundException {
        List<Attachment> attachments = this.attachmentCache.get(lecture);
        if (attachments == null) {
            Cursor cursor = this.database.query(AttachmentTable.TABLE_NAME, AttachmentTable.ALL_COLUMNS,
                    AttachmentTable.COLUMN_RELATED_TO + "=?", new String[]{lecture.getID()},
                    null, null, AttachmentTable.COLUMN_NAME);
            attachments = new LinkedList<>();
            while (cursor.moveToNext()) {
                String attachmentId = cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_ID));
                String attachmentName = cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_NAME));
                String uriString = cursor.getString(cursor.getColumnIndex(AttachmentTable.COLUMN_URI));
                Uri uri = Uri.parse(uriString);

                Attachment attachment = new Attachment(attachmentId, attachmentName, uri);

                System.out.println("URI WHEN READING FROM DATABASE: " + uri);
                System.out.println("URI STRING WHEN READING FROM DATABASE: " + uriString);

                attachments.add(attachment);
            }
            this.attachmentCache.add(lecture, attachments);
            cursor.close();
        }
        return attachments;
    }

    @Override
    public void addAttachment(Identifiable lecture, Attachment attachment) throws DataKeyNotFoundException {
        this.attachmentCache.addElement(lecture, attachment);
        ContentValues values = new ContentValues();
        values.put(AttachmentTable.COLUMN_ID, attachment.getID());
        values.put(AttachmentTable.COLUMN_NAME, attachment.getName());
        Uri uri = attachment.getContent();
        String uriString = uri.getPath();
        values.put(AttachmentTable.COLUMN_URI, uri.toString());

        System.out.println("URI WHEN WRITING IN DATABASE: " + uri);
        System.out.println("URI STRING WHEN WRITING IN DATABASE: " + uriString);

        values.put(AttachmentTable.COLUMN_RELATED_TO, lecture.getID());

        this.database.insert(AttachmentTable.TABLE_NAME, null, values);
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

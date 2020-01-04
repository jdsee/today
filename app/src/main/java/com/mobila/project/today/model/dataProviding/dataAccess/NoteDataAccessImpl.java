package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.NoteReference;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.NoteReferenceTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.NoteTable;

import java.util.List;

class NoteDataAccessImpl implements NoteDataAccess {
    private static final String TAG = NoteDataAccessImpl.class.getName();
    private static final String NO_CONTENT_FOR_NOTE_MSG = "no content found for given course";
    private static final String NO_REFERENCES_FOR_NOTE_MSG = "no note references found for given course";

    private SQLiteDatabase database;

    private NoteDataAccessImpl(){
        this.database = OrganizerDataProvider.getInstance().getDatabase();
    }

    @Override
    public void setTitle(Identifiable note, String title) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_TITLE, title);
        this.database.update(NoteTable.TABLE_NAME, values,
                NoteTable.COLUMN_ID + "=?", new String[]{note.getID()});
    }

    @Override
    public Spannable getContent(Identifiable note) throws DataKeyNotFoundException {
        Cursor cursor = this.database.query(
                NoteTable.TABLE_NAME,
                new String[]{NoteTable.COLUMN_CONTENT},
                NoteTable.COLUMN_ID + "=?",
                new String[]{note.getID()},
                null, null, null
        );

        if (!cursor.moveToNext()) {
            DataKeyNotFoundException t = new DataKeyNotFoundException(DataKeyNotFoundException.NO_ENTRY_MSG);
            Log.d(TAG, DataKeyNotFoundException.NO_ENTRY_MSG + ": " + NO_CONTENT_FOR_NOTE_MSG, t);
            throw t;
        }

        String contentHtml = cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_CONTENT));
        return new SpannableString(Html.fromHtml(contentHtml, Html.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public void setContent(Identifiable note, Spannable content) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_CONTENT, Html.toHtml(content, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
        this.database.update(NoteTable.TABLE_NAME, values,
                NoteTable.COLUMN_ID + "=?s", new String[]{note.getID()});
    }

    @Override
    public List<NoteReference> getReferences(Identifiable note) throws DataKeyNotFoundException {
        Cursor cursor = this.database.query(
                NoteReferenceTable.TABLE_NAME,
                null,
                NoteReferenceTable.COLUMN_NOTE_ID + "=",
                new String[]{note.getID()},
                null, null, null
        );

        if (!cursor.moveToNext()) {
            DataKeyNotFoundException t = new DataKeyNotFoundException(DataKeyNotFoundException.NO_ENTRY_MSG);
            Log.d(TAG, DataKeyNotFoundException.NO_ENTRY_MSG + ": " + NO_REFERENCES_FOR_NOTE_MSG, t);
            throw t;
        }

        do {
            /*NoteReference noteReference = new NoteReference(
                    cursor.getString(cursor.getColumnIndex(NoteReferenceTable.COLUMN_ID)),

            );*/
        } while(cursor.moveToNext());

        return null;
    }

    @Override
    public void addReference(Identifiable note, Identifiable reference, int row) throws DataKeyNotFoundException {

    }

    @Override
    public void removeReference(Identifiable reference) {

    }
}

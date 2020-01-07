package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.NoteTable;

class NoteDataAccessImpl extends ParentDataAccessImpl implements NoteDataAccess {
    private static NoteDataAccessImpl instance;

    private static final String TAG = NoteDataAccessImpl.class.getName();
    private static final String NO_CONTENT_FOR_NOTE_MSG = "no content found for given course";

    static NoteDataAccess getInstance() {
        if (instance == null)
            instance = new NoteDataAccessImpl();
        return instance;
    }

    private NoteDataAccessImpl() {
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

        String contentHtml = "";
        if (cursor.moveToFirst())
            contentHtml = cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_CONTENT));
        cursor.close();

        return new SpannableString(Html.fromHtml(contentHtml, Html.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public void setContent(Identifiable note, Spannable content) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_CONTENT, Html.toHtml(content, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
        this.database.update(NoteTable.TABLE_NAME, values,
                NoteTable.COLUMN_ID + "=?", new String[]{note.getID()});
    }
}

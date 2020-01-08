package com.mobila.project.today.model.dataProviding.dataAccess;

import android.text.Spannable;

import com.mobila.project.today.model.Identifiable;

public interface NoteDataAccess extends ParentDataAccess{

    static NoteDataAccess getInstance() {
        return NoteDataAccessImpl.getInstance();
    }

    void setTitle(Identifiable note, String title) throws DataKeyNotFoundException;

    Spannable getContent(Identifiable note) throws DataKeyNotFoundException;

    void setContent(Identifiable note, Spannable content) throws DataKeyNotFoundException;
}

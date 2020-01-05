package com.mobila.project.today.model.dataProviding.dataAccess;

import android.text.Spannable;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;

public interface NoteDataAccess {

    static NoteDataAccess getInstance() {
        return NoteDataAccessImpl.getInstance();
    }

    void setTitle(Identifiable note, String title) throws DataKeyNotFoundException;

    Spannable getContent(Identifiable note) throws DataKeyNotFoundException;

    void setContent(Identifiable note, Spannable content) throws DataKeyNotFoundException;

    //List<NoteReference> getReferences(Identifiable note) throws DataKeyNotFoundException;

    //void addReference(Identifiable note, Identifiable reference, int row) throws DataKeyNotFoundException;

    //void removeReference(Identifiable reference);
}

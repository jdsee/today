package com.mobila.project.today.model;

import android.text.Spannable;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.util.List;
import java.util.UUID;

public class Note implements Identifiable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_NOTE";

    private final NoteDataAccess noteDataAccess;

    private final String ID;
    private String title;

    public Note(String ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.noteDataAccess = dataProvider.getNoteDataAccess();
    }

    private Note(String title) {
        this(
                KeyGenerator.getUniqueKey(),
                title
        );
    }

    public Note(){
        this("");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.noteDataAccess.setTitle(this, title);
    }

    public Spannable getContent() throws DataKeyNotFoundException {
        return this.noteDataAccess.getContent(this);
    }

    public void setContent(Spannable content) throws DataKeyNotFoundException {
        this.noteDataAccess.setContent(this, content);
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
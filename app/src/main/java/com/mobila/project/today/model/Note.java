package com.mobila.project.today.model;

import android.text.Spannable;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.util.List;

public class Note implements Identifiable {
    public static final String INTENT_EXTRA_CODE = "EXTRA_NOTE";

    private final RootDataAccess rootDataAccess;
    private final NoteDataAccess noteDataAccess;

    private final String ID;
    private String title;


    public Note(String ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.noteDataAccess = dataProvider.getNoteDataAccess();
    }

    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    public Lecture getLecture() throws DataKeyNotFoundException {
        return this.noteDataAccess.getLecture(this);
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

    public List<NoteReference> getReferences() throws DataKeyNotFoundException {
        return this.noteDataAccess.getReferences(this);
    }

    public void addReference(Identifiable ref, int row) throws DataKeyNotFoundException {
        this.noteDataAccess.addReference(this, ref, row);
    }

    public void removeReference(Identifiable ref) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(ref);
    }
    @Override
    public String getID() {
        return this.ID;
    }
}
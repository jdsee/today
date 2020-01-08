package com.mobila.project.today.model;

import android.text.Spannable;

import com.mobila.project.today.model.dataProviding.dataAccess.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.NoteDataAccess;

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

    public Note(String title) {
        this(
                KeyGenerator.getUniqueKey(),
                title
        );
    }

    public Note(){
        this("");
    }

    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    public Lecture getLecture() throws DataKeyNotFoundException {
        //TODO return lecture or remove method
        return null;
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

   /* public List<NoteReference> getReferences() throws DataKeyNotFoundException {
        return this.noteDataAccess.getReferences(this);
    }

    public void addReference(Identifiable reference, int row) throws DataKeyNotFoundException {
        this.noteDataAccess.addReference(this, reference, row);
    }

    public void removeReference(Identifiable reference) throws DataKeyNotFoundException {
        this.noteDataAccess.removeReference(reference);
    }*/

    @Override
    public String getID() {
        return this.ID;
    }
}
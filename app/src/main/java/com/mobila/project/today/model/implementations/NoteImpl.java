package com.mobila.project.today.model.implementations;

import android.text.Spannable;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.NoteReference;

import java.util.List;

class NoteImpl implements Note {
    private final RootDataAccess rootDataAccess;
    private final NoteDataAccess dataAccess;

    private final int ID;
    private String title;

    public NoteImpl(int ID, String title) {
        this.ID = ID;
        this.title = title;

        OrganizerDataProvider dataProvider = OrganizerDataProvider.getInstance();
        this.rootDataAccess = dataProvider.getRootDataAccess();
        this.dataAccess = dataProvider.getNoteDataAccess();
    }

    @Override
    public Lecture getLecture() throws DataKeyNotFoundException {
        return this.dataAccess.getLecture(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) throws DataKeyNotFoundException {
        this.title = title;
        this.dataAccess.setTitle(this, title);
    }

    @Override
    public Spannable getContent() throws DataKeyNotFoundException {
        return this.dataAccess.getContent(this);
    }

    @Override
    public void setContent(Spannable content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
    }

    @Override
    public List<Attachment> getAttachments() throws DataKeyNotFoundException {
        return this.dataAccess.getAttachments(this);
    }

    @Override
    public void addAttachment(Attachment attachment) throws DataKeyNotFoundException {
        this.dataAccess.addAttachment(this, attachment);
    }

    @Override
    public void removeAttachment(Identifiable attachment) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(attachment);
    }

    @Override
    public List<NoteReference> getReferences() throws DataKeyNotFoundException {
        return this.dataAccess.getReferences(this);
    }

    @Override
    public void addReference(Identifiable ref, int row) throws DataKeyNotFoundException {
        this.dataAccess.addReference(this, ref, row);
    }

    @Override
    public void removeReference(Identifiable ref) throws DataKeyNotFoundException {
        this.rootDataAccess.removeEntityInstance(ref);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

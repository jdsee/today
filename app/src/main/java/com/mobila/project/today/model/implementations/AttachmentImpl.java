package com.mobila.project.today.model.implementations;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.dataProviding.dataAccess.AttachmentDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.NoteDataAccess;
import com.mobila.project.today.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Lecture;

import java.io.File;

class AttachmentImpl implements Attachment {
    private final AttachmentDataAccess dataAccess;

    private final int ID;
    private String title;
    private int position;

    public AttachmentImpl(int ID, String title, int position) {
        this.ID = ID;
        this.title = title;
        this.position = position;

        this.dataAccess = OrganizerDataProvider.getInstance().getAttachmentDataAccess();
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
    public File getContent() throws DataKeyNotFoundException {
        return this.dataAccess.getContent(this);
    }

    @Override
    public void setContent(File content) throws DataKeyNotFoundException {
        this.dataAccess.setContent(this, content);
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(int position) throws DataKeyNotFoundException {
        this.position = position;
        this.dataAccess.setPosition(this, position);
    }

    @Override
    public int getID() {
        return this.ID;
    }
}

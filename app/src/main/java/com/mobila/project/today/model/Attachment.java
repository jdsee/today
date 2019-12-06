package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.implementations.AttachmentImpl;

import java.io.File;

public interface Attachment extends Identifiable {

    static Attachment createAttachment(int ID, String title) {
        return new AttachmentImpl(ID, title);
    }

    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    Lecture getLecture() throws DataKeyNotFoundException;

    String getTitle();

    void setTitle(String title) throws DataKeyNotFoundException;

    File getContent() throws DataKeyNotFoundException;

    void setContent(File content) throws DataKeyNotFoundException;
}

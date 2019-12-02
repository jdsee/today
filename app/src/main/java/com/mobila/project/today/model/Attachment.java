package com.mobila.project.today.model;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.io.File;

public interface Attachment extends Identifiable {
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

    int getPosition();

    void setPosition(int position) throws DataKeyNotFoundException;
}

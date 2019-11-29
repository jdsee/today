package com.mobila.project.today.model;

import java.io.File;

public interface Attachment<T> extends Identifiable {
    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    Lecture getLecture();


    String getTitle();

    void setTitle();


    File getContent();

    void setContent(File content);


    int getPosition();

    void setPosition();
}

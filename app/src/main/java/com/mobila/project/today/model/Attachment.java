package com.mobila.project.today.model;

public interface Attachment<T> extends Identifiable {
    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    Lecture getLecture();

    T getContent();

    void addContent(T content);

    void removeContent(Identifiable content);

    String getTitle();

    void setTitle();
}

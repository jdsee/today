package com.mobila.project.today.model;

import android.text.Spannable;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface Note extends Identifiable {
    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    Lecture getLecture();


    String getTitle();

    void setTitle(String title);


    Spannable getContent();

    void setContent(Spannable content);


    List<Attachment> getAttachments();

    void addAttachment(Attachment attachment);

    void removeAttachment(Identifiable attachment);


    List<NoteReference> getReferences();

    void addReference(Identifiable ref, int row);

    void removeReference(Identifiable ref);
}

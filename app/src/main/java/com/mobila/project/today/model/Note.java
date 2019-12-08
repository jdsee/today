package com.mobila.project.today.model;

import android.text.Spannable;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;

import java.util.List;

/**
 *
 */
public interface Note extends Identifiable {
    String INTENT_EXTRA_CODE = "EXTRA_NOTE";

    /**
     * Returns the lecture containing this attachment.
     *
     * @return the lecture containing this attachment
     */
    Lecture getLecture() throws DataKeyNotFoundException;

    String getTitle();

    void setTitle(String title) throws DataKeyNotFoundException;

    Spannable getContent() throws DataKeyNotFoundException;

    void setContent(Spannable content) throws DataKeyNotFoundException;

    List<NoteReference> getReferences() throws DataKeyNotFoundException;

    void addReference(Identifiable ref, int row) throws DataKeyNotFoundException;

    void removeReference(Identifiable ref) throws DataKeyNotFoundException;
}

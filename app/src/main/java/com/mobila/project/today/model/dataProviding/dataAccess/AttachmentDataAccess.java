package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;

import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;

import java.io.File;

public interface AttachmentDataAccess {

    static AttachmentDataAccess getInstance() {
        return AttachmentDataAccessImpl.getInstance();
    }

    Lecture getLecture(Identifiable attachment) throws DataKeyNotFoundException;

    String getTitle(Identifiable attachment) throws DataKeyNotFoundException;

    void setTitle(Identifiable attachment, String title) throws DataKeyNotFoundException;

    File getContent(Identifiable attachment) throws DataKeyNotFoundException;

    void setContent(Identifiable attachment, File file) throws DataKeyNotFoundException;

    int getPosition(Identifiable attachment) throws DataKeyNotFoundException;

    void setPosition(Identifiable attachment, int position) throws DataKeyNotFoundException;
}

package com.mobila.project.today.model.dataProviding.dataAccess;

import android.net.Uri;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;

public interface AttachmentDataAccess {

    static AttachmentDataAccess getInstance() {
        return AttachmentDataAccessImpl.getInstance();
    }

    String getTitle(Identifiable attachment) throws DataKeyNotFoundException;

    void setTitle(Identifiable attachment, String title) throws DataKeyNotFoundException;

    Uri getContent(Identifiable attachment) throws DataKeyNotFoundException;

    void setContent(Identifiable attachment, Uri file) throws DataKeyNotFoundException;
}

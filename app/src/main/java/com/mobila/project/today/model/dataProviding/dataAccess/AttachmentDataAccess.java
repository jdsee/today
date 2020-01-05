package com.mobila.project.today.model.dataProviding.dataAccess;

import android.net.Uri;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;

public interface AttachmentDataAccess {

    static AttachmentDataAccess getInstance() {
        return AttachmentDataAccessImpl.getInstance();
    }

    void setName(Identifiable attachment, String title) throws DataKeyNotFoundException;
}

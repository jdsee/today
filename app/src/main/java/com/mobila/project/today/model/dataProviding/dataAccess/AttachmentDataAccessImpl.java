package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;

import com.mobila.project.today.model.dataProviding.dataAccess.databank.AttachmentTable;

public class AttachmentDataAccessImpl extends ParentDataAccessImpl implements AttachmentDataAccess {
    private static AttachmentDataAccess instance;

    static AttachmentDataAccess getInstance() {
        if (instance == null)
            instance = new AttachmentDataAccessImpl();
        return instance;
    }

    private AttachmentDataAccessImpl(){
        super();
    }

    @Override
    public void setName(Identifiable attachment, String title) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(AttachmentTable.COLUMN_NAME, title);
        this.database.update(AttachmentTable.TABLE_NAME, values,
                AttachmentTable.COLUMN_ID + "=?", new String[]{attachment.getID()});
    }
}

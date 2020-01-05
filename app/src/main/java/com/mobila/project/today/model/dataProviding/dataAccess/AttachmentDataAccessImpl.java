package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.AttachmentTable;

public class AttachmentDataAccessImpl implements AttachmentDataAccess {
    private static AttachmentDataAccess instance;

    private SQLiteDatabase database;

    static AttachmentDataAccess getInstance() {
        if (instance == null)
            instance = new AttachmentDataAccessImpl();
        return instance;
    }

    private AttachmentDataAccessImpl(){
        this.database = OrganizerDataProvider.getInstance().getDatabase();
    }

    @Override
    public void setName(Identifiable attachment, String title) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(AttachmentTable.COLUMN_NAME, title);
        this.database.update(AttachmentTable.TABLE_NAME, values,
                AttachmentTable.COLUMN_ID + "=?", new String[]{attachment.getID()});
    }
}

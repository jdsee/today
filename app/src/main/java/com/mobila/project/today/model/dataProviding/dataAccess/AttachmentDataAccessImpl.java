package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import java.io.File;

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
    public Lecture getLecture(Identifiable attachment) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public String getTitle(Identifiable attachment) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setTitle(Identifiable attachment, String title) throws DataKeyNotFoundException {

    }

    @Override
    public File getContent(Identifiable attachment) throws DataKeyNotFoundException {
        return null;
    }

    @Override
    public void setContent(Identifiable attachment, File file) throws DataKeyNotFoundException {

    }

    @Override
    public int getPosition(Identifiable attachment) throws DataKeyNotFoundException {
        return 0;
    }

    @Override
    public void setPosition(Identifiable attachment, int position) throws DataKeyNotFoundException {

    }
}

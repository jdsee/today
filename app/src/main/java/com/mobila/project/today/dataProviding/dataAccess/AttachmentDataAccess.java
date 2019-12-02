package com.mobila.project.today.dataProviding.dataAccess;

import com.mobila.project.today.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;

import java.io.File;

public interface AttachmentDataAccess {

    Lecture getLecture(Identifiable attachment) throws DataKeyNotFoundException;


    String getTitle(Identifiable attachment) throws DataKeyNotFoundException;

    void setTitle(Identifiable attachment, String title) throws DataKeyNotFoundException;


    File getContent(Identifiable attachment) throws DataKeyNotFoundException;

    void setContent(Identifiable attachment, File file) throws DataKeyNotFoundException;


    int getPosition(Identifiable attachment) throws DataKeyNotFoundException;

    void setPosition(Identifiable attachment, int position) throws DataKeyNotFoundException;

    //test
}

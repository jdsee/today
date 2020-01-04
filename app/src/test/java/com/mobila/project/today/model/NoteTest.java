package com.mobila.project.today.model;

import com.mobila.project.today.model.dataProviding.dataAccess.NoteDataAccess;

import org.junit.Before;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NoteTest {
    private Note note;
    private String noteId;
    private String noteTitle;
    private NoteDataAccess dataAccessMock;

    @Before
    public void setup() {
        this.note = new Note(this.noteId, this.noteTitle);
        this.dataAccessMock = Mockito.mock(NoteDataAccess.class);
    }
}
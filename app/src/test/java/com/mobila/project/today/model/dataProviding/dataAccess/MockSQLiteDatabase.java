package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

public class MockSQLiteDatabase {
    private SQLiteDatabase mockedDatabase;
    private Cursor mockedCursor;

    public SQLiteDatabase getMockedDatabase(){
        return this.mockedDatabase;
    }

    public MockSQLiteDatabase() {
        this.mockedDatabase = Mockito.mock(SQLiteDatabase.class);
        this.mockedCursor = Mockito.mock(Cursor.class);
        this.setupMock();
    }

    private void setupMock() {
        this.setupCursor();
        this.setupQuery();
        this.setupInsert();
        this.setupRemove();
    }

    private void setupCursor() {
        Mockito.when(this.mockedCursor.moveToNext()).thenReturn(false);
    }

    private void setupQuery() {
        Mockito.when(this.mockedDatabase.query(
                anyString(),
                nullable(String[].class),
                nullable(String.class),
                nullable(String[].class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class)
        )).thenReturn(this.mockedCursor);
    }

    private void setupInsert() {
        Mockito.when(this.mockedDatabase.insert(
                anyString(),
                nullable(String.class),
                any(ContentValues.class)
        )).thenReturn(0L);
    }
    private void setupRemove() {
        Mockito.when(this.mockedDatabase.delete(
                anyString(),
                anyString(),
                any(String[].class)
        ));
    }
}

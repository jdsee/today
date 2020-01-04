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
    private boolean cursorMoveToNext;
    private int cursorMoveToNextTimes;

    public SQLiteDatabase getMockedDatabase() {
        return this.mockedDatabase;
    }

    public MockSQLiteDatabase() {
        this.mockedDatabase = Mockito.mock(SQLiteDatabase.class);
        this.mockedCursor = Mockito.mock(Cursor.class);
        this.cursorMoveToNext = true;
        this.setupMock();
    }

    /**
     * The database will by default simulate to contain at least one entry by returning a cursor
     * object that returns true one time when moveToNext() is called.
     * <p>
     * This method will change that behaviour so that moveToNext() returns false.
     * <p>
     * That makes it possible to simulate an empty course, e.g. no findings for the call on the db.
     *
     * @return the modified MockSQLiteDatabase-object
     */
    public MockSQLiteDatabase setTableEmpty() {
        this.setupCursor(false);
        this.setupQuery();
        return this;
    }

    public MockSQLiteDatabase setMockedCursor(Cursor mockedCursor) {
        this.mockedCursor = mockedCursor;
        return this;
    }

    private void setupMock() {
        this.setupCursor(true);
        this.setupQuery();
        this.setupInsert();
        //this.setupRemove();
    }

    private void setupCursor(boolean hasEntry) {
        this.cursorMoveToNextTimes = hasEntry ? 2 : 0;
        Mockito.when(this.mockedCursor.moveToNext()).thenAnswer(answer -> {
            this.cursorMoveToNextTimes--;
            System.out.println("cursorMoveToNextTimes: " + this.cursorMoveToNextTimes);
            return cursorMoveToNextTimes > 0;
        });
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


    /*private void setupRemove() {
        Mockito.when(this.mockedDatabase.delete(
                anyString(),
                anyString(),
                any(String[].class)
        ));
    }*/
}

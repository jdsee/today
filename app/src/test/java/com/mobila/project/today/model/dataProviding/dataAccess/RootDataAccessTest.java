package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.MockContext;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

public class RootDataAccessTest {
    private RootDataAccessImpl dataAccess;

    private SQLiteDatabase databaseMock;
    private Semester semesterMock1;
    private List<Semester> semesterMocks;
    @Mock
    private Task taskMock1;
    @Mock
    private Task taskMock2;
    @Mock
    private Task taskMock3;
    private List<Task> taskMocks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.taskMocks = new LinkedList<>();
        this.taskMocks.add(this.taskMock1);
        this.taskMocks.add(this.taskMock2);
        this.taskMocks.add(this.taskMock3);

        this.semesterMock1 = this.getSemesterMock("SemesterMock1", 1);
        this.semesterMock1 = this.getSemesterMock("SemesterMock2", 2);
        this.semesterMock1 = this.getSemesterMock("SemesterMock3", 3);
        this.semesterMocks = new LinkedList<>();
        this.semesterMocks.add(this.semesterMock1);

        Cursor mockedCursor = this.getMockedCursor();
        this.databaseMock = new MockSQLiteDatabase()
                .setMockedCursor(mockedCursor)
                .getMockedDatabase();
        Context contextMock = new MockContext().getMockedContext();
        this.dataAccess = new RootDataAccessImpl(this.databaseMock);
    }

    private int moveToNextTimes;

    private Cursor getMockedCursor() {
        Cursor mockedCursor = Mockito.mock(Cursor.class);
        this.moveToNextTimes = semesterMocks.size();
        Mockito.when(mockedCursor.moveToNext()).
                thenAnswer(answer -> {
                    this.moveToNextTimes--;
                    return this.moveToNextTimes > 0;
                });
        Mockito.when(mockedCursor.getColumnIndex(SemesterTable.COLUMN_ID)).thenReturn(0);
        Mockito.when((mockedCursor.getString(0)))
                .thenAnswer(answer -> {
                    Semester semester = this.semesterMocks.get(this.moveToNextTimes);
                    return semester != null ? semester.getID() : "no mock object found";
                });
        Mockito.when(mockedCursor.getColumnIndex(SemesterTable.COLUMN_NR)).thenReturn(1);
        Mockito.when(mockedCursor.getInt(1))
                .thenAnswer(answer -> {
                    Semester semester = this.semesterMocks.get(this.moveToNextTimes);
                    return semester != null ? semester.getSemesterNr() : 666;
                });
        return mockedCursor;
    }

    private Semester getSemesterMock(String mockId, int mockNr) {
        Semester mockedSemester = Mockito.mock(Semester.class);
        Mockito.when(mockedSemester.getID()).thenReturn(mockId);
        Mockito.when(mockedSemester.getSemesterNr()).thenReturn(mockNr);
        return mockedSemester;
    }

    private void verifyQueryOnDatabase(int wantedNumberOfInvocations) {
        Mockito.verify(this.databaseMock, Mockito.times(wantedNumberOfInvocations))
                .query(
                        anyString(),
                        nullable(String[].class),
                        nullable(String.class),
                        nullable(String[].class),
                        nullable(String.class),
                        nullable(String.class),
                        nullable(String.class));
    }

    @Test
    public void getAllSemestersInitially_Test() {
        //exercise
        this.dataAccess.getAllSemesters();
        //verify
        this.verifyQueryOnDatabase(1);
    }

    @Test
    public void getAllSemestersWhenDatabaseWasAlreadyAccessedBefore_Test() {
        //exercise
        this.dataAccess.getAllSemesters();

        //verify
        this.verifyQueryOnDatabase(1);
    }

    @Test
    public void addSemester() {
        //setup
        ContentValues mockValues = new ContentValues();
        Mockito.when(this.semesterMock1.toValues()).thenReturn(mockValues);
        mockValues.put(SemesterTable.COLUMN_ID, "semersterMock1");
        mockValues.put(SemesterTable.COLUMN_NR, 1);
        //exercise
        this.dataAccess.addSemester(this.semesterMock1);
        //verify
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .insert(anyString(),
                        nullable(String.class),
                        any(ContentValues.class));
    }

    @Test
    public void getAllTasksInitially_Test() {
        //exercise
        this.dataAccess.getAllTasks();
        //verify
        this.verifyQueryOnDatabase(1);
    }

    /*@Test
    public void getAllTasksWhenDatabaseWasAlreadyAccessedBefore_Test(){
        //exercise
        this.dataAccess.getAllTasks();
        this.dataAccess.getAllTasks();
        //verify
        this.verifyQueryOnDatabase(1);
    }*/
}
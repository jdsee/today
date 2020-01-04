package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Course;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CourseDataAccessTest {
    private SQLiteDatabase databaseMock;
    private CourseDataAccess dataAccess;
    private Course courseMock;

    @Before
    public void setup() {
        this.databaseMock = new MockSQLiteDatabase().getMockedDatabase();
        this.dataAccess = CourseDataAccess.getInstance();
        this.courseMock = Mockito.mock(Course.class);

    }

    @Test
    public void getTasksInitially_Test() {
        //setup
        //exercise
        this.dataAccess.getTasks(this.courseMock);
        //verify
    }
}
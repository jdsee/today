package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

public class SemesterDataAccessImplTest {

    private SemesterDataAccess dataAccess;
    private Semester semesterMock;
    private Course courseMock1;
    private Course courseMock2;
    private Course courseMock3;
    private List<Course> courseMocks;
    private IdentityMapper courseCacheMock;
    private SQLiteDatabase databaseMock;

    @Before
    public void setUp() {
        this.semesterMock = Mockito.mock(Semester.class);
        Mockito.when(this.semesterMock.getID()).thenReturn("111");

        this.courseMocks = new LinkedList<>();
        this.courseMocks.add(courseMock1);
        this.courseMocks.add(courseMock2);
        this.courseMocks.add(courseMock3);
        for (Course course : courseMocks)
            course = Mockito.mock(Course.class);

        this.courseCacheMock = Mockito.mock(IdentityMapper.class);

        this.databaseMock = new MockSQLiteDatabase().getMockedDatabase();

        this.dataAccess = new SemesterDataAccessImpl(courseCacheMock, databaseMock);
    }

    @Test
    public void getCoursesWhenCourseCacheIsEmpty_Test() {
        //given
        Mockito.when(this.courseCacheMock.get(this.semesterMock)).thenReturn(null);
        //when
        this.dataAccess.getCourses(semesterMock);
        //then
        Mockito.verify(this.courseCacheMock, Mockito.times(1)).get(this.semesterMock);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                        "WHERE " + CourseTable.COLUMN_RELATED_TO + "=?s", new String[]{this.semesterMock.getID()},
                        null, null, null);
    }

    @Test
    public void getCoursesWhenValueIsStoredInCourseCache_Test() {
        //given
        Mockito.when(this.courseCacheMock.get(this.semesterMock)).thenReturn(this.courseMocks);
        //when
        this.dataAccess.getCourses(semesterMock);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1)).get(this.semesterMock);
        Mockito.verifyZeroInteractions(databaseMock);
    }

    @Test
    public void removeCourseWhenCourseCacheIsEmpty_Test(){

    }
}
package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

public class SemesterDataAccessTest {

    private SemesterDataAccess dataAccess;
    private Semester semesterMock;
    private Course courseMock1;
    private Course courseMock2;
    private Course courseMock3;
    private List<Course> courseMocks;
    private IdentityMapper<Course> courseCacheMock;
    private SQLiteDatabase databaseMock;

    @Before
    public void setUp() {
        this.semesterMock = Mockito.mock(Semester.class);
        Mockito.when(this.semesterMock.getID()).thenReturn("semesterMockID");

        this.courseMock1 = this.getCourseMock("course1MockID", "course1MockTitle");
        this.courseMock2 = this.getCourseMock("course2MockID", "course2MockTitle");
        this.courseMock3 = this.getCourseMock("course3MockID", "course3MockTitle");
        this.courseMocks = new LinkedList<>();
        this.courseMocks.add(courseMock1);
        this.courseMocks.add(courseMock2);
        this.courseMocks.add(courseMock3);

        this.courseCacheMock = Mockito.mock(IdentityMapper.class);

        this.databaseMock = new MockSQLiteDatabase().getMockedDatabase();

        this.dataAccess = new SemesterDataAccessImpl(courseCacheMock, databaseMock);
    }

    private Course getCourseMock(String mockID, String mockTitle) {
        Course mockedCourse = Mockito.mock(Course.class);
        Mockito.when(mockedCourse.getID()).thenReturn(mockID);
        Mockito.when(mockedCourse.getTitle()).thenReturn(mockTitle);
        return mockedCourse;
    }


    private void setValueToCourseCache() {
        Mockito.when(this.courseCacheMock.get(semesterMock)).thenReturn(this.courseMocks);
    }

    private void setCourseCacheEmpty() {
        Mockito.when(this.courseCacheMock.get(any(Semester.class))).thenReturn(null);
    }

    @Test
    public void getCoursesWhenCourseCacheIsEmpty_Test() {
        //setup
        this.setCourseCacheEmpty();
        //exercise
        this.dataAccess.getCourses(this.semesterMock);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .get(this.semesterMock);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                        CourseTable.COLUMN_RELATED_TO + "=?",
                        new String[]{this.semesterMock.getID()},
                        null, null, null);
    }

    @Test
    public void getCoursesWhenValueIsAssignedToCourseCache_Test() {
        //setup
        this.setValueToCourseCache();
        //exercise
        this.dataAccess.getCourses(semesterMock);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .get(this.semesterMock);
        Mockito.verifyZeroInteractions(databaseMock);
    }

    @Test
    public void getCoursesWithNoEntryRelatedToKeyExistentInDB_Test() {
        //setup
        this.setCourseCacheEmpty();
        this.databaseMock = new MockSQLiteDatabase().setTableEmpty().getMockedDatabase();
        this.dataAccess = new SemesterDataAccessImpl(this.courseCacheMock, this.databaseMock);
        //exercise
        List<Course> courses = this.dataAccess.getCourses(this.semesterMock);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .get(this.semesterMock);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                        CourseTable.COLUMN_RELATED_TO + "=?",
                        new String[]{this.semesterMock.getID()},
                        null, null, null);
        Assert.assertTrue(courses.isEmpty());
    }

    @Test
    public void removeCourseWhenCourseCacheIsEmpty_Test() {
        //setup
        this.setCourseCacheEmpty();
        //exercise
        this.dataAccess.removeCourse(this.semesterMock, this.courseMock1);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .removeElement(this.semesterMock, this.courseMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .delete(CourseTable.TABLE_NAME,
                        CourseTable.COLUMN_ID + "=?",
                        new String[]{this.courseMock1.getID()});
    }

    @Test
    public void removeCourseWhenValueIsAssignedToCourseCache_Test() {
        //setup
        this.setValueToCourseCache();
        //exercise
        this.dataAccess.removeCourse(this.semesterMock, this.courseMock1);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .removeElement(this.semesterMock, this.courseMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .delete(CourseTable.TABLE_NAME,
                        CourseTable.COLUMN_ID + "=?",
                        new String[]{this.courseMock1.getID()});
    }

    @Test
    public void addCourseWhenCourseCacheIsEmpty_Test() {
        //setup
        this.setCourseCacheEmpty();
        //exercise
        this.dataAccess.addCourse(this.semesterMock, this.courseMock1);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .addElement(this.semesterMock, this.courseMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .insert(anyString(), nullable(String.class), any(ContentValues.class));
    }

    @Test
    public void addCourseWhenValueIsAssignedToCourseCache_Test() {
        //setup
        this.setValueToCourseCache();
        //exercise
        this.dataAccess.addCourse(this.semesterMock, this.courseMock1);
        //verify
        Mockito.verify(this.courseCacheMock, Mockito.times(1))
                .addElement(this.semesterMock, this.courseMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .insert(anyString(), nullable(String.class), any(ContentValues.class));
    }

    @Test
    public void setSemesterNumber_Test() {
        //exercise
        this.dataAccess.setNumber(this.semesterMock, 42);
        //verify
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .update(anyString(),
                        any(ContentValues.class),
                        anyString(),
                        any(String[].class));
    }
}
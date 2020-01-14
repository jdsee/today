package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.LectureTable;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;

public class SectionDataAccessTest {
    private SectionDataAccess dataAccess;
    private Section sectionMock;
    private Lecture lectureMock1;
    private Lecture lectureMock2;
    private Lecture lectureMock3;
    private List<Lecture> lectureMocks;
    private IdentityMapper<Lecture> lectureCacheMock;
    private SQLiteDatabase databaseMock;

    @Before
    public void setUp() {
        this.sectionMock = Mockito.mock(Section.class);
        Mockito.when(this.sectionMock.getID()).thenReturn("semesterMockID");

        this.lectureMock1 = this.getLectureMock("lecture1MockID", new Date(), 1, "ch1");
        this.lectureMock2 = this.getLectureMock("lecture2MockID", new Date(), 2, "ch2");
        this.lectureMock3 = this.getLectureMock("lecture3MockID", new Date(), 3, "ch3");
        this.lectureMocks = new LinkedList<>();
        this.lectureMocks.add(lectureMock1);
        this.lectureMocks.add(lectureMock2);
        this.lectureMocks.add(lectureMock3);

        this.lectureCacheMock = Mockito.mock(IdentityMapper.class);

        this.databaseMock = new MockSQLiteDatabase().getMockedDatabase();

        this.dataAccess = new SectionDataAccessImpl(lectureCacheMock, databaseMock);
    }

    private Lecture getLectureMock(String mockID, Date mockDate, int mockLectureNr, String mockRoomNr) {
        Lecture mockedLecture = Mockito.mock(Lecture.class);
        Mockito.when(mockedLecture.getID()).thenReturn(mockID);
        Mockito.when(mockedLecture.getDate()).thenReturn(mockDate);
        Mockito.when(mockedLecture.getLectureNr()).thenReturn(mockLectureNr);
        Mockito.when(mockedLecture.getRoomNr()).thenReturn(mockRoomNr);

        return mockedLecture;
    }


    private void setValueToLectureCache() {
        Mockito.when(this.lectureCacheMock.get(sectionMock)).thenReturn(this.lectureMocks);
    }

    private void setLectureCacheEmpty() {
        Mockito.when(this.lectureCacheMock.get(any(Section.class))).thenReturn(null);
    }

    @Test
    public void getLecturesWhenLectureCacheIsEmpty_Test() {
        //setup
        this.setLectureCacheEmpty();
        //exercise
        this.dataAccess.getLectures(this.sectionMock);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(2))
                .get(this.sectionMock);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .query(LectureTable.TABLE_NAME,
                        new String[]{LectureTable.COLUMN_ID, LectureTable.COLUMN_NR,
                                LectureTable.COLUMN_DATE, LectureTable.COLUMN_ROOM_NR},
                        LectureTable.COLUMN_RELATED_TO + "=?",
                        new String[]{this.sectionMock.getID()},
                        null, null, null);
    }

    @Test
    public void getLecturesWhenValueIsAssignedToLectureCache_Test() {
        //setup
        this.setValueToLectureCache();
        //exercise
        this.dataAccess.getLectures(sectionMock);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(2))
                .get(this.sectionMock);
        Mockito.verifyZeroInteractions(databaseMock);
    }

    @Test
    public void getLecturesWithNoEntryRelatedToKeyExistentInDB_Test() {
        //setup
        this.setLectureCacheEmpty();
        this.databaseMock = new MockSQLiteDatabase().setTableEmpty().getMockedDatabase();
        this.dataAccess = new SectionDataAccessImpl(lectureCacheMock, databaseMock);
        //exercise
        List<Lecture> lectures = this.dataAccess.getLectures(this.sectionMock);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(2))
                .get(this.sectionMock);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .query(LectureTable.TABLE_NAME,
                        new String[]{LectureTable.COLUMN_ID, LectureTable.COLUMN_NR,
                                LectureTable.COLUMN_DATE, LectureTable.COLUMN_ROOM_NR},
                        LectureTable.COLUMN_RELATED_TO + "=?",
                        new String[]{this.sectionMock.getID()},
                        null, null, null);
    }

    @Test
    public void removeLectureWhenLectureCacheIsEmpty_Test() {
        //setup
        this.setLectureCacheEmpty();
        //exercise
        this.dataAccess.removeLecture(this.sectionMock, this.lectureMock1);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(1))
                .removeElement(this.sectionMock, this.lectureMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .delete(LectureTable.TABLE_NAME,
                        LectureTable.COLUMN_ID + "=?",
                        new String[]{this.lectureMock1.getID()});
    }

    @Test
    public void removeLectureWhenValueIsAssignedToLectureCache_Test() {
        //setup
        this.setValueToLectureCache();
        //exercise
        this.dataAccess.removeLecture(this.sectionMock, this.lectureMock1);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(1))
                .removeElement(this.sectionMock, this.lectureMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .delete(LectureTable.TABLE_NAME,
                        LectureTable.COLUMN_ID + "=?",
                        new String[]{this.lectureMock1.getID()});
    }

    @Test
    public void addLectureWhenLectureCacheIsEmpty_Test() {
        //setup
        this.setLectureCacheEmpty();
        //exercise
        this.dataAccess.addLecture(this.sectionMock, this.lectureMock1);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(1))
                .addElement(this.sectionMock, this.lectureMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .insert(anyString(), nullable(String.class), any(ContentValues.class));
    }

    @Test
    public void addLectureWhenValueIsAssignedToLectureCache_Test() {
        //setup
        this.setValueToLectureCache();
        //exercise
        this.dataAccess.addLecture(this.sectionMock, this.lectureMock1);
        //verify
        Mockito.verify(this.lectureCacheMock, Mockito.times(1))
                .addElement(this.sectionMock, this.lectureMock1);
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .insert(anyString(), nullable(String.class), any(ContentValues.class));
    }

    @Test
    public void setSectionNumber_Test() {
        //exercise
        this.dataAccess.setLecturer(this.sectionMock, "Prof. Schwotzer");
        //verify
        Mockito.verify(this.databaseMock, Mockito.times(1))
                .update(anyString(),
                        any(ContentValues.class),
                        anyString(),
                        any(String[].class));
    }
}
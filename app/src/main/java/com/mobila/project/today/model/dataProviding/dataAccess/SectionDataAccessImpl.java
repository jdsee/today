package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.LectureTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SectionTable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class SectionDataAccessImpl extends ParentDataAccessImpl implements SectionDataAccess {
    private static SectionDataAccess instance;

    private static final String TAG = SectionDataAccessImpl.class.getName();
    private static final String LOG_MSG_DB_UPDATE = "section has been updated in database";

    private IdentityMapper<Lecture> lectureCache;

    private SectionDataAccessImpl() {
        this(
                new IdentityMapper<>(),
                null
        );
    }

    static SectionDataAccess getInstance() {
        if (instance == null)
            instance = new SectionDataAccessImpl();
        return instance;
    }

    /**
     * Supposed for TESTING REASONS ONLY.
     * Do not use this constructor for normal instantiating,
     * it is only supposed to be used for dependency injection -> mockInjection
     *
     * @param lectureCache the IdentityMapper dependency
     * @param database     the SQLiteDatabase dependency
     */
    SectionDataAccessImpl(IdentityMapper<Lecture> lectureCache, SQLiteDatabase database) {
        this.lectureCache = lectureCache;
        this.database = database;
    }

    @Override
    public Course getCourse(Identifiable section) {
        Cursor sectionCursor = this.database.query(SectionTable.TABLE_NAME, new String[]{SectionTable.COLUMN_RELATED_TO},
                SectionTable.COLUMN_ID + "=?", new String[]{section.getID()},
                null, null, null);
        if (!sectionCursor.moveToNext())
            throw new DataKeyNotFoundException("FATAL ERROR: no parent found for given section!");
        String courseID = sectionCursor.getString(sectionCursor.getColumnIndex(SectionTable.COLUMN_RELATED_TO));
        sectionCursor.close();

        Cursor courseCursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                CourseTable.COLUMN_ID + "=?", new String[]{courseID},
                null, null, null);
        courseCursor.moveToFirst();
        Course course = new Course(
                courseCursor.getString(courseCursor.getColumnIndex(CourseTable.COLUMN_ID)),
                courseCursor.getString(courseCursor.getColumnIndex(CourseTable.COLUMN_TITLE))
        );
        courseCursor.close();

        return course;
    }

    @Override
    public List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException {
        List<Lecture> lectures = this.lectureCache.get(course);
        if (lectures == null) {
            lectures = this.getLecturesFromDB(course);
            this.lectureCache.add(course, lectures);
        }
        return lectures;
    }

    private List<Lecture> getLecturesFromDB(Identifiable course) {
        Cursor cursor = this.database.query(
                LectureTable.TABLE_NAME,
                new String[]{LectureTable.COLUMN_ID, LectureTable.COLUMN_NR,
                        LectureTable.COLUMN_DATE, LectureTable.COLUMN_ROOM_NR},
                LectureTable.COLUMN_RELATED_TO + "=?",
                new String[]{course.getID()}, null, null, null);
        List<Lecture> lectures = new LinkedList<>();
        while (cursor.moveToNext()) {
            Lecture lecture = new Lecture(
                    cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(LectureTable.COLUMN_NR)),
                    new Date(cursor.getInt(cursor.getColumnIndex(LectureTable.COLUMN_DATE))),
                    cursor.getString(cursor.getColumnIndex(LectureTable.COLUMN_ROOM_NR))
            );
            lectures.add(lecture);
        }
        cursor.close();
        return lectures;
    }

    @Override
    public void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException {
        this.addLectureToDB(section, lecture);
        this.lectureCache.addElement(section, lecture);
    }

    private void addLectureToDB(Identifiable section, Lecture lecture) {
        ContentValues values = new ContentValues();
        values.put(LectureTable.COLUMN_ID, lecture.getID());
        values.put(LectureTable.COLUMN_NR, lecture.getLectureNr());
        values.put(LectureTable.COLUMN_DATE, lecture.getDate().getTime());
        values.put(LectureTable.COLUMN_ROOM_NR, lecture.getRoomNr());
        values.put(LectureTable.COLUMN_RELATED_TO, section.getID());

        this.database.insert(LectureTable.TABLE_NAME, null, values);

        Log.d(TAG, "lecture has been added to database (id: " + lecture + ")");
    }

    @Override
    public void setTitle(Identifiable section, String title) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(SectionTable.COLUMN_TITLE, title);

        this.updateSectionInDB(section, values);

        Log.d(TAG, LOG_MSG_DB_UPDATE + " (id: " + section + ")");
    }

    @Override
    public void setLecturer(Identifiable section, String lecturer) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(SectionTable.COLUMN_LECTURER, lecturer);

        this.updateSectionInDB(section, values);
    }

    private void updateSectionInDB(Identifiable section, ContentValues values) {
        this.database.update(SectionTable.TABLE_NAME, values,
                SectionTable.COLUMN_ID + "=?", new String[]{section.getID()});

        Log.d(TAG, LOG_MSG_DB_UPDATE + " (id: " + section + ")");
    }

    @Override
    public void removeLecture(Identifiable section, Lecture lecture) {
        this.database.delete(LectureTable.TABLE_NAME,
                LectureTable.COLUMN_ID + "=?", new String[]{lecture.getID()});
        this.lectureCache.removeElement(section, lecture);
    }
}

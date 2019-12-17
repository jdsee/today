package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;

import java.util.LinkedList;
import java.util.List;

import static com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterDataSource.TAG;

class SemesterDataAccessImpl implements SemesterDataAccess {
    private static SemesterDataAccess instance;

    private IdentityMapper<Course> courseCache;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    private SemesterDataAccessImpl() {
        this(
                new IdentityMapper<>(),
                null
        );
    }

    SemesterDataAccessImpl(IdentityMapper<Course> courseCache, SQLiteDatabase database) {
        this.courseCache = courseCache;
        this.database = database;
    }

    public static final SemesterDataAccess getInstance() {
        if (instance == null)
            instance = new SemesterDataAccessImpl();
        return instance;
    }

    @Override
    public void open() {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    /**
     * Accesses the data base and returns a list of courses corresponding to the specified semester
     *
     * @param semester semester containing the foreign key of the enquired courses
     * @return list of courses corrsesponding to the specified semester
     * @throws DataKeyNotFoundException if the key contained by the specified semester is not existent in db
     */
    @Override
    public List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException {
        List<Course> courses = this.courseCache.get(semester);
        if (courses == null) {
            courses = this.getCoursesFromDB(semester);
            this.courseCache.add(semester, courses);
        }
        return courses;
    }

    /**
     * Accesses data bank to get courses associated to the specified identifiable.
     * Before calling this method, the caller should check if there is a matching list of courses
     * in the courseCache-IdentityMapper. Also the caller is required to add the list returned
     * by this method to the courseCache.
     *
     * @param semester the corresponding semester
     * @return a list of courses corresponding to the specified semester
     */
    private List<Course> getCoursesFromDB(Identifiable semester) throws DataKeyNotFoundException {
        Cursor cursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                "WHERE " + CourseTable.COLUMN_RELATED_TO + "=?s", new String[]{semester.getID()},
                null, null, null);
        Log.d(TAG, "getting courses from data base");
        List<Course> courses = new LinkedList<>();
        while (cursor.moveToNext()) {
            Course course = new Course(
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_TITLE))
            );
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    @Override
    public void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException {
        List<Course> courses = this.courseCache.get(semester);
        if (courses == null)
            courses.add(course);
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_ID, course.getID());
        values.put(CourseTable.COLUMN_TITLE, course.getTitle());
        this.database.insert(CourseTable.TABLE_NAME, null, values);
    }

    @Override
    public void removeCourse(Identifiable semester, Course course) {
        List<Course> courses = this.courseCache.get(semester);
        if (courses == null)
            courses.remove(course);
        this.database.delete(CourseTable.TABLE_NAME,
                "WHERE " + CourseTable.COLUMN_ID + "=?s", new String[]{course.getID()});
    }

    @Override
    public void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(SemesterTable.COLUMN_NR, nr);
        this.database.update(SemesterTable.TABLE_NAME, values,
                "WHERE " + SemesterTable.COLUMN_NR + "=?s", new String[]{String.valueOf(nr)});
    }
}

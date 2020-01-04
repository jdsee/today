package com.mobila.project.today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.SemesterTable;

import java.util.LinkedList;
import java.util.List;

class SemesterDataAccessImpl implements SemesterDataAccess {
    public static final String TAG = SemesterDataAccessImpl.class.getName();

    private static SemesterDataAccess instance;

    private IdentityMapper<Course> courseCache;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    private SemesterDataAccessImpl() {
        this(
                new IdentityMapper<>(),
                null
        );
        this.dbHelper = new DBHelper(null);
    }

    //TODO decide when to open the db connection

    /**
     * Supposed for TESTING REASONS ONLY.
     * Do not use this constructor for normal instantiating,
     * it is only supposed to be used for dependency injection -> mockInjection
     *
     * @param courseCache the IdentityMapper dependency
     * @param database    the SQLiteDatabase dependency
     */
    SemesterDataAccessImpl(IdentityMapper<Course> courseCache, SQLiteDatabase database) {
        this.courseCache = courseCache;
        this.database = database;
    }

    static SemesterDataAccess getInstance() {
        if (instance == null)
            instance = new SemesterDataAccessImpl();
        return instance;
    }

    @Override
    public void open(Context context) {
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        this.dbHelper.close();
    }

    /**
     * Accesses the data base and returns a list of courses corresponding to the specified semester
     *
     * @param semester semester containing the foreign key of the enquired courses
     * @return list of courses corresponding to the specified semester
     * @throws DataKeyNotFoundException if the key associated to the specified semester is not existent in db
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
     * <p>
     * Before calling this method, the caller should check if there is a matching list of courses
     * in the courseCache-IdentityMapper. Also the caller is required to add the list returned
     * by this method to the courseCache.
     *
     * @param semester the corresponding semester
     * @return a list of courses corresponding to the specified semester
     * @throws DataKeyNotFoundException if the key associated to the specified semester is not existent in db
     */
    private List<Course> getCoursesFromDB(Identifiable semester) throws DataKeyNotFoundException {
        Log.d(TAG, "requesting courses from data base");
        Cursor cursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                /*
                CourseTable.COLUMN_RELATED_TO + " = '" + semester.getID() + "'",
                null, null, null, null);
                 */
                CourseTable.COLUMN_RELATED_TO + "=?s", new String[]{semester.getID()},
                null, null, null);
        List<Course> courses = new LinkedList<>();
        while (cursor.moveToNext()) {
            Course course = new Course(
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_TITLE)));
            courses.add(course);
        }
        cursor.close();
        return courses;
    }

    /**
     * Adds the specified course, referenced to the specified semester, to the database.
     * If the courseCache related to this semester already exists it will be added there too.
     *
     * @param semester the semester corresponding to the specified course
     * @param course   the course that is to be added
     * @throws DataKeyNotFoundException if the key associated to the specified semester is not existent in db
     */
    @Override
    //TODO probably DataKeyNotFoundException makes no sense here
    //-> id of semester is not approved within this call
    public void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException {
        this.courseCache.addElement(semester, course);
        this.addCourseToDB(semester, course);
    }

    /**
     * Accesses database to add the specified course to the course table.
     *
     * @param semester the semester corresponding to the specified course
     * @param course   the course that is to be added
     */
    private void addCourseToDB(Identifiable semester, Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_ID, course.getID());
        values.put(CourseTable.COLUMN_TITLE, course.getTitle());
        values.put(CourseTable.COLUMN_RELATED_TO, semester.getID());
        this.database.insert(CourseTable.TABLE_NAME, null, values);
    }

    /**
     * Removes the specified course, to the database from the database.
     * If the courseCache related to this semester already exists it will be removed there too.
     * <p>
     * If the key associated to the specified course does not exists, nothing happens
     *
     * @param semester the semester corresponding to the specified course
     * @param course   the course that is to be removed
     */
    @Override
    public void removeCourse(Identifiable semester, Course course) {
        this.database.delete(CourseTable.TABLE_NAME,
        //CourseTable.COLUMN_ID + " = '" + course.getID() + "' ", null);
        CourseTable.COLUMN_ID + "=?s", new String[]{course.getID()});
        this.courseCache.removeElement(semester, course);
    }

    /**
     * Sets the semesterNumber to the specified semester.
     *
     * @param semester the semester whose nr is to be set
     * @param nr       the semesterNumber
     * @throws DataKeyNotFoundException if the key associated to the specified semester is not existent in db
     */
    @Override
    public void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException {
        ContentValues values = new ContentValues();
        values.put(SemesterTable.COLUMN_NR, nr);
        this.database.update(SemesterTable.TABLE_NAME, values,
                SemesterTable.COLUMN_NR + "=?s", new String[]{String.valueOf(nr)});
    }
}

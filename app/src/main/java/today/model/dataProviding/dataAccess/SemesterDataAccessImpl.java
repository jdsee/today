package today.model.dataProviding.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Course;
import today.model.Identifiable;
import today.model.dataProviding.dataAccess.databank.CourseTable;
import today.model.dataProviding.dataAccess.databank.DBHelper;
import today.model.dataProviding.dataAccess.databank.SemesterTable;

import java.util.LinkedList;
import java.util.List;

import static today.model.dataProviding.dataAccess.databank.SemesterDataSource.TAG;

class SemesterDataAccessImpl implements SemesterDataAccess {
    private static SemesterDataAccess instance;

    private static final String NO_COURSES_FOR_SEM_MSG = "no courses related to given semester";

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

    public static SemesterDataAccess getInstance() {
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
                "WHERE " + CourseTable.COLUMN_RELATED_TO + "=?s", new String[]{semester.getID()},
                null, null, null);
        if (!cursor.moveToNext()) {
            DataKeyNotFoundException t = new DataKeyNotFoundException(DataKeyNotFoundException.NO_ENTRY_MSG);
            Log.d(TAG, DataKeyNotFoundException.NO_ENTRY_MSG + ": " + NO_COURSES_FOR_SEM_MSG, t);
            throw t;
        }
        List<Course> courses = new LinkedList<>();
        do {
            Course course = new Course(
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_TITLE))
            );
            courses.add(course);
        } while (cursor.moveToNext());
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
    public void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException {
        this.courseCache.addElement(semester, course);
        this.addCourseToDB(course);
    }

    /**
     * Accesses database to add the specified course to the course table.
     *
     * @param course the course that is to be added
     */
    private void addCourseToDB(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_ID, course.getID());
        values.put(CourseTable.COLUMN_TITLE, course.getTitle());
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
        this.courseCache.removeElement(semester, course);
        this.database.delete(CourseTable.TABLE_NAME,
                "WHERE " + CourseTable.COLUMN_ID + "=?s", new String[]{course.getID()});
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
                "WHERE " + SemesterTable.COLUMN_NR + "=?s", new String[]{String.valueOf(nr)});
    }
}

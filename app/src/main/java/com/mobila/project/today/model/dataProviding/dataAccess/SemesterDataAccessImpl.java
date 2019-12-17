package com.mobila.project.today.model.dataProviding.dataAccess;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.CourseTable;

import java.util.List;

class SemesterDataAccessImpl implements SemesterDataAccess {
    private static SemesterDataAccess instance;
    private IdentityMapper<Course> courseCache;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private List<Course> courses;

    private SemesterDataAccessImpl() {
        this.courseCache = new IdentityMapper<>();
    }

    public static final SemesterDataAccess getInstance() {
        if (instance == null)
            instance = new SemesterDataAccessImpl();
        return instance;
    }

    @Override
    public List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException {
        List<Course> courses = this.courseCache.get(semester);
        if (courses == null) {
            this.getCourseFromDB(semester);
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
    private List<Course> getCourseFromDB(Identifiable semester) throws DataKeyNotFoundException {
        Cursor cursor = this.database.query(CourseTable.TABLE_NAME, CourseTable.ALL_COLUMNS,
                "WHERE relatedTo=?s", new String[]{semester.getID()},
                null, null, CourseTable.TABLE_NAME);
        while (cursor.moveToNext()) {
            Course course = new Course(
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(CourseTable.COLUMN_TITLE))
            );
            this.courses.add(course);
        }
        cursor.close();
        return this.courses;
    }

    @Override
    public void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException {

    }

    @Override
    public int getNumber(Identifiable semester) throws DataKeyNotFoundException {
        return 0;
    }

    @Override
    public void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException {

    }
}

package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.dataProviding.DataKeyNotFoundException;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;

import java.util.List;

class SemesterDataAccessImpl implements SemesterDataAccess {
    private static SemesterDataAccess instance;
    private IdentityMapper<Course> courseCache;

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
        //TODO access data bank to get courses associated to the speciied identifiable
        return null;
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

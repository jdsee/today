package today.model.dataProviding.dataAccess;

import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Course;
import today.model.Identifiable;

import java.util.List;

public interface SemesterDataAccess {
    void open();

    void close();

    List<Course> getCourses(Identifiable semester) throws DataKeyNotFoundException;

    void addCourse(Identifiable semester, Course course) throws DataKeyNotFoundException;

    void removeCourse(Identifiable semester, Course course);

    void setNumber(Identifiable semester, int nr) throws DataKeyNotFoundException;
}

package today.model.dataProviding.dataAccess;

import today.model.Lecture;
import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Course;
import today.model.Identifiable;

import java.util.List;

public interface SectionDataAccess {
    Course getCourse(Identifiable section) throws DataKeyNotFoundException;

    List<Lecture> getLectures(Identifiable course) throws DataKeyNotFoundException;

    void addLecture(Identifiable section, Lecture lecture) throws DataKeyNotFoundException;

    String getTitle(Identifiable section) throws DataKeyNotFoundException;

    String setTitle(Identifiable section, String title) throws DataKeyNotFoundException;

    String getLecturer(Identifiable lecture) throws DataKeyNotFoundException;

    void setLecturer(Identifiable lecture, String lecturer) throws DataKeyNotFoundException;
}

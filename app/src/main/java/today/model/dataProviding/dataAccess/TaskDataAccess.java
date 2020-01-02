package today.model.dataProviding.dataAccess;

import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Course;
import today.model.Identifiable;

import java.util.Date;

public interface TaskDataAccess {
    Course getCourse(Identifiable section) throws DataKeyNotFoundException;

    Date getDeadline(Identifiable task) throws DataKeyNotFoundException;

    void setDeadline(Identifiable task, Date date) throws DataKeyNotFoundException;

    String getContent(Identifiable task) throws DataKeyNotFoundException;

    void setContent(Identifiable task, String content) throws DataKeyNotFoundException;
}

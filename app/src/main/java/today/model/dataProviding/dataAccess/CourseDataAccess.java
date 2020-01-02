package today.model.dataProviding.dataAccess;

import today.model.dataProviding.DataKeyNotFoundException;
import today.model.Identifiable;
import today.model.Section;
import today.model.Semester;
import today.model.Task;

import java.util.List;

public interface CourseDataAccess {

    Semester getSemester(Identifiable course) throws DataKeyNotFoundException;

    List<Section> getSections(Identifiable course) throws DataKeyNotFoundException;

    void addSection(Identifiable course, Section section) throws DataKeyNotFoundException;

    List<Task> getTasks(Identifiable course) throws DataKeyNotFoundException;

    void addTask(Identifiable course, Task task) throws DataKeyNotFoundException;

    String getTitle(Identifiable course) throws DataKeyNotFoundException;

    void setTitle(Identifiable course, String title) throws DataKeyNotFoundException;
}

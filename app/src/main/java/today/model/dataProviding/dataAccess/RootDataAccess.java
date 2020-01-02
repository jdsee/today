package today.model.dataProviding.dataAccess;

import today.model.Semester;
import today.model.Task;

import java.util.List;

public interface RootDataAccess {
    static RootDataAccess getInstance() {
        return RootDataAccessImpl.getInstance();
    }

    void open();

    void close();

    List<Semester> getAllSemesters();

    void addSemester(Semester semester);

    List<Task> getAllTasks();
}

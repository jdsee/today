package today.test;

import android.database.sqlite.SQLiteDatabase;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Identifiable;
import com.mobila.project.today.model.dataProviding.dataAccess.CourseDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.CourseDataAccessImpl;
import com.mobila.project.today.model.dataProviding.dataAccess.MockSQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CourseDataAccessTest {
    private SQLiteDatabase databaseMock;
    private CourseDataAccess dataAccess;
    private Course courseMock;

    @Before
    public void setup() {
        this.databaseMock = new MockSQLiteDatabase().getMockedDatabase();
        this.dataAccess = new CourseDataAccessImpl();
        this.courseMock = Mockito.mock(Course.class);

    }

    @Test
    public void getTasksInitially_Test() {
        //setup
        //exercise
        this.dataAccess.getTasks(this.courseMock);
        //verify
    }
}
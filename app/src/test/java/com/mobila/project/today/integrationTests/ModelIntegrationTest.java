package com.mobila.project.today.integrationTests;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.Student;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;
import com.mobila.project.today.model.dataProviding.dataAccess.databank.DBHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ModelIntegrationTest {
    private Context instrumentationContext;

    private RootDataAccess rootDataAccess;
    private Student student;

    @Before
    public void setup() {
        DBHelper dbHelper = new DBHelper(ApplicationProvider.getApplicationContext());

        //TODO inject db to OrganizerDataProvider!

        this.instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();

        this.rootDataAccess = OrganizerDataProvider.getInstance().getRootDataAccess();
        this.student = new Student();

        this.setupSemesters();
        this.setupTasks();
        this.setupCourses();
        this.setupSections();

//        this.openDatabaseConnection();
    }

    private Semester semester1;
    private Semester semester2;
    private Semester semester3;
    private List<Semester> semesters;
    private List<Semester> resultSemesters;

    private void setupSemesters() {
        this.semester1 = new Semester(1);
        this.semester2 = new Semester(2);
        this.semester3 = new Semester(3);
        this.semesters = new LinkedList<>();
        this.semesters.add(this.semester1);
        this.semesters.add(this.semester2);
        this.semesters.add(this.semester3);
    }

    private Task task1;
    private Task task2;
    private Task task3;
    private List<Task> tasks;
    private List<Task> resultTasks;

    private void setupTasks() {
        Date anyWhen = new Date(123456789);
        this.task1 = new Task("1_id", "1_content", anyWhen);
        this.task2 = new Task("2_id", "2_content", anyWhen);
        this.task3 = new Task("3_id", "3_content", anyWhen);
        this.tasks = new LinkedList<>();
        this.tasks.add(task1);
        this.tasks.add(task2);
        this.tasks.add(task3);
    }

    private Course course1;
    private Course course2;
    private Course course3;
    private List<Course> courses;
    private List<Course> resultCourses;

    private void setupCourses() {
        this.course1 = new Course("course1");
        this.course2 = new Course("course2");
        this.course3 = new Course("course3");
        this.courses = new LinkedList<>();
        this.courses.add(this.course1);
        this.courses.add(this.course2);
        this.courses.add(this.course3);
    }

    private Section section1;
    private Section section2;
    private Section section3;
    private List<Section> sections;
    private List<Section> resultSections;

    private void setupSections(){
        this.section1 = new Section("title1", "lecturer1");
        this.section2 = new Section("title2", "lecturer2");
        this.section3 = new Section("title3", "lecturer3");
        this.sections = new LinkedList<>();
        this.sections.add(section1);
        this.sections.add(section2);
        this.sections.add(section3);
    }

    private void openDatabaseConnection() {
//      this can also be seen as test. if there are problems in setup() approve this
        OrganizerDataProvider.getInstance().openDbConnection(this.instrumentationContext);
    }

    @After
    public void tearDown() {
        OrganizerDataProvider.getInstance().closeDbConnection();
    }


    private void addSemestersToStudent() {
        this.student.addSemester(semester1);
        this.student.addSemester(semester2);
        this.student.addSemester(semester3);
    }

    @Test
    public void addAndGetSemestersViaStudent_Test() {
        this.resultSemesters = this.student.getAllSemesters();
        assertNotNull(this.resultSemesters);
        assertTrue(this.resultSemesters.isEmpty());

        this.addSemestersToStudent();
        this.resultSemesters = this.student.getAllSemesters();

        assertEquals(this.semesters.size(), this.resultSemesters.size());
        Semester expected;
        Semester actual;
        for (int i = 0; i < this.resultSemesters.size(); i++) {
            actual = this.resultSemesters.get(i);
            expected = this.semesters.get(i);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void passNullToAddSemesterViaStudent_Test() {
        this.addSemestersToStudent();
        this.resultSemesters = this.student.getAllSemesters();
        int expectedSemestersSize = this.resultSemesters.size();

        this.student.addSemester(null);
        this.resultSemesters = this.student.getAllSemesters();

        assertEquals(expectedSemestersSize, this.resultSemesters.size());
    }

    @Test
    public void deleteSemestersViaStudent_Test() {
        this.addSemestersToStudent();

        this.resultSemesters = this.rootDataAccess.getAllSemesters();
        int expectedSemestersSize = this.resultSemesters.size();

        this.student.removeSemester(this.semester2);

        assertEquals(expectedSemestersSize, this.resultSemesters.size());
        assertTrue(this.resultSemesters.contains(semester1));
        assertTrue(this.resultSemesters.contains(semester3));
        assertFalse(this.resultSemesters.contains(semester2));
    }

    @Test
    public void passNullToDeleteSemesterViaStudent_Test() {
        this.resultSemesters = this.student.getAllSemesters();
        int expectedSemestersSize = this.resultSemesters.size();

        this.student.removeSemester(null);

        this.resultSemesters = this.student.getAllSemesters();
        assertEquals(expectedSemestersSize, this.resultSemesters.size());
    }

    @Test
    public void getEmptyTasksViaStudent_Test() {
        this.resultTasks = this.student.getAllTasks();

        assertNotNull(this.resultTasks);
        assertTrue(this.resultTasks.isEmpty());
    }

    //TODO test remove und getAllTaks as soon as courses are tested

    @Test
    public void getEmptyCoursesViaSemester() {
        resultCourses = semester1.getCourses();

        assertNotNull(resultCourses);
        assertTrue(resultCourses.isEmpty());

        List<Course> secondCallResult;
        secondCallResult = semester1.getCourses();

        assertNotNull(secondCallResult);
        assertTrue(secondCallResult.isEmpty());
        assertEquals(resultCourses, secondCallResult);
    }

    @Test
    public void addAndGetCoursesViaSemester() {
        semester1.addCourse(course1);
        semester1.addCourse(course2);
        semester3.addCourse(course3);

        resultCourses = semester1.getCourses();

        assertEquals(2, resultCourses.size());
        assertTrue(resultCourses.contains(course1));
        assertTrue(resultCourses.contains(course2));
        assertFalse(resultCourses.contains(course3));
    }

    @Test
    public void removeCourseViaSemester(){
        semester1.addCourse(course1);
        semester1.addCourse(course2);

        semester1.removeCourse(course2);

        resultCourses=semester1.getCourses();

        assertEquals(1, resultCourses.size());
    }

    @Test
    public void removeCourseViaSemesterWithNotAddedCourseShouldNotDoAnything(){
        semester1.addCourse(course1);
        semester1.addCourse(course2);

        semester1.removeCourse(course3);

        resultCourses=semester1.getCourses();

        assertEquals(2, resultCourses.size());
    }

    @Test
    public void removeCourseViaSemesterWithNullParameterShouldNotDoAnything(){
        semester1.addCourse(course1);
        semester1.addCourse(course2);

        semester1.removeCourse(null);

        resultCourses=semester1.getCourses();

        assertEquals(2, resultCourses.size());
    }

    @Test
    public void setSemesterNrShouldSucceed(){
        semester1.setSemesterNr(42);

        assertEquals(42, semester1.getSemesterNr());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setSemesterNrDoubleShouldFail(){
        for (Semester semester:semesters) {
            this.student.addSemester(semester);
        }
        semester3.setSemesterNr(2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setSemesterNumberNegativeShouldFail(){
        semester1.setSemesterNr(-1);
    }


}
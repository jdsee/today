package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

public class SemesterDataAccessImplTest {

    Semester semesterMock;
    Course courseMock1;
    Course courseMock2;
    Course courseMock3;
    IdentityMapper<Course> courseCacheMock;
    List<Course> courseMocks;

    @Before
    public void setUp() {
        this.semesterMock = Mockito.mock(Semester.class);
        Mockito.when(this.semesterMock.getID()).thenReturn("111");

        this.courseMocks.add(courseMock1);
        this.courseMocks.add(courseMock2);
        this.courseMocks.add(courseMock3);
        for (Course course : courseMocks)
            course = Mockito.mock(Course.class);

        this.courseCacheMock = Mockito.mock(IdentityMapper.class);
    }

    @Test
    public void getCourses() {
        //given
        Mockito.when(this.courseCacheMock.get(this.semesterMock)).thenReturn(this.courseMocks);

    }


}
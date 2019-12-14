package com.mobila.project.today.model.dataProviding.dataAccess;

import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IdentityMapperTest {

    private IdentityMapper<Course> identityMapper;
    private List<Course> courses1;
    private List<Course> courses2;
    private Semester sem1;
    private Semester sem2;

    @Before
    public void setup() {
        //given
        this.courses1 = new ArrayList<>();
        this.courses2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.courses1.add(Mockito.mock(Course.class));
            this.courses2.add(Mockito.mock(Course.class));
        }

        this.sem1 = Mockito.mock(Semester.class);
        this.sem2 = Mockito.mock(Semester.class);
        Mockito.when(this.sem1.getID()).thenReturn("sem1");
        Mockito.when(this.sem2.getID()).thenReturn("sem2");

        this.identityMapper = new IdentityMapper<>();
    }

    @Test
    public void getFromEmptyMap_Test() {
        //when
        List<Course> result = this.identityMapper.get(this.sem1);
        //then
        assertEquals(null, result);
    }

    @Test(expected = NullPointerException.class)
    public void getWithNullParam_Test() {
        //when
        this.identityMapper.get(null);
    }

    @Test
    public void addAndGetEntry_Test() {
        //when
        this.identityMapper.add(sem1, courses1);
        List<Course> resultSem1 = this.identityMapper.get(this.sem1);
        List<Course> resultSem2 = this.identityMapper.get(this.sem2);
        //then
        assertEquals(this.courses1, resultSem1);
        assertNotEquals(this.courses2, resultSem2);
        assertNull(resultSem2);
    }

    @Test(expected = KeyAlreadyAssignedException.class)
    public void addEntryToAlreadyAssignedKey_Test() {
        //given
        this.identityMapper.add(sem1, courses1);
        //when
        this.identityMapper.add(sem1, courses2);
    }

    @Test
    public void overwriteEntry_Test() {
        //given
        this.identityMapper.add(sem1, courses1);
        //when
        this.identityMapper.overwrite(sem1, courses2);
        List<Course> result = identityMapper.get(sem1);
        //then
        assertEquals(courses2, result);
        assertNotEquals(courses1, result);
        assertNull(identityMapper.get(sem2));
    }

    @Test
    public void removeEntry_Test() {
        //given
        this.identityMapper.add(sem1, courses1);
        this.identityMapper.add(sem2, courses2);
        //when
        this.identityMapper.remove(sem2);
        List<Course> resultSem1 = this.identityMapper.get(sem1);
        List<Course> resultSem2 = this.identityMapper.get(sem2);
        //then
        assertEquals(courses1, resultSem1);
        assertEquals(null, resultSem2);
    }

    @Test(expected = NullPointerException.class)
    public void addEntryWitNullParam_Test1() {
        this.identityMapper.add(null, this.courses1);
    }

    @Test(expected = NullPointerException.class)
    public void addEntryWitNullParam_Test2() {
        this.identityMapper.add(this.sem1, null);
    }

    @Test(expected = NullPointerException.class)
    public void overwriteEntryWithNullParam_Test1() {
        this.identityMapper.overwrite(null, this.courses1);
    }

    @Test(expected = NullPointerException.class)
    public void overwriteEntryWithNullParam_Test2() {
        this.identityMapper.overwrite(this.sem1, null);
    }
}
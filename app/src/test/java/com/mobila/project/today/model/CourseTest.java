package com.mobila.project.today.model;

import android.os.Parcel;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void writeAndReadParcel_Test() {
        Parcel parcel = MockParcel.obtain();
        Course course = new Course("test course");
        course.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Course fromParcel = Course.CREATOR.createFromParcel(parcel);
        assertEquals(course, fromParcel);
    }
}
package com.mobila.project.today.model;

import android.os.Parcel;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class LectureTest {

    @Test
    public void writeAndReadParcel_Test() {
        Parcel parcel = MockParcel.obtain();
        Lecture lecture = new Lecture(42, Calendar.getInstance().getTime(), "*");
        lecture.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Lecture fromParcel =  Lecture.CREATOR.createFromParcel(parcel);
        assertEquals(lecture, fromParcel);
    }
}
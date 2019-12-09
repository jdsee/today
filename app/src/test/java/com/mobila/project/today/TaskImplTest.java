package com.mobila.project.today;

import android.os.Parcel;

import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TaskImplTest {
    private Task task;

    @Before
    public void setUp() {
        this.task = new TaskImpl(1234, "get your shit done", new Date());
    }

    @Test
    public void writeAndReadParcel_Test() {
        Parcel parcel = MockParcel.obtain();
        this.task.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Task fromParcel = Task.CREATOR.createFromParcel(parcel);
        assertEquals(task, fromParcel);
    }
}
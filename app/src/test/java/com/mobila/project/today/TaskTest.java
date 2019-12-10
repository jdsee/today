package com.mobila.project.today;

import android.os.Parcel;

import com.mobila.project.today.model.dataProviding.dataAccess.TaskDataAccess;
import com.mobila.project.today.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;

public class TaskTest {
    private Task task;
    private String testContent;
    private Date testDate;
    private TaskDataAccess dataAccessMock;

    @Before
    public void setUp() {
        this.dataAccessMock = Mockito.mock(TaskDataAccess.class);
        Mockito.when(this.dataAccessMock.getContent(this.task)).thenReturn(this.testContent);
        Mockito.when(this.dataAccessMock.getDeadline(this.task)).thenReturn(this.testDate);

        this.testContent = "get your shit done";
        this.testDate = new Date();
        this.task = new Task(1234, this.testContent, this.testDate, this.dataAccessMock);
    }

    @Test
    public void writeAndReadParcel_Test() {
        Parcel parcel = MockParcel.obtain();
        this.task.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Task fromParcel = Task.CREATOR.createFromParcel(parcel);
        assertEquals(task, fromParcel);
    }

    @Test
    public void accessContent_Test(){
        String content;
        content = this.task.getContent();
        assertEquals(this.testContent, content);
        Mockito.verifyZeroInteractions(this.dataAccessMock);

        String changedContent = "changed content";
        this.task.setContent(changedContent);
        content = this.task.getContent();
        assertEquals(changedContent, content);
        Mockito.verify(this.dataAccessMock, Mockito.times(1))
                .setContent(this.task, changedContent);
        Mockito.verify(this.dataAccessMock, Mockito.times(0))
                .getContent(this.task);

        this.task.setContent(null);
        Mockito.verify(this.dataAccessMock, Mockito.times(1))
                .setContent(this.task, changedContent);
        content = this.task.getContent();
        assertEquals(null, content);
        Mockito.verify(this.dataAccessMock, Mockito.times(1))
                .getContent(this.task);

        Task nullContentTask = new Task(1234, null, null, this.dataAccessMock);
        content = nullContentTask.getContent();
        assertNull(content);
        Mockito.verify(this.dataAccessMock, Mockito.times(1)).getContent(nullContentTask);
    }
}
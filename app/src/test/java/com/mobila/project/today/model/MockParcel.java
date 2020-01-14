package com.mobila.project.today.model;

import android.os.Parcel;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class MockParcel {
    public static Parcel obtain() {
        return new MockParcel().getMockedParcel();
    }

    private Parcel mockedParcel;
    private int position;
    private List<Object> objects;

    public Parcel getMockedParcel() {
        return mockedParcel;
    }

    public MockParcel() {
        mockedParcel = mock(Parcel.class);
        objects = new ArrayList<>();
        setupMock();
    }

    private void setupMock() {
        setupWrites();
        setupReads();
        setupOthers();
    }

    private void setupWrites() {
        Answer<Void> writeValueAnswer = invocation -> {
            Object parameter = invocation.getArguments()[0];
            objects.add(parameter);
            return null;
        };
        doAnswer(writeValueAnswer).when(mockedParcel).writeLong(anyLong());
        doAnswer(writeValueAnswer).when(mockedParcel).writeString(anyString());
        doAnswer(writeValueAnswer).when(mockedParcel).writeInt(anyInt());
    }

    private void setupReads() {
        Mockito.when(mockedParcel.readLong()).thenAnswer(this.stubReadAnswer());
        Mockito.when(mockedParcel.readString()).thenAnswer(this.stubReadAnswer());
        Mockito.when(mockedParcel.readInt()).thenAnswer(this.stubReadAnswer());
    }

    private <T> Answer<T> stubReadAnswer(){
        return invocation -> (T) objects.get(position++);
    }

    private void setupOthers() {
        doAnswer((Answer<Void>) invocation -> {
            position = ((Integer) invocation.getArguments()[0]);
            return null;
        }).when(mockedParcel).setDataPosition(anyInt());
    }
}

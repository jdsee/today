package com.mobila.project.today.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MockContext {
    @Mock
    private Context mockedContext;
    @Mock
    private Resources mockedResources;
    @Mock
    private SharedPreferences mockedSharedPreferences;

    public Context getMockedContext() {
        return this.mockedContext;
    }

    public MockContext() {
        MockitoAnnotations.initMocks(this);
        when(mockedContext.getResources()).
                thenReturn(mockedResources);
        when(mockedContext.getSharedPreferences(anyString(), anyInt())).
                thenReturn(mockedSharedPreferences);
        when(mockedContext.getApplicationContext()).thenReturn(mockedContext);
    }

    private void setupMock() {
        this.setupResources();
    }

    private void setupResources() {
        when(mockedResources.getString(anyInt())).
                thenReturn("mockedResource1", "mockedResource2");
        when(mockedResources.getStringArray(anyInt())).
                thenReturn(new String[]{"mockedResource1", "mockedResource2"});
        when(mockedResources.getColor(anyInt())).
                thenReturn(Color.BLACK);
        when(mockedResources.getBoolean(anyInt())).
                thenReturn(false);
        when(mockedResources.getDimension(anyInt())).
                thenReturn(100f);
        when(mockedResources.getIntArray(anyInt())).
                thenReturn(new int[]{1, 2, 3});
    }
}

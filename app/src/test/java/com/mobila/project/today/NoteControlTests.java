package com.mobila.project.today;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;

import com.mobila.project.today.control.NoteControl;

import org.junit.Test;
import org.mockito.Mockito;

public class NoteControlTests {
    @Test
    public void setForegroundColorSpanShouldSucceed(){
        Context mockContext = Mockito.mock(Context.class);
        Mockito.when(mockContext.getColor(Color.RED)).thenReturn(0xFFFF0000);
        Mockito.when(mockContext.getResources().getColor(Color.RED)).thenReturn(0xFFFF0000);
        EditText editText = new EditText(mockContext);
        NoteControl control = new NoteControl(mockContext, editText);

        editText.setText("This is a test text that is exactly 50 chars long.");
        editText.setSelection(10, 14);
        control.setForegroundColorSpan(Color.RED);
    }
}

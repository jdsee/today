package com.mobila.project.today;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Spannable;
import android.widget.EditText;

import com.mobila.project.today.control.NoteControl;

import org.junit.Test;
import org.mockito.Mockito;

public class NoteControlTests {
    @Test
    public void setForegroundColorSpanShouldSucceed() {

        Resources mockResources = Mockito.mock(Resources.class);
        Mockito.when(mockResources.getColor(Color.RED)).thenReturn(0xFFFF0000);

        Context mockContext = Mockito.mock(Context.class);
        Mockito.when(mockContext.getResources()).thenReturn(mockResources);
        Mockito.when(mockContext.getColor(Color.RED)).thenReturn(0xFFFF0000);

        Editable mockEditable = Mockito.mock(Editable.class);

        EditText mockEditText = Mockito.mock(EditText.class);
        Mockito.when(mockEditText.getSelectionStart()).thenReturn(10);
        Mockito.when(mockEditText.getSelectionEnd()).thenReturn(14);
        Mockito.when(mockEditText.getText()).thenReturn(mockEditable);

        Parcelable mockParcelable = Mockito.mock(Parcelable.class);

        //Actual Test
        NoteControl control = new NoteControl(mockContext, mockEditText);
        control.setSpan(mockParcelable, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //Verifying
        Mockito.verify(mockEditable, Mockito.times(1))
                .setSpan(mockParcelable, 10, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Mockito.verify(mockEditText, Mockito.times(1)).setSelection(14);
    }
}

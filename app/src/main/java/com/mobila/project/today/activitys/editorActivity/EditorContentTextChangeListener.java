package com.mobila.project.today.activitys.editorActivity;

import android.text.Editable;
import android.text.Spannable;
import android.widget.EditText;

import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.TextChangeListener;


public class EditorContentTextChangeListener extends TextChangeListener {
    private EditText editText;
    private NoteMock note;

    EditorContentTextChangeListener(Object target, EditText editText, NoteMock note) {
        super(target);
        this.editText = editText;
        this.note = note;
    }

    @Override
    public void onTextChanged(Object target, Editable s) {
        Spannable spannable = editText.getText();
        note.setContent(spannable);
    }
}

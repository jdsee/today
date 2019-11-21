package com.mobila.project.today.activitys.editorActivity;

import android.text.Editable;
import android.text.Spannable;
import android.widget.EditText;

import com.mobila.project.today.modelMock.Note;
import com.mobila.project.today.utils.TextChangeListener;


public class EditorContentTextChangeListener extends TextChangeListener {
    private EditText editText;
    private Note note;

    EditorContentTextChangeListener(Object target, EditText editText, Note note) {
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

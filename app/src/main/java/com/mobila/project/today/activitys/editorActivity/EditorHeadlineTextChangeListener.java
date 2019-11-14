package com.mobila.project.today.activitys.editorActivity;

import android.text.Editable;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.model.Note;
import com.mobila.project.today.utils.TextChangeListener;

import java.util.Objects;

public class EditorHeadlineTextChangeListener extends TextChangeListener {
    private AppCompatActivity appCompatActivity;
    private EditText editText;
    private Note note;

    EditorHeadlineTextChangeListener(Object target, AppCompatActivity appCompatActivity,
                                            EditText editText, Note note) {
        super(target);
        this.appCompatActivity =appCompatActivity;
        this.editText =editText;
        this.note=note;
    }

    @Override
    public void onTextChanged(Object target, Editable s) {
        String content = editText.getText().toString();
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setTitle(content);
        if (content.equals(""))
            Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setTitle(note.getTitle());
    }
}

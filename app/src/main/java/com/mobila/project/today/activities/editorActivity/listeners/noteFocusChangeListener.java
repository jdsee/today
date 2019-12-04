package com.mobila.project.today.activities.editorActivity.listeners;

import android.view.View;

import com.mobila.project.today.activities.editorActivity.EditorActivity;

public class noteFocusChangeListener implements View.OnFocusChangeListener {

    private final EditorActivity activity;

    public noteFocusChangeListener(EditorActivity activity){
        this.activity = activity;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            activity.setFocusOnNoteContent();
            activity.showAppropiateItems();
        } else {
            activity.removeFocusOnNoteContent();
            activity.showAppropiateItems();
        }
    }
}

package today.activities.editorView.listeners;

import android.view.View;

import today.activities.editorView.EditorActivity;

public class noteFocusChangeListener implements View.OnFocusChangeListener {

    private final EditorActivity activity;

    public noteFocusChangeListener(EditorActivity activity){
        this.activity = activity;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            activity.setFocusOnNoteContent();
            activity.showAppropriateMenu();
        } else {
            activity.removeFocusOnNoteContent();
            activity.showAppropriateMenu();
        }
    }
}

package com.mobila.project.today.activitys.editorActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class EditorKeyboardEventListener implements KeyboardVisibilityEventListener {
    private EditorActivity activity;

    EditorKeyboardEventListener(EditorActivity activity) {
        this.activity=activity;
    }


    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (isOpen) {
            this.activity.setKeyboardOpen(true);
        } else {
            this.activity.setKeyboardOpen(false);
        }
        this.activity.invalidateOptionsMenu();
    }
}

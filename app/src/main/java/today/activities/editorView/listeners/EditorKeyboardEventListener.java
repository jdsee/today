package today.activities.editorView.listeners;

import today.activities.editorView.EditorActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class EditorKeyboardEventListener implements KeyboardVisibilityEventListener {
    private EditorActivity activity;

    public EditorKeyboardEventListener(EditorActivity activity) {
        this.activity=activity;
    }


    @Override
    public void onVisibilityChanged(boolean isOpen) {
        this.activity.setKeyboardOpen(isOpen);
        this.activity.showAppropriateMenu();
        this.activity.closeFontOptionMenus();
    }
}

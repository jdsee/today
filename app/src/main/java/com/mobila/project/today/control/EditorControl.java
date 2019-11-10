package com.mobila.project.today.control;

import android.content.Context;
import android.view.View;

/**
 * responsible for controlling interactions inside the editor-activity
 */
public interface EditorControl   {
    /**
     * updates document-title on user input and reflects the change in action-bar
     * @param context the context where the change occurred
     */
    void onHeadlineChanged(Context context, View view);

    /**
     * sets the visibility of the appropriate toolbar according to the visibility of the keyboard
     * @param context the context where the keyboard visibility change occurred
     */
    void onKeyboardVisibilityChanged(Context context);
}

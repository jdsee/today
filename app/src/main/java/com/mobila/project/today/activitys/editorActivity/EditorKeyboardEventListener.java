package com.mobila.project.today.activitys.editorActivity;

import android.app.Activity;
import android.view.View;

import com.mobila.project.today.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class EditorKeyboardEventListener implements KeyboardVisibilityEventListener {
    private Activity activity;

    EditorKeyboardEventListener(Activity activity) {
        this.activity=activity;
    }

    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (isOpen) {
            activity.findViewById(R.id.bottom_font_view).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.bottom_extension_view).setVisibility(View.INVISIBLE);
        } else {
            activity.findViewById(R.id.bottom_font_view).setVisibility(View.INVISIBLE);
            activity.findViewById(R.id.bottom_extension_view).setVisibility(View.VISIBLE);
        }
    }
}

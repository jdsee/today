package com.mobila.project.today.activitys.editorActivity;

import android.app.Activity;
import android.view.View;

import com.mobila.project.today.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class EditorKeyboardEventListener implements KeyboardVisibilityEventListener {
    private View bottomFont;
    private View bottomExtension;

    EditorKeyboardEventListener(Activity activity) {
        bottomFont = activity.findViewById(R.id.bottom_font_view);
        bottomExtension = activity.findViewById(R.id.bottom_extension_view);
    }

    @Override
    public void onVisibilityChanged(boolean isOpen) {
        if (isOpen) {
            bottomFont.setVisibility(View.VISIBLE);
            bottomExtension.setVisibility(View.INVISIBLE);
        } else {
            bottomFont.setVisibility(View.INVISIBLE);
            bottomExtension.setVisibility(View.VISIBLE);
        }
    }
}

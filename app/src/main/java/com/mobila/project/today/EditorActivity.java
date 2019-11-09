package com.mobila.project.today;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.utils.TextChangeListener;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {
    private EditText headline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String improvisedEvent = "Veranstalltung 3";
        final String improvisedDate = "30.02.2018";
        final String improvisedSubjectCategorie = "Übung";
        final String improvisedSubject = "Mobile Anwendungen";

        //set view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //set action-bar heading
        Objects.requireNonNull(getSupportActionBar()).setTitle(improvisedEvent);
        //set action-bar subtitle
        getSupportActionBar().setSubtitle(improvisedDate + " - "+  improvisedSubjectCategorie
                + ", "+ improvisedSubject);

        //set textEdit-listener to display current headline in Action-bar
        headline = findViewById(R.id.editor_headline);
        headline.addTextChangedListener(new TextChangeListener<EditText>(headline) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String content = headline.getText().toString();
                Objects.requireNonNull(getSupportActionBar()).setTitle(content);
                if (content.equals(""))
                    Objects.requireNonNull(getSupportActionBar()).setTitle(improvisedEvent);
            }
        });

        //set keyboard-eventlistener to disblay either the extension-toolbar or the text-toolbar
        KeyboardVisibilityEvent.setEventListener(this,
                new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen){
                    findViewById(R.id.bottom_font_view).setVisibility(View.VISIBLE);
                    findViewById(R.id.bottom_extension_view).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.bottom_font_view).setVisibility(View.INVISIBLE);
                    findViewById(R.id.bottom_extension_view).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_action_bar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //force keyboard to close if necessary
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true;
    }
}

package com.mobila.project.today.activitys.editorActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Note;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {
    private Note note;
    private EditText editTextHeadline;
    private EditText editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.note = new Note(3, "Headline", new SpannableString("Inhalt"),
                2, "Mobile Anwendungen", "Übung",
                "Veranstalltung 3", "07.05.18");

        //set view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //set action-bar heading
        Objects.requireNonNull(getSupportActionBar()).setTitle(note.getEvent());

        //set action-bar subtitle
        getSupportActionBar().setSubtitle(note.getDate() + " - " + note.getCategory()
                + ", " + note.getCourse());

        //checks if device has camera. If not the "take-photo" item gets hidden
        if(!deviceHasCamera()){
            MenuItem cameraItem=findViewById(R.id.action_take_photo);
            cameraItem.setVisible(false);
        }

        //set textEdit-listener to display current editTextHeadline in Action-bar
        editTextHeadline = findViewById(R.id.editor_headline);
        editTextHeadline.addTextChangedListener(
                new EditorHeadlineTextChangeListener(editTextHeadline, this,
                        editTextHeadline, note));

        //set textEdit-listener to keep the Note synchronized with the EditText-view
        editTextContent = findViewById(R.id.editor_note);
        editTextContent.addTextChangedListener(
                new EditorContentTextChangeListener(editTextContent, this,
                        editTextContent, note));

        //set keyboard-eventListener to display either the extension-toolbar or the text-toolbar
        KeyboardVisibilityEvent.setEventListener(
                this, new EditorKeyboardEventListener(this));
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
        //sliding animation to the left out of the activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //force keyboard to close
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        onBackPressed();
        //sliding animation to the left out of the activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true;
    }


    /**
     * Is invoked by pressing the Colour-Symbol in the lower menu.
     * It sets the colour of the selected text
     * @param item The item which was pressed
     */
    public void onColourPickerPressed(MenuItem item) {
        setStyle(new ForegroundColorSpan(Color.RED));
    }

    /**
     * Is invoked by pressing the Highlight-Symbol in the lower menu.
     * @param item The item which was pressed
     */
    public void onHighlightPickerPressed(MenuItem item){
        setStyle(new BackgroundColorSpan(Color.GREEN));
    }

    /**
     * Is invoked by pressing the Style-Symbol in the lower menu.
     * @param item The item which was pressed
     */
    public void onStylePickerPressed(MenuItem item){
        setStyle(new StyleSpan(Typeface.ITALIC));
    }

    /**
     * Applies given Style onto the selected text
     * @param parcelable The style that should be applied
     */
    private void setStyle(Parcelable parcelable){
        int startSelection = editTextContent.getSelectionStart();
        int endSelection = editTextContent.getSelectionEnd();
        note.getContent().setSpan(parcelable, startSelection, endSelection, 0);
        editTextContent.setText(note.getContent(), TextView.BufferType.SPANNABLE);
    }

    private boolean deviceHasCamera(){
        PackageManager pm = getBaseContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}

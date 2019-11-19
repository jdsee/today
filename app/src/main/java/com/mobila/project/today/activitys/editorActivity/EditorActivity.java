package com.mobila.project.today.activitys.editorActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Spannable;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobila.project.today.R;
import com.mobila.project.today.model.Note;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Objects;

public class EditorActivity extends AppCompatActivity {
    private Note note;
    private EditText editTextContent;
    private boolean keyBoardOpen;
    final int REQUEST_IMAGE_CAPTURE = 0;
    final int REQUEST_FILE_OPEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Slide-in Animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        this.note = new Note(3, "Headline", new SpannableString("Inhalt"),
                2, "Mobile Anwendungen", "Übung",
                "Veranstalltung 3", "07.05.18");
        //set view
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar_Bridge);
        getWindow().setNavigationBarColor(Color.GRAY);
        setContentView(R.layout.activity_editor);
        Toolbar bar = findViewById(R.id.editor_toolbar);
        setSupportActionBar(bar);
        //sets preset Title
        EditText headline = findViewById(R.id.editor_title);
        headline.setHint(note.getEvent());
        //Sets Action-Listener on "next-button" of keyboard inside the TitleEditText to move the
        //focus to the NoteEditText if pressed
        headline.setOnEditorActionListener(new TitleOnEditorActionListener(this));
        //Set subtitle to appropriate content of the note
        TextView textView = findViewById(R.id.editor_subtitle);
        textView.setText(
                String.format("%s  -  %s %s", note.getDate(), note.getCourse(), note.getCategory()));

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        //checks if device has camera. If not the "take-photo" item gets hidden
        if (!deviceHasCamera()) {
            MenuItem cameraItem = findViewById(R.id.action_take_photo);
            cameraItem.setVisible(false);
        }
        //set textEdit-listener to keep the Note synchronized with the EditText-view
        this.editTextContent = findViewById(R.id.editor_note);
        editTextContent.addTextChangedListener(
                new EditorContentTextChangeListener(this,
                        this.editTextContent, this.note));

        //set keyboard-eventListener to display either the extension-toolbar or the text-toolbar
        KeyboardVisibilityEvent.setEventListener(
                this, new EditorKeyboardEventListener(this));
        //Remove elevation from note-button
        FloatingActionButton actionButton = findViewById(R.id.button_note);
        actionButton.setCompatElevation(0);
    }

    public void setKeyboardOpen(Boolean keyBoardOpen) {
        this.keyBoardOpen = keyBoardOpen;
    }

    /**
     * Method to close Activity
     *
     * @param view The vie that is taking this action
     */
    public void onBackPressed(View view) {
        finish();
        prepareGoBack();
    }

    @Override
    public void onBackPressed() {
        finish();
        prepareGoBack();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        prepareGoBack();
        return true;
    }

    /**
     * Method for preparing leaving the activity
     */
    private void prepareGoBack() {
        //force keyboard to close
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        //sliding animation to the left out of the activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    /**
     * Is invoked by pressing the Colour-Symbol in the lower menu.
     * It sets the colour of the selected text
     *
     * @param item The item which was pressed
     */
    public void onColourPickerPressed(MenuItem item) {
        setStyle(new ForegroundColorSpan(Color.RED));
    }

    /**
     * Is invoked by pressing the Highlight-Symbol in the lower menu.
     *
     * @param item The item which was pressed
     */
    public void onHighlightPickerPressed(MenuItem item) {
        setStyle(new BackgroundColorSpan(Color.GREEN));
    }

    /**
     * Is invoked by pressing the Style-Symbol in the lower menu.
     *
     * @param item The item which was pressed
     */
    public void onStylePickerPressed(MenuItem item) {
        setStyle(new StyleSpan(Typeface.ITALIC));
    }

    /**
     * Applies given Style onto the selected text
     *
     * @param parcelable The style that should be applied
     */
    private void setStyle(Parcelable parcelable) {
        int startSelection = this.editTextContent.getSelectionStart();
        int endSelection = this.editTextContent.getSelectionEnd();
        this.note.getContent().setSpan(parcelable, startSelection, endSelection, 0);
        this.editTextContent.setText(this.note.getContent(), TextView.BufferType.SPANNABLE);
        //moves cursor to the end of the selection
        this.editTextContent.setSelection(endSelection);
    }

    public void onTabButtonClicked(MenuItem item) {
        int tabWidth = 150;
        int startSelection = this.editTextContent.getSelectionStart();
        int endSelection = this.editTextContent.getSelectionEnd();
        String tab = "\t";
        editTextContent.getText().insert(startSelection, tab);
        this.note.setContent(editTextContent.getText());
        this.note.getContent().setSpan(
                new CustomTabWidthSpan(
                        Float.valueOf(tabWidth).intValue()),
                startSelection, endSelection+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.editTextContent.setText(this.note.getContent(), TextView.BufferType.SPANNABLE);
        //moves cursor to the end of the selection
        this.editTextContent.setSelection(endSelection+1);
    }

    public void onNumberedBulledPointListCLicked(MenuItem item) {
        //TODO
    }

    public void onBulletPointListClicked(MenuItem item) {
        //TODO
    }


    /**
     * Opens Camera
     *
     * @param item The item which was pressed
     */
    public void onTakePhotoPickerPressed(MenuItem item) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onFilePickerPressed(MenuItem item) {
        Intent openFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openFileIntent.setType("*/*");
        startActivityForResult(openFileIntent, REQUEST_FILE_OPEN);
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Object attachedData = extras.get("data");
                this.note.addExtension(attachedData);
                Toast toast;
                if (requestCode == REQUEST_IMAGE_CAPTURE) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Image Saved", Toast.LENGTH_LONG);
                } else if (requestCode == REQUEST_FILE_OPEN) {
                    toast = Toast.makeText(getApplicationContext(),
                            "File Saved", Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(getApplicationContext(),
                            "Something went Wrong!", Toast.LENGTH_LONG);
                }
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Nothing got back", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nothing was saved", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Method for detecting if the device on which the application is installed has a camera
     *
     * @return If the device has a camera
     */
    private boolean deviceHasCamera() {
        PackageManager pm = getBaseContext().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Method for opening the functions hidden behind the three dots in the Toolbar
     *
     * @param view the view that calls this method
     */
    public void showEditorHiddenFunctions(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.editor_action_bar, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (keyBoardOpen) {
            inflater.inflate(R.menu.editor_font_options_bottom, menu);
        } else {
            inflater.inflate(R.menu.editor_extension_bottom, menu);
        }
        return true;
    }
}

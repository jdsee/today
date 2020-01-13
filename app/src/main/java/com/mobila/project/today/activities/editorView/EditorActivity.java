package com.mobila.project.today.activities.editorView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.DatabaseConnectionActivity;
import com.mobila.project.today.activities.editorView.listeners.EditorKeyboardEventListener;
import com.mobila.project.today.activities.editorView.listeners.TitleOnEditorActionListener;
import com.mobila.project.today.activities.editorView.listeners.noteFocusChangeListener;
import com.mobila.project.today.control.ShareContentManager;
import com.mobila.project.today.control.NoteControl;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Lecture;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class EditorActivity extends DatabaseConnectionActivity {

    private static final String TAG = EditorActivity.class.getName();
    private Lecture lecture;

    private EditText contentEditText;
    private EditText titleEditText;

    private NoteControl noteEditor;

    private boolean keyBoardOpen;
    private boolean focusOnNoteContent;
    private Note note;

    private AttachmentView attachmentView;
    private TaskView taskView;

    private ShareContentManager shareContentManager;

    /**
     * Method for creating this activity.
     * It is responsible for retrieving information of the bundle and setting up all views, contentEditText
     * and listeners
     *
     * @param savedInstanceState The bundle that has at least a note attached to it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set view
        super.onCreate(savedInstanceState);
        //get Note from Intent

        setContentView(R.layout.activity_editor);
        setSupportActionBar(findViewById(R.id.editor_toolbar));

        this.contentEditText = findViewById(R.id.editor_content);
        this.titleEditText = findViewById(R.id.editor_title);

        this.lecture = getIntent().getParcelableExtra(Lecture.INTENT_EXTRA_CODE);
        this.shareContentManager = new ShareContentManager(this);

        if (lecture != null) {
            this.note = lecture.getNote();
            this.contentEditText.setText(this.note.getContent());
            this.titleEditText.setText(this.note.getTitle());
            this.setupSubtitle();
            this.attachmentView = new AttachmentView(this, this.lecture);
            this.taskView = new TaskView(this, this.lecture);
        } else {
            this.note = shareContentManager.getNoteFromIntent(getIntent());
            this.findViewById(R.id.tasks_open_button).setVisibility(View.GONE);
            hideShareIfNecessary();
        }

        this.contentEditText.setText(this.note.getContent());
        this.titleEditText.setText(this.note.getTitle());


        hideCameraIfNotAvailable();

        setupListeners();
        setupControls();
        showAppropriateMenu();
    }

    private void hideShareIfNecessary() {
        findViewById(R.id.menu_button).setVisibility(View.GONE);
    }

    /**
     * Method for setting up and displaying the note.
     * This is where the Title and the contentEditText of the note get connected with their respective view
     */
    private void setupSubtitle() {
        this.titleEditText.setHint("Lecture " + this.lecture.getLectureNr());
        TextView textView = findViewById(R.id.editor_subtitle);
        SimpleDateFormat lectureDate =
                new SimpleDateFormat(DateUtils.DAY_DATE_FORMAT, Locale.getDefault());
        textView.setText(String.format(
                "%s  -  %s", lectureDate.format(lecture.getDate()), this.lecture.getSection().getTitle()));
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
    }


    @Override
    protected void onPause() {
        if (this.taskView != null) this.taskView.removeCheckedTasks();
        this.saveContent();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Method for saving the contentEditText of the editor
     */
    private void saveContent() {
        String title = this.titleEditText.getText().toString();
        note.setTitle(title);

        Spannable content = this.contentEditText.getText();
        note.setContent(content);
    }

    /**
     * Method that hides the camera if none is detected on the device that runs this application
     */
    private void hideCameraIfNotAvailable() {
        if (!deviceHasCamera()) {
            ImageButton cameraItem = findViewById(R.id.button_add_photo);
            cameraItem.setVisibility(View.GONE);
        }
    }

    /**
     * Method for initializing the Logic behind the Attachments and the Editor
     */
    private void setupControls() {
        this.noteEditor = new NoteControl(this, this.contentEditText);
    }

    /**
     * Method for setting up different Action-Listeners.
     * This is needed for showing the appropriate views depending on the users actions.
     */
    private void setupListeners() {
        this.titleEditText.setOnEditorActionListener(new TitleOnEditorActionListener(this));
        KeyboardVisibilityEvent.setEventListener(
                this, new EditorKeyboardEventListener(this));
        this.contentEditText.setOnFocusChangeListener(new noteFocusChangeListener(this));
    }

    /**
     * Method for setting the state of the keyboard
     *
     * @param keyBoardOpen Defines if the keyboard is open or closed
     */
    public void setKeyboardOpen(Boolean keyBoardOpen) {
        this.keyBoardOpen = keyBoardOpen;
        if (this.attachmentView != null) this.attachmentView.closeAttachments();
    }

    /**
     * Method for setting focusOnNoteContent
     */
    public void setFocusOnNoteContent() {
        this.focusOnNoteContent = true;
    }

    /**
     * Method for removing focusOnNoteContent
     */
    public void removeFocusOnNoteContent() {
        this.focusOnNoteContent = false;
    }

    /**
     * Method to close Activity
     *
     * @param view The vie that is taking this action
     */
    public void onBackPressed(View view) {
        prepareGoBack();
        finish();
    }

    @Override
    public void onBackPressed() {
        prepareGoBack();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Method for preparing leaving the activity.
     * It closes the Keyboard if it is visible and starts the go back animation
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
    }

    /**
     * Method for displaying a menu of different font-styles
     *
     * @param view the font-style icon that was clicked
     */
    public void onFontStyleChooserClicked(View view) {
        LinearLayout fontStyleOptions = findViewById(R.id.editor_font_styles);
        handleMenuClick(fontStyleOptions);
    }

    /**
     * Method for displaying a menu of different font-colours
     *
     * @param view the font-colours icon that was clicked
     */
    public void onFontColourChooserClicked(View view) {
        LinearLayout fontColorOptions = findViewById(R.id.editor_color_chooser);
        handleMenuClick(fontColorOptions);
    }

    /**
     * Method for displaying a menu of different font-highlight-options
     *
     * @param view the font-highlight icon that was clicked
     */
    public void onFontHighlighterChooserPressed(View view) {
        LinearLayout fontHighlighterOptions = findViewById(R.id.font_highlighter_chooser);
        handleMenuClick(fontHighlighterOptions);
    }

    /**
     * Method for displaying or closing the sub-menu of a pressed item depending on its prior
     * visibility-state
     *
     * @param menu the menu that should be opened or closed
     */
    private void handleMenuClick(LinearLayout menu) {
        if (menu.getVisibility() == View.GONE) {
            closeTasks(menu);
            closeFontOptionMenus();
            menu.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.GONE);
        }
    }

    /**
     * Method for applying a the appropriate font-style depending on the icon that was pressed
     *
     * @param icon the icon that was pressed
     */
    public void onStyleSetterClicked(View icon) {
        noteEditor.applyStyle(icon);
        closeFontOptionMenus();
    }

    /**
     * Method for closing all font-option menus
     */
    public void closeFontOptionMenus() {
        findViewById(R.id.font_highlighter_chooser).setVisibility(View.GONE);
        findViewById(R.id.editor_color_chooser).setVisibility(View.GONE);
        findViewById(R.id.editor_font_styles).setVisibility(View.GONE);
    }

    /**
     * Method for inserting a Tab in the note-contentEditText
     *
     * @param view the clicked tab-icon. Is only needed for using this method via the layout
     */
    public void onTabButtonClicked(View view) {
        this.noteEditor.insertTab();
    }

    /**
     * Opens Camera for taking one picture
     *
     * @param view the clicked camera-icon. Is only needed for using this method via the layout
     */
    public void onTakePhotoPickerPressed(View view) {
        this.attachmentView.onTakePhotoPickerPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "request permission returned. " + permissions[0] + " " + grantResults[0]);

        //for now only camera permission is requested

        if (requestCode == 1
                && permissions[0].equals(Manifest.permission.CAMERA)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            this.attachmentView.intentTakePhoto();
        }
    }

    /**
     * Method for importing one file into a note
     *
     * @param view the pressed file-icon. Is only needed for using this method via the layout
     */
    public void onFilePickerPressed(View view) {
        this.attachmentView.onFilePickerPressed();
    }

    /**
     * Method for resolving the contentEditText of a received intent
     *
     * @param requestCode the code of the request that asked for a result
     * @param resultCode  the code that contains information about the success of the request
     * @param data        the data contained in the result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.attachmentView.onActivityResult(requestCode, resultCode, data);
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
        inflater.inflate(R.menu.editor_toolbar_options, popup.getMenu());
        popup.show();
    }

    /**
     * opens or closes the extension list depending on if it was open or closed before the button
     * was pressed. If it was open, it gets closed and vise versa
     *
     * @param view the view that calls this Method. Only needed for calling this Method via layout
     */
    public void onAttachmentsPressed(View view) {
        this.attachmentView.onAttachmentsPressed();
    }

    /**
     * Method for displaying the task-view
     *
     * @param view the view that was clicked on. Only needed for calling this method via layout
     */
    public void openTasks(View view) {
        closeFontOptionMenus();
        this.taskView.openTasks();
    }

    /**
     * Method for closing the task-view
     *
     * @param view the view that was clicked on. Only needed for calling this method via layout
     */
    public void closeTasks(View view) {
        this.taskView.closeTasks();
    }

    /**
     * Method for showing the relevant menu for the view in focus.
     * The font-options should be only shown if the note-contentEditText is in focus and the keyboard open
     */
    public void showAppropriateMenu() {
        if ((keyBoardOpen && focusOnNoteContent) | this.attachmentView == null) {
            showFontMenu();
        } else {
            showAttachmentMenu();
        }
    }

    /**
     * Method for displaying the font-menu and hiding the options-menu
     */
    private void showFontMenu() {
        findViewById(R.id.edit_items).setVisibility(View.VISIBLE);
        if (this.attachmentView != null) this.attachmentView.hideAttachmentMenu();
    }

    /**
     * Method for displaying the attachment-view and hiding the font-menu
     */
    private void showAttachmentMenu() {
        if (this.attachmentView != null) this.attachmentView.showAttachmentMenu();
        findViewById(R.id.edit_items).setVisibility(View.GONE);
    }

    public void onShareLectureClicked(MenuItem item) {
        Spannable spannable = this.contentEditText.getText();
        String noteTitle = this.titleEditText.getText().toString();
        this.shareContentManager.sendSpannable(spannable, noteTitle);
    }

    public void onSharePdfClicked(MenuItem item) {
        EditText out = new EditText(this);
        out.setText(this.titleEditText.getText());
        Editable outText = out.getText();
        outText.setSpan(new StyleSpan(Typeface.BOLD), 0, out.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        outText.setSpan(new RelativeSizeSpan(3f), 0, out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        outText.append("\n\n\n");
        outText.append(this.contentEditText.getText());


        Editable cachedContent = this.contentEditText.getText();
        this.contentEditText.setText(outText);

        this.shareContentManager.createPdfFromContentView(contentEditText, titleEditText.getText().toString());

        this.contentEditText.setText(cachedContent);
    }

    public void onDeleteLectureClicked(MenuItem item) {
        this.lecture.getSection().removeLecture(this.lecture);
        onBackPressed();
    }
}
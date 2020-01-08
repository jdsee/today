package com.mobila.project.today.activities.editorView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
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
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.DatabaseConnectionActivity;
import com.mobila.project.today.activities.adapters.AttachmentAdapter;
import com.mobila.project.today.activities.adapters.RecyclerViewButtonClickListener;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.activities.editorView.listeners.EditorKeyboardEventListener;
import com.mobila.project.today.activities.editorView.listeners.TitleOnEditorActionListener;
import com.mobila.project.today.activities.editorView.listeners.noteFocusChangeListener;
import com.mobila.project.today.control.AttachmentControl;
import com.mobila.project.today.control.CommunicationController;
import com.mobila.project.today.control.NoteControl;
import com.mobila.project.today.control.TaskController;
import com.mobila.project.today.control.utils.AttachmentUtils;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Note;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.model.dataProviding.dataAccess.OrganizerDataProvider;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.mobila.project.today.control.AttachmentControl.REQUEST_FILE_OPEN;
import static com.mobila.project.today.control.AttachmentControl.REQUEST_TAKE_PHOTO;

public class EditorActivity extends DatabaseConnectionActivity
        implements RecyclerViewButtonClickListener {

    private static final String TAG = EditorActivity.class.getName();
    private Lecture lecture;
    private Section section;
    private List<Attachment> attachments;

    private EditText contentEditText;
    private EditText titleEditText;

    private NoteControl noteEditor;
    private AttachmentControl attachmentControl;

    private RecyclerView fileContainer;

    private boolean keyBoardOpen;
    private boolean focusOnNoteContent;
    private Note note;

    private AttachmentAdapter attachmentAdapter;

    /**
     * Method for creating this activity.
     * It is responsible for retrieving information of the bundle and setting up all views, contentEditText
     * and listeners
     *
     * @param savedInstanceState The bundle that has at least a note attached to it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Slide-in Animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //set view
        super.onCreate(savedInstanceState);
        //get Note from Intent

        this.lecture = getIntent().getParcelableExtra(Lecture.INTENT_EXTRA_CODE);
        this.note = Objects.requireNonNull(lecture).getNote();

        this.section = lecture.getSection();
        this.attachments = lecture.getAttachments();


        setupViews();

        this.contentEditText = findViewById(R.id.editor_content);
        this.titleEditText = findViewById(R.id.editor_title);

        this.contentEditText.setText(this.note.getContent());
        this.titleEditText.setText(this.note.getTitle());

        this.attachmentAdapter = new AttachmentAdapter(this.getApplicationContext(), this, this.attachments);

        hideCameraIfNotAvailable();
        setupContent();
        setupListeners();
        setupControls();
    }

    @Override
    protected void onPause() {
        this.saveContent();
        super.onPause();
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
     * Method for Setting up the theme of the activity and all the recycler-view containers like
     * the attachment-view and the task-view
     */
    private void setupViews() {
        setContentView(R.layout.activity_editor);
        setSupportActionBar(findViewById(R.id.editor_toolbar));
        initAttachmentsView();
        initTaskView();
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
        this.attachmentControl = new AttachmentControl(this);
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
     * Method for setting up and displaying the note.
     * This is where the Title and the contentEditText of the note get connected with their respective view
     */
    private void setupContent() {
        //Todo Title just needs to be preset if no other has been set
        this.titleEditText.setHint("Lecture " + this.lecture.getLectureNr());
        TextView textView = findViewById(R.id.editor_subtitle);
        SimpleDateFormat lectureDate =
                new SimpleDateFormat(DateUtils.DAY_DATE_FORMAT, Locale.getDefault());
        textView.setText(String.format(
                "%s  -  %s", lectureDate.format(lecture.getDate()), this.section.getTitle()));
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
    }

    /**
     * Method for setting the state of the keyboard
     *
     * @param keyBoardOpen Defines if the keyboard is open or closed
     */
    public void setKeyboardOpen(Boolean keyBoardOpen) {
        this.keyBoardOpen = keyBoardOpen;
        closeAttachments();
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
        //TODO save note


        //sliding animation to the left out of the activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        startActivityForResult(attachmentControl.getTakePictureIntent(), REQUEST_TAKE_PHOTO);
    }

    /**
     * Method for importing one file into a note
     *
     * @param view the pressed file-icon. Is only needed for using this method via the layout
     */
    public void onFilePickerPressed(View view) {
        startActivityForResult(attachmentControl.getOpenFileIntent(), REQUEST_FILE_OPEN);
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
        //TODO Needs to avoid contentEditText provider all together after SQL db is established to make contentEditText provider obsolete
        super.onActivityResult(requestCode, resultCode, data);
        OrganizerDataProvider.getInstance().openDbConnection(this);
        Uri uri = attachmentControl.onActivityResult(requestCode, resultCode, data);
        this.addFileToAttachments(uri);
        updateFileNumber();
    }

    public void addFileToAttachments(Uri uri) {
        if (uri != null) {
            String fileName = AttachmentUtils.getFileName(this.getApplicationContext(), uri);
            this.lecture.addAttachment(new Attachment(fileName, uri));

            this.attachmentAdapter.notifyDataSetChanged();

            Log.d(TAG, "file behind uri has been added to attachments");
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
        if (fileContainer.getVisibility() == View.VISIBLE) {
            closeAttachments();
        } else if (lecture.getAttachments().size() != 0) {
            openAttachments();
        } else Toast.makeText(
                this, "Your attachments go here", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method for closing the attachment-view
     */
    public void closeAttachments() {
        fileContainer.setVisibility(View.GONE);
        displayFileNumber();
    }

    /**
     * Method for opening the attachmentControl-view
     */
    public void openAttachments() {
        fileContainer.setVisibility(View.VISIBLE);
        findViewById(R.id.attachment_counter).setVisibility(View.GONE);
        hideFileNumber();
    }

    /**
     * Method for hiding the attachment file-counter
     */
    private void hideFileNumber() {
        findViewById(R.id.attachment_counter).setVisibility(View.GONE);
    }

    /**
     * Method for displaying the file-counter.
     * It only display a number if there are actually file/s saved
     */
    public void displayFileNumber() {
        TextView attachmentCounter = findViewById(R.id.attachment_counter);
        if (attachments.size() == 0) {
            attachmentCounter.setVisibility(View.GONE);
        } else {
            attachmentCounter.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method for synchronising the file-counter with the saved attachmentControl
     */
    public void updateFileNumber() {
        TextView attachmentCounter = findViewById(R.id.attachment_counter);
        int numberOfAttachments = attachments.size();
        attachmentCounter.setText(
                String.format(Locale.getDefault(), "%d", numberOfAttachments));
        if (numberOfAttachments == 0)
            closeAttachments();
        if (this.fileContainer.getVisibility() == View.GONE)
            displayFileNumber();
    }

    /**
     * Method for initializing the attachmentControl-view
     */
    private void initAttachmentsView() {
        this.fileContainer = findViewById(R.id.recycler_view_files);
        this.attachmentAdapter = new AttachmentAdapter(this, this, this.lecture.getAttachments());
        this.fileContainer.setAdapter(attachmentAdapter);
        this.fileContainer.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void recyclerViewButtonClicked(View view, int position) {
        lecture.removeAttachment(this.attachments.get(position));
        this.attachmentAdapter.notifyDataSetChanged();
    }

    /**
     * Method for initializing the task-view
     */
    private void initTaskView() {
        Course course = this.lecture.getSection().getCourse();
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter taskAdapter = new TaskAdapter(this, course.getTasks());
        recyclerView.setAdapter(taskAdapter);
        EditText taskEnterField = findViewById(R.id.edit_text_add_task);
        ImageButton confirmationButton = findViewById(R.id.add_task_button);
        new TaskController(taskEnterField, confirmationButton, course, taskAdapter);
    }

    /**
     * Method for displaying the task-view
     *
     * @param view the view that was clicked on. Only needed for calling this method via layout
     */
    public void openTasks(View view) {
        closeFontOptionMenus();
        displayCloseTasksButton();
        CoordinatorLayout noteView = findViewById(R.id.note_view);
        noteView.setVisibility(View.VISIBLE);
    }

    /**
     * Method for closing the task-view
     *
     * @param view the view that was clicked on. Only needed for calling this method via layout
     */
    public void closeTasks(View view) {
        displayTasksButton();
        CoordinatorLayout noteView = findViewById(R.id.note_view);
        noteView.setVisibility(View.GONE);
    }

    /**
     * Method for displaying the tasks-button
     */
    private void displayTasksButton() {
        CoordinatorLayout taskButton = findViewById(R.id.note_action_button);
        taskButton.setVisibility(View.VISIBLE);
        CoordinatorLayout closeButton = findViewById(R.id.go_back_actionButton);
        closeButton.setVisibility(View.GONE);
    }

    /**
     * Method for displaying the close-tasks-button
     */
    private void displayCloseTasksButton() {
        CoordinatorLayout taskButton = findViewById(R.id.note_action_button);
        taskButton.setVisibility(View.GONE);
        CoordinatorLayout closeButton = findViewById(R.id.go_back_actionButton);
        closeButton.setVisibility(View.VISIBLE);
    }

    /**
     * Method for showing the relevant menu for the view in focus.
     * The font-options should be only shown if the note-contentEditText is in focus and the keyboard open
     */
    public void showAppropriateMenu() {
        if (keyBoardOpen && focusOnNoteContent) {
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
        findViewById(R.id.attachment_items).setVisibility(View.GONE);
    }

    /**
     * Method for displaying the attachment-view and hiding the font-menu
     */
    private void showAttachmentMenu() {
        findViewById(R.id.attachment_items).setVisibility(View.VISIBLE);
        findViewById(R.id.edit_items).setVisibility(View.GONE);
    }

    public void onShareLectureClicked(MenuItem item) {
        CommunicationController communicator = new CommunicationController(this);
        Spannable spannable = this.contentEditText.getText();
        String noteTitle = this.titleEditText.getText().toString();
        communicator.sendSpannable(spannable, noteTitle);
    }
}
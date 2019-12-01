package com.mobila.project.today.activities.editorActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.activities.editorActivity.listeners.EditorKeyboardEventListener;
import com.mobila.project.today.activities.editorActivity.listeners.TitleOnEditorActionListener;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.modelMock.TaskMock;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

public class EditorActivity extends AppCompatActivity {
    private NoteMock note;
    private EditorNoteControl noteEditor;
    private EditorAttachmentControl attachments;

    private EditorFileHolderAdapter fileHolderAdapter;
    private View fileContainer;

    private boolean keyBoardOpen;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Slide-in Animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //set view
        super.onCreate(savedInstanceState);
        //get Note from Intent
        this.note = getIntent().getParcelableExtra(NoteMock.INTENT_EXTRA_CODE);
        this.tasks = getIntent().getParcelableArrayListExtra(Task.INTENT_EXTRA_CODE);

        setupViews();
        hideCameraIfNotAvailable();
        setupContent();
        setupListeners();
        setupControls();
    }

    private void setupViews() {
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar_Bridge);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.lightGrey));
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_editor);
        setSupportActionBar(findViewById(R.id.editor_toolbar));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.lightGrey));
        this.fileContainer = findViewById(R.id.recycler_view_files);
        initAttachmentsView();
        initTaskView();
    }

    private void hideCameraIfNotAvailable() {
        if (!deviceHasCamera()) {
            ImageButton cameraItem = findViewById(R.id.button_add_photo);
            cameraItem.setVisibility(View.GONE);
        }
    }

    private void setupControls() {
        this.noteEditor = new EditorNoteControl(this, this.note);
        this.attachments = new EditorAttachmentControl(this, note, fileHolderAdapter);
    }

    private void setupListeners() {
        EditText headline = findViewById(R.id.editor_title);
        //todo find out if still necessary with focus
        headline.setOnEditorActionListener(new TitleOnEditorActionListener(this));
        KeyboardVisibilityEvent.setEventListener(
                this, new EditorKeyboardEventListener(this));
    }

    private void setupContent() {
        //Todo Title just needs to be preset if no other has been set
        EditText headline = findViewById(R.id.editor_title);
        headline.setHint(note.getSection());
        TextView textView = findViewById(R.id.editor_subtitle);
        textView.setText(String.format(
                "%s  -  %s %s", note.getDate(), note.getCourse(), note.getCategory()));
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

    public void onFontStyleChooserClicked(View view){
        LinearLayout fontStyleOptions = findViewById(R.id.editor_font_styles);
        handleMenuClick(fontStyleOptions);
    }

    public void onFontColourChooserClicked(View view){
        LinearLayout fontColorOptions = findViewById(R.id.editor_color_chooser);
        handleMenuClick(fontColorOptions);
    }

    public void onFontHighlighterChooserPressed(View view){
        LinearLayout fontHighlighterOptions = findViewById(R.id.font_highlighter_chooser);
        handleMenuClick(fontHighlighterOptions);
    }

    private void handleMenuClick(LinearLayout menu){
        if (menu.getVisibility() == View.GONE){
            closeTasks(menu);
            closeMenus();
            menu.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.GONE);
        }
    }

    public void onStyleSetterClicked(View view){
        noteEditor.applyStyle(view);
    }

    public void closeMenus(){
        findViewById(R.id.font_highlighter_chooser).setVisibility(View.GONE);
        findViewById(R.id.editor_color_chooser).setVisibility(View.GONE);
        findViewById(R.id.editor_font_styles).setVisibility(View.GONE);
    }

    /**
     * Method for inserting a Tab in the note-content
     */
    public void onTabButtonClicked(View view) {
        this.noteEditor.insertTab();
    }

    /**
     * Opens Camera
     */
    public void onTakePhotoPickerPressed(View view) {
        attachments.takePicture();
    }

    /**
     * Method for importing a file into a note
     */
    public void onFilePickerPressed(View view) {
        attachments.importFile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO Needs to avoid content provider all together after SQL db is established to make content provider obsolete
        super.onActivityResult(requestCode, resultCode, data);
        attachments.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.keyBoardOpen) {
            if (findViewById(R.id.recycler_view_files).getVisibility()==View.VISIBLE) {
                closeAttachments();
            }
        }
        return true;
    }

    /**
     * opens or closes the extension list depending on if it was open or closed before the button
     * was pressed. If it was open, it gets closed and vise versa
     */
    public void onAttachmentsPressed(View view) {
        if (findViewById(R.id.recycler_view_files).getVisibility()==View.VISIBLE) {
            closeAttachments();
        } else if (this.note.getAttachmentCount() != 0) {
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
     * Method for opening the attachments-view
     */
    public void openAttachments() {
        fileContainer.setVisibility(View.VISIBLE);
        findViewById(R.id.attachment_counter).setVisibility(View.GONE);
        hideFileNumber();
    }

    private void hideFileNumber() {
        findViewById(R.id.attachment_counter).setVisibility(View.GONE);
    }

    public void displayFileNumber(){
        TextView attachmentCounter = findViewById(R.id.attachment_counter);
        if (note.getAttachmentCount()==0){
            attachmentCounter.setVisibility(View.GONE);
        }
        else {
            attachmentCounter.setVisibility(View.VISIBLE);
        }
    }

    public void updateFileNumber(){
        TextView attachmentCounter = findViewById(R.id.attachment_counter);
        int numberOfAttachments = note.getAttachmentCount();
        attachmentCounter.setText(
                String.format(Locale.getDefault(), "%d", numberOfAttachments));
        if (numberOfAttachments == 0)
            closeAttachments();
        if (findViewById(R.id.recycler_view_files).getVisibility()==View.GONE)
            displayFileNumber();
    }

    /**
     * Method for initializing the attachments-view
     */
    private void initAttachmentsView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_files);
        this.fileHolderAdapter = new EditorFileHolderAdapter( this, this.note);
        recyclerView.setAdapter(this.fileHolderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openTasks(View view){
        closeMenus();
        displayCloseNoteButton();
        CoordinatorLayout noteView = findViewById(R.id.note_view);
        noteView.setVisibility(View.VISIBLE);
    }

    public void closeTasks(View view){
        displayNoteButton();
        CoordinatorLayout noteView = findViewById(R.id.note_view);
        noteView.setVisibility(View.GONE);
    }

    private void displayNoteButton(){
        CoordinatorLayout taskButton = findViewById(R.id.note_action_button);
        taskButton.setVisibility(View.VISIBLE);
        CoordinatorLayout closeButton = findViewById(R.id.go_back_actionButton);
        closeButton.setVisibility(View.GONE);
    }

    private void displayCloseNoteButton(){
        CoordinatorLayout taskButton = findViewById(R.id.note_action_button);
        taskButton.setVisibility(View.GONE);
        CoordinatorLayout closeButton = findViewById(R.id.go_back_actionButton);
        closeButton.setVisibility(View.VISIBLE);
    }

    public void showAppropiateItems() {
        LinearLayout editItems= findViewById(R.id.edit_items);
        CoordinatorLayout attachmentItems = findViewById(R.id.attachment_items);
        if (keyBoardOpen){
            editItems.setVisibility(View.VISIBLE);
            attachmentItems.setVisibility(View.GONE);
        } else {
            editItems.setVisibility(View.GONE);
            attachmentItems.setVisibility(View.VISIBLE);
        }
    }
}
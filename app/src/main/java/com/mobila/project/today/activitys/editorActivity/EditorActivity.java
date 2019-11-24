package com.mobila.project.today.activitys.editorActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobila.project.today.R;
import com.mobila.project.today.activitys.editorActivity.listeners.EditorContentTextChangeListener;
import com.mobila.project.today.activitys.editorActivity.listeners.EditorKeyboardEventListener;
import com.mobila.project.today.activitys.editorActivity.listeners.TitleOnEditorActionListener;
import com.mobila.project.today.modelMock.NoteMock;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.xml.datatype.Duration;

public class EditorActivity extends AppCompatActivity {
    private NoteMock note;
    private EditText editTextContent;

    private boolean keyBoardOpen;

    private final int REQUEST_TAKE_PHOTO = 1;
    private final int REQUEST_FILE_OPEN = 2;

    private boolean extensionsOpen = false;

    String currentImagePath;

    FileHolderAdapter fileHolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Slide-in Animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //set view
        super.onCreate(savedInstanceState);
        //get Note from Intent
        Intent intent = getIntent();
        this.note = intent.getParcelableExtra("EXTRA_NOTE");
        //set Theme with grey Navigation-Bar
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
                String.format(
                        "%s  -  %s %s", note.getDate(), note.getCourse(), note.getCategory()));
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        //checks if device has camera. If not the "take-photo" item gets hidden
        if (!deviceHasCamera()) {
            MenuItem cameraItem = findViewById(R.id.action_take_photo);
            cameraItem.setVisible(false);
        }
        //set textEdit-listener to keep the NoteMock synchronized with the EditText-view
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Text-Color Options
            case R.id.colour_yellow:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_yellow)));
                return true;
            case R.id.colour_orange:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_orange)));
                return true;
            case R.id.colour_red:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_red)));
                return true;
            case R.id.colour_purple:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_purple)));
                return true;
            case R.id.colour_blue:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_blue)));
                return true;
            case R.id.colour_green:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.textcolour_green)));
                return true;

            //Highlighter Options
            case R.id.highlighter_yellow:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.highlighter_yellow)));
                return true;
            case R.id.highlighter_green:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.highlighter_green)));
                return true;
            case R.id.highlighter_blue:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.highlighter_blue)));
                return true;
            case R.id.highlighter_purple:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.highlighter_purple)));
                return true;
            case R.id.highlighter_red:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.highlighter_red)));
                return true;

            //Style Options
            case R.id.style_bold:
                setStyle(new StyleSpan(Typeface.BOLD));
                return true;
            case R.id.style_italic:
                setStyle(new StyleSpan(Typeface.ITALIC));
                return true;
            case R.id.style_underlined:
                setStyle(new UnderlineSpan());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        editTextContent.getText().delete(startSelection, endSelection);
        editTextContent.getText().insert(startSelection, tab);
        this.note.setContent(editTextContent.getText());
        this.note.getContent().setSpan(
                new CustomTabWidthSpan(
                        Float.valueOf(tabWidth).intValue()),
                startSelection, startSelection + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.editTextContent.setText(this.note.getContent(), TextView.BufferType.SPANNABLE);
        //moves cursor to the end of the selection
        this.editTextContent.setSelection(startSelection + 1);
    }


    /**
     * Opens Camera
     *
     * @param item The item which was pressed
     */
    public void onTakePhotoPickerPressed(MenuItem item) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensuring there is a camera on the device
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create File for photo
            File photoFile = createImageFile();
            //check if file was created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.mobila.project.today.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onFilePickerPressed(MenuItem item) {
        Intent openFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openFileIntent.setType("*/*");
        openFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(openFileIntent, REQUEST_FILE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO Needs t avoid content provider all together after SQL db is established to make content provider obsolete
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && currentImagePath != null) {
                File file = new File(currentImagePath);
                this.note.addAttachment(file);
                Toast.makeText(getApplicationContext(),
                        "Image Saved", Toast.LENGTH_LONG).show();
                if (fileHolderAdapter != null) {
                    fileHolderAdapter.notifyDataSetChanged();
                }
                this.currentImagePath = null;
            } else if (requestCode == REQUEST_FILE_OPEN && data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    String sourceString = fileUri.getPath();
                    File sourceFile = null;
                    if (sourceString != null) {
                        sourceFile = new File(sourceString);
                    }
                    String filename = getFileName(fileUri);
                    File destinationFile;
                    destinationFile =
                            new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
                    try {
                        if (sourceFile != null) {
                            Files.copy(sourceFile.toPath(), destinationFile.toPath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),
                            "File Saved", Toast.LENGTH_LONG).show();
                    this.note.addAttachment(destinationFile);
                    if (fileHolderAdapter != null) {
                        fileHolderAdapter.notifyDataSetChanged();
                    }
                } else Toast.makeText(getApplicationContext(),
                        "File was lost", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nothing was saved", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() {
        //File creation
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat(
                getString(R.string.date_format)).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName + ".jpg");
        //save file Path for other intents
        this.currentImagePath = image.getAbsolutePath();
        return image;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void openFile(File file){
        //TODO after Establishing SQLite Database
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "*/*");
//        startActivity(intent);
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
        View recyclerViewContainer = findViewById(R.id.recycler_view_file_holder);
        if (this.keyBoardOpen) {
            if (this.extensionsOpen) {
                recyclerViewContainer.setVisibility(View.GONE);
                findViewById(R.id.action_attachment).setBackgroundColor(Color.TRANSPARENT);
                this.extensionsOpen = false;
            }
            inflater.inflate(R.menu.editor_font_options_bottom, menu);
        } else {
            inflater.inflate(R.menu.editor_extension_bottom, menu);
        }
        return true;
    }

    /**
     * opens or closes the extension list depending on if it was open or closed before the button
     * was pressed. If it was open, it gets closed and vise versa
     *
     * @param item Has no purpose because there isn't a menu populated with items attached to this
     *             button. It is just there for the compiler.
     */
    public void onAttachmentsPressed(MenuItem item) {
        View recyclerContainer = findViewById(R.id.recycler_view_file_holder);
        if (this.extensionsOpen) {
            recyclerContainer.setVisibility(View.GONE);
            this.extensionsOpen = false;
            findViewById(R.id.action_attachment).setBackgroundColor(Color.TRANSPARENT);
        } else if(this.note.getAttachmentCount()!=0){
            initRecyclerView();
            recyclerContainer.setVisibility(View.VISIBLE);
            findViewById(R.id.action_attachment).setBackgroundColor(
                    ContextCompat.getColor(this, R.color.slightly_darker_grey));
            this.extensionsOpen = true;
        } else Toast.makeText(
                this, "Put your attachments here", Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_files);
        this.fileHolderAdapter = new FileHolderAdapter(this, this, this.note);
        recyclerView.setAdapter(this.fileHolderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

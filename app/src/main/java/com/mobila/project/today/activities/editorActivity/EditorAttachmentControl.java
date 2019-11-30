package com.mobila.project.today.activities.editorActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.AttachmentUtils;
import com.mobila.project.today.activities.adapters.FileHolderAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static android.app.Activity.RESULT_OK;

class EditorAttachmentControl {

    private EditorActivity activity;
    private NoteMock note;
    private FileHolderAdapter adapter;

    private final int REQUEST_TAKE_PHOTO = 1;
    private final int REQUEST_FILE_OPEN = 2;

    private String currentImagePath;

    EditorAttachmentControl(EditorActivity activity,
                            NoteMock note, @NonNull FileHolderAdapter adapter) {
        this.activity = activity;
        this.note = note;
        this.adapter = adapter;
    }

    /**
     * Method for taking a picture by opening a camera app
     */
    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensuring there is a camera on the device
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            //Create File for photo
            File photoFile = AttachmentUtils.createImageFile(activity);
            this.currentImagePath = photoFile.getAbsolutePath();
            //check if file was created
            Uri photoURI = FileProvider.getUriForFile(activity,
                    "com.mobila.project.today.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    /**
     * Method for importing a file by opening a file explorer app
     */
    void importFile() {
        Intent openFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openFileIntent.setType("*/*");
        openFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(openFileIntent, REQUEST_FILE_OPEN);
    }

    /**
     * Method that saves the files that were returned by a intent
     *
     * @param requestCode the Code of the request
     * @param resultCode  the confirmation if the request was successful
     * @param data        the optional data of the intent-result
     */
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && currentImagePath != null) {
                File file = new File(currentImagePath);
                this.note.addAttachment(file);
                Toast.makeText(activity.getApplicationContext(),
                        "Image Saved", Toast.LENGTH_LONG).show();
                this.currentImagePath = null;
            } else if (requestCode == REQUEST_FILE_OPEN && data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    String sourceString = fileUri.getPath();
                    File sourceFile = null;
                    if (sourceString != null) {
                        sourceFile = new File(sourceString);
                    }
                    String filename = AttachmentUtils.getFileName(activity, fileUri);
                    File destinationFile;
                    destinationFile =
                            new File(activity.getExternalFilesDir(
                                    Environment.DIRECTORY_DOCUMENTS), filename);
                    try {
                        if (sourceFile != null) {
                            Files.copy(sourceFile.toPath(), destinationFile.toPath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(activity.getApplicationContext(),
                            "File Saved", Toast.LENGTH_LONG).show();
                    this.note.addAttachment(destinationFile);
                } else Toast.makeText(activity.getApplicationContext(),
                        "File was lost", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity.getApplicationContext(),
                        "Nothing was saved", Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();
            activity.updateFileNumber();
        }
    }
}
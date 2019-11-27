package com.mobila.project.today.activities.editorActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.AttachmentUtils;
import com.mobila.project.today.views.adapters.FileHolderAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static android.app.Activity.RESULT_OK;

class EditorAttachmentControl {
    private AppCompatActivity context;
    private NoteMock note;
    private FileHolderAdapter adapter;

    private final int REQUEST_TAKE_PHOTO = 1;
    private final int REQUEST_FILE_OPEN = 2;

    private String currentImagePath;

    EditorAttachmentControl(AppCompatActivity context, NoteMock note, FileHolderAdapter adapter) {
        this.context = context;
        this.note = note;
        this.adapter = adapter;
    }

    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensuring there is a camera on the device
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            //Create File for photo
            File photoFile = AttachmentUtils.createImageFile(context);
            this.currentImagePath=photoFile.getAbsolutePath();
            //check if file was created
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.mobila.project.today.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    void openFile() {
        Intent openFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openFileIntent.setType("*/*");
        openFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(openFileIntent, REQUEST_FILE_OPEN);
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && currentImagePath != null) {
                File file = new File(currentImagePath);
                this.note.addAttachment(file);
                Toast.makeText(context.getApplicationContext(),
                        "Image Saved", Toast.LENGTH_LONG).show();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
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
                    String filename = AttachmentUtils.getFileName(context, fileUri);
                    File destinationFile;
                    destinationFile =
                            new File(context.getExternalFilesDir(
                                    Environment.DIRECTORY_DOCUMENTS), filename);
                    try {
                        if (sourceFile != null) {
                            Files.copy(sourceFile.toPath(), destinationFile.toPath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context.getApplicationContext(),
                            "File Saved", Toast.LENGTH_LONG).show();
                    this.note.addAttachment(destinationFile);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else Toast.makeText(context.getApplicationContext(),
                        "File was lost", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nothing was saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}

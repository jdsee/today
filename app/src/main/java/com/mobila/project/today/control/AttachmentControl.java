package com.mobila.project.today.control;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.control.utils.AttachmentUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AttachmentControl {

    private Context context;
    private Lecture lecture;

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_FILE_OPEN = 2;

    private String currentImagePath;

    public AttachmentControl(Context context,
                             Lecture lecture) {
        this.context = context;
        this.lecture = lecture;
    }

    /**
     * Method for taking a picture by opening a camera app
     */
    public Intent getTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensuring there is a camera on the device
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            //Create File for photo
            File photoFile = AttachmentUtils.createImageFile(context);
            this.currentImagePath = photoFile.getAbsolutePath();
            //check if file was created
            Uri photoURI = FileProvider.getUriForFile(context,
                    "com.mobila.project.today.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            return takePictureIntent;
        }
        throw new NullPointerException();
    }

    /**
     * Method for importing a file by opening a file explorer app
     */
    public Intent getOpenFileIntent() {
        Intent openFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openFileIntent.setType("*/*");
        openFileIntent = Intent.createChooser(openFileIntent, "Choose a FIle");
        return openFileIntent;
    }

    /**
     * Method that saves the files that were returned by a intent
     *
     * @param requestCode the Code of the request
     * @param resultCode  the confirmation if the request was successful
     * @param data        the optional data of the intent-result
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && currentImagePath != null) {
                File file = new File(currentImagePath);
                Uri fileUri = Uri.fromFile(file);
                String fileName = AttachmentUtils.getFileName(context, fileUri);

                this.lecture.addAttachment(new Attachment(fileName, fileUri));

                Toast.makeText(context.getApplicationContext(),
                        "Image Saved", Toast.LENGTH_LONG).show();

                this.currentImagePath = null;
            } else if (requestCode == REQUEST_FILE_OPEN && data != null) {
                try {
                    Uri fileUri = data.getData();
                    InputStream fileInputStream = context.getContentResolver().openInputStream(fileUri);

                    String destinationFileName = AttachmentUtils.getFileName(context, fileUri);
                    File destinationFile = new File(context.getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS), destinationFileName);

                    AttachmentUtils.copy(fileInputStream, destinationFile);

                    Uri destinationUri = Uri.fromFile(destinationFile);
                    this.lecture.addAttachment(new Attachment(destinationFileName, destinationUri));
                    Toast.makeText(context.getApplicationContext(),
                            "File Saved", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nothing was saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}
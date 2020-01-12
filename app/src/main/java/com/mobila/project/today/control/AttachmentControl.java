package com.mobila.project.today.control;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.mobila.project.today.control.utils.FileUtils;
import com.mobila.project.today.model.dataProviding.dataAccess.DataKeyNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AttachmentControl {
    private static final String TAG = AttachmentControl.class.getName();

    private Context context;

    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_FILE_OPEN = 2;

    private String currentImagePath;

    public AttachmentControl(Context context) {
        this.context = context;
    }

    /**
     * Method for taking a picture by opening a camera app
     */
    public Intent getTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensuring there is a camera on the device
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            //Create File for photo
            File photoFile = FileUtils.createImageFile(context);
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
    public Uri onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && currentImagePath != null) {
                File file = new File(currentImagePath);
                //Uri fileUri = Uri.fromFile(file);
                Uri fileUri = FileProvider.getUriForFile(context,
                        context.getApplicationContext().getPackageName() + ".fileprovider", file);

                Toast.makeText(context.getApplicationContext(), "Image Saved", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Image persisted on internal storage. not added to database yet");
                this.currentImagePath = null;

                return fileUri;
            } else if (requestCode == REQUEST_FILE_OPEN && data != null) {
                try {
                    Uri fileUri = data.getData();
                    File destinationFile;
                    try (InputStream fileInputStream =context.getContentResolver().openInputStream(Objects.requireNonNull(fileUri))) {

                        String destinationFileName = FileUtils.getFileName(context, fileUri);
                        destinationFile = new File(context.getExternalFilesDir(
                                Environment.DIRECTORY_DOCUMENTS), destinationFileName);

                        FileUtils.copy(fileInputStream, destinationFile);
                    }

                    Uri destinationUri = FileProvider.getUriForFile(context,
                            context.getApplicationContext().getPackageName() + ".fileprovider", destinationFile);
                    //Uri destinationUri = Uri.fromFile(destinationFile);

                    Toast.makeText(this.context.getApplicationContext(), "File Saved", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Image persisted on internal storage. not added to database yet");

                    return destinationUri;
                } catch (DataKeyNotFoundException e) {
                    e.printStackTrace();
                    Log.e(TAG, "uri could not been added to database", e);
                    throw e;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e(TAG, "file could not been found", e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(context.getApplicationContext(),
                    "Nothing was saved", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
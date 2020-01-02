package today.control;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import today.model.Attachment;
import today.model.Lecture;
import today.control.utils.AttachmentUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
                    "today.fileprovider", photoFile);
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
        openFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
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
                this.lecture.addAttachment(new Attachment(file));
                Toast.makeText(context.getApplicationContext(),
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
                    this.lecture.addAttachment(new Attachment(destinationFile));
                } else Toast.makeText(context.getApplicationContext(),
                        "File was lost", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "Nothing was saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}
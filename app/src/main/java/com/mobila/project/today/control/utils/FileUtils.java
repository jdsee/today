package com.mobila.project.today.control.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public interface FileUtils {
    String TAG = FileUtils.class.getSimpleName();

    /**
     * Method for obtaining the Mime-Type of a file
     *
     * @param context The context from where the method gets called from
     * @param uri     the uri of the file in question
     * @return the Mime-Type of the file
     */
    static String getMimeType(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();

        Log.d(TAG, "FILE URI: " + uri);

        return cR.getType(uri);
    }


    /**
     * Method for obtaining the name of a file
     *
     * @param context The context from where the method gets called from
     * @param uri     the Uri pf the file in question
     * @return the name of the file
     */
    static String getFileName(Context context, Uri uri) {
        String result = null;
        if (Objects.requireNonNull(uri.getScheme()).equals("content")) {
            try (Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null)) {
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

    static String getFileNameWOExtension(Context context, Uri uri){
        String fileNameWExtension = getFileName(context, uri);
        return fileNameWExtension.substring(0, fileNameWExtension.lastIndexOf('.'));
    }

    /**
     * Method for receiving a symbolic icon for a file-type
     *
     * @param context  The context from where the method gets called from
     * @param mimeType the Mime-Type of the file in question
     * @return a Drawable containing a symbolic icon
     */
    static Drawable getDrawable(Context context, String mimeType) {
        if (mimeType != null)
            switch (mimeType) {
                case "application/pdf":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_pdf);
                case "image/jpeg":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_jpg);
                case "audio/mpeg":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_mp3);
                case "application/zip":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_zip);
                case "text/plain":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_txt);
                case "application/msword":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_doc);
                case "application/x-msdos-program":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_exe);
                case "application/x-flac":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_flac);
                case "image/gif":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_gif);
                case "text/html":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_html);
                case "application/x-iso9660-image":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_iso);
                case "application/x-javascript":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_js);
                case "video/mp4":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_mp4);
                case "application/vnd.ms-powerpoint":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_ppt);
                case "image/x-photoshop":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_psd);
                case "application/rar":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_rar);
                case "image/svg+xml":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_svg);
                case "audio/x-wav":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_wav);
                case "audio/x-ms-wma":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_wma);
                case "application/xml":
                    return ContextCompat.getDrawable(context, R.drawable.file_format_xml);
            }
        return ContextCompat.getDrawable(context, R.drawable.file_format_unknown);
    }

    /**
     * Method for receiving a symbolic icon for a file
     *
     * @param context The context from where the method gets called from
     * @param uri     the uri of the file for which the icon is searched for
     * @return a symbolic icon
     */
    static Drawable getDrawable(Context context, Uri uri) {
        String mimeType = getMimeType(context, uri);
        return getDrawable(context, mimeType);
    }

    /**
     * Method for creating a image file (in which an image can be copied into)
     *
     * @param context The context from where the method gets called from
     * @return an file with the name IMAGE_ + (current date) + (current time).jpg
     */
    static File createImageFile(Context context) {
        //File creation
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat(
                context.getString(R.string.date_format)).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, imageFileName + ".jpg");
    }

    static File createDataFile(Context context, String name) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), name);
    }

    /**
     * Method for opening a file
     *
     * @param uri the the uri of the file that should be opened
     */
    static void openFile(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, getMimeType(context, uri));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "There is no Application to open files of this type.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "File could not be opened");
        }
    }

    static void copy(InputStream src, File dst) throws IOException {
        try (InputStream in = src) {
            try (OutputStream out  = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}

package today.control.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;

import today.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public interface AttachmentUtils {

    /**
     * Method for obtaining the name of a file
     * @param context The context from where the method gets called from
     * @param uri the Uri pf the file in question
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

    /**
     * Method for obtaining the Mime-Type of a file
     * @param context The context from where the method gets called from
     * @param file the file in question
     * @return the Mime-Type of the file
     */
    static String getMimeType(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        String mimeType;
        if (Objects.requireNonNull(uri.getScheme()).equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    /**
     * Method for receiving a symbolic icon for a file-type
     * @param context The context from where the method gets called from
     * @param mimeType the Mime-Type of the file in question
     * @return a Drawable containing a symbolic icon
     */
    static Drawable getDrawable(Context context, String mimeType) {
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
            default:
                return ContextCompat.getDrawable(context, R.drawable.file_format_unknown);
        }
    }

    /**
     * Method for receiving a symbolic icon for a file
     * @param context The context from where the method gets called from
     * @param file the file for which the icon is searched for
     * @return a symbolic icon
     */
    static Drawable getDrawable(Context context, File file) {
        String mimeType = getMimeType(context, file);
        return getDrawable(context, mimeType);
    }

    /**
     * Method for creating a image file (in which an image can be copied into)
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

    /**
     * Method for opening a file
     * @param file the file that should be opened
     */
    static void openFile(File file){
        //TODO after Establishing SQLite Database
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "*/*");
//        startActivity(intent);
    }

}

package com.mobila.project.today.utils;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;

import java.io.File;
import java.util.Objects;

public class AttachmentUtils {
    private AttachmentUtils(){}

    public static String getMimeType(AppCompatActivity context, File file) {
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

    public static Drawable getDrawable(AppCompatActivity context, String mimeType) {
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
            default:
                return ContextCompat.getDrawable(context, R.drawable.file_format_unknown);
        }
    }

    public static Drawable getDrawable(AppCompatActivity context, File file) {
        String mimeType = getMimeType(context, file);
        return getDrawable(context, mimeType);
    }
}

package com.mobila.project.today.control;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShareContentManager {

    private static final String TAG = ShareContentManager.class.getSimpleName();
    private final Context context;

    public ShareContentManager(Context context) {
        this.context = context;
    }

    public void sendSpannable(Spannable spannable, String fileName) {
        this.testFileCreation();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

        File shareFile = this.createFileFromSpannable(spannable, fileName);
        Uri uriShareFile = FileProvider.getUriForFile(this.context,
                this.context.getPackageName() + ".fileprovider", shareFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriShareFile);
        shareIntent.setType("today/*");
        context.startActivity(Intent.createChooser(shareIntent, "Sending: " + fileName));
    }

    private File createFileFromSpannable(Spannable spannable, String fileName) {
        String spanAsHtml = Html.toHtml(spannable, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);

        File filePath = new File(this.context.getFilesDir(), "shared");
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File shareFile = null;
        try {
            shareFile = new File(filePath, fileName + ".today");
            FileWriter writer = new FileWriter(shareFile);
            writer.write(spanAsHtml);
            writer.flush();
            writer.close();
            Toast.makeText(this.context, "Sending your note", Toast.LENGTH_LONG).show();
            Log.d(TAG, "created new file: " + shareFile.getAbsolutePath() + ", exists: " + shareFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shareFile;
    }

    private void testFileCreation() {
        File filePath = new File(this.context.getFilesDir(), "shared");
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        try {
            File file = new File(filePath, "sample");
            FileWriter writer = new FileWriter(file);
            writer.append("ciao");
            writer.flush();
            writer.close();
            Toast.makeText(this.context, "Send your note", Toast.LENGTH_LONG).show();
            Log.d(TAG, "created new file: " + file.getAbsolutePath() + ", exists: " + file.exists());
        } catch (Exception e) {
        }
    }
}
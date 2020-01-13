package com.mobila.project.today.control;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.control.utils.FileUtils;
import com.mobila.project.today.model.Note;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class ShareContentManager {

    private static final String TAG = ShareContentManager.class.getSimpleName();
    private final Context context;

    public ShareContentManager(Context context) {
        this.context = context;
    }

    public void sendSpannable(Spannable spannable, String fileName) {
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

        File filePath = this.getSharedFilePath();
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

    private File getSharedFilePath() {
        File filePath = new File(this.context.getFilesDir(), "shared");
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        return filePath;
    }

    public void createPdfFromContentView(View content, String title){
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),content.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        content.draw(page.getCanvas());
        document.finishPage(page);


        try {
            File filePath = this.getSharedFilePath();
            File pdfFile = new File(filePath, title + ".pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);
            document.writeTo(fos);
            document.close();
            fos.close();

            Uri fileUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".fileprovider", pdfFile);

            FileUtils.openFile(this.context, fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if(firstLine){
                sb.append(line);
                firstLine = false;
            } else {
                sb.append("\n").append(line);
            }
        }
        reader.close();
        return sb.toString();
    }

    public Note getNoteFromIntent(Intent intent) {
        Note receivedNote = new Note();

        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && type != null) {
            Uri uri = intent.getData();

            InputStream fileInputStream;
            try {
                assert uri != null;
                fileInputStream = this.context.getContentResolver().openInputStream(uri);
                String fileString = ShareContentManager.convertStreamToString(fileInputStream);
                Spannable spannable = new SpannableString(Html.fromHtml(fileString, Html.FROM_HTML_MODE_LEGACY));
                receivedNote = new Note();
                receivedNote.setTitle(FileUtils.getFileNameWOExtension(this.context, uri));
                receivedNote.setContent(spannable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return receivedNote;
    }
}
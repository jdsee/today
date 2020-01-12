package com.mobila.project.today.activities.editorView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.AttachmentAdapter;
import com.mobila.project.today.activities.adapters.RecyclerViewButtonClickListener;
import com.mobila.project.today.control.AttachmentControl;
import com.mobila.project.today.control.utils.FileUtils;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.model.Lecture;

import java.util.Locale;

import static com.mobila.project.today.control.AttachmentControl.REQUEST_FILE_OPEN;
import static com.mobila.project.today.control.AttachmentControl.REQUEST_TAKE_PHOTO;

public class AttachmentView implements RecyclerViewButtonClickListener{
    private static final String TAG = AttachmentView.class.getName();

    private final Lecture lecture;
    private AppCompatActivity activity;
    private AttachmentAdapter attachmentAdapter;
    private AttachmentControl attachmentControl;
    private RecyclerView attachmentsRecyclerView;

    private TextView attachmentCounter;

    private CoordinatorLayout attachmentItems;

    AttachmentView(AppCompatActivity activity, Lecture lecture) {
        this.lecture = lecture;
        this.activity = activity;

        this.attachmentControl = new AttachmentControl(this.activity);
        this.attachmentsRecyclerView = this.activity.findViewById(R.id.recycler_view_files);
        this.attachmentAdapter = new AttachmentAdapter(
                this.activity,this, this.lecture.getAttachments());
        this.attachmentsRecyclerView.setAdapter(this.attachmentAdapter);
        this.attachmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this.activity));

        this.attachmentItems = this.activity.findViewById(R.id.attachment_items);
        this.attachmentCounter = this.activity.findViewById(R.id.attachment_counter);
        updateFileNumber();
    }

    private void addFileToAttachments(Uri uri) {
        if (uri != null) {
            String fileName = FileUtils.getFileName(this.activity, uri);
            this.lecture.addAttachment(new Attachment(fileName, uri));
            this.attachmentAdapter.notifyDataSetChanged();
            Log.d(TAG, "file behind uri has been added to attachments");
        }
    }

    void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri uri = attachmentControl.onActivityResult(requestCode, resultCode, data);
        this.addFileToAttachments(uri);
        this.updateFileNumber();
    }

    /**
     * Method for synchronising the file-counter with the saved attachmentControl
     */
    private void updateFileNumber() {
        int numberOfAttachments = this.lecture.getAttachments().size();
        attachmentCounter.setText(
                String.format(Locale.getDefault(), "%d", numberOfAttachments));
        if (numberOfAttachments == 0)
            closeAttachments();
        if (this.attachmentsRecyclerView.getVisibility() == View.GONE)
            displayFileNumber();
    }

    @Override
    public void onRecyclerViewButtonClicked(View view, int position) {
        lecture.removeAttachment(this.lecture.getAttachments().get(position));
        this.attachmentAdapter.notifyDataSetChanged();
        updateFileNumber();
    }

    /**
     * Method for closing the attachment-view
     */
    void closeAttachments() {
        this.attachmentsRecyclerView.setVisibility(View.GONE);
        this.attachmentCounter.setVisibility(View.VISIBLE);
        displayFileNumber();
    }

    /**
     * Method for opening the attachmentControl-view
     */
    private void openAttachments() {
        this.attachmentsRecyclerView.setVisibility(View.VISIBLE);
        this.attachmentCounter.setVisibility(View.GONE);
        hideFileNumber();
    }

    void onAttachmentsPressed(){
        if (this.attachmentsRecyclerView.getVisibility() == View.VISIBLE) {
            closeAttachments();
        } else if (lecture.getAttachments().size() != 0) {
            openAttachments();
        } else Toast.makeText(this.activity, "Your attachments go here", Toast.LENGTH_SHORT).show();
    }

    private void displayFileNumber(){
        if (this.lecture.getAttachments().size() == 0) {
            this.attachmentCounter.setVisibility(View.GONE);
        } else {
            this.attachmentCounter.setVisibility(View.VISIBLE);
        }
    }

    private void hideFileNumber() {
        this.activity.findViewById(R.id.attachment_counter).setVisibility(View.GONE);
    }

    void onTakePhotoPickerPressed(){
        if (this.activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            this.intentTakePhoto();
        } else {
            if (this.activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this.activity, "Camera permission is needed to add photos.", Toast.LENGTH_SHORT).show();
            }
            this.activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);
        }
    }

    private void intentTakePhoto() {
        try {
            this.activity.startActivityForResult(attachmentControl.getTakePictureIntent(), REQUEST_TAKE_PHOTO);
        } catch (Exception e) {
            Toast.makeText(this.activity, "Camera is not available.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Camera Intent could not proceed");
        }
    }

    void onFilePickerPressed(){
        this.activity.startActivityForResult(attachmentControl.getOpenFileIntent(), REQUEST_FILE_OPEN);
    }

    void showAttachmentMenu(){
        this.attachmentItems.setVisibility(View.VISIBLE);
    }

    void hideAttachmentMenu(){
        this.attachmentItems.setVisibility(View.GONE);
    }
}

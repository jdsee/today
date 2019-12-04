package com.mobila.project.today.activities.editorActivity;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobila.project.today.activities.adapters.FileHolderAdapter;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.AttachmentUtils;

import java.io.File;

public class EditorFileHolderAdapter extends FileHolderAdapter {

    private NoteMock note;
    private EditorActivity context;

    EditorFileHolderAdapter(EditorActivity context, NoteMock note) {
        super(context, note);
        this.note = note;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final File attachment = note.getAttachment(position);
        //Set name and icon of file
        holder.fileName.setText(attachment.getName());
        holder.fileImage.setImageDrawable(AttachmentUtils.getDrawable(context, attachment));

        holder.fileHolder.setOnClickListener(v -> {
            Toast.makeText(context, AttachmentUtils.getMimeType(context, attachment),
                    Toast.LENGTH_LONG).show();
            AttachmentUtils.openFile(note.getAttachment(position));
        });

        holder.button.setOnClickListener(v -> {
            note.removeAttachment(position);
            notifyDataSetChanged();
            context.updateFileNumber();
        });
    }

}

package com.mobila.project.today.activities.adapters;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobila.project.today.activities.UpdatableAppCompatActivity;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.AttachmentUtils;

import java.io.File;

public class UpdatableFileHolderAdapter extends FileHolderAdapter {

    private NoteMock note;
    private UpdatableAppCompatActivity context;

    public UpdatableFileHolderAdapter(UpdatableAppCompatActivity context, NoteMock note) {
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
            context.update();
        });
    }

}

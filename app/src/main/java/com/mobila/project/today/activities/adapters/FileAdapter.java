package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.AttachmentUtils;

import java.io.File;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private NoteMock note;
    private Context context;

    public FileAdapter(Context context, NoteMock note) {
        this.note = note;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_file, parent, false);
        return new ViewHolder(view);
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
        });
    }

    @Override
    public int getItemCount() {
        return note.getAttachmentCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView fileImage;
        public TextView fileName;
        public RelativeLayout fileHolder;
        public AppCompatImageButton button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImage = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.file_title);
            fileHolder = itemView.findViewById(R.id.parent_file_holder);
            button = itemView.findViewById(R.id.button_remove_file);
        }
    }
}

package com.mobila.project.today.presenter.activities.adapters;

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
import com.mobila.project.today.model.Lecture;
import com.mobila.project.today.control.utils.AttachmentUtils;

import java.io.File;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private Lecture lecture;
    private Context context;

    public FileAdapter(Context context, Lecture lecture) {
        this.lecture = lecture;
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
        final File attachment = lecture.getAttachments().get(position).getContent();
        //Set name and icon of file
        holder.fileName.setText(attachment.getName());
        holder.fileImage.setImageDrawable(AttachmentUtils.getDrawable(context, attachment));

        holder.fileHolder.setOnClickListener(v -> {
            Toast.makeText(context, AttachmentUtils.getMimeType(context, attachment),
                    Toast.LENGTH_LONG).show();
            AttachmentUtils.openFile(lecture.getAttachments().get(position).getContent());
        });

        holder.button.setOnClickListener(v -> {
            lecture.removeAttachment(lecture.getAttachments().get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return lecture.getAttachments().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fileImage;
        TextView fileName;
        RelativeLayout fileHolder;
        AppCompatImageButton button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImage = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.file_title);
            fileHolder = itemView.findViewById(R.id.parent_file_holder);
            button = itemView.findViewById(R.id.button_remove_file);
        }
    }
}

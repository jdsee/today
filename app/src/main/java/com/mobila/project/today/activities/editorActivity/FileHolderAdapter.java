package com.mobila.project.today.activities.editorActivity;

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

public class FileHolderAdapter extends RecyclerView.Adapter<FileHolderAdapter.ViewHolder> {

    private NoteMock note;
    private Context mContext;
    private EditorActivity activity;

    FileHolderAdapter(EditorActivity activity, Context mContext, NoteMock note) {
        this.note = note;
        this.mContext = mContext;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final File attachment = note.getAttachment(position);
        //Set name and icon of file
        holder.fileName.setText(attachment.getName());
        holder.fileImage.setImageDrawable(AttachmentUtils.getDrawable(activity, attachment));
        //setClickListener for List Item
        holder.fileHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, AttachmentUtils.getMimeType(activity, attachment),
                        Toast.LENGTH_LONG).show();
                activity.openFile(note.getAttachment(position));
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.removeAttachment(position);
                notifyDataSetChanged();
                if (note.getAttachmentCount()==0) activity.closeAttachments();
            }
        });
    }

    @Override
    public int getItemCount() {
        return note.getAttachmentCount();
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

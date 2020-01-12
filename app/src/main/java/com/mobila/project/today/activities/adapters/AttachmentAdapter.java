package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Attachment;
import com.mobila.project.today.control.utils.FileUtils;

import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {

    private Context context;
    private List<Attachment> attachments;
    private RecyclerViewButtonClickListener recyclerViewButtonClickListener;

    public AttachmentAdapter(Context context,
                             RecyclerViewButtonClickListener recyclerViewButtonClickListener,
                             List<Attachment> attachments) {
        this.attachments = attachments;
        this.context = context;
        this.recyclerViewButtonClickListener = recyclerViewButtonClickListener;
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
        final Attachment attachment = this.attachments.get(position);
        //Set name and icon of file
        holder.fileName.setText(attachment.getName());
        Drawable fileIcon = FileUtils.getDrawable(this.context, attachment.getContent());
        holder.fileImage.setImageDrawable(fileIcon);

        holder.fileHolder.setOnClickListener(v -> {
            Uri uri = this.attachments.get(position).getContent();
            FileUtils.openFile(context, uri);
        });

        holder.button.setOnClickListener(v -> {
            this.recyclerViewButtonClickListener.onRecyclerViewButtonClicked(v, position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return this.attachments.size();
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
            button = itemView.findViewById(R.id.button_remove_course);
        }
    }
}

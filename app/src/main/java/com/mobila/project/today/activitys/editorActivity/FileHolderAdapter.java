package com.mobila.project.today.activitys.editorActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.NoteMock;

import java.io.File;
import java.util.Objects;

public class FileHolderAdapter extends RecyclerView.Adapter<FileHolderAdapter.ViewHolder> {

    private NoteMock note;
    private Context mContext;
    private EditorActivity activity;

    FileHolderAdapter(EditorActivity activity, Context mContext,
                      NoteMock note) {
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
        holder.fileImage.setImageDrawable(getDrawable(getMimeType(attachment)));
        //setClickListener for List Item
        holder.fileHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, getMimeType(attachment), Toast.LENGTH_LONG).show();
                activity.openFile(note.getAttachment(position));
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImage = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.file_title);
            fileHolder = itemView.findViewById(R.id.parent_fileholder);
        }
    }

    private String getMimeType(File file) {
        Uri uri = Uri.fromFile(file);
        String mimeType;
        if (Objects.requireNonNull(uri.getScheme()).equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = mContext.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private Drawable getDrawable(String mimeType) {
        switch (mimeType) {
            case "application/pdf":
                return ContextCompat.getDrawable(mContext, R.drawable.file_format_pdf);
            case "image/jpeg":
                return ContextCompat.getDrawable(mContext, R.drawable.file_format_jpg);
            case "audio/mpeg":
                return ContextCompat.getDrawable(mContext, R.drawable.file_format_mp3);
            default:
                return ContextCompat.getDrawable(mContext, R.drawable.file_format_unknown);
        }
    }
}

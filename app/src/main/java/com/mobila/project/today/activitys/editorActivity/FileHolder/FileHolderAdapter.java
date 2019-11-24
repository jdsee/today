package com.mobila.project.today.activitys.editorActivity.FileHolder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activitys.editorActivity.EditorActivity;
import com.mobila.project.today.modelMock.NoteMock;

import java.util.ArrayList;

public class FileHolderAdapter extends RecyclerView.Adapter<FileHolderAdapter.ViewHolder>{

    private NoteMock note;
    private ArrayList<Drawable> mFileImages;
    private Context mContext;
    private EditorActivity activity;

    public FileHolderAdapter(EditorActivity activity, Context mContext,
                             NoteMock note,
                             ArrayList<Drawable> mFileImages) {
        this.note = note;
        this.mFileImages = mFileImages;
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
        holder.fileName.setText(note.getAttachment(position).getName());
        holder.fileImage.setImageDrawable(mFileImages.get(position));

        holder.fileHolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, note.getAttachment(position).getName(), Toast.LENGTH_LONG).show();
                activity.openFile(note.getAttachment(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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
}

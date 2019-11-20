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

import java.util.ArrayList;

public class FileHolderAdapter extends RecyclerView.Adapter<FileHolderAdapter.ViewHolder>{

    private ArrayList<String> mFileNames;
    private ArrayList<Drawable> mFileImages;
    private Context mContext;

    public FileHolderAdapter(Context mContext,
                             ArrayList<String> mFileNames,
                             ArrayList<Drawable> mFileImages) {
        this.mFileNames = mFileNames;
        this.mFileImages = mFileImages;
        this.mContext = mContext;
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
        holder.fileName.setText(mFileNames.get(position));
        holder.fileImage.setImageDrawable(mFileImages.get(position));

        holder.fileHolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mFileNames.get(position), Toast.LENGTH_LONG).show();
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

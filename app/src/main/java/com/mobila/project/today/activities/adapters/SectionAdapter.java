package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    public SectionAdapter(Context context, List<Section> sections) {
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSectionName;
        TextView tvLecturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvSectionName = itemView.findViewById(R.id.txt_section_name);
            this.tvLecturer = itemView.findViewById(R.id.txt_lecturer);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

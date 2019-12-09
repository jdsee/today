package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.ExampleCollection;
import com.mobila.project.today.model.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private final Context context;
    private List<Section> sections;

    public SectionAdapter(Context context, List<Section> sections) {
        this.sections = sections;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlSectionItem;
        RecyclerView rvLectures;
        TextView tvSectionName;
        TextView tvLecturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.rlSectionItem = itemView.findViewById(R.id.rl_section_item);
            this.tvSectionName = itemView.findViewById(R.id.txt_section_name);
            this.tvLecturer = itemView.findViewById(R.id.txt_lecturer);
            this.rvLectures = itemView.findViewById(R.id.rv_lecture_list);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSectionName.setText(this.sections.get(position).getTitle());
        holder.tvLecturer.setText(this.sections.get(position).getLecturer());

        RecyclerView rvLectures = holder.rvLectures;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        rvLectures.setLayoutManager(layoutManager);
        rvLectures.setAdapter(new LectureAdapter(ExampleCollection.getExampleLectures()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvLectures.getContext(),
                layoutManager.getOrientation());
        rvLectures.addItemDecoration(dividerItemDecoration);

        holder.rlSectionItem.setOnClickListener(v -> {
                    if (rvLectures.getVisibility() == View.GONE)
                        rvLectures.setVisibility(View.VISIBLE);
                    else
                        rvLectures.setVisibility(View.GONE);
                }
        );
    }

    @Override
    public int getItemCount() {
        return this.sections.size();
    }
}

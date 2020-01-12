package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    private final Context context;
    private List<Section> sections;

    private RecyclerViewButtonClickListener recyclerViewButtoneClickListener;
    public static final String BTN_ADD_LECTURE_TAG = "BTN_ADD_LECTURE";
    public static final String BTN_REMOVE_SECTION_TAG = "BTN_REMOVE_SECTION";

    public SectionAdapter(Context context, List<Section> sections,
                          RecyclerViewButtonClickListener addLectureClickListener) {
        this.sections = sections;
        this.context = context;
        this.recyclerViewButtoneClickListener = addLectureClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnRemoveSection;
        Button btnAddLecture;
        RelativeLayout rlSectionItem;
        RecyclerView rvLectures;
        TextView tvSectionName;
        TextView tvLecturer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.rlSectionItem = itemView.findViewById(R.id.rl_section_item);
            this.tvSectionName = itemView.findViewById(R.id.txt_section_name);
            this.tvLecturer = itemView.findViewById(R.id.txt_lecturer);
            this.rvLectures = itemView.findViewById(R.id.rv_lecture_list);
            this.btnAddLecture = itemView.findViewById(R.id.btn_add_lecture);
            this.btnRemoveSection = itemView.findViewById(R.id.btn_section_options);

            this.rlSectionItem.setOnClickListener(v -> {
                if (rvLectures.getVisibility() == View.GONE) {
                    rvLectures.setVisibility(View.VISIBLE);
                    btnAddLecture.setVisibility(View.VISIBLE);
                } else {
                    rvLectures.setVisibility(View.GONE);
                    btnAddLecture.setVisibility(View.GONE);
                }
            });
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
        Section section = this.sections.get(position);

        holder.tvSectionName.setText(section.getTitle());
        holder.tvLecturer.setText(section.getLecturer());

        this.setLectureListRecyclerView(holder, section);
        this.setAddLectureButtonClickListener(holder, position);
        this.setRemoveSectionButtonClickListener(holder, position);
    }

    private void setLectureListRecyclerView(ViewHolder holder, Section section) {
        RecyclerView rvLectures = holder.rvLectures;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        rvLectures.setLayoutManager(layoutManager);
        rvLectures.setAdapter(new LectureAdapter(this.context, section.getLectures()));
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(rvLectures.getContext(), layoutManager.getOrientation());
        rvLectures.addItemDecoration(dividerItemDecoration);
    }

    public void setAddLectureButtonClickListener(ViewHolder holder, int position) {
        holder.btnAddLecture.setTag(BTN_ADD_LECTURE_TAG);
        holder.btnAddLecture.setOnClickListener(view ->
                this.recyclerViewButtoneClickListener.onRecyclerViewButtonClicked(view, position)
        );
    }

    private void setRemoveSectionButtonClickListener(ViewHolder holder, int position) {
        holder.btnRemoveSection.setTag(BTN_REMOVE_SECTION_TAG);
        holder.btnRemoveSection.setOnClickListener(view ->
                this.recyclerViewButtoneClickListener.onRecyclerViewButtonClicked(view, position)
        );
    }

    @Override
    public int getItemCount() {
        return this.sections.size();
    }
}

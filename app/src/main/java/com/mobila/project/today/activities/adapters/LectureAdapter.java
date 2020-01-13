package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.editorView.EditorActivity;
import com.mobila.project.today.control.utils.DateUtils;
import com.mobila.project.today.model.Lecture;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {
    private static final String LECTURE_TITLE = "Lecture";
    private final Context context;
    private List<Lecture> lectures;

    LectureAdapter(Context context, List<Lecture> lectures) {
        this.context = context;
        this.lectures = lectures;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLectureTitle;
        TextView tvRoomNr;
        TextView tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvLectureTitle = itemView.findViewById(R.id.txt_lecture_title);
            this.tvRoomNr = itemView.findViewById(R.id.txt_room_nr);
            this.tvTime = itemView.findViewById(R.id.txt_lecture_time);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_lecture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lecture lecture = this.lectures.get(position);

        String lectureTitle = lecture.getNote().getTitle();
        lectureTitle = lectureTitle.isEmpty() ?
                String.format(Locale.getDefault(), "%s %d", LECTURE_TITLE, lecture.getLectureNr())
                : lectureTitle;
        holder.tvLectureTitle.setText(lectureTitle);

        holder.tvRoomNr.setText(lecture.getRoomNr());
        SimpleDateFormat date =
                new SimpleDateFormat(DateUtils.DAY_W_TIME_FORMAT, Locale.getDefault());
        holder.tvTime.setText(date.format(lecture.getDate()));

        holder.itemView.setOnClickListener(
                v -> this.openLectureInEditor(position));
    }

    @Override
    public int getItemCount() {
        return this.lectures.size();
    }

    private void openLectureInEditor(int position) {
        Intent intent = new Intent(this.context, EditorActivity.class);
        intent.putExtra(Lecture.INTENT_EXTRA_CODE, this.lectures.get(position));
        this.context.startActivity(intent);
    }
}

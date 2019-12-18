package com.mobila.project.today.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Lecture;

import java.util.List;
import java.util.Locale;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {
    private static final String LECTURE_TITLE = "Veranstaltung";
    private List<Lecture> lectures;

    LectureAdapter(List<Lecture> lectures) {
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
            holder.tvLectureTitle.setText(String.format(
                    Locale.getDefault(), "%s %d", LECTURE_TITLE, lecture.getLectureNr()));
            holder.tvRoomNr.setText(lecture.getRoomNr());
            holder.tvTime.setText(lecture.getDate().toString());
        }

        @Override
       public int getItemCount() {
            return this.lectures.size();
        }
}

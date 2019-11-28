package com.mobila.project.today.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.CourseMock;

import java.util.ArrayList;

public class CourseHolderAdapter extends RecyclerView.Adapter<CourseHolderAdapter.ViewHolder> {

    private ArrayList<CourseMock> courses;

    public CourseHolderAdapter(ArrayList<CourseMock> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.course.setText(courses.get(position).getName());
        holder.professor.setText(courses.get(position).getLecturer());
        holder.room.setText(courses.get(position).getRoom());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView course;
        TextView professor;
        TextView room;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course_name);
            professor = itemView.findViewById(R.id.professor_name);
            room = itemView.findViewById(R.id.room_name);
        }
    }
}

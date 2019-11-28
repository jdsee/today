package com.mobila.project.today.activities.dataStructureView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.CourseMock;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<CourseMock> courses;

    CourseAdapter(Context context, List<CourseMock> courses) {
        super();
        this.courses = courses;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        TextView tvLecturer;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvCourseName = itemView.findViewById(R.id.txt_course_name);
            tvLecturer = itemView.findViewById(R.id.txt_lecturer);

            itemView.setOnClickListener(view -> {});
        }
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(courses.get(position));

        holder.tvCourseName.setText(courses.get(position).getName());
        holder.tvLecturer.setText(courses.get(position).getLecturer());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

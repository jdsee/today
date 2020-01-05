package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.courseContentView.CourseContentActivity;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Semester;
import com.mobila.project.today.model.dataProviding.dataAccess.RootDataAccess;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Semester semester;
    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, Semester semester) {
        this.courses = semester.getCourses();
        this.context = context;
        this.semester = semester;
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
        holder.course.setText(courses.get(position).getTitle());
        holder.courseLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(this.context, CourseContentActivity.class);
                    intent.putExtra(Course.INTENT_EXTRA_CODE, courses.get(position));
                    this.context.startActivity(intent);
                }
        );
        holder.button.setOnClickListener(v -> {
            semester.removeCourse(courses.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView course;
        RelativeLayout courseLayout;
        AppCompatImageButton button;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course_name);
            courseLayout = itemView.findViewById(R.id.rl_course_holder);
            button = itemView.findViewById(R.id.button_remove_course);
        }
    }
}
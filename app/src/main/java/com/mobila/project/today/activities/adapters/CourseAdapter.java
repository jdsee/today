package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.courseContentView.CourseContentActivity;
import com.mobila.project.today.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, List<Course> courses) {
        this.courses = courses;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView course;
        RelativeLayout courseLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course_name);
            courseLayout = itemView.findViewById(R.id.rl_course_holder);
        }
    }
}

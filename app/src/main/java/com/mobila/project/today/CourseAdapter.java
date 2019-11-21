package com.mobila.project.today;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.modelMock.CourseMock;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<CourseMock> courses;
    private Context context;

    private static int currentPosition = 0;

    CourseAdapter(Context context, List<CourseMock> courses) {
        this.courses = courses;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtCourseName;
        TextView txtLecturer;
        LinearLayout linearLayout;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtCourseName = itemView.findViewById(R.id.txt_course_name);
            txtLecturer = itemView.findViewById(R.id.txt_lecturer);

            linearLayout = itemView.findViewById(R.id.linear_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
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
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(courses.get(position));
        holder.txtCourseName.setText(courses.get(position).getName());
        holder.txtLecturer.setText(courses.get(position).getLecturer());

        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.rv_expand_anim);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.txtCourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                CourseAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

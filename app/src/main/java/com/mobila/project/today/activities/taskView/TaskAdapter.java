package com.mobila.project.today.activities.taskView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Task;

import java.util.List;

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private final List<Task> tasks;
    private final Context context;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbTask;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cbTask = itemView.findViewById(R.id.cb_task_item);
            this.linearLayout = itemView.findViewById(R.id.ll_course_tasks);
        }
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);

        return new TaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(tasks.get(position));
        holder.cbTask.setText(tasks.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private final List<Task> tasks;
    private final Context context;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.taskText = itemView.findViewById(R.id.task_item_text_alt);
            this.linearLayout = itemView.findViewById(R.id.ll_course_tasks);
        }
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_alt, parent, false);

        return new TaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(tasks.get(position));
        holder.taskText.setText(tasks.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private static final String TAG = TaskAdapter.class.getSimpleName();

    private final List<Task> tasks;
    private final Context context;
    private List<Task> condemnedTasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.condemnedTasks = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_task_alt, parent, false);

        return new TaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(tasks.get(position));
        holder.taskText.setText(tasks.get(position).getContent());
        holder.checkBox.setOnCheckedChangeListener((view, isChecked) ->
            this.onCheckBoxClicked(holder, isChecked, position));
    }

    private void onCheckBoxClicked(TaskAdapter.ViewHolder holder, boolean isChecked, int position) {
        holder.checkBox.setChecked(isChecked);
        Task currentTask = this.tasks.get(position);
        if (isChecked) {
            holder.taskText.setPaintFlags(holder.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            this.condemnedTasks.add(currentTask);
            Log.d(TAG, "task has been checked (id: " + currentTask.getID() + ")");
        } else {
            holder.taskText.setPaintFlags(holder.taskText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            this.condemnedTasks.remove(currentTask);
            Log.d(TAG, "task has been unchecked (id: " + currentTask.getID() + ")");
        }
        Log.d(TAG, "condemnedTasks: " + condemnedTasks);
    }

    public void removeCheckedTasks(){
        for (Task task : condemnedTasks) {
            task.getCourse().removeTask(task);
            tasks.remove(task);
        }
        condemnedTasks.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        TextView taskText;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.taskText = itemView.findViewById(R.id.task_item_text_alt);
            this.linearLayout = itemView.findViewById(R.id.ll_course_tasks);
            this.checkBox = itemView.findViewById(R.id.cb_task_item);
        }
    }
}

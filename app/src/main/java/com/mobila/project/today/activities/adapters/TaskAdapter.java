package com.mobila.project.today.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.editorView.EditorActivity;
import com.mobila.project.today.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public static final String RV_BUTTON_CLICKED_TAG = "TASK_ADAPTER_BUTTON_CLICKED";

    private final List<Task> tasks;
    private final Context context;

    private RecyclerViewButtonClickListener rvButtonClickListener;

    public TaskAdapter(Context context, RecyclerViewButtonClickListener rvButtonClickListener, List<Task> tasks) {
        this.rvButtonClickListener = rvButtonClickListener;
        this.tasks = tasks;
        this.context = context;
    }

    public TaskAdapter(Context context, List<Task> tasks) {
        this(context, null, tasks);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        LinearLayout linearLayout;
        ImageButton btnAddTask;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.taskText = itemView.findViewById(R.id.task_item_text_alt);
            this.linearLayout = itemView.findViewById(R.id.ll_course_tasks);
            this.btnAddTask = itemView.findViewById(R.id.btn_add_task);
        }
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
        if (this.rvButtonClickListener != null)
            holder.btnAddTask.setOnClickListener(
                    v -> {
                        this.rvButtonClickListener.onRecyclerViewButtonClicked(v, position);
                        v.setTag(RV_BUTTON_CLICKED_TAG);
                    });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}

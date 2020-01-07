package com.mobila.project.today.control;

import android.widget.EditText;
import android.widget.ImageButton;

import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Task;

import java.util.List;

public class TaskController {

    public TaskController(EditText taskEnterField, ImageButton confirmationButton, List<Task> tasks, TaskAdapter taskAdapter){
        confirmationButton.setOnClickListener(v -> {
            tasks.add(new Task(taskEnterField.getText().toString()));
            taskAdapter.notifyDataSetChanged();
            taskEnterField.setText("");
        });
    }


}

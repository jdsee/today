package com.mobila.project.today.control;

import android.widget.EditText;
import android.widget.ImageButton;

import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

public class TaskController {

    public TaskController(EditText taskEnterField, ImageButton confirmationButton, Course course, TaskAdapter taskAdapter){
        confirmationButton.setOnClickListener(v -> {
            String enteredText = taskEnterField.getText().toString();
            if (!enteredText.isEmpty()) {
                course.addTask(new Task(enteredText));
                taskAdapter.notifyDataSetChanged();
                taskEnterField.setText("");
            }
        });
    }
}

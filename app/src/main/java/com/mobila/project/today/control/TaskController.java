package com.mobila.project.today.control;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Task;

public class TaskController {

    public TaskController(Context context, EditText taskEnterField, ImageButton confirmationButton, Course course, TaskAdapter taskAdapter){
        confirmationButton.setOnClickListener(v -> {
            String enteredText = taskEnterField.getText().toString();
            if (!enteredText.isEmpty()) {
                course.addTask(new Task(enteredText));
                taskAdapter.notifyDataSetChanged();
                taskEnterField.setText("");
            } else {
                Toast.makeText(context, "Enter text to add task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

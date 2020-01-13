package com.mobila.project.today.activities.editorView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.control.TaskController;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Lecture;

class TaskView {

    private CoordinatorLayout taskLayout;
    private CoordinatorLayout openTasksButton;
    private CoordinatorLayout closeTasksButton;

    private TaskAdapter taskAdapter;

    TaskView(AppCompatActivity activity, Lecture lecture){

        this.taskLayout = activity.findViewById(R.id.task_view);
        this.openTasksButton = activity.findViewById(R.id.tasks_open_button);
        this.closeTasksButton = activity.findViewById(R.id.close_tasks_button);

        Course course = lecture.getSection().getCourse();

        RecyclerView recyclerView = activity.findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.taskAdapter =
                new TaskAdapter(activity, course.getTasks());
        recyclerView.setAdapter(taskAdapter);
        EditText taskEnterField = activity.findViewById(R.id.edit_text_add_task);
        ImageButton confirmationButton = activity.findViewById(R.id.add_task_button);
        new TaskController(activity, taskEnterField, confirmationButton, course, taskAdapter);
    }

    void removeCheckedTasks(){
        this.taskAdapter.removeCheckedTasks();
    }

    void openTasks(){
        this.taskLayout.setVisibility(View.VISIBLE);
        this.openTasksButton.setVisibility(View.GONE);
        this.closeTasksButton.setVisibility(View.VISIBLE);
    }

    void closeTasks(){
        this.taskLayout.setVisibility(View.GONE);
        this.openTasksButton.setVisibility(View.VISIBLE);
        this.closeTasksButton.setVisibility(View.GONE);
    }
}

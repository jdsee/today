package com.mobila.project.today.activities.taskView;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;

import java.util.ArrayList;
import java.util.List;

public class CourseTasksActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tasks);

        recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tasks = new ArrayList<>();
        tasks.add(createExampleTask());

        adapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
    }

    private Task createExampleTask() {
        return new TaskImpl();
    }

    public void onAddTaskClicked(View view) {
        tasks.add(createExampleTask());
        this.adapter.notifyDataSetChanged();
    }
}

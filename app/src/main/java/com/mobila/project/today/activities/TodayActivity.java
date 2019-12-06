package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.activities.adapters.CourseHolderAdapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

public class TodayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Task> tasks= new ArrayList<>();

    CourseHolderAdapter courseHolderAdapter;
    List<CourseMock> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        getWindow().setNavigationBarColor(Color.WHITE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        courses.add(new CourseMock(1, "Mobile Anwendungen",
                "Prof. Dr.-Ing Schwotzer", "WHC 442"));
        courses.add(new CourseMock(1, "Software Engeneering",
                "Dr. Peter Habdichlieb", "TAC 832"));
        courses.add(new CourseMock(1, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));
        courses.add(new CourseMock(1, "Mobile Anwendungen",
                "Prof. Dr.-Ing Schwotzer", "WHC 442"));
        courses.add(new CourseMock(1, "Software Engeneering",
                "Dr. Peter Habdichlieb", "TAC 832"));
        courses.add(new CourseMock(1, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));
        courses.add(new CourseMock(1, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));

        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));
        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));
        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));
        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));
        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));


        initCourseView();
        initTaskView();
    }

    private void initTaskView() {
        recyclerView = findViewById(R.id.rv_course_tasks);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tasks.add(new TaskImpl(1,"", Date.valueOf("221100")));
        adapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
    }

    private void initCourseView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        this.courseHolderAdapter = new CourseHolderAdapter(courses);
        recyclerView.setAdapter(this.courseHolderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

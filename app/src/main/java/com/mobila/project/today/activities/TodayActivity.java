package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Task;
import com.mobila.project.today.model.implementations.TaskImpl;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.activities.adapters.CourseHolderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

public class TodayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Task> tasks = new ArrayList<>();

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
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

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

        tasks.add(new TaskImpl());
        tasks.add(new TaskImpl());
        tasks.add(new TaskImpl());
        tasks.add(new TaskImpl());
        tasks.add(new TaskImpl());


        initCourseView();
        initTaskView();
        setTimeDisplayed();
    }

    private void setTimeDisplayed() {
        TextView dateDisplay = findViewById(R.id.day_display);
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat day =
                new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat date =
                new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        builder.append(day.format(calender.getTime()));
        builder.append(System.getProperty("line.separator"));
        builder.append(date.format(calender.getTime()));
        dateDisplay.setText(builder);
    }

    private void initTaskView() {
        recyclerView = findViewById(R.id.rv_course_tasks);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tasks.add(new TaskImpl());
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

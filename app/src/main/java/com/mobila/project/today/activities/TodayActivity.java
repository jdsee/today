package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.views.adapters.CourseHolderAdapter;
import com.mobila.project.today.views.adapters.FileHolderAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class TodayActivity extends AppCompatActivity {

    CourseHolderAdapter courseHolderAdapter;
    ArrayList<CourseMock> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        courses.add(new CourseMock(1, "Mobile Anwendungen",
                "Prof. Dr.-Ing Schwotzer", "WHC 442"));
        courses.add(new CourseMock(1, "Software Engeneering",
                "Dr. Peter Habdichlieb", "TAC 832"));
        courses.add(new CourseMock(1, "Programmieren 3",
                "Eine Hubrich", "WHC 624"));

        initCourseView();
    }

    private void initCourseView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        this.courseHolderAdapter = new CourseHolderAdapter(courses);
        recyclerView.setAdapter(this.courseHolderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}

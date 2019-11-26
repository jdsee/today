package com.mobila.project.today.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mobila.project.today.R;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.modelMock.SectionMock;
import com.mobila.project.today.modelMock.TaskMock;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements CourseAdapter.ItemClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<CourseMock> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        recyclerView = findViewById(R.id.rv_course_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        courses = new ArrayList<>();
        courses.add(createExampleCourse(1, "Kurs 1", "Dr. Damage"));
        courses.add(createExampleCourse(2, "Kurs 2", "Prof. Punch"));
        courses.add(createExampleCourse(3, "Kurs 3", "Mr. Mad"));
        courses.add(createExampleCourse(4, "Kurs 4", "Snoop Dogg"));

        adapter = new CourseAdapter(this, courses);
        recyclerView.setAdapter(adapter);
    }

    private CourseMock createExampleCourse(int id, String name, String lecturer) {
        List<TaskMock> tasks = new ArrayList<>();
        List<SectionMock> sections = new ArrayList<>();
        return new CourseMock(id, name, lecturer, tasks, sections);
    }

    @Override
    public void onItemClicked(int index) {
        //TODO expand course here
    }
}

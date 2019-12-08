package com.mobila.project.today.activities.courseView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.adapters.CourseAdapter;
import com.mobila.project.today.modelMock.CourseMock;
import com.mobila.project.today.modelMock.SectionMock;
import com.mobila.project.today.modelMock.TaskMock;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
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

        adapter = new CourseAdapter(this, courses);
        recyclerView.setAdapter(adapter);
    }

    private CourseMock createExampleCourse(int id, String name, String lecturer) {
        List<TaskMock> tasks = new ArrayList<>();
        List<SectionMock> sections = new ArrayList<>();
        return new CourseMock(id, name, lecturer, tasks, sections);
    }

    public void onAddCourseClicked(View view) {
        courses.add(createExampleCourse(5, "mobila", "Prof. Schowtzer"));
        this.adapter.notifyDataSetChanged();
    }
}

package com.mobila.project.today.activities.courseContentView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.ExampleCollection;
import com.mobila.project.today.activities.adapters.SectionAdapter;
import com.mobila.project.today.activities.adapters.TaskAdapter;
import com.mobila.project.today.model.Course;
import com.mobila.project.today.model.Section;
import com.mobila.project.today.model.Task;

import java.util.List;

public class CourseContentActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;

    private Course course;
    private List<Task> tasks;
    private List<Section> sections;
    private TaskAdapter taskAdapter;
    private SectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        this.layoutManager = new LinearLayoutManager(this);

        this.course = this.getIntent().getParcelableExtra(Course.INTENT_EXTRA_CODE);

        ActionBar ab = this.getSupportActionBar();
        ab.setTitle(this.course.getTitle());

        /*TODO this.course.getTasks*/
        this.tasks = ExampleCollection.getExampleTasks();
        this.initTaskView();

        /*TODO this.course.getSections*/
        this.sections =

    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(this.layoutManager);
        this.taskAdapter = new TaskAdapter(this, this.tasks);
        recyclerView.setAdapter(this.taskAdapter);
    }

    private void initSectionView(){
        RecyclerView recyclerView = findViewById(R.id.rv_section_list);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        this.sectionAdapter = new SectionAdapter(this, this.sections);
        recyclerView.setAdapter(this.sectionAdapter);
    }

    public void onAddCourseClicked(View view) {
        this.adapter.notifyDataSetChanged();
    }
}
